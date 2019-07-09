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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.CuentaPK;
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
            predicados.add(constructor.equal(tabla.get("cuentaPK").get("codigo"), filtro.get("codigo")));
        }
        
        if(filtro.get("nombre")!=null){
            predicados.add(constructor.equal(tabla.get("nombre"), filtro.get("nombre")));
        }
        
        if(filtro.get("codigoCuentaPadre")!=null){
            predicados.add(constructor.equal(tabla.get("cuentaPadre").get("cuentaPK").get("codigo"), filtro.get("codigoCuentaPadre")));
        }
        
        if(filtro.containsKey("cuentasPrincipales")){
           if((boolean)filtro.containsKey("cuentasPrincipales")){
               Cuenta ctaPadre=new Cuenta(new CuentaPK(null, ((Proyecto)filtro.get("proyecto")).getId()));
               predicados.add(constructor.equal(tabla.get("cuentaPadre"), ctaPadre));
           }
        }
        
        if(filtro.get("proyecto")!=null){
            predicados.add(constructor.equal(tabla.get("cuentaPK").get("idproyecto"), ((Proyecto)filtro.get("proyecto")).getId()));
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
    

}
