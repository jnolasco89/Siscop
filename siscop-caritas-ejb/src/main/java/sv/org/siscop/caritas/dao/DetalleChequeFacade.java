/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.dao;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.org.siscop.caritas.entidades.Detallecheque;

@Stateless
public class DetalleChequeFacade extends AbstractFacade<Detallecheque> {

    @PersistenceContext(unitName = "siscop_pu")
    private EntityManager em;
    private final static Logger logger = Logger.getLogger(DetalleChequeFacade.class.getName());

    public DetalleChequeFacade() {
        super(Detallecheque.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
