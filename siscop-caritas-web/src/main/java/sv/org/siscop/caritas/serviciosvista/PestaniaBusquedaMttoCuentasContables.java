/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.serviciosvista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Leonardo Martinez
 */
public class PestaniaBusquedaMttoCuentasContables {

    private ServicioProyectoLocal servProyecto;
    private List<ItemCatalogo> estadosProyecto;
    private List<Proyecto> proyectosRegistrados;
    private Proyecto proyectoAbuscar;
    private Proyecto proyectoActual;

    public PestaniaBusquedaMttoCuentasContables() {
    }

    public PestaniaBusquedaMttoCuentasContables(ServicioProyectoLocal servProyecto, ServiciosCatalogoLocal servCatalogo) {
        this.servProyecto = servProyecto;
        resetPestaniaBusqueda();
        this.estadosProyecto = servCatalogo.findCatalogoById(20).getItemCatalogoList();
    }

    //================= GETTER Y SETTER ========================
    public List<Proyecto> getProyectosRegistrados() {
        return proyectosRegistrados;
    }

    public void setProyectosRegistrados(List<Proyecto> proyectosRegistrados) {
        this.proyectosRegistrados = proyectosRegistrados;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public Proyecto getProyectoAbuscar() {
        return proyectoAbuscar;
    }

    public void setProyectoAbuscar(Proyecto proyectoAbuscar) {
        this.proyectoAbuscar = proyectoAbuscar;
    }

    public List<ItemCatalogo> getEstadosProyecto() {
        return estadosProyecto;
    }

    public void setEstadosProyecto(List<ItemCatalogo> estadosProyecto) {
        this.estadosProyecto = estadosProyecto;
    }

    //================ METODOS DE SERVICIO ======================
    public void buscarProyectosRegistrados(Map filtros) {
        this.proyectosRegistrados = servProyecto.buscarProyetosCriterial(filtros);
    }

    public void resetPestaniaBusqueda() {
        //Iniciando la lista de proyectos a mostrar
        //Seran solo los proyectos en ejecucion
        Map filtros = new HashMap();
        filtros.put("estado", new ItemCatalogo(63));
        this.proyectosRegistrados = servProyecto.buscarProyetosCriterial(filtros);
        //Reseteando el proyecto actual
        this.proyectoActual = new Proyecto();
        //Reseteando el proyecto a buscar
        this.proyectoAbuscar = new Proyecto();
        this.proyectoAbuscar.setEstado(new ItemCatalogo(63));
    }

}
