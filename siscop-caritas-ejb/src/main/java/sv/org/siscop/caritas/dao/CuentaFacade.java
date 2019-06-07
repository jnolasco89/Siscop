/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.org.siscop.caritas.entidades.Cuenta;

/**
 *
 * @author Nolasco
 */
@Stateless
public class CuentaFacade extends AbstractFacade<Cuenta> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public CuentaFacade() {
        super(Cuenta.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Cuenta> getCuentasPrincipales() {
        Query q = em.createQuery("SELECT c FROM Cuenta c WHERE c.codigoctapadre IS NULL");
        List<Cuenta> cuentas = q.getResultList();
        return cuentas;
    }

    public void InsersionPorLotes(List<Cuenta> cuentas) {
        int batchSize = 30;

        // - - - - - - - - - - - - - - Hibernate/JPA Batch Insert example - - - - - - - - - - - -
        em.getTransaction().begin();

        for (int i = 0; i < cuentas.size(); i++) {
            em.persist(cuentas.get(i));

            if (i % batchSize == 0 && i > 0) {
                em.flush();
                em.clear();
            }
        }
        em.getTransaction().commit();
    }

    public List<Cuenta> paginacion(int inicio, int tamanio, Map<String, Object> filtros) {
        //Se crea una criteria query por medio del builder
        //como parametro se le envia la clase correspondiente
        //al resultado de la consulta.
        CriteriaQuery<Cuenta> criterialQuery = em.getCriteriaBuilder().createQuery(Cuenta.class);
        //Se definen las entidades que formaran parta de la consulta, es equivalente
        //a definir el FROM de una JPQL.
        Root<Cuenta> root = criterialQuery.from(Cuenta.class);

        //
        CriteriaQuery<Cuenta> queryArmada = criterialQuery.select(root);

        TypedQuery<Cuenta> query = em.createQuery(queryArmada);
        query.setFirstResult(inicio);
        query.setMaxResults(tamanio);

        List<Cuenta> cuentas = query.getResultList();

        return cuentas;
    }

}
