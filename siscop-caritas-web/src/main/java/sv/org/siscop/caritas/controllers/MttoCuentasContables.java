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
<<<<<<< HEAD
import javax.inject.Named;
import javax.faces.view.ViewScoped;
=======
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
>>>>>>> origin/master
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
<<<<<<< HEAD
    private List<Proyecto> proyectos;
=======
    private int modoProyecto;
    private List<Proyecto> proyectosTabla;
    private List<Proyecto> proyectosModal;
>>>>>>> origin/master
    private List<ItemCatalogo> estadosProyecto;
    private String msjPestaniaCatalogo;
    private Map msjs;
    private TreeNode nodoActual;
    //Modelo para la vista
<<<<<<< HEAD
    private Proyecto proyectoActual;
    private Proyecto proyectoCatalogo;
    private List<Cuenta> catalogoDeCuentas;
    private TreeNode arbolCuentas;
=======
    private Proyecto proyectoActualCatalogo;
    private Proyecto proyectoActualDetalle;//SIN USAR POR EL MOMENTO!!!!!!!!!!!!!!!!!!****>>>
    private Proyecto proyectoBusquedaTabla;
    private Proyecto proyectoBusquedaModal;
    private List<Cuenta> catalogoDeCuentas;
    private TreeNode arbolCuentasCatalogo;
    private TreeNode arbolCuentasModal;
>>>>>>> origin/master
    private Cuenta cuentaActual;

    /**
     * Creates a new instance of MttoCuentasContables
     */
    public MttoCuentasContables() {
    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
<<<<<<< HEAD
        msjs = new HashMap();
        msjs.put("vacio", "El proyecto no posee catalogo de cuentas");
        msjs.put("seleccionar", "Seleccione un proyecto para cargar el catalogo de cuentas");

        msjPestaniaCatalogo = msjs.get("seleccionar").toString();
        proyectos = servProyecto.getAllProyectos();
        proyectoActual = new Proyecto();
        proyectoActual.setEstado(new ItemCatalogo(63));
        proyectoCatalogo = new Proyecto();
        estadosProyecto = servCatalogo.findCatalogoById(20).getItemCatalogoList();
        catalogoDeCuentas = null;
        arbolCuentas = null;
        cuentaActual = new Cuenta();
=======
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

        proyectoActualCatalogo = new Proyecto();
        proyectoActualCatalogo.setEstado(new ItemCatalogo());
        proyectoActualDetalle = new Proyecto();
        proyectoActualDetalle.setEstado(new ItemCatalogo());
        proyectoBusquedaTabla = new Proyecto();
        proyectoBusquedaTabla.setEstado(new ItemCatalogo(63));
        proyectoBusquedaModal = new Proyecto();
        proyectoBusquedaModal.setEstado(new ItemCatalogo(0));

        catalogoDeCuentas = null;
        arbolCuentasCatalogo = null;
        arbolCuentasModal = null;
        cuentaActual = new Cuenta();
        cuentaActual.setObjProyecto(new Proyecto());
>>>>>>> origin/master
    }

    // ================== GETTER Y SETTER ==================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

<<<<<<< HEAD
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
=======
    public List<Proyecto> getProyectosTabla() {
        return proyectosTabla;
    }

    public void setProyectosTabla(List<Proyecto> proyectos) {
        this.proyectosTabla = proyectos;
    }

    public Proyecto getProyectoActualCatalogo() {
        return proyectoActualCatalogo;
    }

    public void setProyectoActualCatalogo(Proyecto proyectoActual) {
        this.proyectoActualCatalogo = proyectoActual;
>>>>>>> origin/master
    }

    public List<ItemCatalogo> getEstadosProyecto() {
        return estadosProyecto;
    }

    public void setEstadosProyecto(List<ItemCatalogo> estadosProyecto) {
        this.estadosProyecto = estadosProyecto;
    }

<<<<<<< HEAD
    public Proyecto getProyectoCatalogo() {
        return proyectoCatalogo;
    }

    public void setProyectoCatalogo(Proyecto proyectoCatalogo) {
        this.proyectoCatalogo = proyectoCatalogo;
    }

=======
>>>>>>> origin/master
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

<<<<<<< HEAD
    public TreeNode getArbolCuentas() {
        return arbolCuentas;
    }

    public void setArbolCuentas(TreeNode arbolCuentas) {
        this.arbolCuentas = arbolCuentas;
=======
    public TreeNode getArbolCuentasCatalogo() {
        return arbolCuentasCatalogo;
    }

    public void setArbolCuentasCatalogo(TreeNode arbolCuentasCatalogo) {
        this.arbolCuentasCatalogo = arbolCuentasCatalogo;
>>>>>>> origin/master
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

<<<<<<< HEAD
    // =================== METODOS =========================
    // ----- Pestaña busqueda --
    public void buscarProyecto() {
=======
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

    public Proyecto getProyectoActualDetalle() {
        return proyectoActualDetalle;
    }

    public void setProyectoActualDetalle(Proyecto proyectoActualDetalle) {
        this.proyectoActualDetalle = proyectoActualDetalle;
    }

    public TreeNode getArbolCuentasModal() {
        return arbolCuentasModal;
    }

    public void setArbolCuentasModal(TreeNode arbolCuentasModal) {
        this.arbolCuentasModal = arbolCuentasModal;
    }

    // =================== METODOS =========================
    // ----- Pestaña busqueda --
    public void buscarProyectoEnTabla() {
        proyectosTabla = buscarProyecto(proyectoBusquedaTabla);
    }

    public List<Proyecto> buscarProyecto(Proyecto p) {
>>>>>>> origin/master
        Map filtros = new HashMap();
        filtros.put("codigo", null);
        filtros.put("nombre", null);
        filtros.put("nombreCorto", null);
        filtros.put("fechaIni", null);
        filtros.put("fechaFin", null);
        filtros.put("estado", null);

<<<<<<< HEAD
        if (proyectoActual.getCodigo() != null) {
            if (proyectoActual.getCodigo().length() > 0) {
                filtros.replace("codigo", proyectoActual.getCodigo());
            }
        }

        if (proyectoActual.getNombre() != null) {
            if (proyectoActual.getNombre().length() > 0) {
                filtros.replace("nombre", proyectoActual.getNombre());
            }
        }

        if (proyectoActual.getNombreCorto() != null) {
            if (proyectoActual.getNombreCorto().length() > 0) {
                filtros.replace("nombreCorto", proyectoActual.getNombreCorto());
            }
        }

        if (proyectoActual.getFechaini() != null) {
            filtros.replace("fechaIni", proyectoActual.getFechaini());
        }

        if (proyectoActual.getFechafin() != null) {
            filtros.replace("fechaFin", proyectoActual.getFechafin());
        }

        if (proyectoActual.getEstado() != null) {
            if (proyectoActual.getEstado().getId() > 0) {
                filtros.replace("estado", proyectoActual.getEstado());
            }
        }

        proyectos = servProyecto.buscarProyetosCriterial(filtros);
    }

    public void limpiarBusquedaProyecto() {
        proyectoActual = new Proyecto();
        proyectoActual.setEstado(new ItemCatalogo(63));
        proyectoCatalogo = new Proyecto();

        proyectos = servProyecto.getAllProyectos();
    }

    public void cargarCatalogoDeCuentasProyecto() {
        Map filtros = new HashMap();
        filtros.put("cuentasPrincipales", true);
        filtros.put("proyecto", proyectoCatalogo);
        catalogoDeCuentas = servCuenta.buscarCuentas(filtros);

        arbolCuentas = new DefaultTreeNode("Raiz", null);
        recorrerCuentas(catalogoDeCuentas, arbolCuentas);
        tabActiva = 1;
    }

    // ----- Pestaña catalogo -----------
=======
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
        tabActiva = 0;
        proyectoBusquedaTabla = new Proyecto();
        proyectoBusquedaTabla.setEstado(new ItemCatalogo(63));

        Map filtro = new HashMap();
        filtro.put("estado", proyectoBusquedaTabla.getEstado());
        proyectosTabla = servProyecto.buscarProyetosCriterial(filtro);
    }

    public void cargarCatalogoDeCuentasDelProyecto() {
        Map filtros = new HashMap();
        filtros.put("cuentasPrincipales", true);
        filtros.put("proyecto", proyectoActualCatalogo);
        catalogoDeCuentas = servCuenta.buscarCuentas(filtros);

        arbolCuentasCatalogo = new DefaultTreeNode("Raiz", null);
        recorrerCuentas(catalogoDeCuentas, arbolCuentasCatalogo, true);
        tabActiva = 1;
        modoProyecto = 2;
    }

    // ----- Pestaña catalogo -----------
    public void guardarCatalogo() {
        servCuenta.registraCatalogoDeCuentas(catalogoDeCuentas, proyectoActualCatalogo);

        FacesContext.getCurrentInstance()
                .addMessage("msgGrowl",
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Operacion exitosa",
                                "Catalogo registrado"));
        tabActiva = 1;
        PrimeFaces.current().ajax().update(":formTabs");

    }

    public void limpiarPestaniaCatalogo() {
        proyectoActualCatalogo = new Proyecto();
        proyectoActualCatalogo.setId(null);
        catalogoDeCuentas = null;
        arbolCuentasCatalogo = null;
        msjPestaniaCatalogo = msjs.get("seleccionar").toString();
        modoProyecto = 1;
    }

    // ----- Pestaña detalle ------------
>>>>>>> origin/master
    public void accionBtnGuardar() {
        System.out.println("Btn guardar action");
    }

    public void limpiarFormCuenta() {
        System.out.println("Limpiar from cuenta");
    }

<<<<<<< HEAD
    public void cargarCuentaSeleccionada() {
        System.out.println("Cargar cuenta");
=======
    public void cargarCuentaSeleccionadaTabla() {
        cuentaActual = (Cuenta) nodoActual.getData();
        cuentaActual.setObjProyecto(proyectoActualCatalogo);
        //update=":formTabs:tabs:detalle" 
        tabActiva=2;
        PrimeFaces.current().ajax().update(":formTabs:tabs");
>>>>>>> origin/master
    }

    public void subirYprocesarArchivo(FileUploadEvent event) {
        try {
<<<<<<< HEAD
            catalogoDeCuentas = servCuenta.leerArchivo(event.getFile().getInputstream());
            arbolCuentas = new DefaultTreeNode("Raiz", null);
            recorrerCuentas(catalogoDeCuentas, arbolCuentas);
            
=======

            catalogoDeCuentas = servCuenta.leerArchivo(event.getFile().getInputstream());
            arbolCuentasCatalogo = new DefaultTreeNode("Raiz", null);
            recorrerCuentas(catalogoDeCuentas, arbolCuentasCatalogo, true);

            PrimeFaces.current().ajax().update(":formTabs:tabs:contenedorArbolCuentas");
>>>>>>> origin/master
        } catch (IOException ex) {
            Logger.getLogger(MttoCuentasContables.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

<<<<<<< HEAD
    // ----- Render para componentes -----
=======
    // ----- Render y enabled para componentes -----
>>>>>>> origin/master
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

<<<<<<< HEAD
    // ---- Metodos utilitarios -------
    public void recorrerCuentas(List<Cuenta> cuentas, TreeNode padre) {
        cuentas.forEach((cuenta) -> {
            TreeNode nodo = new DefaultTreeNode(cuenta, padre);
            if (cuenta.getIdctapadre() == null) {
                nodo.setExpanded(true);
            }
=======
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
        if (tabActiva == 1) {
            proyectoActualCatalogo=proyectoBusquedaModal;
            PrimeFaces.current().ajax().update(":formTabs:tabs:datosProyectoCatalogo");
        } else if (tabActiva == 2) {
            cuentaActual.setObjProyecto(proyectoBusquedaModal);
            PrimeFaces.current().ajax().update(":formTabs:tabs:txtProyectoCta");
        }
        
        PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");
    }

    public void abrirModalBuscarProyectoEnTabCatalogo() {
        proyectoBusquedaModal = proyectoActualCatalogo;
        PrimeFaces.current().executeScript("PF('dialogProyectos').show()");
        tabActiva = 1;
    }

    public void abrirModalBuscarProyectoEnTabDetalle() {
        cuentaActual.getObjProyecto().setEstado(new ItemCatalogo(0));
        proyectoBusquedaModal = cuentaActual.getObjProyecto();
        PrimeFaces.current().executeScript("PF('dialogProyectos').show()");
        tabActiva = 2;
    }

    public void cerrarModalBuscarProyecto() {
        PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");
    }

    // ---- Metodos modal buscar cuenta ----
    public void mostrarModalBuscarCuenta(){
        Proyecto p=cuentaActual.getObjProyecto();
        
        Map filtros=new HashMap();
        filtros.put("cuentasPrincipales", true);
        filtros.put("proyecto",p);
        servCuenta.buscarCuentas(msjs);
        
        
    }
    
    public void cargarCuentaSeleccionadaModal(){
        System.out.println("");
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

>>>>>>> origin/master
            if (cuenta.getCuentaList().size() > 0) {
                //Ordenando las subcuentas por codigo
                for (int i = 0; i < cuenta.getCuentaList().size() - 1; i++) {

                    for (int j = 0; j < cuenta.getCuentaList().size() - 1; j++) {
<<<<<<< HEAD
                        int codigoPos1 = Integer.parseInt(cuenta.getCuentaList().get(j).getCodigo());
                        int codigoPos2 = Integer.parseInt(cuenta.getCuentaList().get(j + 1).getCodigo());
=======
                        int codigoPos1 = Integer.parseInt(cuenta.getCuentaList().get(j).getCuentaPK().getCodigo());
                        int codigoPos2 = Integer.parseInt(cuenta.getCuentaList().get(j + 1).getCuentaPK().getCodigo());
>>>>>>> origin/master
                        if (codigoPos1 > codigoPos2) {

                            Cuenta tmp = cuenta.getCuentaList().get(j + 1);

                            cuenta.getCuentaList().set(j + 1, cuenta.getCuentaList().get(j));

                            cuenta.getCuentaList().set(j, tmp);
                        }
                    }
                }

<<<<<<< HEAD
                recorrerCuentas(cuenta.getCuentaList(), nodo);
            }
        });
=======
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
>>>>>>> origin/master
    }
}
