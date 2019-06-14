/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Catalogo;
import sv.org.siscop.caritas.entidades.ItemCatalogo;

/**
 *
 * @author Nolasco
 */
@Local
public interface ServiciosCatalogoLocal {

    public void addCatalogo(Catalogo c);

    public void editCatalogo(Catalogo c);

    public List<Catalogo> buscarCatalogoPorCualquierCampo(Map campos);

    public List<Catalogo> getAllCatalogos();

    public Catalogo findCatalogoById(Integer id);

    public ItemCatalogo findItemCatalogoById(Integer id);
}
