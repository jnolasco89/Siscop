/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.serviciosvista;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.org.siscop.caritas.ejb.ServiciosCuentaLocal;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.CuentaPK;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Leonardo Martinez
 */
public class PestaniaCatalogoMttoCuentasContables {

    private ServiciosCuentaLocal servCuenta;
    private Proyecto proyectoActual;
    private TreeNode arbolCuentasTreeNode;
    private TreeNode nodoActual;
    private Cuenta arbolCuentasEntidad;
    private int modoCatalogo;//1 para agregar, 2 para editar
    private List<Cuenta> ctasAeliminar;

    public PestaniaCatalogoMttoCuentasContables() {
    }

    public PestaniaCatalogoMttoCuentasContables(ServiciosCuentaLocal servCuenta) {
        this.servCuenta = servCuenta;
        this.modoCatalogo = 1;
        this.ctasAeliminar = new ArrayList<>();
    }

    //================= GETTER Y SETTER ========================
    public Proyecto getProyectoActual() {
        return proyectoActual;
    }

    public void setProyectoActual(Proyecto proyectoActual) {
        this.proyectoActual = proyectoActual;
    }

    public TreeNode getArbolCuentasTreeNode() {
        return arbolCuentasTreeNode;
    }

    public void setArbolCuentasTreeNode(TreeNode arbolCuentasTreeNode) {
        this.arbolCuentasTreeNode = arbolCuentasTreeNode;
    }

    public TreeNode getNodoActual() {
        return nodoActual;
    }

    public void setNodoActual(TreeNode nodoActual) {
        this.nodoActual = nodoActual;
    }

    public Cuenta getArbolCuentasEntidad() {
        return arbolCuentasEntidad;
    }

    public void setArbolCuentasEntidad(Cuenta arbolCuentasEntidad) {
        this.arbolCuentasEntidad = arbolCuentasEntidad;
    }

    //================ METODOS DE SERVICIO ======================
    public void resetearPestaniacatalogo() {
        proyectoActual = null;
        arbolCuentasTreeNode = null;
        nodoActual = null;
        arbolCuentasEntidad = null;
        this.modoCatalogo = 1;
        this.ctasAeliminar = new ArrayList<>();
    }

    public void buscarCatalogoDeCuentas(Map filtros) {
        arbolCuentasEntidad = servCuenta.buscarCuentasNodos(filtros);

        arbolCuentasTreeNode = new DefaultTreeNode("Raiz", null);
        armarArbolDeCuentas(arbolCuentasEntidad, arbolCuentasTreeNode, true);
    }

    public void subirYprocesarArchivo(InputStream inputStream) {
        arbolCuentasEntidad = servCuenta.leerArchivorToCuenta(inputStream);

        arbolCuentasTreeNode = new DefaultTreeNode("Raiz", null);
        armarArbolDeCuentas(arbolCuentasEntidad, arbolCuentasTreeNode, true);
    }

    public void armarArbolDeCuentas(Cuenta ctaPadre, TreeNode nodoPadre, boolean expandirNodo) {
        for (Cuenta ctaHijo : ctaPadre.getCuentaList()) {
            TreeNode nodoHijo = new DefaultTreeNode(ctaHijo, nodoPadre);

//            if (ctaHijo.getCuentaPadre() == null ? true : ctaHijo.getCuentaPadre().getCuentaPK().getCodigo() == null) {
//                nodoHijo.setExpanded(true);
//            }
            if (ctaHijo.getCuentaList() != null ? ctaHijo.getCuentaList().size() > 0 : false) {
                nodoHijo.setExpanded(true);
                armarArbolDeCuentas(ctaHijo, nodoHijo, expandirNodo);
            }
        }
    }

    public void guardarCatalogo() {
        servCuenta.registraCatalogoDeCuentas(arbolCuentasEntidad.getCuentaList(), proyectoActual);
    }

    public void agregarCuentas(List<Cuenta> cuentas) {

        //Si los arbolos no estan inicializados se procede a 
        //incializarlos con cero nodos
        if (arbolCuentasEntidad == null) {
            arbolCuentasEntidad = new Cuenta();
            arbolCuentasEntidad.setCuentaList(new ArrayList<>());
            arbolCuentasEntidad.setCuentaPK(new CuentaPK());
            arbolCuentasEntidad.setCuentaPadre(null);
            arbolCuentasEntidad.setNombre("Raiz");
        } else if (arbolCuentasEntidad.getCuentaList() == null) {
            arbolCuentasEntidad.setCuentaList(new ArrayList<>());
        }

        if (arbolCuentasTreeNode == null) {
            arbolCuentasTreeNode = new DefaultTreeNode("Raiz", null);
        }

        //Se utilza una cuenta del arreglo para obtener el path
        Cuenta c = cuentas.get(0);
        if (c.getCuentaPadre() == null) {
            c.setCuentaPadre(arbolCuentasEntidad);
        } else if (c.getCuentaPadre().getCuentaPK() != null ? c.getCuentaPadre().getCuentaPK().getCodigo() == null : true) {
            c.setCuentaPadre(arbolCuentasEntidad);
        }

        //Se obtiene el path de la cuenta a la que se le 
        //agregaran subcuentas
        Map paths = getPathCuenta(c);
        List<Integer> indices = (List<Integer>) paths.get("indice");

        Cuenta ctaActual = arbolCuentasEntidad;
        TreeNode nodoBuscado = arbolCuentasTreeNode;
        for (Integer indice : indices) {
            ctaActual = ctaActual.getCuentaList().get(indice);
            nodoBuscado = nodoBuscado.getChildren().get(indice);
        }

        for (Cuenta cuenta : cuentas) {
            cuenta.setCuentaPadre(ctaActual);
            cuenta.setCuentaList(new ArrayList<>());
            ctaActual.getCuentaList().add(cuenta);

            TreeNode nuevoNodo = new DefaultTreeNode(cuenta, nodoBuscado);
            nodoBuscado.getChildren().add(nuevoNodo);
        }

        if (nodoBuscado.getChildCount() > 0) {
            nodoBuscado.setExpanded(true);
        }
    }

    public void editarCuenta(Cuenta c) {
        Map path = this.getPathCuenta(c);
        List<Integer> indices=(List<Integer>) path.get("indice");
        
        Cuenta cuentaBuscada=arbolCuentasEntidad;
        TreeNode nodoBuscado=arbolCuentasTreeNode;
        
        for(int i=0;i<indices.size()-1;i++){ 
            int indiceBusqueda=indices.get(i);
            cuentaBuscada=cuentaBuscada.getCuentaList().get(indiceBusqueda);
            nodoBuscado=nodoBuscado.getChildren().get(indices.get(indiceBusqueda));
        }
        
        cuentaBuscada.getCuentaList().set(indices.get(indices.size()-1), c);
//        TreeNode nodoEditado=new DefaultTreeNode(c, nodoBuscado);
//        nodoBuscado.getChildren().set(indices.get(indices.size()-1), nodoEditado);
    }

    public void eliminarCuenta() {
        Cuenta c = (Cuenta) nodoActual.getData();
        Map paths = getPathCuenta(c);
        List<Integer> indices = (List<Integer>) paths.get("indice");

        //Procede a elminar del arbol
        Cuenta ctaActual = arbolCuentasEntidad;
        TreeNode nodoBuscado = arbolCuentasTreeNode;
        for (int i = 0; i < indices.size() - 1; i++) {
            ctaActual = ctaActual.getCuentaList().get(indices.get(i));
            nodoBuscado = nodoBuscado.getChildren().get(indices.get(i));
        }

        int indiceAelminar = indices.get(indices.size() - 1);
        ctaActual.getCuentaList().remove(indiceAelminar);
        nodoBuscado.getChildren().remove(indiceAelminar);
    }

    public Map getPathCuenta(Cuenta ctaObjetivo) {
        Map paths = new HashMap();
        List<String> pathCodigos = new ArrayList<>();
        List<Integer> pathPosiciones = new ArrayList<>();

        Cuenta ctaPadre = ctaObjetivo.getCuentaPadre();
        Cuenta ctaHija = ctaObjetivo;
        boolean seguir = true;

        //Se procede a buscar la cuenta hija dentro de la
        //cuenta padre para obtener el indice en el que esta
        //se encuentra
        while (seguir) {
//            if(ctaPadre.getCuentaList()==null){
//                ctaPadre.setCuentaList(new ArrayList<>());
//            }

            for (int i = 0; i < ctaPadre.getCuentaList().size(); i++) {
                if (ctaPadre.getCuentaList().get(i).getCuentaPK().getCodigo().equals(ctaHija.getCuentaPK().getCodigo())) {
                    pathPosiciones.add(i);
                    pathCodigos.add(ctaHija.getCuentaPK().getCodigo());
                    break;
                }
            }

            ctaHija = ctaPadre;
            ctaPadre = ctaHija.getCuentaPadre();

            if (ctaHija == null) {
                seguir = false;
            } else {
                seguir = ctaHija.getCuentaPK() == null ? false : ctaHija.getCuentaPK().getCodigo() != null;
            }
        }

        int indice = pathCodigos.size() - 1;
        List<String> pathCodigoOrdenado = new ArrayList<>();
        List<Integer> pathPosicionesOrdenado = new ArrayList<>();
        while (indice >= 0) {
            pathCodigoOrdenado.add(pathCodigos.get(indice));
            pathPosicionesOrdenado.add(pathPosiciones.get(indice));
            indice--;
        }

        paths.put("indice", pathPosicionesOrdenado);
        paths.put("codigo", pathCodigoOrdenado);

        return paths;
    }

}
