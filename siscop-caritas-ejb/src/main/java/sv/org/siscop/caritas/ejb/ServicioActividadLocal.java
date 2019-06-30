/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Actividad;
import sv.org.siscop.caritas.entidades.Recurso;

/**
 *
 * @author Nolasco
 */
@Local
public interface ServicioActividadLocal {
    public void agregarActividad(Actividad a);
    public void agregarRecursos(List<Recurso> recursos);
    public void editarActiviad(Actividad a);
    public List<Actividad> buscarActividadPorCualquierCampo(Map campos);
    public List<Actividad> buscarActividadPorCualquierCampoCriterial(String campo);
    public List<Actividad> getAllActividades();
    public List<Actividad> getAllActividadesPorEstado(int estado);
}
