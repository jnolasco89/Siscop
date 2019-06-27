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
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.entidades.Planitem;

@Stateless
public class PlantillaCotizacionFacade extends AbstractFacade<Plancotizacion> {

    private final static Logger logger = Logger.getLogger(PlantillaCotizacionFacade.class.getName());

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public PlantillaCotizacionFacade() {
        super(Plancotizacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Plancotizacion> buscarPlancotizaciones(Map filtro) throws Exception {
        List<Plancotizacion> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(p) FROM  Plancotizacion p ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("id")) {
                sql.append("AND p.id = :id ");
            }
            if (filtro.containsKey("idproyecto")) {
                sql.append("AND p.idproyecto =:idproyecto ");
            }
            if (filtro.containsKey("idestado")) {
                sql.append("AND p.actividad.estado.id =:idestado ");
            }
            if (filtro.containsKey("descripcion")) {
                sql.append("AND UPPER(p.descripcion) like :descripcion ");
            }

            sql.append(" ORDER BY p.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("id")) {
                q.setParameter("id", filtro.get("id"));
            }
            if (filtro.containsKey("idproyecto")) {
                q.setParameter("idproyecto", filtro.get("idproyecto"));
            }
            if (filtro.containsKey("idestado")) {
                q.setParameter("idestado", filtro.get("idestado"));
            }
            if (filtro.containsKey("descripcion")) {
                q.setParameter("descripcion",
                        "%" + filtro.get("descripcion").toString().toUpperCase() + "%");
            }
            lista = (List<Plancotizacion>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(PlantillaCotizacionFacade.class.getName()).log(Level.SEVERE, "Error en buscarPlancotizaciones(): {0}", ex);
            throw new Exception(ex);
        }
    }

    public void nuevoPlanItem(Planitem item) throws Exception {
        try {
            this.getEntityManager().persist(item);

        } catch (Exception ex) {

            logger.log(Level.SEVERE, "Error en nuevoPlanItem", ex);
            throw new Exception(ex);
        }
    }

}
