/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Nolasco
 */
public class ValidacionesActividad {

    private ValidacionesGenerales generales;

    public ValidacionesActividad() {
        generales = new ValidacionesGenerales();

    }

    public void validarProyecto(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        
        if (value == null) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Campo requerido")
            );
        }
        
        generales.validarCadena("Formato de nombre incorrecto", value);
        
//        Proyecto p = (Proyecto) value;
//        generales.validarCadena("Formato de nombre incorrecto", p.getNombreCorto());
        
    }

    public void validarNombre(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        generales.validarCadena("Formato de nombre incorrecto", value);
    }

    public void validarCantidadRecurso(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        generales.validarNumeroEntero("Formato numerico incorrecto", value);
    }

    public void validarCostUnitario(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        generales.validarNumeroDecimal("Formato numerico incorrecto", value);
    }

    public void validarFechaInicio(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        generales.validarFecha("Formato de fecha incorrecto", value);
    }

    public void validarFechaFin(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        generales.validarFecha("Formato de fecha incorrecto", value);
    }

}
