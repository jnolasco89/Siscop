/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Henry
 */
@Entity
@Table(name = "detallepartida")
public class Detallepartida implements Serializable {

    private static final long serialVersionUID = 1L;
   @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partidadetalle_generator")
    @SequenceGenerator(name = "partidadetalle_generator", sequenceName = "seq_partidadetalle", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "aplicacion")
    private Short aplicacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private BigDecimal monto;
    @JoinColumns({
        @JoinColumn(name = "codcuenta", referencedColumnName = "codigo")
        , @JoinColumn(name = "idproyecto", referencedColumnName = "idproyecto")})
    @ManyToOne
    private Cuenta cuenta;
    @JoinColumn(name = "idpartida", referencedColumnName = "id")
    @ManyToOne
    private Partida partida;
    @JoinColumn(name = "sublibro", referencedColumnName = "id")
    @ManyToOne
    private Persona sublibro;

    public Detallepartida() {
    }

    public Detallepartida(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Short aplicacion) {
        this.aplicacion = aplicacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Persona getSublibro() {
        return sublibro;
    }

    public void setSublibro(Persona sublibro) {
        this.sublibro = sublibro;
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
        if (!(object instanceof Detallepartida)) {
            return false;
        }
        Detallepartida other = (Detallepartida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Detallepartida[ id=" + id + " ]";
    }
    
}
