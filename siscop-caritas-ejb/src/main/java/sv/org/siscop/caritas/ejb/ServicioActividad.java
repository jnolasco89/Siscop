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
import sv.org.siscop.caritas.dao.RecursoFacade;
import sv.org.siscop.caritas.entidades.Actividad;
import sv.org.siscop.caritas.entidades.Recurso;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ServicioActividad implements ServicioActividadLocal {
    
    @EJB
    ActividadFacade actividadDao;
    @EJB
    RecursoFacade recursoDao;
    
    @Override
    public void agregarActividad(Actividad a) {
        actividadDao.create(a);
    }
    
    @Override
    public List<Actividad> buscarActividadPorCualquierCampo(Map campos) {
        return actividadDao.buscarActividadPorCualquierCampo(campos);
    }
    
    @Override
    public void agregarRecursos(List<Recurso> recursos) {
        recursos.forEach((r) -> {
            recursoDao.create(r);
        });
//        for (Recurso r : recursos) {
//            recursoDao.create(r);
//        }
    }
    
    @Override
    public void editarActiviad(Actividad a){
       for(int i=0;i<a.getRecursoList().size();i++){
           if(a.getRecursoList().get(i).isEliminar()){
               recursoDao.remove(a.getRecursoList().get(i));
               a.getRecursoList().remove(i);
           }else{
               a.getRecursoList().get(i).setIdactividad(a);
               recursoDao.edit(a.getRecursoList().get(i));
           }
       }
       actividadDao.edit(a);
    }
    
    @Override
    public List<Actividad> buscarActividadPorCualquierCampoCriterial(String campo) {
        return actividadDao.buscarActividadPorCualquierCampoCriterial(campo);
    }
    
    @Override
    public List<Actividad> getAllActividades() {
        return actividadDao.findAll();
    }
    
    @Override
    public List<Actividad> getAllActividadesPorEstado(int estado) {
        //21-Terminada
        //22-En ejecucion
        return actividadDao.getAllActividadesPorEstado(estado);
    }
}
