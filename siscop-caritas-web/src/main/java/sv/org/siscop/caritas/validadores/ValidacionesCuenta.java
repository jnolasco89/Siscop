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

/**
 *
 * @author Nolasco
 */
public class ValidacionesCuenta {

    public ValidacionesCuenta() {
    }
//^[0-9]+    ^(?!\s*$).+

    public void validarCodigoCuenta(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        String codigo = value.toString();

        if (codigo.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Codigo requerido")
            );
        }

        Pattern patron = Pattern.compile("^[0-9]+");
        Matcher encaja = patron.matcher(codigo);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Formato de codigo invalido")
            );
        }
    }

    public void validarNombreCuenta(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
        String codigo = value.toString();

        if (codigo.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Nombre requerido")
            );
        }

        Pattern patron = Pattern.compile("^(?!\\s*$).+");
        Matcher encaja = patron.matcher(codigo);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            "Formato de codigo invalido")
            );
        }

    }

}
