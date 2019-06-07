/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.exceptions;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.ApplicationException;

/**
 *
 * @author Nolasco
 */
@ApplicationException(rollback = true)
public class ValidacionException extends Exception {

    private List<String> mensajes = new ArrayList();

    public ValidacionException() {
    }

    public ValidacionException(String message) {
        super(message);
    }

    public ValidacionException(Throwable cause) {
        super(cause);
    }

    public ValidacionException(String message, List msg) {
        super(message);
        this.mensajes = msg;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

}
