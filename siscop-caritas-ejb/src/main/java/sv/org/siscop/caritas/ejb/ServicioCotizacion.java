/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.CotizacionFacade;
import sv.org.siscop.caritas.dao.ItemcotizacionFacade;
import sv.org.siscop.caritas.dao.PlantillaCotizacionFacade;
import sv.org.siscop.caritas.entidades.Cotizacion;
import sv.org.siscop.caritas.entidades.Itemcotizacion;
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.entidades.Planitem;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioCotizacion implements ServicioCotizacionLocal {

    @EJB
    PlantillaCotizacionFacade planCotizacionDao;
    @EJB
    CotizacionFacade cotizacionDao;
    @EJB
    ItemcotizacionFacade itemcotizacionDao;

    @Override
    public void nuevoPlancotizacion(Plancotizacion p) {
        planCotizacionDao.create(p);
    }

    @Override
    public Plancotizacion actualizarPlancotizacion(Plancotizacion p) {
        return planCotizacionDao.edit(p);
    }

    @Override
    public void nuevoPlanItem(Planitem item) throws Exception {
        try {
            planCotizacionDao.nuevoPlanItem(item);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCotizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void actualizarPlanItem(Planitem item) throws Exception {
        try {
            planCotizacionDao.actualizarPlanItem(item);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCotizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public List<Plancotizacion> buscarPlancotizaciones(Map params) throws Exception {
        return planCotizacionDao.buscarPlancotizaciones(params);
    }

    @Override
    public void nuevaCotizacion(Cotizacion c) {
        cotizacionDao.create(c);
    }

    @Override
    public void actualizarCotizacion(Cotizacion c) {
        cotizacionDao.edit(c);
    }

    @Override
    public void nuevoItemCotizacion(Itemcotizacion item) throws Exception {
        try {
            itemcotizacionDao.create(item);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCotizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void actualizarItemCotizacion(Itemcotizacion item) throws Exception {
        try {
            itemcotizacionDao.edit(item);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCotizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
