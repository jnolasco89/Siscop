/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.CuentaPK;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;
import sv.org.siscop.caritas.serviciosvista.ModalAgregarCuentas;
import sv.org.siscop.caritas.serviciosvista.ModalBusProyectMttoCuentasContables;
import sv.org.siscop.caritas.serviciosvista.ModalEditarCuenta;
import sv.org.siscop.caritas.serviciosvista.PestaniaBusquedaMttoCuentasContables;
import sv.org.siscop.caritas.serviciosvista.PestaniaCatalogoMttoCuentasContables;

/**
 *
 * @author Leonardo Martinez
 */
@Named(value = "mttoCuentasContables")
@ViewScoped
public class MttoCuentasContables implements Serializable {

    //Servicios
    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServiciosCatalogoLocal servCatalogo;
    @EJB
    private ServiciosCuentaLocal servCuenta;

    //Variables para control de la UI
    private int tabActivo;

    //Modelo de datos para procesar en la UI
    private PestaniaBusquedaMttoCuentasContables pestaniaBusqueda;
    private PestaniaCatalogoMttoCuentasContables pestaniaCatalogo;
    private ModalBusProyectMttoCuentasContables modalBusquedaProyecto;
    private ModalAgregarCuentas modalAgregarCuentas;
    private ModalEditarCuenta modalEditarCuenta;

    /**
     * Creates a new instance of MttoCuentasContables
     */
    public MttoCuentasContables() {
    }

    @PostConstruct
    public void init() {
        tabActivo = 0;

        pestaniaBusqueda = new PestaniaBusquedaMttoCuentasContables(servProyecto, servCatalogo);
        pestaniaCatalogo = new PestaniaCatalogoMttoCuentasContables(servCuenta);
        modalBusquedaProyecto = new ModalBusProyectMttoCuentasContables(servProyecto, servCatalogo);
        modalAgregarCuentas = new ModalAgregarCuentas();
        modalEditarCuenta = new ModalEditarCuenta();
    }

    // ================== GETTER Y SETTER ==================
    public int getTabActivo() {
        return tabActivo;
    }

    public void setTabActivo(int tabActivo) {
        this.tabActivo = tabActivo;
    }

    public PestaniaBusquedaMttoCuentasContables getPestaniaBusqueda() {
        return pestaniaBusqueda;
    }

    public void setPestaniaBusqueda(PestaniaBusquedaMttoCuentasContables pestaniaBusqueda) {
        this.pestaniaBusqueda = pestaniaBusqueda;
    }

    public PestaniaCatalogoMttoCuentasContables getPestaniaCatalogo() {
        return pestaniaCatalogo;
    }

    public void setPestaniaCatalogo(PestaniaCatalogoMttoCuentasContables pestaniaCatalogo) {
        this.pestaniaCatalogo = pestaniaCatalogo;
    }

    public ModalBusProyectMttoCuentasContables getModalBusquedaProyecto() {
        return modalBusquedaProyecto;
    }

    public void setModalBusquedaProyecto(ModalBusProyectMttoCuentasContables modalBusquedaProyecto) {
        this.modalBusquedaProyecto = modalBusquedaProyecto;
    }

    public ModalAgregarCuentas getModalAgregarCuentas() {
        return modalAgregarCuentas;
    }

    public void setModalAgregarCuentas(ModalAgregarCuentas modalAgregarCuentas) {
        this.modalAgregarCuentas = modalAgregarCuentas;
    }

    public ModalEditarCuenta getModalEditarCuenta() {
        return modalEditarCuenta;
    }

    public void setModalEditarCuenta(ModalEditarCuenta modalEditarCuenta) {
        this.modalEditarCuenta = modalEditarCuenta;
    }

    // ============== METODOS PARA LA FUNCIONALIDAD DE LA VISTA ============
    //-------------- Pestaña busqueda ---------------
    public void pBusquedaBuscarProyecto() {
        Map filtros = new HashMap();

        if (pestaniaBusqueda.getProyectoAbuscar().getCodigo() != null
                ? pestaniaBusqueda.getProyectoAbuscar().getCodigo().length() > 0
                : false) {
            filtros.put("codigo", pestaniaBusqueda.getProyectoAbuscar().getCodigo());
        }

        if (pestaniaBusqueda.getProyectoAbuscar().getNombre() != null
                ? pestaniaBusqueda.getProyectoAbuscar().getNombre().length() > 0
                : false) {
            filtros.put("nombre", pestaniaBusqueda.getProyectoAbuscar().getNombre());
        }

        if (pestaniaBusqueda.getProyectoAbuscar().getNombreCorto() != null
                ? pestaniaBusqueda.getProyectoAbuscar().getNombreCorto().length() > 0
                : false) {
            filtros.put("nombreCorto", pestaniaBusqueda.getProyectoAbuscar().getNombreCorto());
        }

        if (pestaniaBusqueda.getProyectoAbuscar().getFechaini() != null) {
            filtros.put("fechaIni", pestaniaBusqueda.getProyectoAbuscar().getFechaini());
        }

        if (pestaniaBusqueda.getProyectoAbuscar().getFechafin() != null) {
            filtros.put("fechaFin", pestaniaBusqueda.getProyectoAbuscar().getFechafin());
        }

        if (pestaniaBusqueda.getProyectoAbuscar().getEstado() != null
                ? pestaniaBusqueda.getProyectoAbuscar().getEstado().getId() > 0
                : false) {
            filtros.put("estado", pestaniaBusqueda.getProyectoAbuscar().getEstado());
        }

        pestaniaBusqueda.buscarProyectosRegistrados(filtros);
    }

    public void pBusquedalimpiarBusqueda() {
        pestaniaBusqueda.resetPestaniaBusqueda();
    }

    public void pBusquedaCargarCatalogoDeCuentas() {
        Map filtros = new HashMap();
        filtros.put("cuentasPrincipales", true);
        filtros.put("proyecto", pestaniaBusqueda.getProyectoActual());

        pestaniaCatalogo.setProyectoActual(pestaniaBusqueda.getProyectoActual());
        pestaniaCatalogo.buscarCatalogoDeCuentas(filtros);
        tabActivo = 1;
    }
    //-------------- Pestaña catalogo ---------------

    public void pCatalogoLimpiarComponentes() {
        pestaniaCatalogo.resetearPestaniacatalogo();
    }

    public void pCatalogoGuardarCatalogo() {
        pestaniaCatalogo.guardarCatalogo();
        tabActivo = 1;
        FacesContext.getCurrentInstance()
                .addMessage("msgGrowl",
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Operacion exitosa",
                                "Catalogo registrado"));
        PrimeFaces.current().ajax().update(":formTabs");
    }

    public void pCtalogoSubirYprocesarArchivo(FileUploadEvent event) {
        try {
            pestaniaCatalogo.subirYprocesarArchivo(event.getFile().getInputstream());
            PrimeFaces.current().ajax().update(":formTabs:tabs:contenedorArbolCuentas");
        } catch (IOException ex) {
            Logger.getLogger(MttoCuentasContables.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pCatalogoAbrirModalBusCuenta() {
    }

    public void pCatalogoCargarCuentaSeleccionada() {

    }

    //-------------- Modal Buscar Proyecto ---------------
    public void mBuscarProyectoAbrirModal() {
        PrimeFaces.current().executeScript("PF('dialogProyectos').show()");
    }

    public void mBuscarProyectoCerrarModal() {
        modalBusquedaProyecto.resetModalBuscarProyecto();
        PrimeFaces.current().ajax().update(":formDialogProyectos:dialogProyectos");
    }

    public void mBuscarProyectoBuscar() {
        Map filtros = new HashMap();

        if (modalBusquedaProyecto.getProyectoAbuscar().getCodigo() != null
                ? modalBusquedaProyecto.getProyectoAbuscar().getCodigo().length() > 0
                : false) {
            filtros.put("codigo", modalBusquedaProyecto.getProyectoAbuscar().getCodigo());
        }

        if (modalBusquedaProyecto.getProyectoAbuscar().getNombre() != null
                ? modalBusquedaProyecto.getProyectoAbuscar().getNombre().length() > 0
                : false) {
            filtros.put("nombre", modalBusquedaProyecto.getProyectoAbuscar().getNombre());
        }

        if (modalBusquedaProyecto.getProyectoAbuscar().getNombreCorto() != null
                ? modalBusquedaProyecto.getProyectoAbuscar().getNombreCorto().length() > 0
                : false) {
            filtros.put("nombreCorto", modalBusquedaProyecto.getProyectoAbuscar().getNombreCorto());
        }

        if (modalBusquedaProyecto.getProyectoAbuscar().getFechaini() != null) {
            filtros.put("fechaIni", modalBusquedaProyecto.getProyectoAbuscar().getFechaini());
        }

        if (modalBusquedaProyecto.getProyectoAbuscar().getFechafin() != null) {
            filtros.put("fechaFin", modalBusquedaProyecto.getProyectoAbuscar().getFechafin());
        }

        if (modalBusquedaProyecto.getProyectoAbuscar().getEstado() != null
                ? modalBusquedaProyecto.getProyectoAbuscar().getEstado().getId() > 0
                : false) {
            filtros.put("estado", modalBusquedaProyecto.getProyectoAbuscar().getEstado());
        }

        modalBusquedaProyecto.buscarProyectosRegistrados(filtros);
    }

    public void mBuscarProyectoLimpiar() {
        modalBusquedaProyecto.resetModal();
    }

    public void mBuscarProyectoCargarProyecto() {
        pestaniaCatalogo.setProyectoActual(modalBusquedaProyecto.getProyectoActual());
        modalBusquedaProyecto.resetModal();
        PrimeFaces.current().ajax().update("formTabs:tabs:txtProyecto");
        PrimeFaces.current().ajax().update("formDialogProyectos:dialogProyectos");
        //PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");

    }

    public void eliminarCuentaDeArbol() {
        pestaniaCatalogo.eliminarCuenta();
        PrimeFaces.current().ajax().update("formTabs:tabs:tree-catalogo-cuentas");
        PrimeFaces.current().executeScript("PF('overlayOpcionesCuenta').hide()");
    }

    //----------- Modal agregar cuentas ----------------------
    public void mAgregarCuentasPrincipalesAbrir() {
        Cuenta c = null;
        modalAgregarCuentas.setVerCuentaPadre(false);
        modalAgregarCuentas.setModoCuenta(1);
        modalAgregarCuentas.setModoModal(1);
        modalAgregarCuentas.setCuentaPadre(c);
        modalAgregarCuentas.setTituloModal("Agregar Cuenta(s) principal(es)");
        PrimeFaces.current().ajax().update(":formModalAgregarSubcuentas:dialogAgregarSubcuentas");
        PrimeFaces.current().executeScript("PF('dialogAgregarSubcuentas').show()");
    }

    public void mAgregarCuentaCerrar() {

    }

    public void mAgregarCuentaAgregar() {
        if (modalAgregarCuentas.getModoCuenta() == 1) {
            modalAgregarCuentas.agregarSubCuenta();
        } else if (modalAgregarCuentas.getModoCuenta() == 2) {
            modalAgregarCuentas.editarCuenta();
        }

        PrimeFaces.current().ajax().update("formModalAgregarSubcuentas:panel-modal-subcuenta");
    }

    public void mAgregarCuentaGuardar() {
        pestaniaCatalogo.agregarCuentas(modalAgregarCuentas.getCuentas());
        PrimeFaces.current().ajax().update("formTabs:tabs:tree-catalogo-cuentas");
        PrimeFaces.current().executeScript("PF('dialogAgregarSubcuentas').hide()");

    }

    public void mAgregarCuentaCargarCuenta(int index, Cuenta c) {
        modalAgregarCuentas.setModoCuenta(2);
        c.setIndexEnList(index);
        modalAgregarCuentas.setCuentaActual(c);
        PrimeFaces.current().ajax().update(":formModalAgregarSubcuentas:panel-modal-subcuenta");
    }

    public void mAgregarCuentaElmiminarCuenta(int index) {
        modalAgregarCuentas.eliminarCuenta(index);
        PrimeFaces.current().ajax().update(":formModalAgregarSubcuentas:tablaCuentasAagregar");
    }

    public void mAgregarSubcuentasAbrir() {
        PrimeFaces.current().executeScript("PF('overlayOpcionesCuenta').hide()");
        Cuenta c = (Cuenta) pestaniaCatalogo.getNodoActual().getData();
        modalAgregarCuentas.setVerCuentaPadre(true);
        modalAgregarCuentas.setModoCuenta(1);
        modalAgregarCuentas.setModoModal(2);
        modalAgregarCuentas.setCuentaPadre(c);
        modalAgregarCuentas.setTituloModal("Agregar subcuent(s)");
        PrimeFaces.current().ajax().update(":formModalAgregarSubcuentas:dialogAgregarSubcuentas");
        PrimeFaces.current().executeScript("PF('dialogAgregarSubcuentas').show()");
    }

    // ------------ Modal editar cuenta --------------------------------
    public void mEditarCuentaAbrir() {
        PrimeFaces.current().executeScript("PF('overlayOpcionesCuenta').show()");
        modalEditarCuenta.setCuentaActual((Cuenta) pestaniaCatalogo.getNodoActual().getData());
        PrimeFaces.current().ajax().update("formModalCuenta:dialogCuenta");
        PrimeFaces.current().executeScript("PF('dialogCuenta').show()");
    }

    public void mEditarCuentaEditar() {
        pestaniaCatalogo.editarCuenta(modalEditarCuenta.getCuentaActual());
        modalEditarCuenta.reset();
        
        PrimeFaces.current().ajax().update(":formTabs:tabs:tree-catalogo-cuentas");
        PrimeFaces.current().executeScript("PF('dialogCuenta').hide()");
    }
}
