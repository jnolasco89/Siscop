/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.org.siscop.caritas.dao.DetalleChequeFacade;
import sv.org.siscop.caritas.dao.ChequeFacade;
import sv.org.siscop.caritas.entidades.Detallecheque;
import sv.org.siscop.caritas.entidades.Cheque;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioCheque implements ServicioChequeLocal {

    @EJB
    ChequeFacade chequeDao;
    @EJB
    DetalleChequeFacade detaChequeDao;

    @Override
    public void nuevoCheque(Cheque r) {
        chequeDao.create(r);
    }

    @Override
    public void actualizarCheque(Cheque r) {
        chequeDao.edit(r);
    }

    @Override
    public List<Cheque> buscarCheques(Map params) throws Exception {
        return chequeDao.buscarCheques(params);
    }

    @Override
    public void nuevoDetalleCheque(Detallecheque i) {
        detaChequeDao.create(i);
    }

    @Override
    public void actualizarDetalleCheque(Detallecheque i) {
        detaChequeDao.edit(i);
    }
}
