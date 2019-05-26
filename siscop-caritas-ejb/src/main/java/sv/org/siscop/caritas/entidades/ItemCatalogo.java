/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
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
    @NamedQuery(name = "ItemCatalogo.findAll", query = "SELECT i FROM ItemCatalogo i")
    , @NamedQuery(name = "ItemCatalogo.findById", query = "SELECT i FROM ItemCatalogo i WHERE i.id = :id")
    , @NamedQuery(name = "ItemCatalogo.findByCodigo", query = "SELECT i FROM ItemCatalogo i WHERE i.codigo = :codigo")
    , @NamedQuery(name = "ItemCatalogo.findByDescripcion", query = "SELECT i FROM ItemCatalogo i WHERE i.descripcion = :descripcion")})
public class ItemCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autoinc_generator")
    @SequenceGenerator(name="autoinc_generator", sequenceName = "seq_autoincrementables", allocationSize = 1)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "idcatalogo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Catalogo idcatalogo;

    public ItemCatalogo() {
    }

    public ItemCatalogo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Catalogo getIdcatalogo() {
        return idcatalogo;
    }

    public void setIdcatalogo(Catalogo idcatalogo) {
        this.idcatalogo = idcatalogo;
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
        if (!(object instanceof ItemCatalogo)) {
            return false;
        }
        ItemCatalogo other = (ItemCatalogo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.ItemCatalogo[ id=" + id + " ]";
    }
    
}
