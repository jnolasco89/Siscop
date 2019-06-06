/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Persona;

/**
 *
 * @author Henry
 */
@Local
public interface ServiciosPersonaLocal {

    public void nuevaPersona(Persona c);

    public void actualizarPersona(Persona c);

}
