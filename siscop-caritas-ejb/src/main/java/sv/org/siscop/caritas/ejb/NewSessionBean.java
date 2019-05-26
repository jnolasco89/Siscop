/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class NewSessionBean {

    public void businessMethod() {
    }

    public void print(){
        System.out.println("IMPRIMIENDO DESDE NEWSESSIONBEAN");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
