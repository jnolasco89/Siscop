/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.entidades.Catalogo;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.validadores.ValidacionesCatalogo;

/**
 *
 * @author Nolasco
 */
@Named(value = "mttoCatalogo")
@ViewScoped
public class MttoCatalogo implements Serializable {

    @EJB
    ServiciosCatalogoLocal servCat;

    //Propiedades para la logica de la ui
    private int tabActiva;
    private int tipoModal;
    private String tituloModal;
    private String tituloBtnModal;
    private String campoBusqueda;
    private List<Catalogo> catalogos;
    //Propiedades para el modelo de catalogo
    private Catalogo catalogoActual;
    private ItemCatalogo itemActual;
    private List<ItemCatalogo> itemsActuales;
    //Objeto para la validacion del catalogo e items
    private ValidacionesCatalogo validaciones;

    /**
     * Creates a new instance of MttoCatalogo
     */
    public MttoCatalogo() {
    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
        tipoModal = 1;//1 para agregar y 2 para editar
        tituloModal = "Agregar item";
        tituloBtnModal = "Agregar";
        campoBusqueda = "";
        catalogos = servCat.getAllCatalogos();
        catalogoActual = new Catalogo();
        itemActual = new ItemCatalogo();
        itemsActuales = new ArrayList<>();
        validaciones = new ValidacionesCatalogo();
    }

    //================ METODOS GET Y SET =================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    public List<Catalogo> getCatalogos() {
        if (catalogos == null) {
            catalogos = new ArrayList<>();
        }
        return catalogos;
    }

    public void setCatalogos(List<Catalogo> catalogos) {
        this.catalogos = catalogos;
    }

    public Catalogo getCatalogoActual() {
        if (catalogoActual == null) {
            catalogoActual = new Catalogo();
        }
        return catalogoActual;
    }

    public void setCatalogoActual(Catalogo catalogoActual) {
        this.catalogoActual = catalogoActual;
    }

    public ItemCatalogo getItemActual() {
        if (itemActual == null) {
            itemActual = new ItemCatalogo();
        }
        return itemActual;
    }

    public void setItemActual(ItemCatalogo itemActual) {
        this.itemActual = itemActual;
    }

    public List<ItemCatalogo> getItemsActuales() {
        if (itemsActuales == null) {
            itemsActuales = new ArrayList<>();
        }
        return itemsActuales;
    }

    public void setItemsActuales(List<ItemCatalogo> itemsActuales) {
        this.itemsActuales = itemsActuales;
    }

    public String getTituloModal() {
        return tituloModal;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public String getTituloBtnModal() {
        return tituloBtnModal;
    }

    public void setTituloBtnModal(String tituloBtnModal) {
        this.tituloBtnModal = tituloBtnModal;
    }

    public ValidacionesCatalogo getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(ValidacionesCatalogo validaciones) {
        this.validaciones = validaciones;
    }

    //================ METODOS DE FUNCIONALIDAD ============
    //Para pestaña busqueda-------------
    public void buscarCatalogo() {
        Map campos = new HashMap();
        campos.put("nombre", this.campoBusqueda);

        catalogos = servCat.buscarCatalogoPorCualquierCampo(campos);
    }

    public void limpiarBusqueda() {
        catalogoActual = new Catalogo();
        campoBusqueda = "";
        catalogos = servCat.getAllCatalogos();
    }

    public String estadoEnString(boolean estado) {
        if (estado) {
            return "Activo";
        }
        return "Inactivo";
    }

    public void cargarCatalogoSeleccionado() {
        tabActiva = 1;
    }

    //Para pestaña detalles -------------
    public void guardarCatalogo() {
        System.out.println("Guardar el catalogo");
    }

    public void limpiarFormularioYbusqueda() {
        catalogoActual = new Catalogo();
        itemsActuales = new ArrayList<>();
        tabActiva = 0;
    }

    public void agregarItem() {
            itemsActuales.add(itemActual);
            itemActual = new ItemCatalogo();
    }

    public void editarItem() {
        itemsActuales.get(itemActual.getId()).setId(null);
        itemsActuales.get(itemActual.getId()).setDescripcion(itemActual.getDescripcion());
        itemActual = new ItemCatalogo();
    }

    public void eliminarItem(int index) {
        itemsActuales.remove(index);
    }

    public void abrirModalNuevoItem() {
        tipoModal = 1;
        tituloModal = "Agregar item";
        tituloBtnModal = "Agregar";
        PrimeFaces.current().executeScript("PF('dialogItemCatalogo').show();");
    }

    public void abrirModalEditarItem(int index) {
        tipoModal = 2;
        tituloModal = "Editar item";
        tituloBtnModal = "Editar";
        itemActual.setId(index);
        itemActual.setDescripcion(itemsActuales.get(index).getDescripcion());

        PrimeFaces.current().executeScript("PF('dialogItemCatalogo').show();");
    }

    public void accionModal() {
        if (tipoModal == 1) {//Para agregar Item
            agregarItem();
        } else if (tipoModal == 2) {//Para editar Item
            editarItem();
        }
    }

    public void limpiarModal() {
        itemActual = new ItemCatalogo();
    }
}