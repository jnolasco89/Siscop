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
import sv.org.siscop.caritas.entidades.OrdenCompra;
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.entidades.Planitem;
import sv.org.siscop.caritas.entidades.Requisicion;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioOrdenCompraLocal {

    public void nuevaOrdenCompra(OrdenCompra o);

    public void actualizarOrdenCompra(OrdenCompra o);

}
