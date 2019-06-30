/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.org.siscop.caritas.ejb.ServicioCotizacionLocal;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.Cotizacion;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Itemcotizacion;
import sv.org.siscop.caritas.entidades.Planitem;
import sv.org.siscop.caritas.entidades.Proyecto;
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
    @EJB
    private ServicioProyectoLocal servProyecto;

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
    private Planitem planItemActual = new Planitem();
    private Plancotizacion plancotizacionB = new Plancotizacion();
    boolean esPlantillaNueva = false;
    private String descripcion;
    private String analisis;
    private Date fecha = new Date();

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

    public Planitem getPlanItemActual() {
        return planItemActual;
    }

    public void setPlanItemActual(Planitem planItemActual) {
        this.planItemActual = planItemActual;
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

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
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
        itemPlanCotizacionList = new ArrayList<>();
        fecha = null;
        descripcion = "";
        proyectoActual = new Proyecto();
    }

    public void onRowSelectPlan(SelectEvent event) throws IOException {
        try {
            limpiarPlancotizacion();

            planCotizacionActual = new Plancotizacion();
            planCotizacionActual = plancotizacionB;

            descripcion = planCotizacionActual.getDescripcion();
            fecha = planCotizacionActual.getFecha();
            itemPlanCotizacionList = planCotizacionActual.getPlanitemList();
            listaCotizaciones = planCotizacionActual.getCotizacionList();

            //proyectoActual =planCotizacionActual.getIdproyecto();
            esPlantillaNueva = false;
            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoPlancotizacion() {
        try {

            limpiarPlancotizacion();
            limpiarItemPlan();
            esPlantillaNueva = true;

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
            this.planCotizacionActual.setAnalisis(analisis);
            this.planCotizacionActual.setPlanitemList(itemPlanCotizacionList);
            this.planCotizacionActual.setCotizacionList(listaCotizaciones);
            if (proyectoActual != null) {
                this.planCotizacionActual.setIdproyecto(proyectoActual.getId());
            }

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

    //Campos de búsqueda de proyecto
    private Integer orden;
    private String producto;
    private String descproducto;
    private Integer idItemMedida;
    private Integer cantidad;

//    private Plancotizacion plantilla = new Plancotizacion()
    //Listas
    private List<Planitem> planItemList = new ArrayList<>();

    //SelectItems
    private List<SelectItem> itemMedida = new ArrayList<>();

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescproducto() {
        return descproducto;
    }

    public void setDescproducto(String descproducto) {
        this.descproducto = descproducto;
    }

    public Integer getIdItemMedida() {
        return idItemMedida;
    }

    public void setIdItemMedida(Integer idItemMedida) {
        this.idItemMedida = idItemMedida;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<Planitem> getPlanItemList() {
        return planItemList;
    }

    public void setPlanItemList(List<Planitem> planItemList) {
        this.planItemList = planItemList;
    }

    public List<SelectItem> getItemMedida() {
        try {
            itemMedida.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.UNIDAD_MEDIDA.getCodigo()).getItemCatalogoList()) {
                itemMedida.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return itemMedida;
    }

    public void limpiarItemPlan() {
        try {
            planItemActual = new Planitem();
            idItemMedida = 0;
            descproducto = "";
            producto = "";
            cantidad = null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void agregarPlanItem() {
        try {
            if (esPlantillaNueva) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Error al guardar plantilla.", "Guarde la planitalla primero.");
                return;
            }

            Planitem item = new Planitem();
//            item.setId(Long.valueOf(cantidad));
            item.setPlancotizacion(planCotizacionActual);
            item.setProducto(producto);
            item.setDescripcion(descproducto);
            item.setCantidad(cantidad);
            item.setMedida(servCat.findItemCatalogoById(idItemMedida));
            servCotizacion.nuevoPlanItem(item);

            itemPlanCotizacionList.add(item);
            limpiarItemPlan();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelectPlanItem(SelectEvent event) throws IOException {
        try {
//            limpiarPlancotizacion();
//
//            planCotizacionActual = new Plancotizacion();
//            planCotizacionActual = plancotizacionB;
//
//            esPlantillaNueva = false;
//            descripcion = planCotizacionActual.getDescripcion();
//            fecha = planCotizacionActual.getFecha();
//
//            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowCancelPlanItem(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada",
                ((Planitem) event.getObject()).getCantidad().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEditPlanItem(CellEditEvent event) throws Exception {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        FacesContext context = FacesContext.getCurrentInstance();
        Planitem entity = context.getApplication().evaluateExpressionGet(context, "#{planitem}", Planitem.class);

        this.servCotizacion.actualizarPlanItem(entity);
    }
    
    
     public void eliminarPlanItem(Planitem item) {
        try {
            item.setPlancotizacion(null);
            itemPlanCotizacionList.remove(item);
            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Eliminado de la lista.", null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void onRowReorderPlanItem(ReorderEvent event) {
        try {
//            Planitem oldValue = (Planitem) event.getSource();

            FacesContext context = FacesContext.getCurrentInstance();

            Planitem entity = (Planitem) ((DataTable) event.getComponent()).getRowData();
//            Planitem entity = context.getApplication().evaluateExpressionGet(context, "#{planitem}", Planitem.class);
            entity.setOrden(event.getToIndex());

            this.servCotizacion.actualizarPlanItem(entity);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: "
                    + event.getFromIndex() + ", To:" + event.getToIndex());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowReorderPlanItem(int index) {
        try {

            FacesContext context = FacesContext.getCurrentInstance();

            Planitem entity = context.getApplication().evaluateExpressionGet(context, "#{planitem}", Planitem.class);
            entity.setOrden(index);

            this.servCotizacion.actualizarPlanItem(entity);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    private List<Cotizacion> listaCotizaciones = new ArrayList<>();
    private List<Itemcotizacion> listaItemCotizacion = new ArrayList<>();
    private Cotizacion cotizacionActual = new Cotizacion();
    private Itemcotizacion itemCotizacionActual = new Itemcotizacion();
    private String comenCotizacion = new String();
    private Date fechaCotizacion = new Date();

    public List<Cotizacion> getListaCotizaciones() {
        return listaCotizaciones;
    }

    public void setListaCotizaciones(List<Cotizacion> listaCotizaciones) {
        this.listaCotizaciones = listaCotizaciones;
    }

    public List<Itemcotizacion> getListaItemCotizacion() {
        return listaItemCotizacion;
    }

    public void setListaItemCotizacion(List<Itemcotizacion> listaItemCotizacion) {
        this.listaItemCotizacion = listaItemCotizacion;
    }

    public Cotizacion getCotizacionActual() {
        return cotizacionActual;
    }

    public void setCotizacionActual(Cotizacion cotizacionActual) {
        this.cotizacionActual = cotizacionActual;
    }

    public Itemcotizacion getItemCotizacionActual() {
        return itemCotizacionActual;
    }

    public void setItemCotizacionActual(Itemcotizacion itemCotizacionActual) {
        this.itemCotizacionActual = itemCotizacionActual;
    }

    public String getComenCotizacion() {
        return comenCotizacion;
    }

    public void setComenCotizacion(String comenCotizacion) {
        this.comenCotizacion = comenCotizacion;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public void onRowSelectCotizacion(SelectEvent event) throws IOException {
        try {

            listaItemCotizacion = cotizacionActual.getItemcotizacionList();

//            limpiarPlancotizacion();
//
//            planCotizacionActual = new Plancotizacion();
//            planCotizacionActual = plancotizacionB;
//
//            esPlantillaNueva = false;
//            descripcion = planCotizacionActual.getDescripcion();
//            fecha = planCotizacionActual.getFecha();
//
//            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelectItemCotizacion(SelectEvent event) throws IOException {
        try {
//            limpiarPlancotizacion();
//
//            planCotizacionActual = new Plancotizacion();
//            planCotizacionActual = plancotizacionB;
//
//            esPlantillaNueva = false;
//            descripcion = planCotizacionActual.getDescripcion();
//            fecha = planCotizacionActual.getFecha();
//
//            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowCancelItemCotizacion(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada",
                ((Planitem) event.getObject()).getCantidad().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEditItemCotizacion(CellEditEvent event) throws Exception {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        FacesContext context = FacesContext.getCurrentInstance();
        Itemcotizacion entity = context.getApplication().evaluateExpressionGet(context, "#{item}", Itemcotizacion.class);

//        if (entity.getLecturaini() > entity.getLecturafin()) {
//            entity.setLecturafin(null);
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//                    "Error en ingreso", "Lectura debe ser igual o mayor a inicial.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            return;
//        }
        //Set total
        if (entity.getPreciounitario() != null && entity.getCantidad() != null) {
            BigDecimal total = entity.getPreciounitario()
                    .multiply(BigDecimal.valueOf(entity.getCantidad()));

            entity.setTotal(total);
        }
        this.servCotizacion.actualizarItemCotizacion(entity);

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correcto", "Anterior: 0"
                    + (oldValue == null ? "" : oldValue)
                    + ", Nueva:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void limpiarItemCotizacion() {
        try {
            itemCotizacionActual = new Itemcotizacion();
            fechaCotizacion = null;
            comenCotizacion = "";
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void agregarCotizacion() {
        try {
            Cotizacion coti = new Cotizacion();
            coti.setComentarios(comenCotizacion);
            coti.setFecha(fechaCotizacion);
            coti.setPlantilla(planCotizacionActual);
            coti.setProveedor(null);

            List<Itemcotizacion> listaItemCotiza = new ArrayList<>();

            for (Planitem pi : itemPlanCotizacionList) {
                Itemcotizacion itemcoti = new Itemcotizacion();
                itemcoti.setDescripcion(pi.getDescripcion());
                itemcoti.setProducto(pi.getProducto());
                itemcoti.setMedida(pi.getMedida());
                itemcoti.setCotizacion(coti);
                itemcoti.setOrden(pi.getOrden());
                itemcoti.setCantidad(pi.getCantidad());
                itemcoti.setPlanItem(pi);

                //servCotizacion.nuevoPlanItem(pi);
                listaItemCotiza.add(itemcoti);
            }

            coti.setItemcotizacionList(listaItemCotiza);
            servCotizacion.nuevaCotizacion(coti);
            listaCotizaciones.add(coti);

            limpiarItemCotizacion();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    //Campos de búsqueda de proyecto
    private Long codigoB;
    private String nombreB;
    private String nombreCortoB;
    private Integer idEstadoB;

    private Proyecto proyectoB = new Proyecto();
    private Proyecto proyectoActual = new Proyecto();

    //Listas
    private List<Proyecto> proyectosList = new ArrayList<>();

    //SelectItems
    private List<SelectItem> itemEstado = new ArrayList<>();

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

    public Integer getIdEstadoB() {
        return idEstadoB;
    }

    public void setIdEstadoB(Integer idEstadoB) {
        this.idEstadoB = idEstadoB;
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

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public List<Proyecto> getProyectosList() {
        return proyectosList;
    }

    public void setProyectosList(List<Proyecto> proyectosList) {
        this.proyectosList = proyectosList;
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

    public void buscarProyectos() {

        try {
            Map filtro = new HashMap();
            if (nombreB != null && !nombreB.isEmpty()) {
                filtro.put("nombre", nombreB);
            }
            if (nombreCortoB != null && !nombreCortoB.isEmpty()) {
                filtro.put("nombrecorto", nombreCortoB);
            }
            if (idEstadoB != 0) {
                filtro.put("estado", idEstadoB);
            }
            if (filtro.isEmpty()) {
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

    public void onRowSelectProyecto(SelectEvent event) throws IOException {
        try {

            proyectoActual = new Proyecto();
            proyectoActual = proyectoB;
            PrimeFaces.current().executeScript("PF('modalBusqProyecto').hide();");

            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void abrirModalBuscarProyecto() {
        PrimeFaces.current().ajax().update(":formbuscarProyecto");
        PrimeFaces.current().executeScript("PF('modalBusqProyecto').show();");
    }

    public void imprimirCuadroComparativo() {
        Map parametros = new HashMap<>();
        Long id = planCotizacionActual.getId();
        parametros.put("id", id);

        String reporte = "ComparativoCotizacion.jasper";
        String nombreArchivo = "Comparativo_" + id + ".pdf";
        this.generarReporte(reporte, nombreArchivo, parametros);

    }
    
    private void generarReporte(String reporte, String nombreArchivo, Map params) {
        try {

            ServletContext servletContext
                    = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("/WEB-INF/jasper/"+reporte);
            System.out.println(path);
            File jasper = new File(path);
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("siscop_ds");
            Connection connection = dataSource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), params,
                    connection);
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + nombreArchivo);
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();

            //TODO: Cómo cerrar conexiones
            connection.close();
        } catch (NamingException ex) {
            this.logger.log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            this.logger.log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            this.logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            this.logger.log(Level.SEVERE, null, ex);
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
