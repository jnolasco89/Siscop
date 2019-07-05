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
import sv.org.siscop.caritas.ejb.ServicioProveedorLocal;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServicioRequisicionLocal;
import sv.org.siscop.caritas.entidades.Plancotizacion;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.Actividad;
import sv.org.siscop.caritas.entidades.Cotizacion;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.ItemRequisicion;
import sv.org.siscop.caritas.entidades.Itemcotizacion;
import sv.org.siscop.caritas.entidades.Planitem;
import sv.org.siscop.caritas.entidades.Proveedor;
import sv.org.siscop.caritas.entidades.Proyecto;
import sv.org.siscop.caritas.entidades.Requisicion;
import sv.org.siscop.caritas.util.Catalogos;

/**
 *
 * @author Henry
 */
@Named(value = "mttoRequisicion")
@SessionScoped
public class MttoRequisicion implements Serializable {

    @EJB
    private ServicioRequisicionLocal servRequisicion;
    @EJB
    private ServiciosCatalogoLocal servCat;
    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServicioProveedorLocal servProveedor;

    private final static Logger logger = Logger.getLogger(MttoRequisicion.class.getName());

    public MttoRequisicion() {
    }

    FacesContext facesContext = FacesContext.getCurrentInstance();
    //Campos de búsqueda
    private Long idProyectoB;
    private String descripcionB;
    private Integer estadoActividadB;

    //Propiedades proyecto
    private Requisicion requisicionActual = new Requisicion();
    private Requisicion requisicionB = new Requisicion();
    private ItemRequisicion itemRequisicionActual = new ItemRequisicion();
    private Actividad actividadActual = new Actividad();
    boolean esRequisicionNueva = false;
    private String descripcion;
    private Long idActividad;
    private Date fecha = new Date();

    int tabindex = 0;

    //Listas
    private List<Requisicion> requisicionesList = new ArrayList<>();
    private List<ItemRequisicion> itemRequisicionList = new ArrayList<>();

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

    //<editor-fold defaultstate="collapsed" desc="Getter y Setter Busqueda de Requisicion">
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

    public List<Requisicion> getRequisicionesList() {
        return requisicionesList;
    }

    public void setRequisicionesList(List<Requisicion> requisicionesList) {
        this.requisicionesList = requisicionesList;
    }

    public List<ItemRequisicion> getItemRequisicionList() {
        return itemRequisicionList;
    }

    public void setItemRequisicionList(List<ItemRequisicion> itemRequisicionList) {
        this.itemRequisicionList = itemRequisicionList;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getter y Setter Plancotizacion">
    public boolean isEsRequisicionNueva() {
        return esRequisicionNueva;
    }

    public void setEsRequisicionNueva(boolean esRequisicionNueva) {
        this.esRequisicionNueva = esRequisicionNueva;
    }

    public Requisicion getRequisicionActual() {
        return requisicionActual;
    }

    public void setRequisicionActual(Requisicion requisicionActual) {
        this.requisicionActual = requisicionActual;
    }

    public Requisicion getRequisicionB() {
        return requisicionB;
    }

    public void setRequisicionB(Requisicion requisicionB) {
        this.requisicionB = requisicionB;
    }

    public ItemRequisicion getItemRequisicionActual() {
        return itemRequisicionActual;
    }

    public void setItemRequisicionActual(ItemRequisicion itemRequisicionActual) {
        this.itemRequisicionActual = itemRequisicionActual;
    }

    public Actividad getActividadActual() {
        return actividadActual;
    }

    public void setActividadActual(Actividad actividadActual) {
        this.actividadActual = actividadActual;
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
    public void buscarRequisiciones() {

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

            requisicionesList = servRequisicion.buscarRequisiciones(filtro);

            if (requisicionesList.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarBusquedaRequisicion() {
        requisicionesList = new ArrayList<>();
        idProyectoB = null;
        descripcionB = "";
        idProyectoB = 0L;
        estadoActividadB = 0;
    }

    public void limpiarRequisicion() {

        requisicionActual = new Requisicion();
        itemRequisicionList = new ArrayList<>();
        actividadActual = new Actividad();
        fecha = null;
        descripcion = "";
        proyectoActual = new Proyecto();
        proveedorActual = new Proveedor();

    }

    public void onRowSelectPlan(SelectEvent event) throws IOException {
        try {
            limpiarRequisicion();

            requisicionActual = new Requisicion();
            requisicionActual = requisicionB;

            descripcion = requisicionActual.getDescripcion();
            fecha = requisicionActual.getFecha();
            actividadActual = requisicionActual.getActividad();
            itemRequisicionList = requisicionActual.getItemrequisicionList();
            esRequisicionNueva = false;
            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaRequisicion() {
        try {

            limpiarRequisicion();
            limpiarItemRequisicion();
            esRequisicionNueva = true;

            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Ingrese datos de nueva cotización.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarRequisicion() {
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

    public void guardarRequisicion() {
        try {
            if (validarRequisicion()) {
                return;
            }

            this.requisicionActual.setDescripcion(descripcion);
            this.requisicionActual.setFecha(fecha);
            this.requisicionActual.setItemrequisicionList(itemRequisicionList);
            if (proveedorActual.getId() != null) {
                this.requisicionActual.setProveedor(proveedorActual);
            }
            if (actividadActual.getId() != null) {
                this.requisicionActual.setActividad(actividadActual);
            }

            //this.planCotizacionActual.setRequisicion(new Requisicion());
            if (esRequisicionNueva) {
                this.servRequisicion.nuevaRequisicion(requisicionActual);
            } else {
                this.servRequisicion.actualizarRequisicion(requisicionActual);
            }

            this.showMessage(FacesMessage.SEVERITY_INFO, "Requisicion guardada exitosamente.", null);

            esRequisicionNueva = false;

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

    public void limpiarItemRequisicion() {
        try {
            itemRequisicionActual = new ItemRequisicion();
            idItemMedida = 0;
            descproducto = "";
            producto = "";
            cantidad = null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarItemRequisicion() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (producto == null || producto.isEmpty()) {
                campos.add("Producto");
            }
            if (descproducto == null || descproducto.isEmpty()) {
                campos.add("Descripción");
            }
            if (idItemMedida == 0) {
                campos.add("Unidad de Medida");
            }
            if (cantidad == null) {
                mensajes.add("Cantidad");
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

    public void agregarItemRequisicion() {
        try {
            if (esRequisicionNueva) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Error al guardar plantilla.", "Guarde la planitalla primero.");
                return;
            }

            if (validarItemRequisicion()) {
                return;
            }

            ItemRequisicion item = new ItemRequisicion();
//            item.setId(Long.valueOf(cantidad));
            item.setRequisicion(requisicionActual);
            item.setProducto(producto);
            item.setDescripcion(descproducto);
            item.setCantidad(cantidad);
            item.setMedida(servCat.findItemCatalogoById(idItemMedida));
            servRequisicion.nuevoItemRequisicion(item);

            itemRequisicionList.add(item);
            limpiarItemRequisicion();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelectItemRequisicion(SelectEvent event) throws IOException {
        try {
//            limpiarRequisicion();
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

    public void onRowCancelItemRequisicion(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada",
                ((Planitem) event.getObject()).getCantidad().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEditItemRequisicion(CellEditEvent event) throws Exception {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        FacesContext context = FacesContext.getCurrentInstance();
        ItemRequisicion entity = context.getApplication().evaluateExpressionGet(context, "#{itemreq}", ItemRequisicion.class);

        this.servRequisicion.actualizarItemRequisicion(entity);
    }

    public void eliminarItemRequisicion(ItemRequisicion item) {
        try {
            item.setRequisicion(null);
            itemRequisicionList.remove(item);
            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Eliminado de la lista.", null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void onRowReorderItemRequisicion(ReorderEvent event) {
        try {
//            Planitem oldValue = (Planitem) event.getSource();

            FacesContext context = FacesContext.getCurrentInstance();

            ItemRequisicion entity = (ItemRequisicion) ((DataTable) event.getComponent()).getRowData();
//            Planitem entity = context.getApplication().evaluateExpressionGet(context, "#{planitem}", Planitem.class);
            entity.setOrden(event.getToIndex());

            this.servRequisicion.actualizarItemRequisicion(entity);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: "
                    + event.getFromIndex() + ", To:" + event.getToIndex());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowReorderItemRequisicion(int index) {
        try {

            FacesContext context = FacesContext.getCurrentInstance();

            ItemRequisicion entity = context.getApplication().evaluateExpressionGet(context, "#{itemreq}", ItemRequisicion.class);
            entity.setOrden(index);

            this.servRequisicion.actualizarItemRequisicion(entity);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private Requisicion requisicion = new Requisicion();
    private Date fechaRequisicion = null;
    private String destino = "";

    public Requisicion getRequisicion() {
        return requisicion;
    }

    public void setRequisicion(Requisicion requisicion) {
        this.requisicion = requisicion;
    }

    public Date getFechaRequisicion() {
        return fechaRequisicion;
    }

    public void setFechaRequisicion(Date fechaRequisicion) {
        this.fechaRequisicion = fechaRequisicion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
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

    public void abrirModalSelCotizacion() {
        PrimeFaces.current().ajax().update(":formSelCotizacion");
        PrimeFaces.current().executeScript("PF('modalSelCotizacion').show();");
    }

    private String duiProvB;
    private String nombre1ProvB;
    private String nombre2ProvB;
    private String apellido1ProvB;
    private String apellido2ProvB;

    boolean nuevoProveedor = false;

    private Proveedor proveedorActual = new Proveedor();
    private Proveedor proveedorSelB = new Proveedor();

    private List<Proveedor> lstProveedores = new ArrayList<>();

    public String getDuiProvB() {
        return duiProvB;
    }

    public void setDuiProvB(String duiProvB) {
        this.duiProvB = duiProvB;
    }

    public String getNombre1ProvB() {
        return nombre1ProvB;
    }

    public void setNombre1ProvB(String nombre1ProvB) {
        this.nombre1ProvB = nombre1ProvB;
    }

    public String getNombre2ProvB() {
        return nombre2ProvB;
    }

    public void setNombre2ProvB(String nombre2ProvB) {
        this.nombre2ProvB = nombre2ProvB;
    }

    public String getApellido1ProvB() {
        return apellido1ProvB;
    }

    public void setApellido1ProvB(String apellido1ProvB) {
        this.apellido1ProvB = apellido1ProvB;
    }

    public String getApellido2ProvB() {
        return apellido2ProvB;
    }

    public void setApellido2ProvB(String apellido2ProvB) {
        this.apellido2ProvB = apellido2ProvB;
    }

    public Proveedor getProveedorActual() {
        return proveedorActual;
    }

    public void setProveedorActual(Proveedor proveedorActual) {
        this.proveedorActual = proveedorActual;
    }

    public Proveedor getProveedorSelB() {
        return proveedorSelB;
    }

    public void setProveedorSelB(Proveedor proveedorSelB) {
        this.proveedorSelB = proveedorSelB;
    }

    public List<Proveedor> getLstProveedores() {
        return lstProveedores;
    }

    public void setLstProveedores(List<Proveedor> lstProveedores) {
        this.lstProveedores = lstProveedores;
    }

    public void abrirModalBuscarProveedores() {
        PrimeFaces.current().ajax().update(":formbuscarProveedor");
        PrimeFaces.current().executeScript("PF('modalbusqProveedor').show();");
    }

    public void buscarProveedores() {

        try {
            Map filtro = new HashMap();

            boolean hayDui = false;
            if (duiProvB != null && !duiProvB.isEmpty()) {
                filtro.put("dui", duiProvB);
                hayDui = true;
            }
            if (nombre1ProvB != null && !nombre1ProvB.isEmpty()) {
                filtro.put("nombre1", nombre1ProvB);
            }
            if (nombre2ProvB != null && !nombre2ProvB.isEmpty()) {
                filtro.put("nombre2", nombre2ProvB);
            }
            if (apellido1ProvB != null && !apellido1ProvB.isEmpty()) {
                filtro.put("apellido1", apellido1ProvB);
            }
            if (apellido2ProvB != null && !apellido2ProvB.isEmpty()) {
                filtro.put("apellido2", apellido2ProvB);
            }

            if (filtro.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Agregue un parámetro de búsqueda", null);
                return;
            }

            if (hayDui) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró metodo.", null);
            } else {
                lstProveedores = servProveedor.buscarProveedors(filtro);
            }

            if (lstProveedores.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void limpiarBusquedaProveedores() {
        lstProveedores = new ArrayList<>();
        duiProvB = "";
        nombre1ProvB = "";
        nombre2ProvB = "";
        apellido1ProvB = "";
        apellido2ProvB = "";
    }

    public void limpiarProveedor() {

    }

    public void onRowSelectProveedor(SelectEvent event) throws IOException {
        try {
            limpiarProveedor();

            proveedorActual = new Proveedor();

            proveedorActual = proveedorSelB;
            limpiarBusquedaProveedores();
            PrimeFaces.current().executeScript("PF('modalbusqProveedor').hide();");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirRequisicion() {
        Map parametros = new HashMap<>();
        Long id = requisicionActual.getId();
        parametros.put("id", id);

        String reporte = "ComparativoCotizacion.jasper";
        String nombreArchivo = "Comparativo_" + id + ".pdf";
        this.generarReporte(reporte, nombreArchivo, parametros);

    }

    private void generarReporte(String reporte, String nombreArchivo, Map params) {
        try {

            ServletContext servletContext
                    = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("/WEB-INF/jasper/" + reporte);
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
