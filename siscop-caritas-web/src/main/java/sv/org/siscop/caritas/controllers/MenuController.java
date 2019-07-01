package sv.org.siscop.caritas.controllers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import sv.org.siscop.caritas.ejb.ServicioSeguridadLocal;
import sv.org.siscop.caritas.entidades.Menu;

@Named
@SessionScoped
public class MenuController implements Serializable {

    @EJB
    private ServicioSeguridadLocal servSeguridad;
    private List<Menu> lista;
    private MenuModel model;

    @PostConstruct
    public void init() {
        this.listarMenus();
        model = new DefaultMenuModel();
        this.llenarMenu();
    }

    public void listarMenus() {
        try {
            Map params = new HashMap();
            params.put("tipo", "M");
            lista = servSeguridad.buscarMenus(params);
        } catch (Exception e) {
            //mensaje jsf
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public void llenarMenu() {

//        model = new DefaultMenuModel();
        DefaultMenuItem rootItem1 = new DefaultMenuItem();
        rootItem1.setValue("System Config");
        rootItem1.setIcon("ui-icon-gear");
        model.addElement(rootItem1);

        for (Menu m : lista) {
            if (!m.getActivo()) {
                continue;
            }
            DefaultSubMenu sm = new DefaultSubMenu();
            sm.setLabel(m.getNombre());
            sm.setExpanded(true);
            sm.setIcon(m.getIcono());
//            sm.getStyleClass("");

            for (Menu submenu : m.getListaSubmenus()) {
                DefaultMenuItem item = new DefaultMenuItem(submenu.getNombre());
                item.setOutcome(submenu.getUrl());
                item.setIcon(submenu.getIcono());
//                item.setValue("View User Activity");
//                item.setCommand("#{menuController.showUserActivity()}");
//                item.setAjax(false);
                sm.addElement(item);
            }

            model.addElement(sm);
        }

    }

//    public void cerrarSesion() {
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//    }
//
//    public String mostrarUsuarioLogeado() {
//        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
//        return us.getCoduser();
//    }
}
