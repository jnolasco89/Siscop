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
import sv.org.siscop.caritas.dao.ProveedorFacade;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.entidades.Proveedor;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioProveedor implements ServicioProveedorLocal {

    @EJB
    ProveedorFacade proveedorDao;
    @EJB
    PersonaFacade personaDao;

    @Override
    public void guardarProveedor(Persona persona, Proveedor proveedor, boolean nuevo) {

        if (persona.getId() != null) {
            personaDao.edit(persona);
        } else {
            personaDao.create(persona);
        }

        if (nuevo) {
            proveedor.setId(persona.getId());
            this.nuevoProveedor(proveedor);
        } else {
            this.actualizarProveedor(proveedor);
        }
    }

    @Override
    public void nuevoProveedor(Proveedor c) {
        proveedorDao.create(c);
    }

    @Override
    public Proveedor actualizarProveedor(Proveedor c) {
        return proveedorDao.edit(c);
    }

    @Override
    public List<Proveedor> buscarProveedors(Map params) throws Exception {
        return proveedorDao.buscarProveedors(params);
    }

}
