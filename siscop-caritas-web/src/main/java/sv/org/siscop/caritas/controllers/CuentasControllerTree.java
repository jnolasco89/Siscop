/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;

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

    /**
     * Creates a new instance of CuentasControllerTree
     */
    public CuentasControllerTree() {

    }

    @PostConstruct
    public void init() {
        tabActiva=0;
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
        if(cuentaActual==null){
            cuentaActual=new Cuenta();
        }
        return cuentaActual;
    }

    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    public Cuenta getCuentaPadreActual() {
        if(cuentaPadreActual==null){
            cuentaPadreActual=new Cuenta();
        }
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
    
    
    //================ METODOS DE FUNCIONALIDAD ============
    public void cargarCuentaSeleccionada(){
        cuentaActual= (Cuenta) nodoActual.getData();
        cuentaPadreActual=cuentaActual.getCodigoctapadre();
        tabActiva=1;
    }
    
    public void actualizarCuentaPadre(){
        cuentaPadreActual=(Cuenta)nodoActual.getData();
        System.out.println("************ "+cuentaPadreActual.getCodigo());
        //cuentaActual.setCodigoctapadre(cuentaActual);
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
