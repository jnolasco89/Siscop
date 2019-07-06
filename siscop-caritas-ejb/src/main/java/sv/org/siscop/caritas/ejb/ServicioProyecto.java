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
import sv.org.siscop.caritas.dao.ProyectoFacade;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioProyecto implements ServicioProyectoLocal {

    @EJB
    ProyectoFacade proyectoDao;

    @Override
    public void nuevoProyecto(Proyecto c) {
        proyectoDao.create(c);
    }

    @Override
    public void getProyecto(Long id) {
        proyectoDao.find(id);
    }

    @Override
    public Proyecto actualizarProyecto(Proyecto c) {
        return proyectoDao.edit(c);
    }

    @Override
    public List<Proyecto> buscarProyectos(Map params) throws Exception {
        return proyectoDao.buscarProyectos(params);

    }

    @Override
    public List<Proyecto> buscarProyetosCriterial(Map params) {
        return proyectoDao.buscarProyetosCriterial(params);
    }

    @Override
    public List<Proyecto> getAllProyectos() {
        return proyectoDao.findAll();
    }

    @Override
    public List<Proyecto> getAllActividadesPorEstado(int estado) {
        return proyectoDao.getAllProyectosPorEstado(estado);
    }
}
