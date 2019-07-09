/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.serviciosvista;

import java.util.ArrayList;
import java.util.List;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.CuentaPK;

/**
 *
 * @author Leonardo Martinez
 */
public class ModalAgregarCuentas {
    private int modoCuenta;
    private int modoModal;//1 Para agregar cuentas principales, 2 para agregar subcuentas
    private boolean verCuentaPadre;
    private String tituloModal;
    private Cuenta cuentaPadre;
    private Cuenta cuentaActual;
    private List<Cuenta> cuentas;

    public ModalAgregarCuentas() {
        resetModal();
    }

    //================= GETTER Y SETTER ========================

    public Cuenta getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public String getTituloModal() {
        return tituloModal;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public Cuenta getCuentaPadre() {
        return cuentaPadre;
    }

    public void setCuentaPadre(Cuenta cuentaPadre) {
        this.cuentaPadre = cuentaPadre;
    }

    public boolean isVerCuentaPadre() {
        return verCuentaPadre;
    }

    public void setVerCuentaPadre(boolean verCuentaPadre) {
        this.verCuentaPadre = verCuentaPadre;
    }

    public int getModoCuenta() {
        return modoCuenta;
    }

    public void setModoCuenta(int modoCuenta) {
        this.modoCuenta = modoCuenta;
    }

    public int getModoModal() {
        return modoModal;
    }

    public void setModoModal(int modoModal) {
        this.modoModal = modoModal;
    }
    
    //================ METODOS DE SERVICIO ======================    
    public void agregarSubCuenta(){
        this.cuentaActual.setCuentaPadre(cuentaPadre);
        this.cuentas.add(cuentaActual);
        this.cuentaActual=new Cuenta();
        this.cuentaActual.setCuentaPK(new CuentaPK());
        this.modoCuenta=1;//1 Para agregar y 2 para editar
    }
    
    public void resetModal(){
        this.cuentaActual=new Cuenta();
        this.cuentaActual.setCuentaPK(new CuentaPK());
        this.cuentas=new ArrayList<>();
        this.cuentaPadre=new Cuenta();
        this.cuentaPadre.setCuentaPK(new CuentaPK());
        this.verCuentaPadre=true;
        this.modoCuenta=1;
        this.modoModal=1;
    }
    
    public void eliminarCuenta(int index){
        this.cuentas.remove(index);
    }
    
    public void editarCuenta(){
        this.cuentas.set(cuentaActual.getIndexEnList(), cuentaActual);
        this.cuentaActual=new Cuenta();
        this.cuentaActual.setCuentaPK(new CuentaPK());
        this.modoCuenta=1;
    }
}
