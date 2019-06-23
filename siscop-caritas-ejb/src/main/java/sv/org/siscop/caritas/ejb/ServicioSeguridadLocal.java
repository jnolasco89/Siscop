/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Menu;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioSeguridadLocal {

    public void nuevaMenu(Menu c);

    public Menu actualizarMenu(Menu c);

    public List<Menu> buscarMenus(Map params) throws Exception;

}
