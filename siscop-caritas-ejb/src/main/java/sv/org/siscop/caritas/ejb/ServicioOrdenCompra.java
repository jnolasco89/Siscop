/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.OrdenCompraFacade;
import sv.org.siscop.caritas.entidades.OrdenCompra;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioOrdenCompra implements ServicioOrdenCompraLocal {

    @EJB
    OrdenCompraFacade ordenCompraDao;

   
    @Override
    public void nuevaOrdenCompra(OrdenCompra o) {
        ordenCompraDao.create(o);
    }

    @Override
    public void actualizarOrdenCompra(OrdenCompra o) {
        ordenCompraDao.edit(o);
    }

}
