/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.serviciosvista;

import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.CuentaPK;

/**
 *
 * @author Leonardo Martinez
 */
public class ModalEditarCuenta {
    private ServiciosCuentaLocal servCta;
    private Cuenta cuentaActual;

    public ModalEditarCuenta() {
    }

    public ModalEditarCuenta(ServiciosCuentaLocal servCta) {
        this.servCta=servCta;
    }

    public Cuenta getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    public void reset(){
        this.cuentaActual=new Cuenta();
        this.cuentaActual.setCuentaPK(new CuentaPK());
    }
    
}
