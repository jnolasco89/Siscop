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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.org.siscop.caritas.entidades.ItemRequisicion;
import sv.org.siscop.caritas.entidades.Requisicion;

@Stateless
public class ItemRequisicionFacade extends AbstractFacade<ItemRequisicion> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;
    private final static Logger logger = Logger.getLogger(ItemRequisicionFacade.class.getName());

    public ItemRequisicionFacade() {
        super(ItemRequisicion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Requisicion> buscarRequisiciones(Map filtro) throws Exception {
        List<Requisicion> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(r) FROM  ItemRequisicion r ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("id")) {
                sql.append("AND r.id = :id ");
            }

            sql.append(" ORDER BY r.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("id")) {
                q.setParameter("id", filtro.get("id"));
            }

            lista = (List<Requisicion>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "Error en buscarItemRequisiciones(): {0}", ex);
            throw new Exception(ex);
        }
    }

   
}
