/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import java.io.Serializable;
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
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Leonardo Martinez
 */
@Named(value = "mttoCuentasContables")
@ViewScoped
public class MttoCuentasContables implements Serializable {

    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServiciosCatalogoLocal servCatalogo;
    @EJB
    private ServiciosCuentaLocal servCuenta;

    //Variables para la ui
    private int tabActiva;
    private int modoProyecto;
    private List<Proyecto> proyectosTabla;
    private List<Proyecto> proyectosModal;
    private List<ItemCatalogo> estadosProyecto;
    private String msjPestaniaCatalogo;
    private Map msjs;
    private TreeNode nodoActual;
    //Modelo para la vista
    private Proyecto proyectoActual;
    private Proyecto proyectoBusquedaTabla;
    private Proyecto proyectoBusquedaModal;
    private List<Cuenta> catalogoDeCuentas;
    private TreeNode arbolCuentas;
    private Cuenta cuentaActual;

    /**
     * Creates a new instance of MttoCuentasContables
     */
    public MttoCuentasContables() {
    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
        modoProyecto = 1;//1 Para agregar, 2 para editar
        msjs = new HashMap();
        msjs.put("vacio", "El proyecto no posee catalogo de cuentas. Para crear un nuevo catalogo cree cuentas desde la pestaña \"detalle\" o cargue un archivo con las cuentas que desea registrar.");
        msjs.put("seleccionar", "Seleccione un proyecto para cargar el catalogo de cuentas o cree un nuevo catalogo. Para crear un nuevo catalogo cree cuentas desde la pestaña \"detalle\" o cargue un archivo con las cuentas que desea registrar.");
        msjPestaniaCatalogo = msjs.get("seleccionar").toString();

        Map filtroProTabl = new HashMap();
        filtroProTabl.put("estado", new ItemCatalogo(63));
        proyectosTabla = servProyecto.buscarProyetosCriterial(filtroProTabl);
        Map filtroProMod = new HashMap();
        proyectosModal = servProyecto.buscarProyetosCriterial(filtroProMod);
        estadosProyecto = servCatalogo.findCatalogoById(20).getItemCatalogoList();

        proyectoActual = new Proyecto();
        proyectoActual.setEstado(new ItemCatalogo());
        proyectoBusquedaTabla = new Proyecto();
        proyectoBusquedaTabla.setEstado(new ItemCatalogo(63));
        proyectoBusquedaModal = new Proyecto();
        proyectoBusquedaModal.setEstado(new ItemCatalogo(0));

        catalogoDeCuentas = null;
        arbolCuentas = null;
        cuentaActual = new Cuenta();
    }

    // ================== GETTER Y SETTER ==================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public List<Proyecto> getProyectosTabla() {
        return proyectosTabla;
    }

    public void setProyectosTabla(List<Proyecto> proyectos) {
        this.proyectosTabla = proyectos;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public List<ItemCatalogo> getEstadosProyecto() {
        return estadosProyecto;
    }

    public void setEstadosProyecto(List<ItemCatalogo> estadosProyecto) {
        this.estadosProyecto = estadosProyecto;
    }

    public List<Cuenta> getCatalogoDeCuentas() {
        return catalogoDeCuentas;
    }

    public void setCatalogoDeCuentas(List<Cuenta> catalogoDeCuentas) {
        this.catalogoDeCuentas = catalogoDeCuentas;
    }

    public String getMsjPestaniaCatalogo() {
        return msjPestaniaCatalogo;
    }

    public void setMsjPestaniaCatalogo(String msjPestaniaCatalogo) {
        this.msjPestaniaCatalogo = msjPestaniaCatalogo;
    }

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

    public List<Proyecto> getProyectosModal() {
        return proyectosModal;
    }

    public void setProyectosModal(List<Proyecto> proyectosModal) {
        this.proyectosModal = proyectosModal;
    }

    public Proyecto getProyectoBusquedaModal() {
        return proyectoBusquedaModal;
    }

    public void setProyectoBusquedaModal(Proyecto proyectoBusquedaModal) {
        this.proyectoBusquedaModal = proyectoBusquedaModal;
    }

    public Proyecto getProyectoBusquedaTabla() {
        return proyectoBusquedaTabla;
    }

    public void setProyectoBusquedaTabla(Proyecto proyectoBusquedaTabla) {
        this.proyectoBusquedaTabla = proyectoBusquedaTabla;
    }

    // =================== METODOS =========================
    // ----- Pestaña busqueda --
    public void buscarProyectoEnTabla() {
        proyectosTabla = buscarProyecto(proyectoBusquedaTabla);
    }

    public List<Proyecto> buscarProyecto(Proyecto p) {
        Map filtros = new HashMap();
        filtros.put("codigo", null);
        filtros.put("nombre", null);
        filtros.put("nombreCorto", null);
        filtros.put("fechaIni", null);
        filtros.put("fechaFin", null);
        filtros.put("estado", null);

        if (p.getCodigo() != null) {
            if (p.getCodigo().length() > 0) {
                filtros.replace("codigo", p.getCodigo());
            }
        }

        if (p.getNombre() != null) {
            if (p.getNombre().length() > 0) {
                filtros.replace("nombre", p.getNombre());
            }
        }

        if (p.getNombreCorto() != null) {
            if (p.getNombreCorto().length() > 0) {
                filtros.replace("nombreCorto", p.getNombreCorto());
            }
        }

        if (p.getFechaini() != null) {
            filtros.replace("fechaIni", p.getFechaini());
        }

        if (p.getFechafin() != null) {
            filtros.replace("fechaFin", p.getFechafin());
        }

        if (p.getEstado() != null) {
            if (p.getEstado().getId() > 0) {
                filtros.replace("estado", p.getEstado());
            }
        }

        return servProyecto.buscarProyetosCriterial(filtros);
    }

    public void limpiarBusquedaProyectoEnTabla() {
        proyectoBusquedaTabla = new Proyecto();
        proyectoBusquedaTabla.setEstado(new ItemCatalogo(63));

        Map filtro = new HashMap();
        filtro.put("estado", proyectoBusquedaTabla.getEstado());
        proyectosTabla = servProyecto.buscarProyetosCriterial(filtro);
    }

    public void cargarCatalogoDeCuentasDelProyecto() {
        Map filtros = new HashMap();
        filtros.put("cuentasPrincipales", true);
        filtros.put("proyecto", proyectoActual);
        catalogoDeCuentas = servCuenta.buscarCuentas(filtros);

        arbolCuentas = new DefaultTreeNode("Raiz", null);
        recorrerCuentas(catalogoDeCuentas, arbolCuentas, true);
        tabActiva = 1;
        modoProyecto = 2;
    }

    // ----- Pestaña catalogo -----------
    public void guardarCatalogo() {
        servCuenta.registraCatalogoDeCuentas(catalogoDeCuentas, proyectoActual);
        
        FacesContext.getCurrentInstance()
                .addMessage("msgGrowl",
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Operacion exitosa",
                                "Catalogo registrado"));

        PrimeFaces.current().ajax().update(":formTabs:tabs:catalogo");

    }

    public void limpiarPestaniaCatalogo() {
        proyectoActual = new Proyecto();
        proyectoActual.setId(null);
        catalogoDeCuentas = null;
        arbolCuentas = null;
        msjPestaniaCatalogo = msjs.get("seleccionar").toString();
        modoProyecto = 1;
    }

    // ----- Pestaña detalle ------------
    public void accionBtnGuardar() {
        System.out.println("Btn guardar action");
    }

    public void limpiarFormCuenta() {
        System.out.println("Limpiar from cuenta");
    }

    public void cargarCuentaSeleccionada() {
        cuentaActual=(Cuenta) nodoActual.getData();
        //update=":formTabs:tabs:detalle" 
        PrimeFaces.current().ajax().update(":formTabs:tabs:detalle");
    }

    public void subirYprocesarArchivo(FileUploadEvent event) {
        try {

            catalogoDeCuentas = servCuenta.leerArchivo(event.getFile().getInputstream());
            arbolCuentas = new DefaultTreeNode("Raiz", null);
            recorrerCuentas(catalogoDeCuentas, arbolCuentas, true);

            PrimeFaces.current().ajax().update(":formTabs:tabs:catalogo");
        } catch (IOException ex) {
            Logger.getLogger(MttoCuentasContables.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ----- Render y enabled para componentes -----
    public boolean verArbolCuentas() {
        if (catalogoDeCuentas == null) {
            return false;
        } else if (catalogoDeCuentas.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean verMsjNoHayCatalogo() {
        if (catalogoDeCuentas == null) {
            msjPestaniaCatalogo = msjs.get("seleccionar").toString();
            return true;
        } else if (catalogoDeCuentas.isEmpty()) {
            msjPestaniaCatalogo = msjs.get("vacio").toString();
            return true;
        }
        return false;
    }

    public boolean deshabilitarInputProyecto() {
        return modoProyecto == 2;
    }

    // ---- Metodos modal buscar proyecto ----
    public void buscarProyectoEnModal() {
        proyectosModal = buscarProyecto(proyectoBusquedaModal);
    }

    public void limpiarBusquedaProyectoEnModal() {
        proyectoBusquedaModal = new Proyecto();
        proyectoBusquedaModal.setEstado(new ItemCatalogo(0));

        Map filtro = new HashMap();
        filtro.put("estado", null);
        proyectosModal = servProyecto.buscarProyetosCriterial(filtro);
    }

    public void cargarProyectoSeleccionadoEnModal() {
        PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");
        PrimeFaces.current().ajax().update(":formTabs:tabs:datosProyectoCatalogo");
    }

    public void abrirModalBuscarProyecto() {
        PrimeFaces.current().executeScript("PF('dialogProyectos').show()");
    }

    public void cerrarModalBuscarProyecto() {
        PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");
    }

    // ---- Metodos utilitarios -------
    public void recorrerCuentas(List<Cuenta> cuentas, TreeNode padre, boolean fullExpansion) {
        cuentas.forEach((cuenta) -> {
            TreeNode nodo = new DefaultTreeNode(cuenta, padre);

            if (cuenta.getCuentaPadre() == null) {
                nodo.setExpanded(true);
            }

            if (cuenta.getCuentaPadre() != null && fullExpansion) {
                nodo.setExpanded(true);
            }

            if (cuenta.getCuentaList().size() > 0) {
                //Ordenando las subcuentas por codigo
                for (int i = 0; i < cuenta.getCuentaList().size() - 1; i++) {

                    for (int j = 0; j < cuenta.getCuentaList().size() - 1; j++) {
                        int codigoPos1 = Integer.parseInt(cuenta.getCuentaList().get(j).getCuentaPK().getCodigo());
                        int codigoPos2 = Integer.parseInt(cuenta.getCuentaList().get(j + 1).getCuentaPK().getCodigo());
                        if (codigoPos1 > codigoPos2) {

                            Cuenta tmp = cuenta.getCuentaList().get(j + 1);

                            cuenta.getCuentaList().set(j + 1, cuenta.getCuentaList().get(j));

                            cuenta.getCuentaList().set(j, tmp);
                        }
                    }
                }

                recorrerCuentas(cuenta.getCuentaList(), nodo, fullExpansion);
            }
        });
//        cuentas.forEach((cuenta) -> {
//            TreeNode nodo = new DefaultTreeNode(cuenta, padre);
//            if (cuenta.getIdctapadre() == null) {
//                nodo.setExpanded(true);
//            }
//            if (cuenta.getCuentaList().size() > 0) {
//                //Ordenando las subcuentas por codigo
//                for (int i = 0; i < cuenta.getCuentaList().size() - 1; i++) {
//
//                    for (int j = 0; j < cuenta.getCuentaList().size() - 1; j++) {
//                        int codigoPos1 = Integer.parseInt(cuenta.getCuentaList().get(j).getCodigo());
//                        int codigoPos2 = Integer.parseInt(cuenta.getCuentaList().get(j + 1).getCodigo());
//                        if (codigoPos1 > codigoPos2) {
//
//                            Cuenta tmp = cuenta.getCuentaList().get(j + 1);
//
//                            cuenta.getCuentaList().set(j + 1, cuenta.getCuentaList().get(j));
//
//                            cuenta.getCuentaList().set(j, tmp);
//                        }
//                    }
//                }
//
//                recorrerCuentas(cuenta.getCuentaList(), nodo);
//            }
//        });
    }
}
