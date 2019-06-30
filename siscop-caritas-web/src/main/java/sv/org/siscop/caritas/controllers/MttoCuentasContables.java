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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
    private List<Proyecto> proyectos;
    private List<ItemCatalogo> estadosProyecto;
    //Modelo para la vista
    private Proyecto proyectoActual;
    private Proyecto proyectoCatalogo;
    private List<Cuenta> catalogoDeCuentas;

    /**
     * Creates a new instance of MttoCuentasContables
     */
    public MttoCuentasContables() {
    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
        proyectos = servProyecto.getAllProyectos();
        proyectoActual = new Proyecto();
        proyectoActual.setEstado(new ItemCatalogo(63));
        proyectoCatalogo = new Proyecto();
        estadosProyecto = servCatalogo.findCatalogoById(20).getItemCatalogoList();
        catalogoDeCuentas=null;
    }

    // ================== GETTER Y SETTER ==================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
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

    public List<ItemCatalogo> getEstadosProyecto() {
        return estadosProyecto;
    }

    public void setEstadosProyecto(List<ItemCatalogo> estadosProyecto) {
        this.estadosProyecto = estadosProyecto;
    }

    public Proyecto getProyectoCatalogo() {
        return proyectoCatalogo;
    }

    public void setProyectoCatalogo(Proyecto proyectoCatalogo) {
        this.proyectoCatalogo = proyectoCatalogo;
    }

    public List<Cuenta> getCatalogoDeCuentas() {
        return catalogoDeCuentas;
    }

    public void setCatalogoDeCuentas(List<Cuenta> catalogoDeCuentas) {
        this.catalogoDeCuentas = catalogoDeCuentas;
    }

    // =================== METODOS =========================
    // ----- Pestaña busqueda --
    public void buscarProyecto() {
        Map filtros = new HashMap();
        filtros.put("codigo", null);
        filtros.put("nombre", null);
        filtros.put("nombreCorto", null);
        filtros.put("fechaIni", null);
        filtros.put("fechaFin", null);
        filtros.put("estado", null);

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
        
    }

    // ----- Pestaña catalogo --
}
