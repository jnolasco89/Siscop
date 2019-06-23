/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Henry
 */
@Entity
@Table(name = "persona")
@EntityListeners(AuditListener.class)
public class Persona implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_generator")
    @SequenceGenerator(name = "persona_generator", sequenceName = "seq_persona", allocationSize = 1)
    private Long id;
    @JoinColumn(name = "idtipo", referencedColumnName = "id")
    @ManyToOne
    private ItemCatalogo tipo;
    @Size(max = 50)
    @Column(name = "nomcom")
    private String nomcom;
    @Size(max = 50)
    @Column(name = "razsocial")
    private String razsocial;
    @Size(max = 15)
    @Column(name = "nombre1")
    private String nombre1;
    @Size(max = 15)
    @Column(name = "nombre2")
    private String nombre2;
    @Size(max = 15)
    @Column(name = "nombre3")
    private String nombre3;
    @Size(max = 15)
    @Column(name = "apellido1")
    private String apellido1;
    @Size(max = 15)
    @Column(name = "apellido2")
    private String apellido2;
    @Size(max = 15)
    @Column(name = "apecasada")
    private String apecasada;
    @Column(name = "fechanac")
    @Temporal(TemporalType.DATE)
    private Date fechanac;
    @Size(max = 1)
    @Column(name = "sexo")
    private String sexo;
    @JoinColumn(name = "idestadocivil", referencedColumnName = "id")
    @ManyToOne
    private ItemCatalogo estadoCivil;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Proveedor empleado;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direccionList;
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentoList;
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonoList;
    @Embedded
    private Audit audit;

    public Persona() {
    }

    public Persona(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemCatalogo getTipo() {
        return tipo;
    }

    public void setTipo(ItemCatalogo tipo) {
        this.tipo = tipo;
    }

    public String getNomcom() {
        return nomcom;
    }

    public void setNomcom(String nomcom) {
        this.nomcom = nomcom;
    }

    public String getRazsocial() {
        return razsocial;
    }

    public void setRazsocial(String razsocial) {
        this.razsocial = razsocial;
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

    public String getNombre3() {
        return nombre3;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
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

    public Date getFechanac() {
        return fechanac;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public ItemCatalogo getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(ItemCatalogo estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Proveedor getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Proveedor empleado) {
        this.empleado = empleado;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }

    @Override
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public List<Direccion> getDireccionList() {
        return direccionList;
    }

    public void setDireccionList(List<Direccion> direccionList) {
        this.direccionList = direccionList;
    }

    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
    }

    public List<Telefono> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<Telefono> telefonoList) {
        this.telefonoList = telefonoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Persona[ id=" + id + " ]";
    }

}
