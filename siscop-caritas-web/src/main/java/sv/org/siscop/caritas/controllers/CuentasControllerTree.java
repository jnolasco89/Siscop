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
    //Objetos cuenta
    private Cuenta ctaSeleccionada;
    //Variables de control para la ui
    private int tabActivo;
    private UploadedFile archivo;
    private TreeNode raiz;

    /**
     * Creates a new instance of CuentasControllerTree
     */
    public CuentasControllerTree() {

    }

    @PostConstruct
    public void init() {

        List<Cuenta> cuentas = servCtas.getCuentasPadres();
        raiz = new DefaultTreeNode("Raiz", null);

        recorrerCuentas(cuentas, raiz);

    }
    //================ METODOS GET Y SET =================

    public int getTabActivo() {
        return tabActivo;
    }

    public void setTabActivo(int tabActivo) {
        this.tabActivo = tabActivo;
    }

    public Cuenta getCtaSeleccionada() {
        if (ctaSeleccionada == null) {
            ctaSeleccionada = new Cuenta();
        }
        return ctaSeleccionada;
    }

    public void setCodigoCuentaPadre(String codigo) {
        Cuenta ctaPadre = new Cuenta();
        ctaPadre.setCodigo(codigo);
        ctaSeleccionada.setCodigoctapadre(ctaPadre);
    }

    public String getCodigoCuentaPadre() {
        if (ctaSeleccionada.getCodigoctapadre() == null) {
            return "";
        }
        return "";
        //return ctaSeleccionada.getIdctapadre().getCodigo();
    }

    public void setCtaSeleccionada(Cuenta ctaSeleccionada) {
        this.ctaSeleccionada = ctaSeleccionada;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public TreeNode getRaiz() {
        return raiz;
    }

    public void setRaiz(TreeNode raiz) {
        this.raiz = raiz;
    }

    //================ METODOS DE FUNCIONALIDAD ============
    public void buscarCuenta() {
        if (ctaSeleccionada != null) {
            System.out.println("Codigo: " + ctaSeleccionada.getCodigo());
            System.out.println("Nombre: " + ctaSeleccionada.getNombre());
            System.out.println("Descripcion: " + ctaSeleccionada.getDescripcion());
        }
    }

    public void limpiar() {
        tabActivo = 0;
        ctaSeleccionada = new Cuenta();
    }

    public void procesarArchivo() {
        if (archivo != null) {
            System.out.println(archivo.getFileName());
        } else {
            System.out.println("ARCHIVO NULL");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            //File a=(File) event.getFile();
            servCtas.procesarArchivo(event.getFile().getInputstream());

            System.out.println(event.getFile().getFileName() + " is uploaded.");
        } catch (IOException ex) {
            Logger.getLogger(CuentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Eventos para los componentes------

    //Evento para seleccion de una fila en la tabla
    //que muestra las cuentas existentes.
    public void evtFilaSeleccionada() {
        tabActivo = 1;
    }

    public void recorrerCuentas(List<Cuenta> cuentas, TreeNode padre) {
        for (Cuenta cuenta : cuentas) {
            TreeNode nodo = new DefaultTreeNode(cuenta.getCodigo() + "-" + cuenta.getNombre(), padre);

            if (cuenta.getCuentaList().size() > 0) {
                recorrerCuentas(cuenta.getCuentaList(), nodo);
            }
        }
    }
}
