/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.MenuFacade;
import sv.org.siscop.caritas.entidades.Menu;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioSeguridad implements ServicioSeguridadLocal {

    @EJB
    MenuFacade menuDao;

    @Override
    public void nuevaMenu(Menu c) {
        menuDao.create(c);
    }

    @Override
    public Menu actualizarMenu(Menu c) {
        return menuDao.edit(c);
    }

    @Override
    public List<Menu> buscarMenus(Map params) throws Exception {
        return menuDao.buscarMenus(params);
    }

}
