/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Nolasco
 */
public class ValidacionesActividad {

    public ValidacionesActividad() {
    }

    public void validarNombre(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        String codigo = value.toString();

        if (codigo.length() == 0) {
            PrimeFaces.current().ajax().addCallbackParam("validacion", false);
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Campo requerido")
            );
        }

        Pattern patron = Pattern.compile("^(?!\\s*$).+");
        Matcher encaja = patron.matcher(codigo);

        if (!encaja.find()) {
            PrimeFaces.current().ajax().addCallbackParam("validacion", false);
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Formato de nombre invalido")
            );
        }

        PrimeFaces.current().ajax().addCallbackParam("validacion", true);
    }

    public void validarCantidadRecurso(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Campo requerido")
            );
        }

        String codigo = value.toString();

        if (codigo.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Campo requerido")
            );
        }

        Pattern patron = Pattern.compile("^[0-9]+");
        Matcher encaja = patron.matcher(codigo);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Solo se permiten datos numericos")
            );
        }
    }
    
     public void validarCostUnitario(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Campo requerido")
            );
        }

        String codigo = value.toString();

        if (codigo.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Campo requerido")
            );
        }

        Pattern patron = Pattern.compile("^[0-9]+");
        Matcher encaja = patron.matcher(codigo);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Solo se permiten datos numericos")
            );
        }
    }
}
