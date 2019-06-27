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
import sv.org.siscop.caritas.entidades.Itemcotizacion;

@Stateless
public class ItemcotizacionFacade extends AbstractFacade<Itemcotizacion> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;

    public ItemcotizacionFacade() {
        super(Itemcotizacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    
    
    
}
