/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.org.siscop.caritas.ejb.ServicioActividadLocal;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.Actividad;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;
import sv.org.siscop.caritas.entidades.Recurso;
import sv.org.siscop.caritas.validadores.ValidacionesActividad;

/**
 *
 * @author Nolasco
 */
@Named(value = "mttoActividades")
@ViewScoped
public class MttoActividades implements Serializable {

    @EJB
    ServicioProyectoLocal servProy;
    @EJB
    ServicioActividadLocal servAct;
    @EJB
    ServiciosCatalogoLocal servCat;

    //Propiedades para la logica de la ui
    private int tabActiva;
    private int opcionesSeteoActividad;
    private int modoRecurso;
    private int modoActividad;
    private List<Actividad> actividades;
    private List<Proyecto> proyectos;
    private List<ItemCatalogo> estadosActividad;
    private String elementosUpdate;
    //Propiedades para el modelo de catalogo
    private Actividad actividadActual;
    private Actividad actividadBusqueda;
    private Recurso recursoActual;
    private List<Recurso> recursos;
    private Proyecto proyectoActual;
    //Objeto para la validacion de la actividad
    private ValidacionesActividad validaciones;

    /**
     * Creates a new instance of MttoActividades
     */
    public MttoActividades() {
    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
        opcionesSeteoActividad = 0;
        modoRecurso = 1;//1 para agregar, 2 para editar
        modoActividad = 1;//1 para agregar, 2 para editar
        actividades = servAct.getAllActividades();
        proyectos = servProy.getAllProyectos();
        estadosActividad = (servCat.findCatalogoById(20)).getItemCatalogoList();
        elementosUpdate = "";

        actividadActual = new Actividad();
        actividadActual.setEstado(new ItemCatalogo());
        recursoActual = new Recurso();
        recursos = new ArrayList<>();
        actividadBusqueda = new Actividad();
        actividadBusqueda.setIdproyecto(new Proyecto());
        actividadBusqueda.setEstado(new ItemCatalogo(63));
        proyectoActual = new Proyecto();

        validaciones = new ValidacionesActividad();
    }

    //================= GETTER Y SETTER =========================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public List getActividades() {
        return actividades;
    }

    public void setActividades(List actividades) {
        this.actividades = actividades;
    }

    public Actividad getActividadActual() {
        return actividadActual;
    }

    public void setActividadActual(Actividad actividadActual) {
        this.actividadActual = actividadActual;
    }

    public ValidacionesActividad getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(ValidacionesActividad validaciones) {
        this.validaciones = validaciones;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Actividad getActividadBusqueda() {
        return actividadBusqueda;
    }

    public void setActividadBusqueda(Actividad actividadBusqueda) {
        this.actividadBusqueda = actividadBusqueda;
    }

    public List<ItemCatalogo> getEstadosActividad() {
        return estadosActividad;
    }

    public void setEstadosActividad(List<ItemCatalogo> estadosActividad) {
        this.estadosActividad = estadosActividad;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public Recurso getRecursoActual() {
        return recursoActual;
    }

    public void setRecursoActual(Recurso recursoActual) {
        this.recursoActual = recursoActual;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    //================= METODOS DE FUNCIONALIDAD =========================
    //---- Pestania busqueda proyecto ----
    public void buscarActividad() {
        Map campos = new HashMap();
        if (actividadBusqueda.getIdproyecto().getId() == null) {
            actividadBusqueda.setIdproyecto(null);
        }
        if (actividadBusqueda.getNombre() == null) {
            actividadBusqueda.setNombre(null);
        }
        if (actividadBusqueda.getEstado().getId() == 0) {
            actividadBusqueda.setEstado(null);
        }

        campos.put("proyecto", actividadBusqueda.getIdproyecto());
        campos.put("nombreActividad", actividadBusqueda.getNombre());
        campos.put("fechaInicio", actividadBusqueda.getFechainicio());
        campos.put("fechaFin", actividadBusqueda.getFechafin());
        campos.put("estado", actividadBusqueda.getEstado());

        actividades = servAct.buscarActividadPorCualquierCampo(campos);
        
        if(actividadBusqueda.getIdproyecto()==null){
            actividadBusqueda.setIdproyecto(new Proyecto());
        }
        if(actividadBusqueda.getEstado()==null){
            actividadBusqueda.setEstado(new ItemCatalogo(0));
        }
    }

    public void limpiarBusquedaActividad() {
        tabActiva=0;
        actividadBusqueda = new Actividad();
        actividadBusqueda.setIdproyecto(new Proyecto());
        actividadBusqueda.setEstado(new ItemCatalogo(63));

        actividades = servAct.getAllActividades();
    }

    public void abrirModalBuscarProyecto() {
        proyectoActual = actividadBusqueda.getIdproyecto();
        opcionesSeteoActividad = 1;//Uno para setear actividadBusueda
        elementosUpdate = "formTabs:tabs:txtBusProyecto";
        PrimeFaces.current().executeScript("PF('dialogProyectos').show()");
    }

    public void cargarActividadSeleccionada() {
        recursos = actividadActual.getRecursoList();
        recursos.forEach((r) -> {
            r.setEliminar(false);
            r.setTotal(r.getCostounitario() * r.getCantidad());
        });
        proyectoActual = actividadActual.getIdproyecto();

        postSeleccionActividadAeditar();
    }

    //---- Pestaña Actividad ----
    public void guardarActividad() {
        String msjGrwol = null;

        if (modoActividad == 1) {
            actividadActual.setEstado(new ItemCatalogo(63));
            recursos.forEach((r) -> {
                r.setIdactividad(actividadActual);
            });
            actividadActual.setRecursoList(recursos);
            servAct.agregarActividad(actividadActual);
            msjGrwol = "Actividad creada";

            postCreacionActividad();
        } else if (modoActividad == 2) {
            recursos.forEach((r)->{
                r.setIdactividad(actividadActual);
            });
            actividadActual.setRecursoList(recursos);
            servAct.editarActiviad(actividadActual);
            postEdicionActividad();
            msjGrwol = "Actividad editada";
        }

        FacesContext.getCurrentInstance()
                .addMessage("msgGrowl",
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Operacion exitosa",
                                msjGrwol));
        PrimeFaces.current().ajax().update(":formTabs");
    }

    public void limpiarFormularioActividadYbusqueda() {
        modoActividad = 1;
        actividadActual.getRecursoList().forEach((r)->{
            r.setEliminar(false);
        });
        actividadActual = new Actividad();
        actividadActual.setEstado(new ItemCatalogo());
        recursos = new ArrayList<>();
        modoRecurso = 1;
        proyectoActual = new Proyecto();
    }

    public void abrirModalAsignarProyecto() {
        proyectoActual = actividadActual.getIdproyecto();
        opcionesSeteoActividad = 2;//Uno para setear actividadBusueda
        elementosUpdate = "formTabs:tabs:txtProyecto";
        PrimeFaces.current().executeScript("PF('dialogProyectos').show()");
    }

    public void accionBtnRecurso() {
        if (modoRecurso == 1) {
            agregarRecurso();
        } else if (modoRecurso == 2) {
            editarRecurso();
        }
    }

    public void agregarRecurso() {
        recursoActual.setTotal(recursoActual.getCantidad() * recursoActual.getCostounitario());
        recursoActual.setNuevo(true);
        recursos.add(recursoActual);
        recursoActual = new Recurso();
        modoRecurso = 1;
    }

    public void editarRecurso() {
        recursos.get(recursoActual.getIndiceEnList()).setNombre(recursoActual.getNombre());
        recursos.get(recursoActual.getIndiceEnList()).setCantidad(recursoActual.getCantidad());
        recursos.get(recursoActual.getIndiceEnList()).setCostounitario(recursoActual.getCostounitario());
        recursos.get(recursoActual.getIndiceEnList()).setTotal(recursoActual.getCantidad() * recursoActual.getCostounitario());
        recursoActual = new Recurso();
        modoRecurso = 1;
    }

    public void limpiarFormularioYtablaRecurso() {
        postLimpiezaPestaniaActividad();
//        recursoActual = new Recurso();
//        recursos = new ArrayList<>();
//        modoRecurso = 1;
    }

    public void cancelarEdicionRecurso() {
        recursoActual = new Recurso();
        modoRecurso = 1;
    }

    public boolean habilitarBtnCancelarEdicionRecurso() {
        return modoRecurso != 2;
    }

    public void eliminarRecurso(int index) {
        recursos.remove(index);
    }

    public void cargarRecursoSeleccionado(int index, Recurso r) {
        r.setIndiceEnList(index);
        recursoActual = r;

        modoRecurso = 2;

    }

    public boolean verListEstado() {
        return modoActividad == 2;
    }

    public boolean verBtnEliminarRecursoDeBd(Recurso r) {
        return modoActividad == 2 && !r.isNuevo();
    }

    public boolean verBtnEliminarRecursoVista(Recurso r) {
        if(modoActividad==1 && r.isNuevo()){
            return true;
        }else if(modoActividad==2 && r.isNuevo()){
            return true;
        }
        
        return false;
    }

    public String classBtnElminarRecursoDeBd(Recurso r) {
        if (r.isEliminar()) {
            return "btn-eliminar-on";
        } else {
            return "btn-eliminar-off";
        }
    }

    public String classTextoTachada(Recurso r) {
        if (r == null) {
            return "";
        } else if (r.isEliminar()) {
            return "texto-tachado";
        } else {
            return "";
        }
    }

    //---- Modal buscar Proyecto ----
    public void cargarProyectoSeleccionado() {
        if (opcionesSeteoActividad == 1) {
            actividadBusqueda.setIdproyecto(proyectoActual);
        } else if (opcionesSeteoActividad == 2) {
            actividadActual.setIdproyecto(proyectoActual);
        }

        PrimeFaces.current().ajax().update(elementosUpdate);
        PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");
    }

    public void cerrarModalProyectos() {
        proyectoActual = new Proyecto();
        PrimeFaces.current().executeScript("PF('dialogProyectos').hide()");
    }

    //================== ESTADOS DE LA VISTA ================
    public void postCreacionActividad() {
        //Propiedades para la logica de la ui
        tabActiva = 1;
        modoRecurso = 1;
        modoActividad = 1;
        actividades = servAct.getAllActividades();
        //Propiedades para el modelo de catalogo
        actividadActual = new Actividad();
        actividadActual.setEstado(new ItemCatalogo());
        recursoActual = new Recurso();
        recursos = new ArrayList<>();
        proyectoActual = new Proyecto();
    }

    public void postEdicionActividad() {
        //Propiedades para la logica de la ui
        tabActiva = 1;
        modoRecurso = 1;
        modoActividad = 1;
        actividades = servAct.getAllActividades();
        //Propiedades para el modelo de catalogo
        actividadActual = new Actividad();
        actividadActual.setEstado(new ItemCatalogo());
        recursoActual = new Recurso();
        recursos = new ArrayList<>();
        proyectoActual = new Proyecto();
    }

    public void postSeleccionActividadAeditar() {
        //Propiedades para la logica de la ui
        tabActiva = 1;
        modoRecurso = 1;
        modoActividad = 2;
        //Propiedades para el modelo de catalogo
        recursoActual = new Recurso();
    }

    public void postLimpiezaPestaniaActividad() {
        //Propiedades para la logica de la ui
        tabActiva = 1;
        modoRecurso = 1;
        modoActividad = 1;
        //Propiedades para el modelo de catalogo
        actividadActual = new Actividad();
        actividadActual.setEstado(new ItemCatalogo());
        recursoActual = new Recurso();
        recursos = new ArrayList<>();
        proyectoActual = new Proyecto();
    }
}
