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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.Direccion;
import sv.org.siscop.caritas.entidades.Documento;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.entidades.Telefono;
import sv.org.siscop.caritas.entidades.Usuario;
import sv.org.siscop.caritas.util.Catalogos;
import sv.org.siscop.caritas.ejb.ServicioPersonaLocal;

/**
 *
 * @author Henry
 */
@Named(value = "mttoPersona")
@SessionScoped
public class MttoPersona implements Serializable {

    @EJB
    private ServiciosCatalogoLocal servCat;
    @EJB
    private ServicioPersonaLocal servPersona;

    private final static Logger logger = Logger.getLogger(MttoPersona.class.getName());

    public MttoPersona() {
    }

    FacesContext facesContext = FacesContext.getCurrentInstance();
    //Campos de búsqueda
    private String duiB;
    private String nombre1B;
    private String nombre2B;
    private String apellido1B;
    private String apellido2B;

    private Persona personaActual = new Persona();
    private Persona personaSelB = new Persona();
    boolean nuevaPersona = false;

    private Integer tipoTel;
    private Integer tipoDir;
    private Integer tipoDoc;
    private Integer estCivil;

    //Propiedades persona
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String apecasada;
    private String numeroDoc;
    private String numeroTel;
    private String nuevaDirecc;
    private Date fechaNacimiento;
    private String sexo;
    private Integer tipoPer;

    int tabindex = 0;

    //Listas
    private List<Persona> lstPersonas = new ArrayList<>();
    private List<Telefono> telefonosList = new ArrayList<>();
    private List<Direccion> direccionesList = new ArrayList<>();
    private List<Documento> documentosList = new ArrayList<>();

    //SelectItems
    private final List<SelectItem> tipoPerList = new ArrayList<>();
    private final List<SelectItem> estadoCivilLst = new ArrayList<>();
    private final List<SelectItem> tipotelLst = new ArrayList<>();
    private final List<SelectItem> tipoDireccLst = new ArrayList<>();
    private final List<SelectItem> tipoDocLst = new ArrayList<>();

    public void onTabChange(TabChangeEvent event) {
        this.tabindex = ((TabView) event.getSource()).getIndex();
    }

    public Persona getPersonaActual() {
        return personaActual;
    }

    public void setPersonaActual(Persona personaActual) {
        this.personaActual = personaActual;
    }

    public Persona getPersonaSelB() {
        return personaSelB;
    }

    public void setPersonaSelB(Persona personaSelB) {
        this.personaSelB = personaSelB;
    }

    public String getNombre1B() {
        return nombre1B;
    }

    public void setNombre1B(String nombre1B) {
        this.nombre1B = nombre1B;
    }

    public String getApellido1B() {
        return apellido1B;
    }

    public void setApellido1B(String apellido1B) {
        this.apellido1B = apellido1B;
    }

    public String getNombre2B() {
        return nombre2B;
    }

    public void setNombre2B(String nombre2B) {
        this.nombre2B = nombre2B;
    }

    public String getApellido2B() {
        return apellido2B;
    }

    public void setApellido2B(String apellido2B) {
        this.apellido2B = apellido2B;
    }

    public String getDuiB() {
        return duiB;
    }

    public void setDuiB(String duiB) {
        this.duiB = duiB;
    }

    public List<Persona> getLstPersonas() {
        return lstPersonas;
    }

    public void setLstPersonas(List<Persona> lstPersonas) {
        this.lstPersonas = lstPersonas;
    }

    public int getTabindex() {
        return tabindex;
    }

    public void setTabindex(int tabindex) {
        this.tabindex = tabindex;
    }

    public boolean isNuevaPersona() {
        return nuevaPersona;
    }

    public Integer getTipoTel() {
        return tipoTel;
    }

    public void setTipoTel(Integer tipoTel) {
        this.tipoTel = tipoTel;
    }

    public Integer getTipoDir() {
        return tipoDir;
    }

    public void setTipoDir(Integer tipoDir) {
        this.tipoDir = tipoDir;
    }

    public Integer getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Integer tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Integer getEstCivil() {
        return estCivil;
    }

    public void setEstCivil(Integer estCivil) {
        this.estCivil = estCivil;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getNuevaDirecc() {
        return nuevaDirecc;
    }

    public void setNuevaDirecc(String nuevaDirecc) {
        this.nuevaDirecc = nuevaDirecc;
    }

    public List<SelectItem> getTipotelLst() {
        try {
            tipotelLst.clear();

            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.TIPO_TEL.getCodigo()).getItemCatalogoList()) {
                tipotelLst.add(new SelectItem(item.getId(), item.getDescripcion()));
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return tipotelLst;
    }

    public List<SelectItem> getTipoDireccLst() {

        try {
            tipoDireccLst.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.TIPO_DIRECC.getCodigo()).getItemCatalogoList()) {
                tipoDireccLst.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return tipoDireccLst;
    }

    public List<SelectItem> getTipoDocLst() {

        try {
            tipoDocLst.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.TIPO_DOC.getCodigo()).getItemCatalogoList()) {
                tipoDocLst.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return tipoDocLst;
    }

    public List<SelectItem> getTipoPerList() {
        try {
            tipoPerList.clear();
            for (ItemCatalogo item : this.servCat
                    .findCatalogoById(Catalogos.TIPO_PERSON.getCodigo()).getItemCatalogoList()) {
                tipoPerList.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return tipoPerList;
    }

    public List<SelectItem> getEstadoCivilLst() {
        try {
            estadoCivilLst.clear();
            for (ItemCatalogo item : this.servCat.findCatalogoById(2).getItemCatalogoList()) {
                estadoCivilLst.add(new SelectItem(item.getId(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return estadoCivilLst;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApecasada() {
        return apecasada;
    }

    public void setApecasada(String apecasada) {
        this.apecasada = apecasada;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getTipoPer() {
        return tipoPer;
    }

    public void setTipoPer(Integer tipoPer) {
        this.tipoPer = tipoPer;
    }

    public List<Telefono> getTelefonosList() {
        return telefonosList;
    }

    public void setTelefonosList(List<Telefono> telefonosList) {
        this.telefonosList = telefonosList;
    }

    public List<Direccion> getDireccionesList() {
        return direccionesList;
    }

    public void setDireccionesList(List<Direccion> direccionesList) {
        this.direccionesList = direccionesList;
    }

    public List<Documento> getDocumentosList() {
        return documentosList;
    }

    public void setDocumentosList(List<Documento> documentosList) {
        this.documentosList = documentosList;
    }

    public void buscar() {

        try {
            Map filtro = new HashMap();

            boolean hayDui = false;
            boolean hay = false;
            if (duiB != null && !duiB.isEmpty()) {
                filtro.put("dui", duiB);
                hayDui = true;
                hay = true;
            }
            if (nombre1B != null && !nombre1B.isEmpty()) {
                filtro.put("nombre1", nombre1B);
                hay = true;
            }
            if (nombre2B != null && !nombre2B.isEmpty()) {
                filtro.put("nombre2", nombre2B);
                hay = true;
            }
            if (apellido1B != null && !apellido1B.isEmpty()) {
                filtro.put("apellido1", apellido1B);
                hay = true;
            }
            if (apellido2B != null && !apellido2B.isEmpty()) {
                filtro.put("apellido2", apellido2B);
                hay = true;
            }

            if (!hay) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Agregue un parámetro de búsqueda", null);
                return;
            }

            if (hayDui) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró metodo.", null);
            } else {
                lstPersonas = servPersona.buscarPersonas(filtro);
            }

            if (lstPersonas.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void limpiarBusquedaPersonas() {
        lstPersonas = new ArrayList<>();
        duiB = "";
        nombre1B = "";
        nombre2B = "";
        apellido1B = "";
        apellido2B = "";
    }

    public void limpiarPersona() {
        nombre1 = new String();
        nombre2 = new String();
        apellido1 = new String();
        apellido2 = new String();
        apecasada = new String();
        fechaNacimiento = null;
        sexo = new String();
        tipoPer = 0;
        estCivil = 0;

        this.telefonosList = new ArrayList<>();
        this.direccionesList = new ArrayList<>();
        this.documentosList = new ArrayList<>();
        limpiarTelefono();
        limpiarDireccion();
        limpiarDocumento();

    }

    public void limpiarTelefono() {
        tipoTel = 0;
        numeroTel = new String();
    }

    public void limpiarDireccion() {
        tipoDir = 0;
        nuevaDirecc = new String();
    }

    public void limpiarDocumento() {
        tipoDoc = 0;
        numeroDoc = new String();
    }

    public void onRowUnselect(UnselectEvent event) {
        personaActual = new Persona();
    }

    public void onRowSelect(SelectEvent event) throws IOException {
        try {
            limpiarPersona();

            personaActual = new Persona();

            personaActual = personaSelB;

            nombre1 = personaActual.getNombre1();
            nombre2 = personaActual.getNombre2();
            apellido1 = personaActual.getApellido1();
            apellido2 = personaActual.getApellido2();
            apecasada = personaActual.getApecasada();
            fechaNacimiento = personaActual.getFechanac();
            sexo = personaActual.getSexo();
            tipoPer = personaActual.getTipo().getId();
            telefonosList = personaActual.getTelefonoList();
            direccionesList = personaActual.getDireccionList();
            documentosList = personaActual.getDocumentoList();
            tabindex = 1;
            if (personaActual.getEstadoCivil() != null) {
                estCivil = personaActual.getEstadoCivil().getId();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaPersona() {
        try {
            limpiarPersona();
            nuevaPersona = true;
            this.personaActual = new Persona();
            Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuario");

            this.showMessage(FacesMessage.SEVERITY_WARN,
                    "Agregue información requerida.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarPersona() {
        boolean hay = false;
        try {
            List<String> mensajes = new ArrayList<>();

            if (tipoPer == 0) {
                mensajes.add("Seleccione tipo de persona.");
            }
            if (nombre1.isEmpty()) {
                mensajes.add("Digite primer nombre.");
            }
            if (apellido1.isEmpty()) {
                mensajes.add("Digite primer apellido.");
            }
            if (sexo == null || sexo.isEmpty()) {
                mensajes.add("Seleccione sexo.");
            }
            if (fechaNacimiento == null) {
                mensajes.add("Seleccione fecha de nacimiento.");
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

    public void guardarPersona() {
        try {
            if (validarPersona()) {
                return;
            }

            this.personaActual.setNombre1(nombre1);
            this.personaActual.setNombre2(nombre2);
            this.personaActual.setApellido1(apellido1);
            this.personaActual.setApellido2(apellido2);
            this.personaActual.setApecasada(apecasada);
            this.personaActual.setFechanac(fechaNacimiento);
            this.personaActual.setSexo(sexo);
            this.personaActual.setTipo(servCat.findItemCatalogoById(tipoPer));
            this.personaActual.setEstadoCivil(servCat.findItemCatalogoById(estCivil));
            String nomcom = nombre1.concat(" ").concat(nombre2);
            nomcom = nomcom.concat(" ").concat(apellido1);
            nomcom = nomcom.concat(" ").concat(apecasada != null ? (" DE " + apecasada) : apellido2);
            this.personaActual.setNomcom(nomcom);

            this.personaActual.setDireccionList(direccionesList);
            this.personaActual.setTelefonoList(telefonosList);
            this.personaActual.setDocumentoList(documentosList);

            if (nuevaPersona) {
                this.servPersona.nuevaPersona(personaActual);
            } else {
                personaActual = this.servPersona.actualizarPersona(personaActual);
            }

            // Actualizar docs para actualizar id's
            telefonosList = personaActual.getTelefonoList();
            direccionesList = personaActual.getDireccionList();
            documentosList = personaActual.getDocumentoList();

            this.showMessage(FacesMessage.SEVERITY_INFO, "Persona guardada exitosamente.", null);

            nuevaPersona = false;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar persona.", ex.getLocalizedMessage());
        }
    }

    public void agregarTelefono() {
        try {

            if (validarTelefono()) {
                return;
            }

            Telefono telefono = new Telefono();
            telefono.setPersona(personaActual);
            telefono.setTipo(servCat.findItemCatalogoById(tipoTel));
            telefono.setNumero(numeroTel);

            if (!telefonosList.contains(telefono)) {
                telefonosList.add(telefono);
                limpiarTelefono();
            } else {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Ya existe este tipo de teléfono en la lista.", null);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarTelefono() {
        boolean hay = false;
        try {
            if (numeroTel.isEmpty()) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Digite un teléfono válido", null);
            }
            if (tipoTel == 0) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Seleccione tipo de teléfono.", null);
            }
            boolean yaExiste = telefonosList.stream()
                    .anyMatch(telefono -> tipoTel.equals(telefono.getTipo().getId()));
            if (yaExiste) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Tipo de teléfono ya existe.", null);
            }
        } catch (Exception ex) {

        }
        return hay;
    }

    public void eliminarTelefono(Telefono tel) {
        try {
            tel.setPersona(null);
            telefonosList.remove(tel);
            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Eliminado de la lista.", null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void agregarDireccion() {
        try {

            if (validarDireccion()) {
                return;
            }

            Direccion dir = new Direccion();
            dir.setPersona(personaActual);
            dir.setTipo(servCat.findItemCatalogoById(tipoDir));
            dir.setDireccion(nuevaDirecc);
            if (!direccionesList.contains(dir)) {
                direccionesList.add(dir);
                limpiarDireccion();
            } else {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Ya existe este tipo de dirección..", null);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarDireccion() {
        boolean hay = false;
        try {
            if (nuevaDirecc.isEmpty()) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Digite una dirección válida.", null);
            }
            if (tipoDir == 0) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Seleccione tipo de dirección.", null);
            }

            boolean yaExiste = direccionesList.stream()
                    .anyMatch(direccion -> tipoDir.equals(direccion.getTipo().getId()));
            if (yaExiste) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Tipo de dirección ya existe.", null);
            }

        } catch (Exception ex) {

        }
        return hay;
    }

    public void eliminarDireccion(Direccion dir) {
        try {
            dir.setPersona(null);
            direccionesList.remove(dir);
            this.showMessage(FacesMessage.SEVERITY_INFO,
                    "Eliminado de la lista.", null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void agregarDocumento() {
        try {

            if (validarDocumento()) {
                return;
            }

            Documento doc = new Documento();
            doc.setPersona(personaActual);
            doc.setTipo(servCat.findItemCatalogoById(tipoDoc));
            doc.setNumero(numeroDoc);

            if (!documentosList.contains(doc)) {
                documentosList.add(doc);
                limpiarDocumento();
            } else {
                this.showMessage(FacesMessage.SEVERITY_INFO,
                        "Ya existe este tipo de documento.", null);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarDocumento() {
        boolean hay = false;
        try {
            if (numeroDoc.isEmpty()) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_INFO,
                        "Digite una número válido.", null);
            }
            if (tipoDoc == 0) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_INFO,
                        "Seleccione tipo de documento.", null);
            }
            boolean yaExiste = documentosList.stream()
                    .anyMatch(documento -> tipoDoc.equals(documento.getTipo().getId()));
            if (yaExiste) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Tipo de documento ya existe.", null);
            }
        } catch (Exception ex) {

        }
        return hay;
    }

    public void eliminarDocumento(Documento doc) {
        try {
            doc.setPersona(null);
            documentosList.remove(doc);
            this.showMessage(FacesMessage.SEVERITY_INFO, "Eliminado de la lista.", null);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
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
