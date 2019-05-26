/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nolasco
 */
@Entity
@Table(name = "catalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catalogo.findAll", query = "SELECT c FROM Catalogo c")
    , @NamedQuery(name = "Catalogo.findById", query = "SELECT c FROM Catalogo c WHERE c.id = :id")
    , @NamedQuery(name = "Catalogo.findByCodigo", query = "SELECT c FROM Catalogo c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Catalogo.findByNombre", query = "SELECT c FROM Catalogo c WHERE c.nombre = :nombre")})
public class Catalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autoinc_generator")
    @SequenceGenerator(name="autoinc_generator", sequenceName = "seq_autoincrementables", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idcatalogo", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<ItemCatalogo> itemCatalogoList;

    public Catalogo() {
    }

    public Catalogo(Long id) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<ItemCatalogo> getItemCatalogoList() {
        return itemCatalogoList;
    }

    public void setItemCatalogoList(List<ItemCatalogo> itemCatalogoList) {
        this.itemCatalogoList = itemCatalogoList;
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
        if (!(object instanceof Catalogo)) {
            return false;
        }
        Catalogo other = (Catalogo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.org.siscop.caritas.entidades.Catalogo[ id=" + id + " ]";
    }
    
}
