/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import sv.org.siscop.caritas.entidades.LoggedUser;
import sv.org.siscop.caritas.exceptions.ValidacionException;

/**
 *
 * @author Nolasco
 */
@Named(value = "catalogoController")
@ViewScoped
public class CatalogoController implements Serializable {
//    @EJB
//    NewSessionBean nb;

    @EJB
    private ServiciosCatalogoLocal servCat;
    private Catalogo currentCatalog;
    private List<Catalogo> listaCatalogos;
    private ItemCatalogo currentAddItem;
    private ItemCatalogo currentEditItem;
    private List<ItemCatalogo> itemsCatalogo;
    private int indexTablaItemsCatalogo;
    private int tabActiva;
    private int modoAgregarOeditar;
    private String campoBusqueda;

    /**
     * Creates a new instance of CatalogoController
     */
    public CatalogoController() {
    }

    @PostConstruct
    public void init() {
        LoggedUser.logIn("Jnolasco89");
        tabActiva = 0;
        indexTablaItemsCatalogo = -1;
        listaCatalogos = servCat.getAllCatalogos();
        modoAgregarOeditar = 1;
    }

    //============== GETTER AND SETTER  ================
    public Catalogo getCurrentCatalog() {
        if (currentCatalog == null) {
            currentCatalog = new Catalogo();
        }

        return currentCatalog;
    }

    public void setCurrentCatalog(Catalogo currentCatalog) {
        this.currentCatalog = currentCatalog;
    }

    public ItemCatalogo getCurrentAddItem() {
        if (currentAddItem == null) {
            currentAddItem = new ItemCatalogo();
        }
        return currentAddItem;
    }

    public void setCurrentAddItem(ItemCatalogo currentAddItem) {
        this.currentAddItem = currentAddItem;
    }

    public List<ItemCatalogo> getItemsCatalogo() {
        if (itemsCatalogo == null) {
            itemsCatalogo = new ArrayList<>();
        }
        return itemsCatalogo;
    }

    public void setItemsCatalogo(List<ItemCatalogo> itemsCatalogo) {
        this.itemsCatalogo = itemsCatalogo;
    }

    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public List<Catalogo> getListaCatalogos() {
        if (listaCatalogos == null) {
            listaCatalogos = new ArrayList<>();
        }
        return listaCatalogos;
    }

    public void setListaCatalogos(List<Catalogo> listaCatalogos) {
        this.listaCatalogos = listaCatalogos;
    }

    public ItemCatalogo getCurrentEditItem() {
        if (currentEditItem == null) {
            currentEditItem = new ItemCatalogo();
        }
        return currentEditItem;
    }

    public void setCurrentEditItem(ItemCatalogo currentEditItem) {
        this.currentEditItem = currentEditItem;
    }

    public String getCampoBusqueda() {
        if (campoBusqueda == null) {
            campoBusqueda = "";
        }
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    //================= METODOS  ===================
    public void validarCatalogo() throws ValidacionException {
        List<String> listaValidaciones = new ArrayList<>();

        if (currentCatalog.getCodigo().length() == 0) {
            listaValidaciones.add("Codigo de catalogo requerido");
        }

        if (currentCatalog.getNombre().length() == 0) {
            listaValidaciones.add("Nombre de catalogo requerido");
        }

        if (!listaValidaciones.isEmpty()) {
            throw new ValidacionException("Campo incorrecto", listaValidaciones);
        }
    }

    public void validarAddItemCatalogo() throws ValidacionException {
        List<String> listaValidaciones = new ArrayList<>();

        if (currentAddItem.getCodigo().length() == 0) {
            listaValidaciones.add("Codigo de item requerido");
        }

        if (currentAddItem.getDescripcion().length() == 0) {
            listaValidaciones.add("Descripcion de item requerida");
        }

        if (!listaValidaciones.isEmpty()) {
            throw new ValidacionException("Campo incorrecto", listaValidaciones);
        }
    }

    public void validarEditItemCatalogo() throws ValidacionException {
        List<String> listaValidaciones = new ArrayList<>();

        if (currentEditItem.getCodigo().length() == 0) {
            listaValidaciones.add("Codigo de item requerido");
        }

        if (currentEditItem.getDescripcion().length() == 0) {
            listaValidaciones.add("Descripcion de item requerida");
        }

        if (!listaValidaciones.isEmpty()) {
            throw new ValidacionException("Campo incorrecto", listaValidaciones);
        }
    }

    public void guardarCatalago() {
        try {
            validarCatalogo();

            String msjGrowl = "";
            //Para agregar0
            if (modoAgregarOeditar == 1) {
                System.out.println("INGRESA A AGREAGR");
                //Preparando y agregando el catalogo
                for (ItemCatalogo i : itemsCatalogo) {
                    i.setCatalogo(currentCatalog);
                }
                this.currentCatalog.setEstado(true);
                this.currentCatalog.setItemCatalogoList(itemsCatalogo);
                servCat.addCatalogo(currentCatalog);
                msjGrowl = "Catalogo agregado";
            } else if (modoAgregarOeditar == 2) {//Para editar
                System.out.println("INGRESA A editar");
                servCat.editCatalogo(currentCatalog);
                msjGrowl = "Catalogo editado";
            }

            //Actualizando la lista de catalogos
            this.listaCatalogos = servCat.getAllCatalogos();

            //Reiniciando los currents
            this.currentCatalog = new Catalogo();
            this.itemsCatalogo = new ArrayList<>();
            this.modoAgregarOeditar = 1;

            //Mostrando el msj
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operacion exitosa", msjGrowl));
        } catch (ValidacionException ex) {
            //Mostrando el msj
            List<String> validaciones = ex.getMensajes();

            for (String validacion : validaciones) {
                FacesContext.getCurrentInstance().addMessage(
                        "msgGrowl",
                        new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                ex.getMessage(),
                                validacion));
            }
        }

    }

    public void setEditIndexAndItem(ItemCatalogo item, int index) {
        currentEditItem = item;
        indexTablaItemsCatalogo = index;
    }

    public void agregarItem() {
        try {
            if (modoAgregarOeditar == 1) {
                validarAddItemCatalogo();
            } else if (modoAgregarOeditar == 2) {
                validarEditItemCatalogo();
            }

            this.currentAddItem.setEstado(true);
            this.currentAddItem.setMostrarEliminar(true);
            this.itemsCatalogo.add(currentAddItem);
            this.currentAddItem = new ItemCatalogo();

            PrimeFaces.current().executeScript("PF('dialogAddItemCatalogo').hide()");
        } catch (ValidacionException ex) {
            List<String> validaciones = ex.getMensajes();

            for (String validacion : validaciones) {
                FacesContext.getCurrentInstance().addMessage(
                        "msgGrowl",
                        new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                ex.getMessage(),
                                validacion));
            }
        }
    }

    public void editarItem() {
        if (indexTablaItemsCatalogo > -1) {
            itemsCatalogo.get(indexTablaItemsCatalogo).setCodigo(currentEditItem.getCodigo());
            itemsCatalogo.get(indexTablaItemsCatalogo).setDescripcion(currentEditItem.getDescripcion());
        }
    }

    public void eliminarItem(int index) {
        this.itemsCatalogo.remove(index);
    }

    public void catalogoSeleccionado() {
        modoAgregarOeditar = 2;
        itemsCatalogo = currentCatalog.getItemCatalogoList();
        tabActiva = 1;
    }

    public void limpiarModalAgregar() {
        currentAddItem = new ItemCatalogo();
        System.out.println("Se limpio el modal AGREGAR");
    }

    public void limpiarModalEditar() {
        currentEditItem = new ItemCatalogo();
        indexTablaItemsCatalogo = -1;
        System.out.println("Se limpio el modal EDITAR");
    }

    public void buscar() {
        Map params = new HashMap();
        params.put("codigo", this.campoBusqueda);
        listaCatalogos = servCat.findCatalogoByAnyField(params);
    }

    public void limpiarBusqueda() {
        modoAgregarOeditar = 1;
        currentCatalog = new Catalogo();
        itemsCatalogo = new ArrayList<>();
        campoBusqueda = "";
        listaCatalogos = servCat.getAllCatalogos();
    }

    public String estadoToString(boolean estado) {
        return estado ? "Activo" : "Inactivo";
    }

    public boolean renderColEstado() {
        return modoAgregarOeditar == 2;
    }

    public boolean renderSelectActivo() {
        return modoAgregarOeditar == 2;
    }

    public void cerrarModalAddItem() {
        this.currentAddItem = new ItemCatalogo();
        PrimeFaces.current().executeScript("PF('dialogAddItemCatalogo').hide()");
    }

    //========== METODOS DE PRUEBA ==================
    public void print() {
        System.out.println("IMPRIMIENDO DESDE CONTROLLER");
//        nb.print();

        Catalogo c = new Catalogo();
//        c.setId(0L);
        c.setCodigo("PR2");
        c.setNombre("Catalogo de prueba 2");

        List<ItemCatalogo> items = new ArrayList<>();
        ItemCatalogo a = new ItemCatalogo();
        a.setCodigo("IT1");
        a.setDescripcion("Item 1");
        a.setCatalogo(c);
        items.add(a);

        c.setItemCatalogoList(items);
        servCat.addCatalogo(c);

        //servCat.addCatalogo(c);
    }

    private void printCatalogo() {
        System.out.println("DATOS CATALOGO---------");
        //System.out.println("ID: "+this.currentCatalog.getId());
        System.out.println("CODIGO: " + this.currentCatalog.getCodigo());
        System.out.println("NOMBRE: " + this.currentCatalog.getNombre());
        System.out.println("ITEMS:");
        for (ItemCatalogo i : currentCatalog.getItemCatalogoList()) {
            //  System.out.println("id: "+i.getId());
            System.out.println("codigo: " + i.getCodigo());
            System.out.println("descripcion " + i.getDescripcion());
        }
    }
}
