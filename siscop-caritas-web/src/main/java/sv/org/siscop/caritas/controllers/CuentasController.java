/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template archivo, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;

/**
 *
 * @author Nolasco
 */
@Named(value = "cuentasController")
@ViewScoped
public class CuentasController implements Serializable {

    //Servicios
    @EJB
    private ServiciosCuentaLocal servCtas;
    //Objetos cuenta
    private Cuenta ctaSeleccionada;
    private LazyDataModel<Cuenta> listaCuentasPadre;
    //Variables de control para la ui
    private int tabActivo;
    private UploadedFile archivo;

    /**
     * Creates a new instance of CuentasController
     */
    public CuentasController() {
    }

    @PostConstruct
    public void init() {
        tabActivo = 0;
        listaCuentasPadre = new LazyDataModel<Cuenta>() {
            List<Cuenta> cuentas;

            @Override
            public List<Cuenta> load(int first, int pageSize, String sortField,
                    SortOrder sortOrder, Map<String, Object> filters) {
                cuentas = servCtas.paginacion(first, pageSize, filters);

                listaCuentasPadre.setRowCount(servCtas.contarTodo());

                return cuentas;
            }

            @Override
            public Object getRowKey(Cuenta c) {
                return c.getCodigo();
            }

            @Override
            public Cuenta getRowData(String rowKey) {
                for (Cuenta cta : cuentas) {
                    if (cta.getCodigo().equals(rowKey)) {
                        return cta;
                    }
                }

                return new Cuenta();
            }
        };

    }

    //================ METODOS GET Y SET =================
    public int getTabActivo() {
        return tabActivo;
    }

    public void setTabActivo(int tabActivo) {
        this.tabActivo = tabActivo;
    }

    public LazyDataModel<Cuenta> getListaCuentasPadre() {
        return listaCuentasPadre;
    }

    public void setListaCuentasPadre(LazyDataModel<Cuenta> listaCuentasPadre) {
        this.listaCuentasPadre = listaCuentasPadre;
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
        return ctaSeleccionada.getCodigoctapadre().getCodigo();
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

}
