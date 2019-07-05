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
import sv.org.siscop.caritas.dao.ItemRequisicionFacade;
import sv.org.siscop.caritas.dao.RequisicionFacade;
import sv.org.siscop.caritas.entidades.ItemRequisicion;
import sv.org.siscop.caritas.entidades.Requisicion;

/**
 *
 * @author Henry
 */
@Stateless
public class ServicioRequisicion implements ServicioRequisicionLocal {

    @EJB
    RequisicionFacade requisicionDao;
    @EJB
    ItemRequisicionFacade itemReqDao;

    @Override
    public void nuevaRequisicion(Requisicion r) {
        requisicionDao.create(r);
    }

    @Override
    public void actualizarRequisicion(Requisicion r) {
        requisicionDao.edit(r);
    }

    @Override
    public List<Requisicion> buscarRequisiciones(Map params) throws Exception {
        return requisicionDao.buscarRequisiciones(params);
    }

    @Override
    public void nuevoItemRequisicion(ItemRequisicion i) {
        itemReqDao.create(i);
    }

    @Override
    public void actualizarItemRequisicion(ItemRequisicion i) {
        itemReqDao.edit(i);
    }
}
