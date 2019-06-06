/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Audit implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechacrea")
    private Date fechaCrea;
    @Column(name = "usercrea")
    private String userCrea;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechamod")
    private Date fechaMod;
    @Column(name = "usermod")
    private String userMod;

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public String getUserCrea() {
        return userCrea;
    }

    public void setUserCrea(String userCrea) {
        this.userCrea = userCrea;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

}
