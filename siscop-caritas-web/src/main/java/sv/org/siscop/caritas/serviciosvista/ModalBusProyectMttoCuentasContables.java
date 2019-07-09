/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.serviciosvista;

import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;

/**
 *
 * @author Leonardo Martinez
 */
public class ModalBusProyectMttoCuentasContables extends PestaniaBusquedaMttoCuentasContables {

    public ModalBusProyectMttoCuentasContables(ServicioProyectoLocal servProyecto, ServiciosCatalogoLocal servCatalogo) {
        super(servProyecto, servCatalogo);
    }

    public void resetModalBuscarProyecto(){
        super.resetPestaniaBusqueda();
    }
    
    public void resetModal(){
        super.resetPestaniaBusqueda();
    }
}
