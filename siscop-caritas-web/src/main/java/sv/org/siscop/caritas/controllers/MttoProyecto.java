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
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.entidades.Proyecto;
import sv.org.siscop.caritas.ejb.ServicioPersonaLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.util.Catalogos;

/**
 *
 * @author Henry
 */
@Named(value = "mttoProyecto")
@SessionScoped
public class MttoProyecto implements Serializable {

    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServiciosCatalogoLocal servCat;

    private final static Logger logger = Logger.getLogger(MttoProyecto.class.getName());

    public MttoProyecto() {
    }

    FacesContext facesContext = FacesContext.getCurrentInstance();
    //Campos de búsqueda
    private Long codigoB;
    private String nombreB;
    private String nombreCortoB;

    //Propiedades proyecto
    private Proyecto proyectoActual = new Proyecto();
    private Proyecto proyectoB = new Proyecto();
    boolean esProyNuevo = false;
    private String nombre;
    private String nombreCorto;
    private String codigoProyecto;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idEstado;

    int tabindex = 0;

    //Listas
    private List<Proyecto> proyectosList = new ArrayList<>();

    //SelectItems
    private List<SelectItem> itemEstado = new ArrayList<>();

    public void onTabChange(TabChangeEvent event) {
        this.tabindex = ((TabView) event.getSource()).getIndex();
    }

    public int getTabindex() {
        return tabindex;
    }

    public void setTabindex(int tabindex) {
        this.tabindex = tabindex;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter y Setter Busqueda de Proyectos">
    public String getNombreB() {
        return nombreB;
    }

    public void setNombreB(String nombreB) {
        this.nombreB = nombreB;
    }

    public String getNombreCortoB() {
        return nombreCortoB;
    }

    public void setNombreCortoB(String nombreCortoB) {
        this.nombreCortoB = nombreCortoB;
    }

    public Long getCodigoB() {
        return codigoB;
    }

    public void setCodigoB(Long codigoB) {
        this.codigoB = codigoB;
    }

    public Proyecto getProyectoB() {
        return proyectoB;
    }

    public void setProyectoB(Proyecto proyectoB) {
        this.proyectoB = proyectoB;
    }

    public List<Proyecto> getProyectosList() {
        return proyectosList;
    }

    public void setProyectosList(List<Proyecto> proyectosList) {
        this.proyectosList = proyectosList;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getter y Setter Proyecto">
    public boolean isEsProyNuevo() {
        return esProyNuevo;
    }

    public void setEsProyNuevo(boolean esProyNuevo) {
        this.esProyNuevo = esProyNuevo;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public String getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public List<SelectItem> getItemEstado() {
        try {
            itemEstado.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.ESTADO_PROYECTO.getCodigo()).getItemCatalogoList()) {
                itemEstado.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return itemEstado;
    }

//</editor-fold>
    public void buscarProyectos() {

        try {
            Map filtro = new HashMap();
            boolean hay = false;

            if (nombreB != null && !nombreB.isEmpty()) {
                filtro.put("nombre", nombreB);
                hay = true;
            }
            if (nombreCortoB != null && !nombreCortoB.isEmpty()) {
                filtro.put("nombrecorto", nombreCortoB);
                hay = true;
            }

            if (!hay) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Agregue un parámetro de búsqueda", null);
                return;
            }

            proyectosList = servProyecto.buscarProyectos(filtro);

            if (proyectosList.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarBusquedaProyectos() {
        proyectosList = new ArrayList<>();
        codigoB = null;
        nombreB = "";
    }

    public void limpiarProyecto() {

        proyectoActual = new Proyecto();
        esProyNuevo = false;
        nombre = "";
        nombreCorto = "";
        codigoProyecto = "";
        descripcion = "";
        fechaInicio = null;
        fechaFin = null;
        idEstado = 0;

    }

    public void onRowSelect(SelectEvent event) throws IOException {
        try {
            limpiarProyecto();

            proyectoActual = new Proyecto();
            proyectoActual = proyectoB;

            esProyNuevo = false;
            nombre = proyectoActual.getNombre();
            nombreCorto = proyectoActual.getNombreCorto();
            codigoProyecto = proyectoActual.getCodigo();
            descripcion = proyectoActual.getDescripcion();
            fechaInicio = proyectoActual.getFechaini();
            fechaFin = proyectoActual.getFechafin();
            idEstado = proyectoActual.getEstado().getId();

            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoProyecto() {
        try {
            limpiarProyecto();
            esProyNuevo = true;
            this.proyectoActual = new Proyecto();

            this.showMessage(FacesMessage.SEVERITY_WARN,
                    "Ingrese datos de nuevo proyecto.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarProyecto() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (nombre == null || nombre.isEmpty()) {
                campos.add("Nombre");
            }
            if (nombreCorto == null || nombreCorto.isEmpty()) {
                campos.add("Nombre Corto");
            }
            if (codigoProyecto == null || codigoProyecto.isEmpty()) {
                campos.add("Código");
            }
            if (idEstado == 0) {
                mensajes.add("Estado");
            }
            if (fechaInicio == null) {
                campos.add("Fecha de Inicio");
            }
            if (fechaInicio == null) {
                campos.add("Fecha Fin");
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
                    "Error al validar persona.", ex.getLocalizedMessage());
            logger.log(Level.SEVERE, null, ex);
        }
        return hay;
    }

    public void guardarProyecto() {
        try {
            if (validarProyecto()) {
                return;
            }

            this.proyectoActual.setNombre(nombre);
            this.proyectoActual.setNombreCorto(nombreCorto);
            this.proyectoActual.setCodigo(codigoProyecto);
            this.proyectoActual.setDescripcion(descripcion);
            this.proyectoActual.setFechaini(fechaInicio);
            this.proyectoActual.setFechafin(fechaFin);
            this.proyectoActual.setEstado(servCat.findItemCatalogoById(idEstado));

            if (esProyNuevo) {
                this.servProyecto.nuevoProyecto(proyectoActual);
            } else {
                proyectoActual = this.servProyecto.actualizarProyecto(proyectoActual);
            }

            this.showMessage(FacesMessage.SEVERITY_INFO, "Proyecto guardado exitosamente.", null);

            esProyNuevo = false;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar proyecto.", ex.getLocalizedMessage());
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
