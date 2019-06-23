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
import sv.org.siscop.caritas.dao.UsuarioFacade;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.entidades.Usuario;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioUsuario implements ServicioUsuarioLocal {

    @EJB
    UsuarioFacade proveedorDao;
    @EJB
    PersonaFacade personaDao;

    @Override
    public void guardarUsuario(Persona persona, Usuario proveedor, boolean nuevo) {
        Persona person = null;
        if (persona.getId() != null) {
            personaDao.edit(persona);
        } else {
            personaDao.create(persona);
        }

        if (nuevo) {
            proveedor.setId(persona.getId());
            this.nuevoUsuario(proveedor);
        } else {
            this.actualizarUsuario(proveedor);
        }
    }

    @Override
    public void nuevoUsuario(Usuario c) {
        proveedorDao.create(c);
    }

    @Override
    public Usuario actualizarUsuario(Usuario c) {
        return proveedorDao.edit(c);
    }

    @Override
    public List<Usuario> buscarUsuarios(Map params) throws Exception {
        return proveedorDao.buscarUsuarios(params);
    }

}
