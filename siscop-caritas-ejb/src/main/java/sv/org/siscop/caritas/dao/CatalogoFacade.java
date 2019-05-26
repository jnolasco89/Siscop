/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.List;
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
    
    public List<Catalogo> findCatalogoByAnyField(String field){
        Query query=em.createQuery("SELECT c FROM Catalogo c WHERE UPPER(c.codigo) LIKE CONCAT('%',UPPER(:parametro),'%') OR UPPER(c.nombre) LIKE CONCAT('%',UPPER(:parametro),'%')");
        query.setParameter("parametro", field);
        List<Catalogo> catalogos= query.getResultList();
        return catalogos;
    }
}
