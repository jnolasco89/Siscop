/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.validadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Nolasco
 */
public class ValidacionesGenerales {

    public ValidacionesGenerales() {
    }

    public void validacionNoVacio(Object valor) {
        String msj = "Campo requerido";

        if (valor == null) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }

        String cadena = valor.toString();

        if (cadena.length() == 0) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }
    }

    public void validarCadena(String msj, Object valor) {
        this.validacionNoVacio(valor);

        String cadena = valor.toString();
        Pattern patron = Pattern.compile("^(?!\\s*$).+");
        Matcher encaja = patron.matcher(cadena);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }
    }

    public void validarNumeroEntero(String msj, Object valor) {
        this.validacionNoVacio(valor);

        try {
            Integer.parseInt(valor.toString());
        } catch (NumberFormatException ex) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }

        String cadena = valor.toString();
        Pattern patron = Pattern.compile("^[0-9]+");
        Matcher encaja = patron.matcher(cadena);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }
    }

    public void validarNumeroDecimal(String msj, Object valor) {
        this.validacionNoVacio(valor);

        String cadena = valor.toString();
        Pattern patron = Pattern.compile("^[0-9]+\\.?[0-9]*$");
        Matcher encaja = patron.matcher(cadena);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }
    }

    public void validarFecha(String msj, Object valor) {
        this.validacionNoVacio(valor);
        
        try {
            //EEE MMM dd HH:mm:ss zzz yyyy --> Fri Jun 14 00:00:00 CST 2019
            //MM-dd-yyyy hh:mm:ss aa --> 06-14-2019 12:00:00 AM
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",new Locale("us", "US"));
            sdf.parse(valor.toString());
        } catch (ParseException ex) {
            Logger.getLogger(ValidacionesGenerales.class.getName()).log(Level.SEVERE, null, ex);
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }
        
        /*
        String cadena = valor.toString();
        Pattern patron = Pattern.compile("^[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}$");
        Matcher encaja = patron.matcher(cadena);

        if (!encaja.find()) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Error",
                            msj)
            );
        }
        */

    }
}
