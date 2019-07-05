/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Henry
 */
@Entity
@Table(name = "saldocuenta")
public class Saldocuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SaldocuentaPK saldocuentaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldoinicial")
    private BigDecimal saldoinicial;
    @Column(name = "cargos")
    private BigDecimal cargos;
    @Column(name = "abonos")
    private BigDecimal abonos;
    @Column(name = "saldofinal")
    private BigDecimal saldofinal;

    public Saldocuenta() {
    }

    public Saldocuenta(SaldocuentaPK saldocuentaPK) {
        this.saldocuentaPK = saldocuentaPK;
    }

    public Saldocuenta(long idproyecto, String codigocuenta, long sublibro, int anio, int mes) {
        this.saldocuentaPK = new SaldocuentaPK(idproyecto, codigocuenta, sublibro, anio, mes);
    }

    public SaldocuentaPK getSaldocuentaPK() {
        return saldocuentaPK;
    }

    public void setSaldocuentaPK(SaldocuentaPK saldocuentaPK) {
        this.saldocuentaPK = saldocuentaPK;
    }

    public BigDecimal getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(BigDecimal saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public BigDecimal getCargos() {
        return cargos;
    }

    public void setCargos(BigDecimal cargos) {
        this.cargos = cargos;
    }

    public BigDecimal getAbonos() {
        return abonos;
    }

    public void setAbonos(BigDecimal abonos) {
        this.abonos = abonos;
    }

    public BigDecimal getSaldofinal() {
        return saldofinal;
    }

    public void setSaldofinal(BigDecimal saldofinal) {
        this.saldofinal = saldofinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saldocuentaPK != null ? saldocuentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saldocuenta)) {
            return false;
        }
        Saldocuenta other = (Saldocuenta) object;
        if ((this.saldocuentaPK == null && other.saldocuentaPK != null) || (this.saldocuentaPK != null && !this.saldocuentaPK.equals(other.saldocuentaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Saldocuenta[ saldocuentaPK=" + saldocuentaPK + " ]";
    }
    
}
