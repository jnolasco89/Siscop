/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Persona;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioPersonaLocal {

    public void nuevaPersona(Persona c);

    public Persona actualizarPersona(Persona c);

    public List<Persona> buscarPersonas(Map params) throws Exception;

}
