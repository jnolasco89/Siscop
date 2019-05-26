/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.org.siscop.caritas.entidades.ItemCatalogo;

/**
 *
 * @author Leonardo Martinez
 */
@Stateless
public class ItemsCatalogoFacade extends AbstractFacade<ItemCatalogo>{
   @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;
   
    public ItemsCatalogoFacade() {
        super(ItemCatalogo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
}
