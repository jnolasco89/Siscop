/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
            lista = (List<Proyecto>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ProyectoFacade.class.getName()).log(Level.SEVERE, "Error en buscarProyectos(): {0}", ex);
            throw new Exception(ex);
        }
    }

}
