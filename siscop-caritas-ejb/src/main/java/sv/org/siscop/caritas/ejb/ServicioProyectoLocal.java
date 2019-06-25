/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioProyectoLocal {

    public void nuevoProyecto(Proyecto c);

    public Proyecto actualizarProyecto(Proyecto c);

    public List<Proyecto> buscarProyectos(Map params) throws Exception;

    public List<Proyecto> getAllProyectos();
    
    public List<Proyecto> getAllActividadesPorEstado(int estado);
}
