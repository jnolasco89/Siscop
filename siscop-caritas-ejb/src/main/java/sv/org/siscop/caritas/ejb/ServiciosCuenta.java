/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.ejb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sv.org.siscop.caritas.dao.CuentaFacade;
import sv.org.siscop.caritas.entidades.Cuenta;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ServiciosCuenta implements ServiciosCuentaLocal {

    @EJB
    private CuentaFacade cuentaDao;

    @Override
    public List<Cuenta> paginacion(int inicio, int tamanio, Map<String, Object> filtros) {
        List<Cuenta> cuentas = cuentaDao.paginacion(inicio, tamanio, filtros);
        return cuentas;
    }

    @Override
    public int contarTodo() {
        return cuentaDao.count();
    }

    @Override
    public Cuenta getCuenta(Long id) {
        return cuentaDao.find(id);
    }

    @Override
    public List<Cuenta> getCuentasPadres() {
        return cuentaDao.getCuentasPrincipales();
    }

    @Override
    public void procesarArchivo(InputStream archivo) {
        try {

            //FileInputStream fis=new FileInputStream(archivo);
            XSSFWorkbook libro = new XSSFWorkbook(archivo);

            //if(archivo.isFile() && archivo.exists()){
            XSSFSheet hoja = libro.getSheetAt(0);

            Iterator<Row> filasHoja = hoja.iterator();

            List<Cuenta> cuentas = new ArrayList<>();

            int indexFila = 0;
            while (filasHoja.hasNext()) {
                Row fila = filasHoja.next();

                if (indexFila > 0) {
                    Iterator<Cell> celdas = fila.cellIterator();

                    Cuenta cuenta = new Cuenta();
                    int indexCelda = 0;
                    while (celdas.hasNext()) {
                        Cell celda = celdas.next();
                        celda.setCellType(CellType.STRING);

                        switch (indexCelda) {
                            case 0:
                                cuenta.setNombre(celda.getStringCellValue());
                                break;
                            case 1:
                                cuenta.setCodigo(celda.getStringCellValue());
                                break;
                        }

                        indexCelda++;
                    }

                    String codigoCtaPadre = obtenerCodigoPadre(cuenta.getCodigo());
                    if (codigoCtaPadre == null) {
                        cuenta.setCodigoctapadre(null);
                    } else {
                        cuenta.setCodigoctapadre(new Cuenta(codigoCtaPadre));
                    }
                    cuenta.setEstado(true);
                    cuentas.add(cuenta);
                }

                indexFila++;
            }
            //}

            libro.close();

            registrarCuentasDelArchivo(cuentas);
            //fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String obtenerCodigoPadre(String codigoHijo) {
        List<Integer> codificacion = new ArrayList<>();
        codificacion.add(1);//Grupo
        codificacion.add(1);//Subgrupo
        codificacion.add(2);//Rubro
        codificacion.add(2);//Cuenta
        codificacion.add(2);//Subcuenta

        int longitudCodigo = codigoHijo.length();
        int contadorDigitosCodigo = 0;
        int indiceCodificacion = 0;

        while (contadorDigitosCodigo < longitudCodigo) {
            if (indiceCodificacion > codificacion.size() - 1) {
                contadorDigitosCodigo += codificacion.get(codificacion.size() - 1);
            } else {
                contadorDigitosCodigo += codificacion.get(indiceCodificacion);
            }
            indiceCodificacion++;
        }

        //Substring indiceInicial: Comienza desde cero
        //indice final: es el numero del indice - 1
        //Ejemplo:
        //"Chaitanya".substring(2,5) retorna "ait"
        int indiceFinal = codigoHijo.length() - codificacion.get(indiceCodificacion - 1);

        //No tiene padre
        if (indiceFinal == 0) {
            return null;
        } else {
            return codigoHijo.substring(0, indiceFinal);
        }

        /*
        System.out.println("Indice Final: "+indiceFinal);
        String codigoPadre=codigoHijo.substring(0, codigoHijo.length()- codificacion.get(indiceCodificacion-1));
        
        return codigoPadre;
         */
    }

    private void registrarCuentasDelArchivo(List<Cuenta> cuentas) {
        //Ordenando las cuentas por codigo
        for (int i = 0; i < cuentas.size() - 1; i++) {

            for (int j = 0; j < cuentas.size() - 1; j++) {
                int codigoPos1 = Integer.parseInt(cuentas.get(j).getCodigo());
                int codigoPos2 = Integer.parseInt(cuentas.get(j + 1).getCodigo());
                if (codigoPos1 > codigoPos2) {

                    Cuenta tmp = cuentas.get(j + 1);

                    cuentas.set(j + 1, cuentas.get(j));

                    cuentas.set(j, tmp);
                }
            }
        }

//        cuentaDao.InsersionPorLotes(cuentas);
        int x = 0;
        for (Cuenta c : cuentas) {
            if (x < 2) {
                System.out.println("----------------------------------------------");
                System.out.println("Cuenta: " + c.getCodigo());
                System.out.println("Nombre: " + c.getNombre());
                if (c.getCodigoctapadre() == null) {
                    System.out.println("Padre: NULL");
                } else {
                    System.out.println(c.getCodigoctapadre().getCodigo());
                }

                cuentaDao.create(c);
            }
            x++;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}