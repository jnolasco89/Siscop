/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import sv.org.siscop.caritas.ejb.ServicioActividadLocal;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.entidades.Actividad;
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

    //Propiedades para la logica de la ui
    private int tabActiva;
    private String campoBusquedaProyecto;
    private String campoBusquedaActividad;
    private List actividades;
    private List<Proyecto> proyectos;
    private int modoRecurso;
    private SimpleDateFormat formateadorFecha;
    //Propiedades para el modelo de catalogo
    private Proyecto proyectoActual;
    private Actividad actividadActual;
    private List<Recurso> recursos;
    private Recurso recursoActual;
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
        campoBusquedaProyecto="";
        campoBusquedaActividad = "";
        formateadorFecha=new SimpleDateFormat("dd-MM-yyyy");
        modoRecurso=1;//Cuando es 1 indica agregar, 2 indica editar
        actividadActual=new Actividad();
        actividades = servAct.getAllActividades();
        proyectoActual=new Proyecto();
        proyectos=servProy.getAllProyectos();
        recursos = new ArrayList<>();
        recursoActual = new Recurso();
        validaciones = new ValidacionesActividad();
    }

    //================= GETTER Y SETTER =========================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public String getCampoBusquedaActividad() {
        return campoBusquedaActividad;
    }

    public void setCampoBusquedaActividad(String campoBusquedaActividad) {
        this.campoBusquedaActividad = campoBusquedaActividad;
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

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public Recurso getRecursoActual() {
        return recursoActual;
    }

    public void setRecursoActual(Recurso recursoActual) {
        this.recursoActual = recursoActual;
    }

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
    }

    public String getCampoBusquedaProyecto() {
        return campoBusquedaProyecto;
    }

    public void setCampoBusquedaProyecto(String campoBusquedaProyecto) {
        this.campoBusquedaProyecto = campoBusquedaProyecto;
    }

    
    //================= METODOS DE FUNCIONALIDAD =========================
    //---- Pestania busqueda proyecto
    public void buscarProyecto(){
        System.out.println("Buscar proyecto");
    }
    
    public void limpiarBusquedaProyecto(){
        proyectoActual=new Proyecto();
        campoBusquedaActividad="";
        proyectos=servProy.getAllProyectos();
        limpiarBusquedaActividad();
    }
    
    public void cargarProyectoSeleccionado(){
        System.out.println("Cargar proyecto seleccionado");
    }
    //---- Pestania busqueda 
    public void buscarActividad() {
        Map campos = new HashMap();
        campos.put("nombre", campoBusquedaActividad);

        actividades = servAct.buscarActividadPorCualquierCampo(campos);
    }

    public void limpiarBusquedaActividad() {
        actividadActual = new Actividad();
        campoBusquedaActividad = "";
        actividades = servAct.getAllActividades();
    }

    public void cargarActividadSeleccionada() {
        System.out.println("Cargar actividad seleccionada");
    }

    //---- Pestania actividad
    public void guardarActividad() {
            System.out.println("Guardar actividad");
    }

    public void limpiarFormularioActividadYbusqueda() {
        System.out.println("limpiar formulario actividad y busqueda");
    }
    
    //---- Subpestaña recursos
    public void accionBtnRecurso(){
        if(modoRecurso==1){
            agregarRecurso();
        }else if(modoRecurso==2){
            editarRecurso();
        }
    }
    
    public void agregarRecurso(){
        recursoActual.setTotal(recursoActual.getCantidad()*recursoActual.getCostounitario());
        recursos.add(recursoActual);
        recursoActual=new Recurso();
        modoRecurso=1;
    }
    
    public void editarRecurso(){
        recursos.get(recursoActual.getIndiceEnList()).setNombre(recursoActual.getNombre());
        recursos.get(recursoActual.getIndiceEnList()).setCantidad(recursoActual.getCantidad());
        recursos.get(recursoActual.getIndiceEnList()).setCostounitario(recursoActual.getCostounitario());
        recursos.get(recursoActual.getIndiceEnList()).setTotal(recursoActual.getCantidad()*recursoActual.getCostounitario());
        recursoActual=new Recurso();
        modoRecurso=1;
    }
    
    public void cargarRecursoSeleccionado(int index, Recurso r){
        r.setIndiceEnList(index);
        recursoActual=r;
        
        modoRecurso=2;
    }
    
    public void eliminarRecurso(int index){
        recursos.remove(index);
    }
    
    public void limpirFormularioYtablaRecurso(){
        recursoActual=new Recurso();
        recursos=new ArrayList<>();
        modoRecurso=1;
    }
    
    public void cancelarEdicionRecurso(){
        recursoActual=new Recurso();
        modoRecurso=1;
    }
    
    public boolean habilitarBtnCancelarEdicionRecurso(){
        return modoRecurso==1;
    }
    
    public String formatearFecha(Date fecha){
        if(fecha==null){
            return "";
        }
        
        return formateadorFecha.format(fecha);
    }
}
