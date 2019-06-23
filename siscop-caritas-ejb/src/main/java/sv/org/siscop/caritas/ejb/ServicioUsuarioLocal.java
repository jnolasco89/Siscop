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
import sv.org.siscop.caritas.entidades.Usuario;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioUsuarioLocal {

    public void nuevoUsuario(Usuario c);

    public Usuario actualizarUsuario(Usuario c);

    public List<Usuario> buscarUsuarios(Map params) throws Exception;

    public void guardarUsuario(Persona persona, Usuario proveedor, boolean nuevo);

}
