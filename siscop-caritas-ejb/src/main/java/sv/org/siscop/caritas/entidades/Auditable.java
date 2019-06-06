/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

 public interface Auditable {

        Audit getAudit();

        void setAudit(Audit audit);
}