/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;
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
    
    //Propiedades para logica de la ui
    private int tabActiva;
    private int modoFormulario;//Si es 1 es agregar si es 2 es editar
    //Propiedades para el modelo de cuenta
    private String codCtaAeditar;
    private Cuenta cuentaActual;
    private Cuenta cuentaPadreActual;
    //Propiedades para mostrar las cuentas en 
    //un objeto tree
    private TreeNode arbolCuentas;
    private TreeNode nodoActual;
    //Objeto de validaciones para cuenta
    private ValidacionesCuenta validaciones;

    /**
     * Creates a new instance of CuentasControllerTree
     */
    public CuentasControllerTree() {

    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
        modoFormulario=1;//En modo agregar
        cuentaActual = new Cuenta();
        cuentaPadreActual = new Cuenta();

        List<Cuenta> cuentas = servCtas.getCuentasPadres();
        arbolCuentas = new DefaultTreeNode("Raiz", null);
        recorrerCuentas(cuentas, arbolCuentas);
        
        validaciones = new ValidacionesCuenta();
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
    public void accionBtnGuardar(){
        if(modoFormulario==1){//Significa que está en modo agregar
            guardarCuenta();
        }else if(modoFormulario==2){//Siginifiac que está en modo editar
            editarCuenta();
        }
    }
    
    public void guardarCuenta() {
        try {
            //Agregando la cuenta nueva
            if (cuentaPadreActual.getCodigo() == null) {
                cuentaActual.setIdctapadre(null);
            } else {
                cuentaActual.setIdctapadre(cuentaPadreActual);
            }
            
            cuentaActual.setCuentaList(new ArrayList<Cuenta>());
            servCtas.agregarCuenta(cuentaActual);
            
            //Limpiando las variables despues de
            //agrega la cuenta nueva
            modoFormulario=1;
            cuentaActual=new Cuenta();
            cuentaPadreActual=new Cuenta();
            List<Cuenta> cuentas = servCtas.getCuentasPadres();
            arbolCuentas = new DefaultTreeNode("Raiz", null);
            recorrerCuentas(cuentas, arbolCuentas);
            
            //Mostrando msj de operacion exitosa
            FacesContext.getCurrentInstance().addMessage("msgGrowl",
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Operacion exitosa",
                            "Cuenta agregada"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("msgGrowl",
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error",
                            ex.getMessage()));
        }
    }
    
    public void editarCuenta() {
        try {
            //Agregando la cuenta nueva
            if (cuentaPadreActual.getCodigo() == null) {
                cuentaActual.setIdctapadre(null);
            } else {
                cuentaActual.setIdctapadre(cuentaPadreActual);
            }
            
            servCtas.editarCuenta(codCtaAeditar,cuentaActual);
            
            //Limpiando las variables despues de
            //agrega la cuenta nueva
            modoFormulario=1;
            cuentaActual=new Cuenta();
            cuentaPadreActual=new Cuenta();
            List<Cuenta> cuentas = servCtas.getCuentasPadres();
            arbolCuentas = new DefaultTreeNode("Raiz", null);
            recorrerCuentas(cuentas, arbolCuentas);
            
            //Mostrando msj de operacion exitosa
            FacesContext.getCurrentInstance().addMessage("msgGrowl",
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Operacion exitosa",
                            "Cuenta editada"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("msgGrowl",
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error",
                            ex.getMessage()));
        }
    }

    public void cargarCuentaSeleccionada() {
        cuentaActual = (Cuenta) nodoActual.getData();
        cuentaPadreActual = cuentaActual.getIdctapadre();
        tabActiva = 1;
        codCtaAeditar=cuentaActual.getCodigo();
        modoFormulario=2;
    }

    public void actualizarCuentaPadre() {
        cuentaPadreActual = (Cuenta) nodoActual.getData();
    }
    
     public void subirYprocesarArchivo(FileUploadEvent event) {
        try {
            servCtas.procesarArchivo(event.getFile().getInputstream());
        } catch (IOException ex) {
             FacesContext.getCurrentInstance().addMessage("msgGrowl",
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error",
                            ex.getMessage()));
        }
    }
    
    public void recorrerCuentas(List<Cuenta> cuentas, TreeNode padre) {
        for (Cuenta cuenta : cuentas) {
            TreeNode nodo = new DefaultTreeNode(cuenta, padre);

            if (cuenta.getIdctapadre() == null) {
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
