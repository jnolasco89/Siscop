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
    public Persona actualizarPersona(Persona c) {
       return personaDao.edit(c);
    }

    @Override
    public List<Persona> buscarPersonas(Map params) throws Exception {
        return personaDao.buscarPersonas(params);
    }

}
