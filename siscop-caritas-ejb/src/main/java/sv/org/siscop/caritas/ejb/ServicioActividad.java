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
import sv.org.siscop.caritas.dao.ActividadFacade;
import sv.org.siscop.caritas.entidades.Actividad;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ServicioActividad implements ServicioActividadLocal {
    @EJB
    ActividadFacade actividadDao;
    
    @Override
    public List<Actividad> buscarActividadPorCualquierCampo(Map campos) {
        return actividadDao.buscarActividadPorCualquierCampo(campos);
    }

    @Override
    public List<Actividad> getAllActividades() {
        return actividadDao.findAll();
    }
    
}
