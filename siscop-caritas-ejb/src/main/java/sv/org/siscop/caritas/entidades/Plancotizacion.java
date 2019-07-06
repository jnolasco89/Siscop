/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "plancotizacion")
public class Plancotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plancotizacion_generator")
    @SequenceGenerator(name = "plancotizacion_generator", sequenceName = "seq_plancotizacion", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "idproyecto")
    private Long idproyecto;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 2000)
    @Column(name = "analisis")
    private String analisis;
    @Size(max = 300)
    @Column(name = "nota")
    private String nota;
    @JoinColumn(name = "idactividad", referencedColumnName = "id")
    @ManyToOne
    private Actividad actividad;
    @OneToMany(mappedBy = "plancotizacion", orphanRemoval = true)
    private List<Planitem> planitemList;
    @OneToMany(mappedBy = "plantilla", orphanRemoval = true)
    private List<Cotizacion> cotizacionList;

    @JoinColumn(name = "idcotizacionsel", referencedColumnName = "id")
    @ManyToOne
    private Cotizacion cotizacionSel;

    public Plancotizacion() {
    }

    public Plancotizacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public List<Planitem> getPlanitemList() {
        return planitemList;
    }

    public void setPlanitemList(List<Planitem> planitemList) {
        this.planitemList = planitemList;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Cotizacion getCotizacionSel() {
        return cotizacionSel;
    }

    public void setCotizacionSel(Cotizacion cotizacionSel) {
        this.cotizacionSel = cotizacionSel;
    }

    public List<Cotizacion> getCotizacionList() {
        return cotizacionList;
    }

    public void setCotizacionList(List<Cotizacion> cotizacionList) {
        this.cotizacionList = cotizacionList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Plancotizacion other = (Plancotizacion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Plancotizacion[ id=" + id + " ]";
    }

}
