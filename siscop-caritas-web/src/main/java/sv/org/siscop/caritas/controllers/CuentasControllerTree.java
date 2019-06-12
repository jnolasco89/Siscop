/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.exceptions.ValidacionException;
import sv.org.siscop.caritas.validadores.ValidacionesCuenta;

/**
 *
 * @author Nolasco
 */
@Named(value = "cuentasControllerTree")
@ViewScoped
public class CuentasControllerTree implements Serializable {

    //Servicios
    @EJB
    private ServiciosCuentaLocal servCtas;
    
    private int tabActiva;
    
    private Cuenta cuentaActual;
    private Cuenta cuentaPadreActual;
    
    private TreeNode arbolCuentas;
    private TreeNode nodoActual;
    
    private ValidacionesCuenta validaciones;

    /**
     * Creates a new instance of CuentasControllerTree
     */
    public CuentasControllerTree() {
        
    }
    
    @PostConstruct
    public void init() {
        tabActiva = 0;
        cuentaActual = new Cuenta();
        cuentaPadreActual = new Cuenta();
        validaciones = new ValidacionesCuenta();
        
        List<Cuenta> cuentas = servCtas.getCuentasPadres();
        arbolCuentas = new DefaultTreeNode("Raiz", null);
        
        recorrerCuentas(cuentas, arbolCuentas);
        
    }
    //================ METODOS GET Y SET =================

    public TreeNode getArbolCuentas() {
        return arbolCuentas;
    }
    
    public void setArbolCuentas(TreeNode arbolCuentas) {
        this.arbolCuentas = arbolCuentas;
    }
    
    public TreeNode getNodoActual() {
        return nodoActual;
    }
    
    public void setNodoActual(TreeNode nodoActual) {
        this.nodoActual = nodoActual;
    }
    
    public Cuenta getCuentaActual() {
        return cuentaActual;
    }
    
    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }
    
    public Cuenta getCuentaPadreActual() {
        return cuentaPadreActual;
    }
    
    public void setCuentaPadreActual(Cuenta cuentaPadreActual) {
        this.cuentaPadreActual = cuentaPadreActual;
    }
    
    public int getTabActiva() {
        return tabActiva;
    }
    
    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }
    
    public ValidacionesCuenta getValidaciones() {
        return validaciones;
    }
    
    public void setValidaciones(ValidacionesCuenta validaciones) {
        this.validaciones = validaciones;
    }

    //================ METODOS DE FUNCIONALIDAD ============
//    public void validarCuenta() throws ValidacionException{
//        List<String> listaValidaciones = new ArrayList<>();
//        
//        if (cuentaActual.getCodigo().length() == 0) {
//            listaValidaciones.add("Codigo de cuenta requerido");
//        }
//
//        if (cuentaActual.getNombre().length() == 0) {
//            listaValidaciones.add("Nombre de cuenta requerido");
//        }
//        
//        if (!listaValidaciones.isEmpty()) {
//            throw new ValidacionException("Campo incorrecto", listaValidaciones);
//        }
//    }
    public void guardarCuenta() {
//        try {

//        cuentaActual.setCodigoctapadre(cuentaPadreActual);
//        cuentaActual.setCuentaList(new ArrayList<Cuenta>());
//
//        System.out.println("PDRE: "+cuentaActual.getCodigoctapadre());
//        System.out.println("CODIGO: " + cuentaActual.getCodigo());
//        System.out.println("NOMBRE: " + cuentaActual.getNombre());
//        servCtas.agregarCuenta(cuentaActual);
        Cuenta padre = new Cuenta();
        padre.setCodigoctapadre(null);
        padre.setCodigo("9");
        padre.setNombre("Prueba");
        padre.setDescripcion("Descripcion");
        padre.setCuentaList(new ArrayList<Cuenta>());
        servCtas.agregarCuenta(padre);
        
     
            if (cuentaPadreActual.getCodigo() == null) {
                cuentaActual.setCodigoctapadre(null);
            } else {
                cuentaActual.setCodigoctapadre(cuentaPadreActual);
            }
      
        cuentaActual.setCuentaList(new ArrayList<Cuenta>());
        servCtas.agregarCuenta(cuentaActual);
        
        FacesContext.getCurrentInstance().addMessage("msgGrowl",
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Operacion exitosa",
                        "Cuenta agregada"));
//        } catch (ValidacionException ex) {
//            System.out.println("LLEGA AL CATCH..........");
//            List<String> validaciones=ex.getMensajes();
//            for (String validacion : validaciones) {
//                FacesContext.getCurrentInstance().addMessage("msgGrowl", 
//                        new FacesMessage(
//                                FacesMessage.SEVERITY_WARN, 
//                                ex.getMessage(), 
//                                validacion));
//            }
//        }
    }
    
    public void cargarCuentaSeleccionada() {
        cuentaActual = (Cuenta) nodoActual.getData();
        cuentaPadreActual = cuentaActual.getCodigoctapadre();
        tabActiva = 1;
    }
    
    public void actualizarCuentaPadre() {
        cuentaPadreActual = (Cuenta) nodoActual.getData();
    }
    
    public void recorrerCuentas(List<Cuenta> cuentas, TreeNode padre) {
        for (Cuenta cuenta : cuentas) {
            TreeNode nodo = new DefaultTreeNode(cuenta, padre);
            
            if (cuenta.getCodigoctapadre() == null) {
                nodo.setExpanded(true);
            }
            
            if (cuenta.getCuentaList().size() > 0) {
                //Ordenando las subcuentas por codigo
                for (int i = 0; i < cuenta.getCuentaList().size() - 1; i++) {
                    
                    for (int j = 0; j < cuenta.getCuentaList().size() - 1; j++) {
                        int codigoPos1 = Integer.parseInt(cuenta.getCuentaList().get(j).getCodigo());
                        int codigoPos2 = Integer.parseInt(cuenta.getCuentaList().get(j + 1).getCodigo());
                        if (codigoPos1 > codigoPos2) {
                            
                            Cuenta tmp = cuenta.getCuentaList().get(j + 1);
                            
                            cuenta.getCuentaList().set(j + 1, cuenta.getCuentaList().get(j));
                            
                            cuenta.getCuentaList().set(j, tmp);
                        }
                    }
                }
                
                recorrerCuentas(cuenta.getCuentaList(), nodo);
            }
        }
    }
    
}
