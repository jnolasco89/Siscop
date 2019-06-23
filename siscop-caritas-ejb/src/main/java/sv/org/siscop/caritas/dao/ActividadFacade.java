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
import sv.org.siscop.caritas.entidades.Actividad;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ActividadFacade extends AbstractFacade<Actividad>{
    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public ActividadFacade() {
        super(Actividad.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    public List buscarActividadPorCualquierCampo(Map campos){
        List<Actividad> actividades=new ArrayList<>();
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a FROM Actividad a WHERE 1 = 1 ");
        if (campos.containsKey("id")) {
            sb.append("AND a.id = :id ");
        }
        if (campos.containsKey("nombre")) {
            sb.append("AND UPPER(a.nombre) LIKE CONCAT('%',UPPER(:nombre),'%') ");
        }
        Query query = em.createQuery(sb.toString());
        if (campos.containsKey("id")) {
            query.setParameter("id", campos.get("id"));
        }
        if (campos.containsKey("nombre")) {
            query.setParameter("nombre", campos.get("nombre"));
        }
        actividades = query.getResultList();
        
        return actividades;
    }
}
