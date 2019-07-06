/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henry
 */
@Embeddable
public class SaldocuentaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idproyecto")
    private long idproyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigocuenta")
    private String codigocuenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sublibro")
    private long sublibro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    private int mes;

    public SaldocuentaPK() {
    }

    public SaldocuentaPK(long idproyecto, String codigocuenta, long sublibro, int anio, int mes) {
        this.idproyecto = idproyecto;
        this.codigocuenta = codigocuenta;
        this.sublibro = sublibro;
        this.anio = anio;
        this.mes = mes;
    }

    public long getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getCodigocuenta() {
        return codigocuenta;
    }

    public void setCodigocuenta(String codigocuenta) {
        this.codigocuenta = codigocuenta;
    }

    public long getSublibro() {
        return sublibro;
    }

    public void setSublibro(long sublibro) {
        this.sublibro = sublibro;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idproyecto;
        hash += (codigocuenta != null ? codigocuenta.hashCode() : 0);
        hash += (int) sublibro;
        hash += (int) anio;
        hash += (int) mes;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaldocuentaPK)) {
            return false;
        }
        SaldocuentaPK other = (SaldocuentaPK) object;
        if (this.idproyecto != other.idproyecto) {
            return false;
        }
        if ((this.codigocuenta == null && other.codigocuenta != null) || (this.codigocuenta != null && !this.codigocuenta.equals(other.codigocuenta))) {
            return false;
        }
        if (this.sublibro != other.sublibro) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        if (this.mes != other.mes) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.SaldocuentaPK[ idproyecto=" + idproyecto + ", codigocuenta=" + codigocuenta + ", sublibro=" + sublibro + ", anio=" + anio + ", mes=" + mes + " ]";
    }
    
}
