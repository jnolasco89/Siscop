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
import sv.org.siscop.caritas.entidades.OrdenCompra;

@Stateless
public class OrdenCompraFacade extends AbstractFacade<OrdenCompra> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public OrdenCompraFacade() {
        super(OrdenCompra.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<OrdenCompra> buscarCotizaciones(Map filtro) throws Exception {
        List<OrdenCompra> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(c) FROM  OrdenCompra d ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("id")) {
                sql.append("AND d.id = :id ");
            }

            sql.append(" ORDER BY c.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("id")) {
                q.setParameter("id", filtro.get("id"));
            }

            lista = (List<OrdenCompra>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(CotizacionFacade.class.getName()).log(Level.SEVERE, "Error en buscarOrdenCompra(): {0}", ex);
            throw new Exception(ex);
        }
    }
    
}
