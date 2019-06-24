/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.org.siscop.caritas.ejb.ServicioCotizacionLocal;
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Planitem;
import sv.org.siscop.caritas.util.Catalogos;

/**
 *
 * @author Henry
 */
@Named(value = "mttoCotizacion")
@SessionScoped
public class MttoCotizacion implements Serializable {

    @EJB
    private ServicioCotizacionLocal servCotizacion;
    @EJB
    private ServiciosCatalogoLocal servCat;

    private final static Logger logger = Logger.getLogger(MttoCotizacion.class.getName());

    public MttoCotizacion() {
    }

    FacesContext facesContext = FacesContext.getCurrentInstance();
    //Campos de búsqueda
    private Long idProyectoB;
    private String descripcionB;
    private Integer estadoActividadB;

    //Propiedades proyecto
    private Plancotizacion planCotizacionActual = new Plancotizacion();
    private Plancotizacion plancotizacionB = new Plancotizacion();
    boolean esPlantillaNueva = false;
    private String descripcion;
    private Date fecha;

    int tabindex = 0;

    //Listas
    private List<Plancotizacion> planCotizacionList = new ArrayList<>();
    private List<Planitem> itemPlanCotizacionList = new ArrayList<>();

    //SelectItems
    private List<SelectItem> itemEstadoActividad = new ArrayList<>();

    public void onTabChange(TabChangeEvent event) {
        this.tabindex = ((TabView) event.getSource()).getIndex();
    }

    public int getTabindex() {
        return tabindex;
    }

    public void setTabindex(int tabindex) {
        this.tabindex = tabindex;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter y Setter Busqueda de Plancotizacions">
    public Long getIdProyectoB() {
        return idProyectoB;
    }

    public void setIdProyectoB(Long idProyectoB) {
        this.idProyectoB = idProyectoB;
    }

    public String getDescripcionB() {
        return descripcionB;
    }

    public void setDescripcionB(String descripcionB) {
        this.descripcionB = descripcionB;
    }

    public Integer getEstadoActividadB() {
        return estadoActividadB;
    }

    public void setEstadoActividadB(Integer estadoActividadB) {
        this.estadoActividadB = estadoActividadB;
    }

    public List<Plancotizacion> getPlanCotizacionList() {
        return planCotizacionList;
    }

    public void setPlanCotizacionList(List<Plancotizacion> planCotizacionList) {
        this.planCotizacionList = planCotizacionList;
    }

    public List<Planitem> getItemPlanCotizacionList() {
        return itemPlanCotizacionList;
    }

    public void setItemPlanCotizacionList(List<Planitem> itemPlanCotizacionList) {
        this.itemPlanCotizacionList = itemPlanCotizacionList;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getter y Setter Plancotizacion">
    public boolean isEsPlantillaNueva() {
        return esPlantillaNueva;
    }

    public void setEsPlantillaNueva(boolean esPlantillaNueva) {
        this.esPlantillaNueva = esPlantillaNueva;
    }

    public Plancotizacion getPlancotizacionActual() {
        return planCotizacionActual;
    }

    public void setPlancotizacionActual(Plancotizacion proyectoActual) {
        this.planCotizacionActual = proyectoActual;
    }

    public Plancotizacion getPlancotizacionB() {
        return plancotizacionB;
    }

    public void setPlancotizacionB(Plancotizacion plancotizacionB) {
        this.plancotizacionB = plancotizacionB;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<SelectItem> getItemEstadoActividad() {
        try {
            itemEstadoActividad.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.ESTADO_ACTIVIDAD.getCodigo()).getItemCatalogoList()) {
                itemEstadoActividad.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return itemEstadoActividad;
    }

//</editor-fold>
    public void buscarPlancotizaciones() {

        try {
            Map filtro = new HashMap();
            if (descripcionB != null && !descripcionB.isEmpty()) {
                filtro.put("descripcion", descripcionB);
            }
            if (idProyectoB != null && idProyectoB != 0) {
                filtro.put("idproyecto", idProyectoB);
            }
            if (estadoActividadB != 0) {
                filtro.put("idestado", estadoActividadB);
            }

            if (filtro.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Agregue un parámetro de búsqueda", null);
                return;
            }

            planCotizacionList = servCotizacion.buscarPlancotizaciones(filtro);

            if (planCotizacionList.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarBusquedaPlancotizaciones() {
        planCotizacionList = new ArrayList<>();
        idProyectoB = null;
        descripcionB = "";
        idProyectoB = 0L;
        estadoActividadB = 0;
    }

    public void limpiarPlancotizacion() {

        planCotizacionActual = new Plancotizacion();
        esPlantillaNueva = false;
        descripcion = "";
        fecha = null;

    }

    public void onRowSelect(SelectEvent event) throws IOException {
        try {
            limpiarPlancotizacion();

            planCotizacionActual = new Plancotizacion();
            planCotizacionActual = plancotizacionB;

            esPlantillaNueva = false;
            descripcion = planCotizacionActual.getDescripcion();
            fecha = planCotizacionActual.getFecha();

            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoPlancotizacion() {
        try {
            limpiarPlancotizacion();
            esPlantillaNueva = true;
            this.planCotizacionActual = new Plancotizacion();

            this.showMessage(FacesMessage.SEVERITY_WARN,
                    "Ingrese datos de nueva cotización.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarPlancotizacion() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (descripcion == null || descripcion.isEmpty()) {
                campos.add("Descripcion");
            }
            if (fecha == null) {
                campos.add("Fecha de Inicio");
            }

            String camposFaltan = campos.stream().collect(Collectors.joining(", "));
            if (!camposFaltan.isEmpty()) {
                mensajes.add("Verifique los siguientes campos: " + camposFaltan);
            }

            for (String msj : mensajes) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN, msj, null);
            }

        } catch (Exception ex) {
            this.showMessage(FacesMessage.SEVERITY_WARN,
                    "Error al validar Plantilla.", ex.getLocalizedMessage());
            logger.log(Level.SEVERE, null, ex);
        }
        return hay;
    }

    public void guardarPlancotizacion() {
        try {
            if (validarPlancotizacion()) {
                return;
            }

            this.planCotizacionActual.setDescripcion(descripcion);
            this.planCotizacionActual.setFecha(fecha);
            this.planCotizacionActual.setPlanitemList(itemPlanCotizacionList);

            if (esPlantillaNueva) {
                this.servCotizacion.nuevoPlancotizacion(planCotizacionActual);
            } else {
                planCotizacionActual = this.servCotizacion.actualizarPlancotizacion(planCotizacionActual);
            }

            this.showMessage(FacesMessage.SEVERITY_INFO, "Plantilla guardada exitosamente.", null);

            esPlantillaNueva = false;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar plantilla.", ex.getLocalizedMessage());
        }
    }

    public void showMessage(FacesMessage.Severity severidad, String error, String desc) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage mensaje = new FacesMessage(severidad, error, desc);
        context.addMessage("msgGrowl", mensaje);
    }

    public void showDialogo(String mensaje) {

        PrimeFaces.current().executeScript("PF('dlgAsigCte').show()");

    }

}
