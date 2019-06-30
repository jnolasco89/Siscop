/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;

@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public ProyectoFacade() {
        super(Proyecto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Proyecto> buscarProyectos(Map filtro) throws Exception {
        List<Proyecto> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(p) FROM  Proyecto p ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("id")) {
                sql.append("AND p.id = :id ");
            }
            if (filtro.containsKey("nombre")) {
                sql.append("AND UPPER(p.nombre) like :nombre ");
            }
            if (filtro.containsKey("nombrecorto")) {
                sql.append("AND UPPER(p.nombreCorto) like :nombrecorto ");
            }
            if (filtro.containsKey("estado")) {
                sql.append("AND p.estado.id = :estado");
            }

            sql.append(" ORDER BY p.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("id")) {
                q.setParameter("id", filtro.get("id"));
            }
            if (filtro.containsKey("nombre")) {
                q.setParameter("nombre",
                        "%" + filtro.get("nombre").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("nombrecorto")) {
                q.setParameter("nombrecorto",
                        "%" + filtro.get("nombrecorto").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("estado")) {
                q.setParameter("estado", filtro.get("estado"));
            }
            lista = (List<Proyecto>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ProyectoFacade.class.getName()).log(Level.SEVERE, "Error en buscarProyectos(): {0}", ex);
            throw new Exception(ex);
        }
    }

    public List<Proyecto> buscarProyetosCriterial(Map filtro) {
        List<Proyecto> proyectos=new ArrayList<>();
        
        //Se obtiene el criterialbuilde a partir del entitymanager
        CriteriaBuilder constructorCriterial = em.getCriteriaBuilder();
        CriteriaQuery<Proyecto> consulta = constructorCriterial.createQuery(Proyecto.class);
        //Representa el contenido del FROM, la tabla
        //en la cual se realizara la consulta
        //todo los elementos que conforman la consulta
        //se van colocandi en el objeto CriteriaQuery "q"
        //en este caso se especifico como tabla de consulta
        //la entidad Actividad y se coloco en el objeot "q"
        Root<Proyecto> tabla = consulta.from(Proyecto.class);
        
        //Armando la consulta
        List<Predicate> predicados=new ArrayList<>();
        
        if(filtro.get("codigo")!=null){
          predicados.add(constructorCriterial.equal(tabla.get("codigo"), filtro.get("codigo")));
        }
        
        if(filtro.get("nombre")!=null){
            predicados.add(constructorCriterial.equal(tabla.get("nombre"), filtro.get("nombre")));
        }
        
        if(filtro.get("nombreCorto")!=null){
           predicados.add(constructorCriterial.equal(tabla.get("nombreCorto"), filtro.get("nombreCorto")));
        }
        
        if(filtro.get("fechaIni")!=null && filtro.get("fechaFin")==null){
            predicados.add(constructorCriterial.equal(tabla.get("fechaini"), filtro.get("fechaIni")));
        }else if(filtro.get("fechaIni")==null && filtro.get("fechaFin")!=null){
            predicados.add(constructorCriterial.equal(tabla.get("fechafin"), filtro.get("fechaFin")));
        }else if(filtro.get("fechaIni")!=null && filtro.get("fechaFin")!=null){
            Predicate pFini=constructorCriterial.greaterThanOrEqualTo(tabla.get("fechaini"), (Date)filtro.get("fechaIni"));
            predicados.add(pFini);
            Predicate pFfin=constructorCriterial.lessThanOrEqualTo(tabla.get("fechafin"), (Date)filtro.get("fechaFin"));
            predicados.add(pFfin);
        }
        
        if(filtro.get("estado")!=null){
            predicados.add(constructorCriterial.equal(tabla.get("estado"), (ItemCatalogo)filtro.get("estado")));
        }
        
        consulta.select(tabla).where(predicados.toArray(new Predicate[]{}));
        
        //La CriteriaQuery es equivalente a una cadena JPQL
        //para mayor comodidad y evitar la conversion manual del
        //resultado generico a objetos especificos, se utiliza
        //un TypedQuery para obtener una respuesta con objetos
        //ya definidos
        TypedQuery<Proyecto> query = em.createQuery(consulta);
        proyectos = query.getResultList();
        
        return proyectos;
    }

    public List<Proyecto> getAllProyectosPorEstado(int estado) {
        //21-Terminada
        //22-En ejecucion
        Query q = em.createQuery("SELECT p FROM Proyecto p WHERE p.estado= :estado");
        q.setParameter("estado", estado);
        List<Proyecto> proyectos = q.getResultList();

        return proyectos;
    }
}
