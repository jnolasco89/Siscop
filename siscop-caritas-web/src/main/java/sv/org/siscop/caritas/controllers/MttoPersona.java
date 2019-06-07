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
//import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.ejb.ServiciosPersonaLocal;
import sv.org.siscop.caritas.entidades.Direccion;
import sv.org.siscop.caritas.entidades.Documento;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Persona;
import sv.org.siscop.caritas.entidades.Telefono;
import sv.org.siscop.caritas.entidades.Usuario;

/**
 *
 * @author jos
 */
@Named(value = "mttoPersona")
@SessionScoped
public class MttoPersona implements Serializable {

    @EJB
    private ServiciosCatalogoLocal servCat;
    @EJB
    private ServiciosPersonaLocal servPersona;

    /**
     * Creates a new instance of MttoPersonas
     */
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
    private Telefono telefono;
    private Direccion nuevaDireccion;

    private Long codper;
    private String nombre1P;
    private String nombre2P;
    private String apellido1P;
    private String apellido2P;
    private String apecasada2P;
    private String numeroDoc;
    private String numeroTel;
    private String nuevaDirecc;
    Date fechaNacP;
    Integer codperNew;
    String sexo;
    Integer tipoPer;

    int tabindex = 0;

    List<SelectItem> tipotelLst = new ArrayList<>();
    List<SelectItem> tipoDireccLst = new ArrayList<>();
    List<SelectItem> tipoDocLst = new ArrayList<>();
    List<SelectItem> estadoCivilLst = new ArrayList<>();
    List<Telefono> personaTelList = new ArrayList<>();
    List<Direccion> personaDireccList = new ArrayList<>();
    List<Documento> personaDocList = new ArrayList<>();

    private List<Persona> lstPersonas = new ArrayList<>();

    public String getDuiB() {
        return duiB;
    }

    public void setDuiB(String duiB) {
        this.duiB = duiB;
    }

    public Long getCodper() {
        return codper;
    }

    public void setCodper(Long codper) {
        this.codper = codper;
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

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public Direccion getNuevaDireccion() {
        return nuevaDireccion;
    }

    public void setNuevaDireccion(Direccion nuevaDireccion) {
        this.nuevaDireccion = nuevaDireccion;
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

            for (ItemCatalogo item : this.servCat.findCatalogoById(3).getItemCatalogoList()) {
                tipotelLst.add(new SelectItem(item.getCodigo(), item.getDescripcion()));
            }

        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipotelLst;
    }

    public List<SelectItem> getTipoDireccLst() {

        try {
            tipoDireccLst.clear();
            for (ItemCatalogo item : this.servCat.findCatalogoById(4).getItemCatalogoList()) {
                tipoDireccLst.add(new SelectItem(item.getCodigo(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipoDireccLst;
    }

    public List<SelectItem> getTipoDocLst() {

        try {
            tipoDocLst.clear();
            for (ItemCatalogo item : this.servCat.findCatalogoById(5).getItemCatalogoList()) {
                tipoDocLst.add(new SelectItem(item.getCodigo(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipoDocLst;
    }

    public List<SelectItem> getEstadoCivilLst() {
        try {
            estadoCivilLst.clear();
            for (ItemCatalogo item : this.servCat.findCatalogoById(6).getItemCatalogoList()) {
                estadoCivilLst.add(new SelectItem(item.getCodigo(), item.getDescripcion()));
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }

        return estadoCivilLst;
    }

    public String getNombre1P() {
        return nombre1P;
    }

    public void setNombre1P(String nombre1P) {
        this.nombre1P = nombre1P;
    }

    public String getNombre2P() {
        return nombre2P;
    }

    public void setNombre2P(String nombre2P) {
        this.nombre2P = nombre2P;
    }

    public String getApellido1P() {
        return apellido1P;
    }

    public void setApellido1P(String apellido1P) {
        this.apellido1P = apellido1P;
    }

    public String getApellido2P() {
        return apellido2P;
    }

    public void setApellido2P(String apellido2P) {
        this.apellido2P = apellido2P;
    }

    public String getApecasada2P() {
        return apecasada2P;
    }

    public void setApecasada2P(String apecasada2P) {
        this.apecasada2P = apecasada2P;
    }

    public Date getFechaNacP() {
        return fechaNacP;
    }

    public void setFechaNacP(Date fechaNacP) {
        this.fechaNacP = fechaNacP;
    }

    public Integer getCodperNew() {
        return codperNew;
    }

    public void setCodperNew(Integer codperNew) {
        this.codperNew = codperNew;
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

    public void setTipotelLst(List<SelectItem> tipotelLst) {
        this.tipotelLst = tipotelLst;
    }

    public List<Telefono> getPersonaTelList() {
        return personaTelList;
    }

    public void setPersonaTelList(List<Telefono> personaTelList) {
        this.personaTelList = personaTelList;
    }

    public List<Direccion> getPersonaDireccList() {
        return personaDireccList;
    }

    public void setPersonaDireccList(List<Direccion> personaDireccList) {
        this.personaDireccList = personaDireccList;
    }

    public List<Documento> getPersonaDocList() {
        return personaDocList;
    }

    public void setPersonaDocList(List<Documento> personaDocList) {
        this.personaDocList = personaDocList;
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
                        "No se encontrO metodo.", null);
            } else {
                lstPersonas = servPersona.buscarPersonas(filtro);
            }

            if (lstPersonas.isEmpty()) {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "No se encontró ningún resultado.", null);
            }

        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
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
        this.personaTelList = new ArrayList<>();
        this.personaDireccList = new ArrayList<>();
        this.personaDocList = new ArrayList<>();
        codper = null;
        nombre1P = new String();
        nombre2P = new String();
        apellido1P = new String();
        apellido2P = new String();
        apecasada2P = new String();
        fechaNacP = null;

        tipoDir = 0;
        tipoTel = 0;
        tipoPer = null;
        tipoDoc = 0;
        estCivil = 0;
        sexo = new String();
        nuevaDirecc = new String();
        numeroTel = new String();
        numeroDoc = new String();

    }

    public void onRowSelect(SelectEvent event) throws IOException {
        try {
            limpiarPersona();

            personaActual = new Persona();

            personaActual = personaSelB;

            codper = personaActual.getId();
            nombre1P = personaActual.getNombre1();
            nombre2P = personaActual.getNombre2();
            apellido1P = personaActual.getNombre1();
            apellido2P = personaActual.getApellido2();
            apecasada2P = personaActual.getApecasada();
            fechaNacP = personaActual.getFechanac();
            sexo = personaActual.getSexo();
            tipoPer = personaActual.getTipo().getId();
            personaTelList = personaActual.getTelefonoList();
            personaDireccList = personaActual.getDireccionList();
            personaDocList = personaActual.getDocumentoList();
            tabindex = 1;
            numeroDoc = null;
            numeroTel = null;
            nuevaDirecc = null;
            if (personaActual.getEstadoCivil() != null) {
                estCivil = personaActual.getEstadoCivil().getId();
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaPersona() {
        try {
            limpiarPersona();
            nuevaPersona = true;
            this.personaActual = new Persona();
            facesContext = FacesContext.getCurrentInstance();
            Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuario");
//            codper = this.procBMT.getCorr("CODPE", "MttoServicio", user.getUsuario());
//            personaActual.setCodper(codper);

            this.showMessage(FacesMessage.SEVERITY_WARN,
                    "Agregue información requerida.", null);

        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarPersona() {
        boolean hay = false;
        try {
            List<String> mensajes = new ArrayList<>();

            if (tipoPer.equals("0")) {
                mensajes.add("Seleccione tipo de persona.");
            }
            if (nombre1P.isEmpty()) {
                mensajes.add("Digite primer nombre.");
            }
            if (apellido1P.isEmpty()) {
                mensajes.add("Digite primer apellido.");
            }
            if (sexo == null || sexo.isEmpty()) {
                mensajes.add("Seleccione sexo.");
            }

            if (fechaNacP == null) {
                mensajes.add("Seleccione fecha de nacimiento.");
            }

            for (String msj : mensajes) {
                hay = true;
                this.showMessage(FacesMessage.SEVERITY_WARN, msj, null);
            }

        } catch (Exception ex) {
            this.showMessage(FacesMessage.SEVERITY_WARN,
                    "Error al validar persona.", ex.getLocalizedMessage());
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hay;
    }

    public void guardarPersona() {
        try {
            if (validarPersona()) {
                return;
            }
//
//            if (newPerson) {
//                this.personaActual = new Persona();
//                this.personaActual.setCodper(codper);
//            }
            this.personaActual.setNombre1(nombre1P);
            this.personaActual.setNombre2(nombre2P);
            this.personaActual.setApellido1(apellido1P);
            this.personaActual.setApellido2(apellido2P);
            this.personaActual.setApecasada(apecasada2P);
            this.personaActual.setFechanac(fechaNacP);
            this.personaActual.setTipo(servCat.findItemCatalogoById(tipoPer));
            this.personaActual.setSexo(sexo);
            this.personaActual.setNomcom(personaActual.getNombre1() + " "
                    + personaActual.getNombre2() + " " + personaActual.getApellido1()
                    + " " + personaActual.getApellido2());

            this.personaActual.setDireccionList(personaDireccList);
            this.personaActual.setTelefonoList(personaTelList);
            this.personaActual.setDocumentoList(personaDocList);

            this.personaActual.setEstadoCivil(servCat.findItemCatalogoById(estCivil));

            this.servPersona.nuevaPersona(personaActual);
            this.showMessage(FacesMessage.SEVERITY_INFO, "Persona guardada exitosamente.", null);

            nuevaPersona = false;

        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar la persona.", ex.getLocalizedMessage());
        }

    }

    public void agregarTelefono() {
        try {

            if (validarTelefono()) {
                return;
            }

            telefono = new Telefono();
            telefono.setPersona(personaActual);
            telefono.setTipo(servCat.findItemCatalogoById(tipoTel));
            telefono.setNumero(numeroTel);
            if (!personaTelList.contains(telefono)) {
                personaTelList.add(telefono);
                tipoTel = 0;
                numeroTel = "";
            } else {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Ya existe este tipo de teléfono en la lista.", null);
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {

        }
        return hay;
    }

    public void eliminarTelefono(Telefono tel) {
        try {
//            personaTelList.remove(tel);
//            this.procGen.eliminarEntidad(tel);
//            this.showMessage(FacesMessage.SEVERITY_INFO,
//                    "Eliminado de la lista.", null);

        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
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
            if (!personaDireccList.contains(dir)) {
                personaDireccList.add(dir);
                nuevaDirecc = null;
                tipoDir = 0;
                nuevaDirecc = "";
            } else {
                this.showMessage(FacesMessage.SEVERITY_WARN,
                        "Ya existe este tipo de dirección..", null);
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {

        }
        return hay;
    }

    public void eliminarDireccion(Direccion dir) {
        try {
//            personaDireccList.remove(dir);
//            this.procGen.eliminarEntidad(dir);
//            this.showMessage(FacesMessage.SEVERITY_INFO,
//                    "Eliminado de la lista.", null);
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarDocumento() {
        try {

            if (validarDocumento()) {
                return;
            }

            Documento doc = new Documento();
            doc.setPersona(personaActual);
            doc.setPersona(personaActual);
            doc.setTipo(servCat.findItemCatalogoById(tipoDoc));
            doc.setNumero(numeroDoc);
            if (!personaDocList.contains(doc)) {
                personaDocList.add(doc);
                tipoDoc = 0;
                numeroDoc = "";
            } else {
                this.showMessage(FacesMessage.SEVERITY_INFO,
                        "Ya existe este tipo de documento.", null);
            }
        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {

        }
        return hay;
    }

    public void eliminarDocumento(Documento doc) {
        try {
//            personaDocList.remove(doc);
//            this.procGen.eliminarEntidad(doc);
//
//            this.showMessage(FacesMessage.SEVERITY_INFO, "Eliminado de la lista.", null);

        } catch (Exception ex) {
            Logger.getLogger(MttoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowUnselect(UnselectEvent event) {
        personaActual = new Persona();
    }

    public void onTabChange(TabChangeEvent event) {
        this.tabindex = ((TabView) event.getSource()).getIndex();
    }

    public void showMessage(FacesMessage.Severity severidad, String error, String desc) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage mensaje = new FacesMessage(severidad, error, desc);
        facesContext.addMessage(null, mensaje);
    }

    public void showDialogo(String mensaje) {

        PrimeFaces.current().executeScript("PF('dlgAsigCte').show()");

    }

}
