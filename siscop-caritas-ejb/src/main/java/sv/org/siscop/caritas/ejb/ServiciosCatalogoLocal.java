/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Catalogo;

/**
 *
 * @author Nolasco
 */
@Local
public interface ServiciosCatalogoLocal {
    public void addCatalogo(Catalogo c);
    public void editCatalogo(Catalogo c);
    public List<Catalogo> findCatalogoByAnyField(String field);
    public List<Catalogo> getAllCatalogos();
}
