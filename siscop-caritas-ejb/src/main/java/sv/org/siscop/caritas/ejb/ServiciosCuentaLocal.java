/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Cuenta;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Nolasco
 */
@Local
public interface ServiciosCuentaLocal {
    public void registraCatalogoDeCuentas(List<Cuenta> cuentas, Proyecto proyecto);
    public void agregarCuenta(Cuenta c) throws Exception;
    public void editarCuenta(String codigoOriginalCta, Cuenta c) throws Exception;
    public List<Cuenta> paginacion(int inicio, int tamanio, Map<String, Object> filtros);
    int contarTodo();
    public List<Cuenta> leerArchivo(InputStream archivo);
    void procesarArchivo(InputStream archivo);
    Cuenta getCuenta(Long id);
    public List<Cuenta> buscarCuentas(Map filtro);
    List<Cuenta> getCuentasPadres();
    List<Cuenta> getTodasLasCuentas();
}
