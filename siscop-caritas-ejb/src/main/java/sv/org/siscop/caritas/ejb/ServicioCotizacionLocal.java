/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Plancotizacion;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioCotizacionLocal {

    public void nuevoPlancotizacion(Plancotizacion p);

    public Plancotizacion actualizarPlancotizacion(Plancotizacion p);

    public List<Plancotizacion> buscarPlancotizaciones(Map params) throws Exception;

}
