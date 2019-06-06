/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
    @Column(name = "apecasada")
    private String apecasada;
    @OneToMany(mappedBy = "persona")
    private List<Direccion> direccionList;
    @OneToMany(mappedBy = "persona")
    private List<Documento> documentoList;
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

    public String getApecasada() {
        return apecasada;
    }

    public void setApecasada(String apecasada) {
        this.apecasada = apecasada;
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
