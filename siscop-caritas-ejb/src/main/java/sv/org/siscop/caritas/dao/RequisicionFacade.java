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
import sv.org.siscop.caritas.entidades.Requisicion;

@Stateless
public class RequisicionFacade extends AbstractFacade<Requisicion> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;
    private final static Logger logger = Logger.getLogger(RequisicionFacade.class.getName());

    public RequisicionFacade() {
        super(Requisicion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Requisicion> buscarRequisiciones(Map filtro) throws Exception {
        List<Requisicion> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(r) FROM  Requisicion r ");
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
            Logger.getLogger(CotizacionFacade.class.getName()).log(Level.SEVERE, "Error en buscarRequisiciones(): {0}", ex);
            throw new Exception(ex);
        }
    }

    public Long buscarMaxRequisicionNumero(Long idProyecto) throws Exception {

        try {
            StringBuilder sql = new StringBuilder("SELECT max(r.numero) FROM  Requisicion r ");
            sql.append("WHERE r.actividad.idproyecto.id = :idproyecto");

            Query q = em.createQuery(sql.toString());
            q.setParameter("idproyecto", idProyecto);

            Long numero = (Long) q.getSingleResult();
            if(numero==null){
                return 1L;
            }
            return numero;
        } catch (NoResultException ex) {
            return 0L;
        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "Error en buscarMaxRequisicionNumero(): {0}", ex);
            throw new Exception(ex);
        }
    }
}
