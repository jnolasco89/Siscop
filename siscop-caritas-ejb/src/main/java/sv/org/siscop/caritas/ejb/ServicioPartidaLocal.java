/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import sv.org.siscop.caritas.entidades.Partida;
import sv.org.siscop.caritas.entidades.Detallepartida;

/**
 *
 * @author Henry
 */
@Local
public interface ServicioPartidaLocal {

    public void nuevoPartida(Partida c);

    public void actualizarPartida(Partida c);

    public List<Partida> buscarPartidas(Map params) throws Exception;

    public void nuevoDetallePartida(Detallepartida dc);

    public void actualizarDetallePartida(Detallepartida dc);
}
