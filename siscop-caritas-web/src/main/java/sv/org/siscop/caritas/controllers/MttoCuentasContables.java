/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.org.siscop.caritas.ejb.ServicioProyectoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCatalogoLocal;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.ItemCatalogo;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Leonardo Martinez
 */
@Named(value = "mttoCuentasContables")
@ViewScoped
public class MttoCuentasContables implements Serializable {

    @EJB
    private ServicioProyectoLocal servProyecto;
    @EJB
    private ServiciosCatalogoLocal servCatalogo;
    @EJB
    private ServiciosCuentaLocal servCuenta;

    //Variables para la ui
    private int tabActiva;
    private List<Proyecto> proyectos;
    private List<ItemCatalogo> estadosProyecto;
    private String msjPestaniaCatalogo;
    private Map msjs;
    private TreeNode nodoActual;
    //Modelo para la vista
    private Proyecto proyectoActual;
    private Proyecto proyectoCatalogo;
    private List<Cuenta> catalogoDeCuentas;
    private TreeNode arbolCuentas;
    private Cuenta cuentaActual;

    /**
     * Creates a new instance of MttoCuentasContables
     */
    public MttoCuentasContables() {
    }

    @PostConstruct
    public void init() {
        tabActiva = 0;
        msjs = new HashMap();
        msjs.put("vacio", "El proyecto no posee catalogo de cuentas");
        msjs.put("seleccionar", "Seleccione un proyecto para cargar el catalogo de cuentas");

        msjPestaniaCatalogo = msjs.get("seleccionar").toString();
        proyectos = servProyecto.getAllProyectos();
        proyectoActual = new Proyecto();
        proyectoActual.setEstado(new ItemCatalogo(63));
        proyectoCatalogo = new Proyecto();
        estadosProyecto = servCatalogo.findCatalogoById(20).getItemCatalogoList();
        catalogoDeCuentas = null;
        arbolCuentas = null;
        cuentaActual = new Cuenta();
    }

    // ================== GETTER Y SETTER ==================
    public int getTabActiva() {
        return tabActiva;
    }

    public void setTabActiva(int tabActiva) {
        this.tabActiva = tabActiva;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public List<ItemCatalogo> getEstadosProyecto() {
        return estadosProyecto;
    }

    public void setEstadosProyecto(List<ItemCatalogo> estadosProyecto) {
        this.estadosProyecto = estadosProyecto;
    }

    public Proyecto getProyectoCatalogo() {
        return proyectoCatalogo;
    }

    public void setProyectoCatalogo(Proyecto proyectoCatalogo) {
        this.proyectoCatalogo = proyectoCatalogo;
    }

    public List<Cuenta> getCatalogoDeCuentas() {
        return catalogoDeCuentas;
    }

    public void setCatalogoDeCuentas(List<Cuenta> catalogoDeCuentas) {
        this.catalogoDeCuentas = catalogoDeCuentas;
    }

    public String getMsjPestaniaCatalogo() {
        return msjPestaniaCatalogo;
    }

    public void setMsjPestaniaCatalogo(String msjPestaniaCatalogo) {
        this.msjPestaniaCatalogo = msjPestaniaCatalogo;
    }

    public TreeNode getArbolCuentas() {
        return arbolCuentas;
    }

    public void setArbolCuentas(TreeNode arbolCuentas) {
        this.arbolCuentas = arbolCuentas;
    }

    public TreeNode getNodoActual() {
        return nodoActual;
    }

    public void setNodoActual(TreeNode nodoActual) {
        this.nodoActual = nodoActual;
    }

    public Cuenta getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    // =================== METODOS =========================
    // ----- Pestaña busqueda --
    public void buscarProyecto() {
        Map filtros = new HashMap();
        filtros.put("codigo", null);
        filtros.put("nombre", null);
        filtros.put("nombreCorto", null);
        filtros.put("fechaIni", null);
        filtros.put("fechaFin", null);
        filtros.put("estado", null);

        if (proyectoActual.getCodigo() != null) {
            if (proyectoActual.getCodigo().length() > 0) {
                filtros.replace("codigo", proyectoActual.getCodigo());
            }
        }

        if (proyectoActual.getNombre() != null) {
            if (proyectoActual.getNombre().length() > 0) {
                filtros.replace("nombre", proyectoActual.getNombre());
            }
        }

        if (proyectoActual.getNombreCorto() != null) {
            if (proyectoActual.getNombreCorto().length() > 0) {
                filtros.replace("nombreCorto", proyectoActual.getNombreCorto());
            }
        }

        if (proyectoActual.getFechaini() != null) {
            filtros.replace("fechaIni", proyectoActual.getFechaini());
        }

        if (proyectoActual.getFechafin() != null) {
            filtros.replace("fechaFin", proyectoActual.getFechafin());
        }

        if (proyectoActual.getEstado() != null) {
            if (proyectoActual.getEstado().getId() > 0) {
                filtros.replace("estado", proyectoActual.getEstado());
            }
        }

        proyectos = servProyecto.buscarProyetosCriterial(filtros);
    }

    public void limpiarBusquedaProyecto() {
        proyectoActual = new Proyecto();
        proyectoActual.setEstado(new ItemCatalogo(63));
        proyectoCatalogo = new Proyecto();

        proyectos = servProyecto.getAllProyectos();
    }

    public void cargarCatalogoDeCuentasProyecto() {
        Map filtros = new HashMap();
        filtros.put("cuentasPrincipales", true);
        filtros.put("proyecto", proyectoCatalogo);
        catalogoDeCuentas = servCuenta.buscarCuentas(filtros);

        arbolCuentas = new DefaultTreeNode("Raiz", null);
        recorrerCuentas(catalogoDeCuentas, arbolCuentas);
        tabActiva = 1;
    }

    // ----- Pestaña catalogo -----------
    public void accionBtnGuardar() {
        System.out.println("Btn guardar action");
    }

    public void limpiarFormCuenta() {
        System.out.println("Limpiar from cuenta");
    }

    public void cargarCuentaSeleccionada() {
        System.out.println("Cargar cuenta");
    }

    public void subirYprocesarArchivo(FileUploadEvent event) {
        try {
            catalogoDeCuentas = servCuenta.leerArchivo(event.getFile().getInputstream());
            arbolCuentas = new DefaultTreeNode("Raiz", null);
            recorrerCuentas(catalogoDeCuentas, arbolCuentas);
            
        } catch (IOException ex) {
            Logger.getLogger(MttoCuentasContables.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ----- Render para componentes -----
    public boolean verArbolCuentas() {
        if (catalogoDeCuentas == null) {
            return false;
        } else if (catalogoDeCuentas.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean verMsjNoHayCatalogo() {
        if (catalogoDeCuentas == null) {
            msjPestaniaCatalogo = msjs.get("seleccionar").toString();
            return true;
        } else if (catalogoDeCuentas.isEmpty()) {
            msjPestaniaCatalogo = msjs.get("vacio").toString();
            return true;
        }
        return false;
    }

    // ---- Metodos utilitarios -------
    public void recorrerCuentas(List<Cuenta> cuentas, TreeNode padre) {
        cuentas.forEach((cuenta) -> {
            TreeNode nodo = new DefaultTreeNode(cuenta, padre);
            if (cuenta.getIdctapadre() == null) {
                nodo.setExpanded(true);
            }
            if (cuenta.getCuentaList().size() > 0) {
                //Ordenando las subcuentas por codigo
                for (int i = 0; i < cuenta.getCuentaList().size() - 1; i++) {

                    for (int j = 0; j < cuenta.getCuentaList().size() - 1; j++) {
                        int codigoPos1 = Integer.parseInt(cuenta.getCuentaList().get(j).getCodigo());
                        int codigoPos2 = Integer.parseInt(cuenta.getCuentaList().get(j + 1).getCodigo());
                        if (codigoPos1 > codigoPos2) {

                            Cuenta tmp = cuenta.getCuentaList().get(j + 1);

                            cuenta.getCuentaList().set(j + 1, cuenta.getCuentaList().get(j));

                            cuenta.getCuentaList().set(j, tmp);
                        }
                    }
                }

                recorrerCuentas(cuenta.getCuentaList(), nodo);
            }
        });
    }
}
