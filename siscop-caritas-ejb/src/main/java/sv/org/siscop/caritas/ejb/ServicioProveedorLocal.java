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
import sv.org.siscop.caritas.entidades.Proveedor;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioProveedorLocal {

    public void nuevoProveedor(Proveedor c);

    public Proveedor actualizarProveedor(Proveedor c);

    public List<Proveedor> buscarProveedors(Map params) throws Exception;

    public void guardarProveedor(Persona persona, Proveedor proveedor, boolean nuevo);

}
