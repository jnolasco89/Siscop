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
@Table(name = "saldocuentasl")
public class Saldocuentasl implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SaldocuentaslPK saldocuentaslPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldoinicial")
    private BigDecimal saldoinicial;
    @Column(name = "cargos")
    private BigDecimal cargos;
    @Column(name = "abonos")
    private BigDecimal abonos;
    @Column(name = "saldofinal")
    private BigDecimal saldofinal;

    public Saldocuentasl() {
    }

    public Saldocuentasl(SaldocuentaslPK saldocuentaslPK) {
        this.saldocuentaslPK = saldocuentaslPK;
    }

    public Saldocuentasl(long idproyecto, String codigocuenta, long sublibro, int anio, int mes) {
        this.saldocuentaslPK = new SaldocuentaslPK(idproyecto, codigocuenta, sublibro, anio, mes);
    }

    public SaldocuentaslPK getSaldocuentaslPK() {
        return saldocuentaslPK;
    }

    public void setSaldocuentaslPK(SaldocuentaslPK saldocuentaslPK) {
        this.saldocuentaslPK = saldocuentaslPK;
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
        hash += (saldocuentaslPK != null ? saldocuentaslPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saldocuentasl)) {
            return false;
        }
        Saldocuentasl other = (Saldocuentasl) object;
        if ((this.saldocuentaslPK == null && other.saldocuentaslPK != null) || (this.saldocuentaslPK != null && !this.saldocuentaslPK.equals(other.saldocuentaslPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Saldocuentasl[ saldocuentaslPK=" + saldocuentaslPK + " ]";
    }
    
}
