/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Cotizacion;
import sv.org.siscop.caritas.entidades.Itemcotizacion;
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.entidades.Planitem;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioCotizacionLocal {

    public void nuevoPlancotizacion(Plancotizacion p);

    public Plancotizacion actualizarPlancotizacion(Plancotizacion p);

    public List<Plancotizacion> buscarPlancotizaciones(Map params) throws Exception;

    public void nuevoPlanItem(Planitem item) throws Exception;

    public void nuevaCotizacion(Cotizacion c);

    public void actualizarCotizacion(Cotizacion c);

    public void actualizarItemCotizacion(Itemcotizacion item) throws Exception;

    public void nuevoItemCotizacion(Itemcotizacion item) throws Exception;

    public void actualizarPlanItem(Planitem item) throws Exception;

}
