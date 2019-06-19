/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import sv.org.siscop.caritas.ejb.ServicioActividadLocal;
import sv.org.siscop.caritas.entidades.Actividad;
import sv.org.siscop.caritas.validadores.ValidacionesActividad;

/**
 *
 * @author Nolasco
 */
@Named(value = "mttoActividades")
@ViewScoped
public class MttoActividades implements Serializable{
    @EJB
    ServicioActividadLocal servAct;
    
    //Propiedades para la logica de la ui
    private int tabActiva;
    private String campoBusqueda;
    private List actividades;
    //Propiedades para el modelo de catalogo
    private Actividad actividadActual;
    //Objeto para la validacion de la actividad
    private ValidacionesActividad validaciones;
    
    /**
     * Creates a new instance of MttoActividades
     */
    public MttoActividades() {
    }
    
    public void init(){
        tabActiva=0;
        campoBusqueda="";
        actividades= servAct.getAllActividades();
        validaciones=new ValidacionesActividad();
    }
    
    //================= GETTER Y SETTER =========================

    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
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
    
    
     //================= METODOS DE FUNCIONALIDAD =========================
    //---- Pestania busqueda 
    public void buscarActividad(){
        Map campos=new HashMap();
        campos.put("nombre", campoBusqueda);
        
        actividades=servAct.buscarActividadPorCualquierCampo(campos);
    }
    
    public void limpiarBusqueda(){
        actividadActual=new Actividad();
        campoBusqueda="";
        actividades=servAct.getAllActividades();
    }
    
    public void cargarActividadSeleccionada(){
        System.out.println("Cargar actividad seleccionada");
    }
    
    //---- Pestania actividad
    public void guardarActividad(){
        System.out.println("Guardar actividad");
    }
    
    public void limpiarFormularioActividadYbusqueda(){
        System.out.println("limpiar formulario actividad y busqueda");
    }
}
