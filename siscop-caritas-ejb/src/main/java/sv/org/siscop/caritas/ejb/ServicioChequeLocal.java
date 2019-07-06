/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Cheque;
import sv.org.siscop.caritas.entidades.Detallecheque;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioChequeLocal {

    public void nuevoCheque(Cheque c);

    public void actualizarCheque(Cheque c);

    public List<Cheque> buscarCheques(Map params) throws Exception;

    public void nuevoDetalleCheque(Detallecheque dc);

    public void actualizarDetalleCheque(Detallecheque dc);
}
