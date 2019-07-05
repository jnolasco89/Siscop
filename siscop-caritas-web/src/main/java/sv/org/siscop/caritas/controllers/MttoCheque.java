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
import sv.org.siscop.caritas.ejb.ServicioProveedorLocal;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Detallecheque;
import sv.org.siscop.caritas.entidades.Proyecto;
import sv.org.siscop.caritas.entidades.Cheque;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.util.Catalogos;

/**
 *
 * @author Henry
 */
@Named(value = "mttoCheque")
@SessionScoped
public class MttoCheque implements Serializable {

    @EJB
    private ServicioChequeLocal servCheque;
    @EJB
    private ServiciosCatalogoLocal servCat;
    @EJB
    private ServiciosCuentaLocal servCuenta;
    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServicioProveedorLocal servProveedor;

    private final static Logger logger = Logger.getLogger(MttoCheque.class.getName());

    public MttoCheque() {
    }

    FacesContext facesContext = FacesContext.getCurrentInstance();
    int tabindex = 0;
    //Campos de búsqueda
    private Long idProyectoB;
    private Integer idEstadoChequeB;
    private String aFavordeB;
    private Date fechaIniB = null;
    private Date fechaFinB = null;
    //SelectItems
    private List<SelectItem> itemEstado = new ArrayList<>();
    private List<SelectItem> itemEstadoChequeB = new ArrayList<>();

    //Propiedades Cheque
    private Cheque ChequeActual = new Cheque();
    private Cheque ChequeB = new Cheque();
    boolean esChequeNuevo = false;
    boolean esDetalleNuevo = true;
    private Date fecha = new Date();
    private String conceptoCheque;
    private String afavorDe;
    private String cantidadLetras;
    private String comentarioCheque;
    private BigDecimal monto;
    //Detalle
    private Detallecheque detaChequeActual = new Detallecheque();
    private Cuenta cuentaActual = new Cuenta();
    private Persona sublibroActual = new Persona();
    private Integer apliccont;
    private BigDecimal valor;

    //Listas
    private List<Cheque> chequesList = new ArrayList<>();
    private List<Detallecheque> chequeDetaList = new ArrayList<>();

    public void onTabChange(TabChangeEvent event) {
        this.tabindex = ((TabView) event.getSource()).getIndex();
    }

    public int getTabindex() {
        return tabindex;
    }

    public void setTabindex(int tabindex) {
        this.tabindex = tabindex;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter y Setter Busqueda de Cheque">
    public Long getIdProyectoB() {
        return idProyectoB;
    }

    public void setIdProyectoB(Long idProyectoB) {
        this.idProyectoB = idProyectoB;
    }

    public Integer getIdEstadoChequeB() {
        return idEstadoChequeB;
    }

    public void setIdEstadoChequeB(Integer idEstadoChequeB) {
        this.idEstadoChequeB = idEstadoChequeB;
    }

    public String getaFavordeB() {
        return aFavordeB;
    }

    public void setaFavordeB(String aFavordeB) {
        this.aFavordeB = aFavordeB;
    }

    public Date getFechaIniB() {
        return fechaIniB;
    }

    public void setFechaIniB(Date fechaIniB) {
        this.fechaIniB = fechaIniB;
    }

    public List<SelectItem> getItemEstadoChequeB() {
        try {
            itemEstadoChequeB.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.ESTADO_CHEQUE.getCodigo()).getItemCatalogoList()) {
                itemEstadoChequeB.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return itemEstadoChequeB;

    }

    public void setItemEstadoChequeB(List<SelectItem> itemEstadoChequeB) {
        this.itemEstadoChequeB = itemEstadoChequeB;
    }

    public Date getFechaFinB() {
        return fechaFinB;
    }

    public void setFechaFinB(Date fechaFinB) {
        this.fechaFinB = fechaFinB;
    }

    public List<Cheque> getChequesList() {
        return chequesList;
    }

    public void setChequesList(List<Cheque> chequesList) {
        this.chequesList = chequesList;
    }

    public List<Detallecheque> getChequeDetaList() {
        return chequeDetaList;
    }

    public void setChequeDetaList(List<Detallecheque> chequeDetaList) {
        this.chequeDetaList = chequeDetaList;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getter y Setter Plancotizacion">
    public boolean isEsChequeNuevo() {
        return esChequeNuevo;
    }

    public void setEsChequeNuevo(boolean esChequeNuevo) {
        this.esChequeNuevo = esChequeNuevo;
    }

    public Cheque getChequeActual() {
        return ChequeActual;
    }

    public void setChequeActual(Cheque ChequeActual) {
        this.ChequeActual = ChequeActual;
    }

    public Cheque getChequeB() {
        return ChequeB;
    }

    public void setChequeB(Cheque ChequeB) {
        this.ChequeB = ChequeB;
    }

    public String getConceptoCheque() {
        return conceptoCheque;
    }

    public void setConceptoCheque(String conceptoCheque) {
        this.conceptoCheque = conceptoCheque;
    }

    public String getAfavorDe() {
        return afavorDe;
    }

    public void setAfavorDe(String afavorDe) {
        this.afavorDe = afavorDe;
    }

    public String getCantidadLetras() {
        return cantidadLetras;
    }

    public void setCantidadLetras(String cantidadLetras) {
        this.cantidadLetras = cantidadLetras;
    }

    public String getComentarioCheque() {
        return comentarioCheque;
    }

    public void setComentarioCheque(String comentarioCheque) {
        this.comentarioCheque = comentarioCheque;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Detallecheque getDetaChequeActual() {
        return detaChequeActual;
    }

    public void setDetaChequeActual(Detallecheque detaChequeActual) {
        this.detaChequeActual = detaChequeActual;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
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
    public void buscarCheques() {

        try {
            Map filtro = new HashMap();
            if (fechaIniB != null && fechaFinB != null) {
                filtro.put("fechaIni", fechaIniB);
                filtro.put("fechaFin", fechaFinB);
            }
            if (idProyectoB != null && idProyectoB != 0) {
                filtro.put("idproyecto", idProyectoB);
            }
            if (idEstadoChequeB != 0) {
                filtro.put("idestado", idEstadoChequeB);
            }
            if (aFavordeB != null && !aFavordeB.isEmpty()) {
                filtro.put("afavorde", aFavordeB);
            }

            if (filtro.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Agregue un parámetro de búsqueda", null);
                return;
            }

            chequesList = servCheque.buscarCheques(filtro);

            if (chequesList.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarBusquedaCheques() {
        chequesList = new ArrayList<>();
        idProyectoB = null;
        fechaIniB = null;
        fechaFinB = null;
        idEstadoChequeB = 0;
    }

    public void limpiarCheque() {

        esChequeNuevo = true;
        ChequeActual = new Cheque();
        chequeDetaList = new ArrayList<>();
        fecha = new Date();
        comentarioCheque = "";
        conceptoCheque = "";
        afavorDe = "";
        cantidadLetras = "";
        proyectoActual = new Proyecto();

        limpiarDetalleCheque();

    }

    public void onRowSelectCheque(SelectEvent event) throws IOException {
        try {
            limpiarCheque();

            ChequeActual = new Cheque();
            ChequeActual = ChequeB;

            fecha = ChequeActual.getFecha();
            conceptoCheque = ChequeActual.getConcepto();
            comentarioCheque = ChequeActual.getComentarios();
            afavorDe = ChequeActual.getAfavorde();
            cantidadLetras = ChequeActual.getCantidadletras();
            chequeDetaList = ChequeActual.getDetallechequeList();
            esChequeNuevo = false;
            tabindex = 1;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoCheque() {
        try {

            limpiarCheque();
            limpiarDetalleCheque();
            esChequeNuevo = true;

            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Ingrese datos de nueva cotización.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarCheque() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (conceptoCheque == null || conceptoCheque.isEmpty()) {
                campos.add("Concepto");
            }
            if (fecha == null) {
                campos.add("Fecha");
            }
            if (proyectoActual == null || proyectoActual.getId() == null) {
                campos.add("Proyecto");
            }
            if (afavorDe == null || afavorDe.isEmpty()) {
                campos.add("A Favor de");
            }
//            if (idEstadoChequeB == 0) {
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

    public void guardarCheque() {
        try {
            if (validarCheque()) {
                return;
            }

            this.ChequeActual.setFecha(fecha);
            this.ChequeActual.setAfavorde(afavorDe);
            this.ChequeActual.setConcepto(conceptoCheque);
            this.ChequeActual.setCantidadletras(cantidadLetras);
            this.ChequeActual.setDetallechequeList(chequeDetaList);
            this.ChequeActual.setComentarios(comentarioCheque);
            this.ChequeActual.setMonto(monto);
            this.ChequeActual.setProyecto(proyectoActual);
            this.ChequeActual.setEstado(servCat.findItemCatalogoById(idEstadoChequeB));

            //this.planCotizacionActual.setRequisicion(new Cheque());
            if (esChequeNuevo) {
                this.servCheque.nuevoCheque(ChequeActual);
            } else {
                this.servCheque.actualizarCheque(ChequeActual);
            }

            this.showMessage(FacesMessage.SEVERITY_INFO, "Cheque guardado exitosamente.", null);

            esChequeNuevo = false;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar cheque.", ex.getLocalizedMessage());
        }
    }

    public void limpiarDetalleCheque() {
        try {
            esDetalleNuevo = true;
            detaChequeActual = new Detallecheque();

            cuentaActual = new Cuenta();
            monto = null;
            apliccont = 0;
            sublibroActual = new Persona();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarDetalleCheque() {
        boolean hay = false;
        try {
            List<String> campos = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();

            if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
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

    public void agregarDetalleCheque() {
        try {
            if (esChequeNuevo) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Error al guardar Cheque.", "Guarde el Cheque para continuar");
                return;
            }

            if (validarDetalleCheque()) {
                return;
            }

            detaChequeActual.setMonto(valor);
            detaChequeActual.setCuenta(cuentaActual);
            detaChequeActual.setAplicacion(apliccont);
            detaChequeActual.setSaldoanterior(valor);
            detaChequeActual.setSaldoposterior(valor);
            detaChequeActual.setSublibro(sublibroActual);
            detaChequeActual.setCheque(ChequeActual);
            if (esDetalleNuevo) {
                servCheque.nuevoDetalleCheque(detaChequeActual);
                chequeDetaList.add(detaChequeActual);
            } else {
                servCheque.actualizarDetalleCheque(detaChequeActual);
                chequeDetaList.add(chequeDetaList.indexOf(detaChequeActual), detaChequeActual);
            }
            limpiarDetalleCheque();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void editarDetalleCheque(Detallecheque detalle) throws IOException {
        try {
            esDetalleNuevo = false;
            detaChequeActual = detalle;
            valor = detaChequeActual.getMonto();
            cuentaActual = detaChequeActual.getCuenta();
            apliccont = detaChequeActual.getAplicacion();
            sublibroActual = detaChequeActual.getSublibro();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
//    public void onRowSelectDetalleCheque(SelectEvent event) throws IOException {
//        try {
//            detaChequeActual = (Detallecheque) event.getObject();
//            esDetalleNuevo = false;
//
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//    }

    public void eliminarDetalleCheque(Detallecheque deta) {
        try {
            deta.setCheque(null);
            chequeDetaList.remove(deta);
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

            tabindex = 1;
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

    public void imprimirCheque() {
        Map parametros = new HashMap<>();
        Long id = ChequeActual.getId();
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
