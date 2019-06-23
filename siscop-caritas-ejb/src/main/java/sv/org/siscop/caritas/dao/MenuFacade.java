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
import sv.org.siscop.caritas.entidades.Menu;

@Stateless
public class MenuFacade extends AbstractFacade<Menu> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public MenuFacade() {
        super(Menu.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Menu> buscarMenus(Map filtro) throws Exception {
        List<Menu> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(m) FROM  Menu m ");
            sql.append("WHERE 1=1 ");
            if (filtro.containsKey("tipo")) {
                sql.append("AND m.tipo=:tipo ");
            }
            sql.append("ORDER BY m.id ");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("tipo")) {
                q.setParameter("tipo", filtro.get("tipo"));
            }
            lista = (List<Menu>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(MenuFacade.class.getName()).log(Level.SEVERE, "Error en buscarMenus(): {0}", ex);
            throw new Exception(ex);
        }
    }

}
