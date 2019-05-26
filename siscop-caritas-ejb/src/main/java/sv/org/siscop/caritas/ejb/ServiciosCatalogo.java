/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.CatalogoFacade;
import sv.org.siscop.caritas.dao.ItemsCatalogoFacade;
import sv.org.siscop.caritas.entidades.Catalogo;
import sv.org.siscop.caritas.entidades.ItemCatalogo;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ServiciosCatalogo implements ServiciosCatalogoLocal {
    @EJB
    CatalogoFacade catalogoDao;
    @EJB
    ItemsCatalogoFacade itemsDao;
    
    @Override
    public void addCatalogo(Catalogo c) {
        catalogoDao.create(c);
    }

    @Override
    public void editCatalogo(Catalogo c) {
       catalogoDao.edit(c);
        for (ItemCatalogo i : c.getItemCatalogoList()) {
            i.setIdcatalogo(c);
            itemsDao.edit(i);
        }
    }
    
    @Override
    public List<Catalogo> findCatalogoByAnyField(String field) {
        List<Catalogo> c=catalogoDao.findCatalogoByAnyField(field);
        return c;
    }
    
    @Override
    public List<Catalogo> getAllCatalogos() {
        return catalogoDao.findAll();
    }

 

  
}
