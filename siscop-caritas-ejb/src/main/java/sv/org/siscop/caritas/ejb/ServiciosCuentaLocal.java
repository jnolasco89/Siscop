/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Cuenta;

/**
 *
 * @author Nolasco
 */
@Local
public interface ServiciosCuentaLocal {
    public List<Cuenta> paginacion(int inicio, int tamanio, Map<String, Object> filtros);
    int contarTodo();
    void procesarArchivo(InputStream archivo);
    Cuenta getCuenta(Long id);
    List<Cuenta> getCuentasPadres();
    List<Cuenta> getTodasLasCuentas();
}
