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

    public List<Catalogo> buscarCatalogoPorCualquierCampo(Map campos) {
        List<Catalogo> catalogos = new ArrayList<>();
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c FROM Catalogo c WHERE 1 = 1 ");
        if (campos.containsKey("id")) {
            sb.append("AND c.id = :id ");
        }
        if (campos.containsKey("nombre")) {
            sb.append("AND UPPER(c.nombre) LIKE CONCAT('%',UPPER(:nombre),'%') ");
        }
        Query query = em.createQuery(sb.toString());
        if (campos.containsKey("id")) {
            query.setParameter("id", campos.get("id"));
        }
        if (campos.containsKey("nombre")) {
            query.setParameter("nombre", campos.get("nombre"));
        }
        catalogos = query.getResultList();
        return catalogos;
    }

    @Override
    public Catalogo find(Object id) {
        return super.find(id);
    }

}
