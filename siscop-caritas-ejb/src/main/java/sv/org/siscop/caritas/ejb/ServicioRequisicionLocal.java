/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.ItemRequisicion;
import sv.org.siscop.caritas.entidades.Requisicion;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioRequisicionLocal {

    public void nuevaRequisicion(Requisicion r);

    public void actualizarRequisicion(Requisicion r);

    public List<Requisicion> buscarRequisiciones(Map params) throws Exception;
    
    public void nuevoItemRequisicion(ItemRequisicion i);

    public void actualizarItemRequisicion(ItemRequisicion i );
}
