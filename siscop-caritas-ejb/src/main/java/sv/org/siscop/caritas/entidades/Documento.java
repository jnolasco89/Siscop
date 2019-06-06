/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henry
 */
@Entity
@Table(name = "documento")
@EntityListeners(AuditListener.class)
public class Documento implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "idtipo")
    private BigInteger idtipo;
    @Size(max = 20)
    @Column(name = "numero")
    private String numero;
    @Column(name = "fechaexpedicion")
    @Temporal(TemporalType.DATE)
    private Date fechaexpedicion;
    @Column(name = "fechavence")
    @Temporal(TemporalType.DATE)
    private Date fechavence;

    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    @ManyToOne
    private Persona persona;
    @Embedded
    private Audit audit;

    public Documento() {
    }

    public Documento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(BigInteger idtipo) {
        this.idtipo = idtipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaexpedicion() {
        return fechaexpedicion;
    }

    public void setFechaexpedicion(Date fechaexpedicion) {
        this.fechaexpedicion = fechaexpedicion;
    }

    public Date getFechavence() {
        return fechavence;
    }

    public void setFechavence(Date fechavence) {
        this.fechavence = fechavence;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }

    @Override
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Documento other = (Documento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Documento[ id=" + id + " ]";
    }

}
