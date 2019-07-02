/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import org.eclipse.persistence.sessions.Session;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Nolasco
 */
@Stateless
public class CuentaFacade extends AbstractFacade<Cuenta> {
    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public CuentaFacade() {
        super(Cuenta.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Cuenta> buscarCuentas(Map filtro){
        List<Cuenta> cuentas=new ArrayList<>();
        
        //Se obtiene el criterialbuilde a partir del entitymanager
        CriteriaBuilder constructor=em.getCriteriaBuilder();
        CriteriaQuery<Cuenta> consulta=constructor.createQuery(Cuenta.class);
        //Representa el contenido del FROM, la tabla
        //en la cual se realizara la consulta
        //todo los elementos que conforman la consulta
        //se van colocandi en el objeto CriteriaQuery "q"
        //en este caso se especifico como tabla de consulta
        //la entidad Actividad y se coloco en el objeot "q"
        Root<Cuenta> tabla=consulta.from(Cuenta.class);
        
        //Armando la consulta
        List<Predicate> predicados=new ArrayList<>();
        
        if(filtro.get("codigo")!=null){
            predicados.add(constructor.equal(tabla.get("codigo"), filtro.get("codigo")));
        }
        
        if(filtro.get("nombre")!=null){
            predicados.add(constructor.equal(tabla.get("nombre"), filtro.get("nombre")));
        }
        
        if(filtro.get("cuentaPadre")!=null){
            predicados.add(constructor.equal(tabla.get("nombre"), filtro.get("nombre")));
        }
        
        if(filtro.containsKey("cuentasPrincipales")){
           if((boolean)filtro.containsKey("cuentasPrincipales")){
               predicados.add(constructor.isNull(tabla.get("idctapadre")));
           }
        }
        
        if(filtro.get("proyecto")!=null){
            predicados.add(constructor.equal(tabla.get("idproyecto"), (Proyecto)filtro.get("proyecto")));
        }
        
        consulta.select(tabla).where(predicados.toArray(new Predicate[]{}));
        
        //La CriteriaQuery es equivalente a una cadena JPQL
        //para mayor comodidad y evitar la conversion manual del
        //resultado generico a objetos especificos, se utiliza
        //un TypedQuery para obtener una respuesta con objetos
        //ya definidos
        TypedQuery<Cuenta> query = em.createQuery(consulta);
        cuentas = query.getResultList();
        
        return cuentas;
    }
    
    public List<Cuenta> getCuentasPrincipales() {
        Query q = em.createQuery("SELECT c FROM Cuenta c WHERE c.codigoctapadre IS NULL ORDER BY c.codigo");
        List<Cuenta> cuentas = q.getResultList();
        return cuentas;
    }

    public void insertarDesdeArchivoPorNativeQuery(List<Cuenta> cuentas) throws NotSupportedException, SystemException {

        Query q = em.createNativeQuery("INSERT INTO cuenta (codigo,nombre,descripcion,codigoctapadre,estado) VALUES(?,?,?,?,?)");
        
        Session sesion=em.unwrap(Session.class);
//        utx.begin();
        
        for (Cuenta cuenta : cuentas) {
            q.setParameter(1, cuenta.getCodigo());
            q.setParameter(2, cuenta.getNombre());
            q.setParameter(3, cuenta.getDescripcion());
            if (cuenta.getIdctapadre() != null) {
                q.setParameter(4, cuenta.getIdctapadre().getCodigo());
            } else {
                q.setParameter(4, null);
            }

            q.setParameter(5, true);

            q.executeUpdate();
            
            em.flush();
        }

    }

    public void InsersionPorLotes(List<Cuenta> cuentas) {
        int batchSize = 30;

        // - - - - - - - - - - - - - - Hibernate/JPA Batch Insert example - - - - - - - - - - - -
        em.getTransaction().begin();

        for (int i = 0; i < cuentas.size(); i++) {
            em.persist(cuentas.get(i));

            if (i % batchSize == 0 && i > 0) {
                em.flush();
                em.clear();
            }
        }
        em.getTransaction().commit();

    }

    public List<Cuenta> paginacion(int inicio, int tamanio, Map<String, Object> filtros) {
        //Se crea una criteria query por medio del builder
        //como parametro se le envia la clase correspondiente
        //al resultado de la consulta.
        CriteriaQuery<Cuenta> criterialQuery = em.getCriteriaBuilder().createQuery(Cuenta.class);
        //Se definen las entidades que formaran parta de la consulta, es equivalente
        //a definir el FROM de una JPQL.
        Root<Cuenta> root = criterialQuery.from(Cuenta.class);

        //
        CriteriaQuery<Cuenta> queryArmada = criterialQuery.select(root);

        TypedQuery<Cuenta> query = em.createQuery(queryArmada);
        query.setFirstResult(inicio);
        query.setMaxResults(tamanio);

        List<Cuenta> cuentas = query.getResultList();

        return cuentas;
    }

}
