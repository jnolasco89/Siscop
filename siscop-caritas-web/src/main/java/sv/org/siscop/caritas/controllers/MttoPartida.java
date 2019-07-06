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
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.org.siscop.caritas.ejb.ServicioChequeLocal;
import sv.org.siscop.caritas.ejb.ServicioPartidaLocal;
import sv.org.siscop.caritas.ejb.ServicioProveedorLocal;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Detallecheque;
import sv.org.siscop.caritas.entidades.Proyecto;
import sv.org.siscop.caritas.entidades.Partida;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.Detallepartida;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.util.Catalogos;

/**
 *
 * @author Henry
 */
@Named(value = "mttoPartida")
@SessionScoped
public class MttoPartida implements Serializable {

    @EJB
    private ServicioPartidaLocal servPartida;
    @EJB
    private ServiciosCatalogoLocal servCat;
    @EJB
    private ServiciosCuentaLocal servCuenta;
    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServicioProveedorLocal servProveedor;

    private final static Logger logger = Logger.getLogger(MttoPartida.class.getName());

    public MttoPartida() {
    }

    FacesContext facesContext = FacesContext.getCurrentInstance();
    int tabindex = 0;
    //Campos de búsqueda
    private Long idProyectoB;
    private Integer idEstadoPartidaB;
    private Date fechaIniB = null;
    private Date fechaFinB = null;
    //SelectItems
    private List<SelectItem> itemEstado = new ArrayList<>();
    private List<SelectItem> itemEstadoPartidaB = new ArrayList<>();

    //Propiedades Partida
    private Partida PartidaActual = new Partida();
    private Partida PartidaB = new Partida();
    boolean esPartidaNueva = false;
    boolean esDetalleNuevo = true;
    private Date fecha = new Date();
    private String descripcionPartida;
    private String comentarioDetalle;

    //Detalle
    private Detallepartida detaPartidaActual = new Detallepartida();
    private Cuenta cuentaActual = new Cuenta();
    private Persona sublibroActual = new Persona();
    private Integer apliccont;
    private BigDecimal valor;

    //Listas
    private List<Partida> partidaList = new ArrayList<>();
    private List<Detallepartida> partidaDetaList = new ArrayList<>();

    public void onTabChange(TabChangeEvent event) {
        this.tabindex = ((TabView) event.getSource()).getIndex();
    }

    public int getTabindex() {
        return tabindex;
    }

    public void setTabindex(int tabindex) {
        this.tabindex = tabindex;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter y Setter Busqueda de Partida">
    public Long getIdProyectoB() {
        return idProyectoB;
    }

    public void setIdProyectoB(Long idProyectoB) {
        this.idProyectoB = idProyectoB;
    }

    public Integer getIdEstadoPartidaB() {
        return idEstadoPartidaB;
    }

    public void setIdEstadoPartidaB(Integer idEstadoPartidaB) {
        this.idEstadoPartidaB = idEstadoPartidaB;
    }

    public Date getFechaIniB() {
        return fechaIniB;
    }

    public void setFechaIniB(Date fechaIniB) {
        this.fechaIniB = fechaIniB;
    }

    public List<SelectItem> getItemEstadoPartidaB() {
        try {
            itemEstadoPartidaB.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.ESTADO_CHEQUE.getCodigo()).getItemCatalogoList()) {
                itemEstadoPartidaB.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return itemEstadoPartidaB;

    }

    public void setItemEstadoPartidaB(List<SelectItem> itemEstadoPartidaB) {
        this.itemEstadoPartidaB = itemEstadoPartidaB;
    }

    public Date getFechaFinB() {
        return fechaFinB;
    }

    public void setFechaFinB(Date fechaFinB) {
        this.fechaFinB = fechaFinB;
    }

    public List<Partida> getPartidaList() {
        return partidaList;
    }

    public void setPartidaList(List<Partida> partidaList) {
        this.partidaList = partidaList;
    }

    public List<Detallepartida> getPartidaDetaList() {
        return partidaDetaList;
    }

    public void setPartidaDetaList(List<Detallepartida> partidaDetaList) {
        this.partidaDetaList = partidaDetaList;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getter y Setter Plancotizacion">
    public boolean isEsPartidaNueva() {
        return esPartidaNueva;
    }

    public void setEsPartidaNueva(boolean esPartidaNueva) {
        this.esPartidaNueva = esPartidaNueva;
    }

    public Partida getPartidaActual() {
        return PartidaActual;
    }

    public void setPartidaActual(Partida PartidaActual) {
        this.PartidaActual = PartidaActual;
    }

    public Partida getPartidaB() {
        return PartidaB;
    }

    public void setPartidaB(Partida PartidaB) {
        this.PartidaB = PartidaB;
    }

    public String getDescripcionPartida() {
        return descripcionPartida;
    }

    public void setDescripcionPartida(String descripcionPartida) {
        this.descripcionPartida = descripcionPartida;
    }

    public String getComentarioDetalle() {
        return comentarioDetalle;
    }

    public void setComentarioDetalle(String comentarioDetalle) {
        this.comentarioDetalle = comentarioDetalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Detallepartida getDetaPartidaActual() {
        return detaPartidaActual;
    }

    public void setDetaPartidaActual(Detallepartida detaPartidaActual) {
        this.detaPartidaActual = detaPartidaActual;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cuenta getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    public Integer getApliccont() {
        return apliccont;
    }

    public void setApliccont(Integer apliccont) {
        this.apliccont = apliccont;
    }

//</editor-fold>
    public void buscarPartidas() {

        try {
            Map filtro = new HashMap();
            if (fechaIniB != null && fechaFinB != null) {
                filtro.put("fechaIni", fechaIniB);
                filtro.put("fechaFin", fechaFinB);
            }
            if (idProyectoB != null && idProyectoB != 0) {
                filtro.put("idproyecto", idProyectoB);
            }
            if (idEstadoPartidaB != 0) {
                filtro.put("idestado", idEstadoPartidaB);
            }
            if (filtro.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Agregue un parámetro de búsqueda", null);
                return;
            }

            partidaList = servPartida.buscarPartidas(filtro);

            if (partidaList.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarBusquedaPartidas() {
        partidaList = new ArrayList<>();
        idProyectoB = null;
        fechaIniB = null;
        fechaFinB = null;
        idEstadoPartidaB = 0;
    }

    public void limpiarPartida() {

        esPartidaNueva = true;
        PartidaActual = new Partida();
        partidaDetaList = new ArrayList<>();
        fecha = new Date();
        comentarioDetalle = "";
        descripcionPartida = "";
        proyectoActual = new Proyecto();

        limpiarDetallePartida();

    }

    public void onRowSelectPartida(SelectEvent event) throws IOException {
        try {
            limpiarPartida();

            PartidaActual = new Partida();
            PartidaActual = PartidaB;

            fecha = PartidaActual.getFecha();
            descripcionPartida = PartidaActual.getDescripcion();
            partidaDetaList = PartidaActual.getDetallepartidaList();
            esPartidaNueva = false;
            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaPartida() {
        try {

            limpiarPartida();
            limpiarDetallePartida();
            esPartidaNueva = true;

            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Ingrese datos de nueva cotización.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarPartida() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (descripcionPartida == null || descripcionPartida.isEmpty()) {
                campos.add("Descripción");
            }
            if (fecha == null) {
                campos.add("Fecha");
            }
            if (proyectoActual == null || proyectoActual.getId() == null) {
                campos.add("Proyecto");
            }
//            if (idEstadoPartidaB == 0) {
//                campos.add("Estado");
//            }

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

    public void guardarPartida() {
        try {
            if (validarPartida()) {
                return;
            }

            this.PartidaActual.setFecha(fecha);
            this.PartidaActual.setDescripcion(descripcionPartida);
            this.PartidaActual.setDetallepartidaList(partidaDetaList);
            this.PartidaActual.setProyecto(proyectoActual);
            this.PartidaActual.setEstado(servCat.findItemCatalogoById(idEstadoPartidaB));

            //this.planCotizacionActual.setRequisicion(new Partida());
            if (esPartidaNueva) {
                this.servPartida.nuevoPartida(PartidaActual);
            } else {
                this.servPartida.actualizarPartida(PartidaActual);
            }

            this.showMessage(FacesMessage.SEVERITY_INFO, "Partida guardado exitosamente.", null);

            esPartidaNueva = false;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar partida.", ex.getLocalizedMessage());
        }
    }

    public void limpiarDetallePartida() {
        try {
            esDetalleNuevo = true;
            detaPartidaActual = new Detallepartida();

            cuentaActual = new Cuenta();
            valor = null;
            apliccont = 0;
            sublibroActual = new Persona();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarDetallePartida() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                campos.add("Monto");
            }
            if (cuentaActual.getCuentaPK() == null) {
                campos.add("Cuenta");
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

    public void agregarDetallePartida() {
        try {
            if (esPartidaNueva) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Error al guardar Partida.", "Guarde el Partida para continuar");
                return;
            }

            if (validarDetallePartida()) {
                return;
            }

            detaPartidaActual.setMonto(valor);
            detaPartidaActual.setCuenta(cuentaActual);
            detaPartidaActual.setAplicacion(apliccont);
//            detaPartidaActual.setSublibro(sublibroActual);
            detaPartidaActual.setPartida(PartidaActual);
            if (esDetalleNuevo) {
                servPartida.nuevoDetallePartida(detaPartidaActual);
                partidaDetaList.add(detaPartidaActual);
            } else {
                servPartida.actualizarDetallePartida(detaPartidaActual);
                partidaDetaList.add(partidaDetaList.indexOf(detaPartidaActual), detaPartidaActual);
            }
            limpiarDetallePartida();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void editarDetallePartida(Detallepartida detalle) throws IOException {
        try {
            esDetalleNuevo = false;
            detaPartidaActual = detalle;
            valor = detaPartidaActual.getMonto();
            cuentaActual = detaPartidaActual.getCuenta();
            apliccont = detaPartidaActual.getAplicacion();
            sublibroActual = detaPartidaActual.getSublibro();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
//    public void onRowSelectDetalleCheque(SelectEvent event) throws IOException {
//        try {
//            detaPartidaActual = (Detallecheque) event.getObject();
//            esDetalleNuevo = false;
//
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    }

    public void eliminarDetallePartida(Detallecheque deta) {
        try {
            deta.setCheque(null);
            partidaDetaList.remove(deta);
            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Eliminado de la lista.", null);
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
    private Persona subLibroActual = new Persona();

    //Listas
    private List<Proyecto> proyectosList = new ArrayList<>();

    //SelectItems
    private List<SelectItem> itemEstadoProyecto = new ArrayList<>();

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

    public Persona getSubLibroActual() {
        return subLibroActual;
    }

    public void setSubLibroActual(Persona subLibroActual) {
        this.subLibroActual = subLibroActual;
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

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void abrirModalBuscarProyecto() {
        PrimeFaces.current().ajax().update(":formbuscarProyecto");
        PrimeFaces.current().executeScript("PF('modalBusqProyecto').show();");
    }

    public void abrirModalCuentas() {
        buscarCuentas();
        PrimeFaces.current().ajax().update(":formCuentas");
        PrimeFaces.current().executeScript("PF('modalSelCuentas').show();");
    }

    public void onRowSelectCuenta(SelectEvent event) throws IOException {
        try {

            PrimeFaces.current().executeScript("PF('modalSelCuentas').hide();");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    //Listas
    private List<Cuenta> cuentasList = new ArrayList<>();

    public List<Cuenta> getCuentasList() {
        return cuentasList;
    }

    public void setCuentasList(List<Cuenta> cuentasList) {
        this.cuentasList = cuentasList;
    }

    public void buscarCuentas() {

        try {
            Map filtro = new HashMap();

            Map filtros = new HashMap();
            filtros.put("cuentasPrincipales", true);
            filtros.put("proyecto", proyectoActual);
            cuentasList = servCuenta.buscarCuentas(filtro);

            if (cuentasList.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No existen cuentas para este proyecto", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirPartida() {
        Map parametros = new HashMap<>();
        Long id = PartidaActual.getId();
        parametros.put("id", id);

        String reporte = "Partida.jasper";
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
