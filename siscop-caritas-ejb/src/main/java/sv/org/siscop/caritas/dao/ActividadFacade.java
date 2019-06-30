/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sv.org.siscop.caritas.entidades.Actividad;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ActividadFacade extends AbstractFacade<Actividad> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public ActividadFacade() {
        super(Actividad.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List buscarActividadPorCualquierCampo(Map campos) {
        List<Actividad> actividades;

        //Se obtiene el criterialbuilde a partir del entitymanager
        CriteriaBuilder constructorCriterial = em.getCriteriaBuilder();
        CriteriaQuery<Actividad> consultaCriterial = constructorCriterial.createQuery(Actividad.class);
        //Representa el contenido del FROM, la tabla
        //en la cual se realizara la consulta
        //todo los elementos que conforman la consulta
        //se van colocando en el objeto CriteriaQuery "q"
        //en este caso se especifico como tabla de consulta
        //la entidad Actividad y se coloco en el objeot "q"
        Root<Actividad> tabla = consultaCriterial.from(Actividad.class);

        Predicate predProyecto, 
                  predNombreActividad, 
                  predFechaIni, 
                  predFechaFin,
                  predEstado;
        List<Predicate> predicados=new ArrayList<>();
        
        if(campos.get("proyecto")!=null){
          predProyecto=constructorCriterial.equal(tabla.get("idproyecto"), (Proyecto)campos.get("proyecto"));
          predicados.add(predProyecto);
        }
        
        if(campos.get("nombreActividad")!=null){
          predNombreActividad=constructorCriterial.like(constructorCriterial.upper(tabla.get("nombre")),"%"+campos.get("nombreActividad")+"%");
          predicados.add(predNombreActividad);
        }
        
        if(campos.get("fechaInicio")!=null && campos.get("fechaFin")!=null){
           predFechaIni=constructorCriterial.greaterThanOrEqualTo(tabla.get("fechainicio"), (Date)campos.get("fechaInicio"));
           predFechaFin=constructorCriterial.lessThanOrEqualTo(tabla.get("fechafin"), (Date)campos.get("fechaFin"));
           predicados.add(predFechaIni);
           predicados.add(predFechaFin);
        }else if(campos.get("fechaInicio")!=null && campos.get("fechaFin")==null){
           predFechaIni=constructorCriterial.equal(tabla.get("fechainicio"), (Date)campos.get("fechaInicio"));
           predicados.add(predFechaIni);
        }else if(campos.get("fechaInicio")==null && campos.get("fechaFin")!=null){
           predFechaFin=constructorCriterial.equal(tabla.get("fechafin"), (Date)campos.get("fechaFin"));
           predicados.add(predFechaFin);
        }
        
        if(campos.get("estado")!=null){
            ItemCatalogo it=(ItemCatalogo)campos.get("estado");
            predEstado=constructorCriterial.equal(tabla.get("estado"),it);
            predicados.add(predEstado);
        }
        
        consultaCriterial.select(tabla).where(predicados.toArray(new Predicate[]{}));

        //La CriteriaQuery es equivalente a una cadena JPQL
        //para mayor comodidad y evitar la conversion manual del
        //resultado generico a objetos especificos, se utiliza
        //un TypedQuery para obtener una respuesta con objetos
        //ya definidos
        TypedQuery<Actividad> query = em.createQuery(consultaCriterial);
        actividades = query.getResultList();

        return actividades;
    }

    public List buscarActividadPorCualquierCampoCriterial(String campo) {
        List<Actividad> actividades;

        //Se obtiene el criterialbuilde a partir del entitymanager
        CriteriaBuilder constructorCriterial = em.getCriteriaBuilder();
        CriteriaQuery<Actividad> consultaCriterial = constructorCriterial.createQuery(Actividad.class);
        //Representa el contenido del FROM, la tabla
        //en la cual se realizara la consulta
        //todo los elementos que conforman la consulta
        //se van colocandi en el objeto CriteriaQuery "q"
        //en este caso se especifico como tabla de consulta
        //la entidad Actividad y se coloco en el objeot "q"
        Root<Actividad> tabla = consultaCriterial.from(Actividad.class);

        Predicate predNombre, predDescripcion, predFechaIni, predFechaFin, predicadoFinal = null;

        Pattern patron = Pattern.compile("^[0-9]{1,2}\\-[0-9]{1,2}\\-[0-9]{4}$");
        Matcher encaja = patron.matcher(campo);
        if (encaja.find()) {
            try {
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                Date fecha=sdf.parse(campo);
                predFechaIni = constructorCriterial.equal(tabla.get("fechainicio"), fecha);
                predFechaFin = constructorCriterial.equal(tabla.get("fechafin"), fecha);
                predicadoFinal = constructorCriterial.or(predFechaIni, predFechaFin);
            } catch (ParseException ex) {
                Logger.getLogger(ActividadFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            predNombre = constructorCriterial.like(constructorCriterial.upper(tabla.get("nombre")), "%" + campo.toUpperCase() + "%");
            predDescripcion = constructorCriterial.like(constructorCriterial.upper(tabla.get("descripcion")), "%" + campo.toUpperCase() + "%");
            predicadoFinal = constructorCriterial.or(predNombre, predDescripcion);
        }

        consultaCriterial.select(tabla).where(predicadoFinal);

        //La CriteriaQuery es equivalente a una cadena JPQL
        //para mayor comodidad y evitar la conversion manual del
        //resultado generico a objetos especificos, se utiliza
        //un TypedQuery para obtener una respuesta con objetos
        //ya definidos
        TypedQuery<Actividad> query = em.createQuery(consultaCriterial);
        actividades = query.getResultList();

        return actividades;
    }

    public List<Actividad> getAllActividadesPorEstado(int estado) {
        //21-Terminada
        //22-En ejecucion
        Query q = em.createQuery("SELECT a FROM Actividad a WHERE a.estado= :estado");
        q.setParameter("estado", estado);
        List<Actividad> actividades = q.getResultList();

        return actividades;
    }
}
