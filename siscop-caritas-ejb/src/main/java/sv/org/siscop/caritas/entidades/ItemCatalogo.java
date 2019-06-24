/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nolasco
 */
@Entity
@Table(name = "item_catalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemCatalogo.findAll", query = "SELECT i FROM ItemCatalogo i")})
public class ItemCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemCatalogo_generator")
    @SequenceGenerator(name = "itemCatalogo_generator", sequenceName = "seq_itemcatalogo", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "fechacrea")
    @Temporal(TemporalType.TIME)
    private Date fechacrea;
    @Size(max = 15)
    @Column(name = "usercrea")
    private String usercrea;
    @Column(name = "fechamod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamod;
    @Size(max = 15)
    @Column(name = "usermod")
    private String usermod;
    @JoinColumn(name = "idcatalogo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Catalogo idcatalogo;
    @Transient
    private boolean verEliminar;

    public ItemCatalogo() {
    }

    public ItemCatalogo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechacrea() {
        return fechacrea;
    }

    public void setFechacrea(Date fechacrea) {
        this.fechacrea = fechacrea;
    }

    public String getUsercrea() {
        return usercrea;
    }

    public void setUsercrea(String usercrea) {
        this.usercrea = usercrea;
    }

    public Date getFechamod() {
        return fechamod;
    }

    public void setFechamod(Date fechamod) {
        this.fechamod = fechamod;
    }

    public String getUsermod() {
        return usermod;
    }

    public void setUsermod(String usermod) {
        this.usermod = usermod;
    }

    public Catalogo getIdcatalogo() {
        return idcatalogo;
    }

    public void setIdcatalogo(Catalogo idcatalogo) {
        this.idcatalogo = idcatalogo;
    }

    public boolean isVerEliminar() {
        return verEliminar;
    }

    public void setVerEliminar(boolean verEliminar) {
        this.verEliminar = verEliminar;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ItemCatalogo other = (ItemCatalogo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.ItemCatalogo[ id=" + id + " ]";
    }
}
