/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.PersonaFacade;
import sv.org.siscop.caritas.entidades.Persona;

/**
 *
 * @author Henry
 */
@Stateless
public class ServiciosPersona implements ServiciosPersonaLocal {

    @EJB
    PersonaFacade personaDao;

    @Override
    public void nuevaPersona(Persona c) {
        personaDao.create(c);
    }

    @Override
    public void actualizarPersona(Persona c) {
        personaDao.edit(c);
    }

}