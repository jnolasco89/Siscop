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
import sv.org.siscop.caritas.entidades.Empleado;

@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public EmpleadoFacade() {
        super(Empleado.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Empleado> buscarEmpleados(Map filtro) throws Exception {
        List<Empleado> lista = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT object(e) FROM  Empleado e ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("nombre1")) {
                sql.append("AND UPPER(e.persona.nombre1) like :nombre1 ");
            }
            if (filtro.containsKey("nombre2")) {
                sql.append("AND UPPER(e.persona.nombre2) like :nombre2 ");
            }
            if (filtro.containsKey("apellido1")) {
                sql.append("AND UPPER(e.persona.apellido1) like :apellido1 ");
            }
            if (filtro.containsKey("apellido2")) {
                sql.append("AND UPPER(e.persona.apellido2) like :apellido2 ");
            }
            if (filtro.containsKey("razsocial")) {
                sql.append("AND UPPER(u.persona.razsocial) like :razsocial ");
            }

            sql.append(" ORDER BY e.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("nombre1")) {
                q.setParameter("nombre1",
                        "%" + filtro.get("nombre1").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("nombre2")) {
                q.setParameter("nombre2",
                        "%" + filtro.get("nombre2").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("apellido1")) {
                q.setParameter("apellido1",
                        "%" + filtro.get("apellido1").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("apellido2")) {
                q.setParameter("apellido2",
                        "%" + filtro.get("apellido2").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("razsocial")) {
                q.setParameter("razsocial",
                        "%" + filtro.get("razsocial").toString().toUpperCase() + "%");
            }

            lista = (List<Empleado>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoFacade.class.getName()).log(Level.SEVERE, "Error en buscarEmpleados(): {0}", ex);
            throw new Exception(ex);
        }
    }
}
