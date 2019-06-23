/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.entidades.Empleado;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioEmpleadoLocal {

    public void nuevoEmpleado(Empleado c);

    public Empleado actualizarEmpleado(Empleado c);

    public List<Empleado> buscarEmpleados(Map params) throws Exception;

    public void guardarEmpleado(Persona persona, Empleado empleado, boolean nuevo);

}
