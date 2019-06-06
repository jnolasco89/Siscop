/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener {

    @PrePersist
    public void setNuevo(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if (audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }

        audit.setFechaCrea(new Date());
        audit.setUserCrea(LoggedUser.get());
    }

    @PreUpdate
    public void setActualizar(Auditable auditable) {
        Audit audit = auditable.getAudit();

        audit.setFechaMod(new Date());
        audit.setUserMod(LoggedUser.get());
    }
}
