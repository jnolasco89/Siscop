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
import sv.org.siscop.caritas.entidades.Persona;

@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public PersonaFacade() {
        super(Persona.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Persona> buscarPersonas(Map filtro) throws Exception {
        List<Persona> lista = new ArrayList();
        try {
            Integer codper = null;
            String dui = null;
            StringBuilder sql = new StringBuilder("SELECT object(a) FROM  Persona a ");
            sql.append("WHERE 1=1 ");

            if (filtro.containsKey("nombre1")) {
                sql.append("AND UPPER(a.nombre1) like :nombre1 ");
            }
            if (filtro.containsKey("nombre2")) {
                sql.append("AND UPPER(a.nombre2) like :nombre2 ");
            }
            if (filtro.containsKey("apellido1")) {
                sql.append("AND UPPER(a.apellido1) like :apellido1 ");
            }
            if (filtro.containsKey("apellido2")) {
                sql.append("AND UPPER(a.apellido2) like :apellido2 ");
            }

            sql.append(" ORDER BY a.id");

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

            lista = (List<Persona>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(PersonaFacade.class.getName()).log(Level.SEVERE, "Error en buscarPersonas(): {0}", ex);
            throw new Exception(ex);
        }
    }

    public List<Persona> buscarPersonasDoc(Map filtro) throws Exception {
        List<Persona> lista = new ArrayList();
        try {
            Integer codper = null;
            String dui = null;
            StringBuilder sql = new StringBuilder("SELECT object(a) FROM  Persona a , IN(a.personadocList) as v ");
            sql.append("WHERE 1=1 ");
            if (filtro.containsKey("nombre1")) {
                sql.append("AND UPPER(a.nombre1) like :nombre1 ");
            }
            if (filtro.containsKey("apellido1")) {
                sql.append("AND UPPER(a.apellido1) like :apellido1 ");
            }

            if (filtro.containsKey("dui")) {
                dui = filtro.get("dui").toString();
                sql.append(" AND v.numero like '").append(dui).append("'");
            }

            sql.append(" GROUP BY a.id ");
            sql.append(" ORDER BY a.id");

            Query q = em.createQuery(sql.toString());

            if (filtro.containsKey("nombre1")) {
                q.setParameter("nombre1",
                        "%" + filtro.get("nombre1").toString().toUpperCase() + "%");
            }
            if (filtro.containsKey("apellido1")) {
                q.setParameter("apellido1",
                        "%" + filtro.get("apellido1").toString().toUpperCase() + "%");
            }

            lista = (List<Persona>) q.getResultList();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(PersonaFacade.class.getName()).log(Level.SEVERE, "Error en buscarPersonas(): {0}", ex);
            throw new Exception(ex);
        }

    }

}
