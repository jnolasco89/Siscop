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
import sv.org.siscop.caritas.entidades.Partida;

@Stateless
public class PartidaFacade extends AbstractFacade<Partida> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;
    private final static Logger logger = Logger.getLogger(PartidaFacade.class.getName());

    public PartidaFacade() {
        super(Partida.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Partida> buscarPartidas(Map filtro) throws Exception {
        List<Partida> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(c) FROM Partida c ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("fechaIni") && filtro.containsKey("fechaFin")) {
                sql.append("AND c.fecha between :fechaIni and :fechaFin ");
            }

            if (filtro.containsKey("idproyecto")) {
                sql.append("AND c.proyecto.id = :idproyecto");
            }

            sql.append(" ORDER BY c.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("fechaIni") && filtro.containsKey("fechaFin")) {
                q.setParameter("fechaIni", filtro.get("fechaIni"));
                q.setParameter("fechaFin", filtro.get("fechaFin"));
            }

            if (filtro.containsKey("idproyecto")) {
                q.setParameter("idproyecto", filtro.get("idproyecto"));
            }

            lista = (List<Partida>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "Error en buscarPartidaes(): {0}", ex);
            throw new Exception(ex);
        }
    }

}
