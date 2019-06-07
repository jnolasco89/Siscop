/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.org.siscop.caritas.entidades.Catalogo;

/**
 *
 * @author Nolasco
 */
@Stateless
public class CatalogoFacade extends AbstractFacade<Catalogo> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public CatalogoFacade() {
        super(Catalogo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public List<Catalogo> findCatalogoByAnyField(Map params) {
        List<Catalogo> catalogos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c FROM Catalogo c WHERE 1 = 1 ");
        if (params.containsKey("codigo")) {
            sb.append("AND UPPER(c.codigo) = UPPER(:codigo) ");
        }
        if (params.containsKey("nombre")) {
            sb.append("AND UPPER(c.nombre) LIKE CONCAT('%',UPPER(:parametro),'%') ");
        }
        Query query = em.createQuery(sb.toString());
        if (params.containsKey("codigo")) {
            query.setParameter("codigo", params.get("codigo"));
        }
        if (params.containsKey("nombre")) {
            query.setParameter("nombre", params.get("nombre"));
        }
        catalogos = query.getResultList();
        return catalogos;
    }

    @Override
    public Catalogo find(Object id) {
        return super.find(id);
    }

}
