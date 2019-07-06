/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
@Table(name = "cheque")
public class Cheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_generator")
    @SequenceGenerator(name = "cheque_generator", sequenceName = "seq_cheque", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private BigDecimal monto;
    @Size(max = 200)
    @Column(name = "cantidadletras")
    private String cantidadletras;
    @Size(max = 200)
    @Column(name = "afavorde")
    private String afavorde;
    @Size(max = 300)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 500)
    @Column(name = "comentarios")
    private String comentarios;
    @JoinColumn(name = "idestado", referencedColumnName = "id")
    @ManyToOne
    private ItemCatalogo estado;
    @JoinColumn(name = "idactividad", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Actividad actividad;
    @JoinColumn(name = "idproyecto", referencedColumnName = "id")
    @ManyToOne
    private Proyecto proyecto;
    @OneToMany(mappedBy = "cheque")
    private List<Detallecheque> detallechequeList;

    public Cheque() {
    }

    public Cheque(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getCantidadletras() {
        return cantidadletras;
    }

    public void setCantidadletras(String cantidadletras) {
        this.cantidadletras = cantidadletras;
    }

    public String getAfavorde() {
        return afavorde;
    }

    public void setAfavorde(String afavorde) {
        this.afavorde = afavorde;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public ItemCatalogo getEstado() {
        return estado;
    }

    public void setEstado(ItemCatalogo estado) {
        this.estado = estado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<Detallecheque> getDetallechequeList() {
        return detallechequeList;
    }

    public void setDetallechequeList(List<Detallecheque> detallechequeList) {
        this.detallechequeList = detallechequeList;
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
        if (!(object instanceof Cheque)) {
            return false;
        }
        Cheque other = (Cheque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Cheque[ id=" + id + " ]";
    }

}
