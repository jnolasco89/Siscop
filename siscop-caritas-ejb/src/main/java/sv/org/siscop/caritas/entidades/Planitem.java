/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Henry
 */
@Entity
@Table(name = "planitem")
public class Planitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planitem_generator")
    @SequenceGenerator(name = "planitem_generator", sequenceName = "seq_planitem", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    private Long id;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 100)
    @Column(name = "producto")
    private String producto;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "idmedida", referencedColumnName = "id")
    @ManyToOne
    private ItemCatalogo medida;
    @JoinColumn(name = "idplancotizacion", referencedColumnName = "id")
    @ManyToOne
    private Plancotizacion plancotizacion;

    public Planitem() {
    }

    public Planitem(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ItemCatalogo getMedida() {
        return medida;
    }

    public void setMedida(ItemCatalogo medida) {
        this.medida = medida;
    }

    public Plancotizacion getPlancotizacion() {
        return plancotizacion;
    }

    public void setPlancotizacion(Plancotizacion plancotizacion) {
        this.plancotizacion = plancotizacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Planitem other = (Planitem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Planitem[ id=" + id + " ]";
    }

}
