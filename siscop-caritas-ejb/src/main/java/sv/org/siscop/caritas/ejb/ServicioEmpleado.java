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
import sv.org.siscop.caritas.dao.PersonaFacade;
import sv.org.siscop.caritas.dao.EmpleadoFacade;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.entidades.Empleado;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioEmpleado implements ServicioEmpleadoLocal {

    @EJB
    EmpleadoFacade empleadoDao;
    @EJB
    PersonaFacade personaDao;

    @Override
    public void guardarEmpleado(Persona persona, Empleado proveedor, boolean nuevo) {
       
        if (persona.getId() != null) {
            personaDao.edit(persona);
        } else {
            personaDao.create(persona);
        }

        if (nuevo) {
            proveedor.setId(persona.getId());
            this.nuevoEmpleado(proveedor);
        } else {
            this.actualizarEmpleado(proveedor);
        }
    }

    @Override
    public void nuevoEmpleado(Empleado c) {
        empleadoDao.create(c);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado c) {
        return empleadoDao.edit(c);
    }

    @Override
    public List<Empleado> buscarEmpleados(Map params) throws Exception {
        return empleadoDao.buscarEmpleados(params);
    }

}
