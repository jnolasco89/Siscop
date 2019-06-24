/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.PlantillaCotizacionFacade;
import sv.org.siscop.caritas.entidades.Plancotizacion;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioCotizacion implements ServicioCotizacionLocal {

    @EJB
    PlantillaCotizacionFacade planCotizacionDao;

    @Override
    public void nuevoPlancotizacion(Plancotizacion c) {
        planCotizacionDao.create(c);
    }

    @Override
    public Plancotizacion actualizarPlancotizacion(Plancotizacion c) {
        return planCotizacionDao.edit(c);
    }

    @Override
    public List<Plancotizacion> buscarPlancotizaciones(Map params) throws Exception {
        return planCotizacionDao.buscarPlancotizaciones(params);

    }

}
