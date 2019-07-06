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
import sv.org.siscop.caritas.entidades.CuentaPK;
import sv.org.siscop.caritas.entidades.Proyecto;

/**
 *
 * @author Nolasco
 */
@Stateless
public class ServiciosCuenta implements ServiciosCuentaLocal {

    @EJB
    private CuentaFacade cuentaDao;

    @Override
    public void registraCatalogoDeCuentas(List<Cuenta> cuentas, Proyecto proyecto) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCuentaPadre()!=null?cuenta.getCuentaPadre().getCuentaPK().getCodigo()==null:false) {
                cuenta.setCuentaPadre(null);
            }
            cuenta.getCuentaPK().setIdproyecto(proyecto.getId());

            if (cuenta.getCuentaList() != null ? cuenta.getCuentaList().size() > 0 : false) {
                asignarProyecto(cuenta.getCuentaList(), proyecto);
            }

            
            cuentaDao.create(cuenta);
        }
    }

    private void asignarProyecto(List<Cuenta> cuentas, Proyecto p) {
        for (Cuenta cuenta : cuentas) {
            cuenta.getCuentaPK().setIdproyecto(p.getId());
            if (cuenta.getCuentaList() != null ? cuenta.getCuentaList().size() > 0 : false) {
                asignarProyecto(cuenta.getCuentaList(), p);
            }
        }
    }
//    @Override
//    public void agregarCuenta(Cuenta c) throws Exception {
//        Cuenta ctaEnBd = cuentaDao.find(c.getCodigo());
//        if (ctaEnBd == null) {
//            cuentaDao.create(c);
//        } else {
//            throw new Exception("El codigo de cuenta ingresado ya se encuentra registrado");
//        }
//    }
//    @Override
//    public void editarCuenta(String codigoOriginalCta, Cuenta c) throws Exception {
//        Cuenta ctaEnBd = cuentaDao.find(c.getCodigo());
//        if (ctaEnBd.getCodigo().equals(codigoOriginalCta)) {
//            cuentaDao.edit(c);
//        } else {
//            throw new Exception("El codigo de cuenta ingresado ya se encuentra registrado");
//        }
//    }

    @Override
    public List<Cuenta> buscarCuentas(Map filtro) {
        return cuentaDao.buscarCuentas(filtro);
    }

//    @Override
//    public List<Cuenta> paginacion(int inicio, int tamanio, Map<String, Object> filtros) {
//        List<Cuenta> cuentas = cuentaDao.paginacion(inicio, tamanio, filtros);
//        return cuentas;
//    }
//
//    @Override
//    public int contarTodo() {
//        return cuentaDao.count();
//    }
//
//    @Override
//    public Cuenta getCuenta(Long id) {
//        return cuentaDao.find(id);
//    }
//
//    @Override
//    public List<Cuenta> getCuentasPadres() {
//        return cuentaDao.getCuentasPrincipales();
//    }
//
//    @Override
//    public List<Cuenta> getTodasLasCuentas() {
//        return cuentaDao.findAll();
//    }
    @Override
    public List<Cuenta> leerArchivo(InputStream archivo) {
        List<Cuenta> cuentas = new ArrayList<>();
        try {
            //FileInputStream fis=new FileInputStream(archivo);
            XSSFWorkbook libro = new XSSFWorkbook(archivo);

            XSSFSheet hoja = libro.getSheetAt(0);

            Iterator<Row> filasHoja = hoja.iterator();

            int indexFila = 0;
            while (filasHoja.hasNext()) {
                Row fila = filasHoja.next();

                //Para empezar a cargar las cuentas desde la fila
                //numero 2. La fila numero 1 contiene los encabezados
                //de la tabla de cuentas
                if (indexFila > 0) {
                    Cuenta cuenta = new Cuenta();

                    //Para leer solamente dos columnas
                    //de la fila, la columna 0 que es
                    //donde se coloca el nombre de la cuenta
                    //y la columna 1 que es donde se coloca
                    //el codigo de la cuenta
                    for (int i = 0; i <= 1; i++) {
                        Cell celda = fila.getCell(i);
                        celda.setCellType(CellType.STRING);

                        switch (i) {
                            case 0:
                                cuenta.setNombre(celda.getStringCellValue());
                                break;
                            case 1:
                                CuentaPK pk = new CuentaPK(celda.getStringCellValue(), 0);
                                cuenta.setCuentaPK(pk);
//                                cuenta.setCodigo(celda.getStringCellValue());
                                break;
                        }
                    }

//                    String codigoCtaPadre = getCodigoPadre(cuenta.getCodigo());
                    String codigoCtaPadre = getCodigoPadre(cuenta.getCuentaPK().getCodigo());
                    if (codigoCtaPadre == null) {
//                        cuenta.setIdctapadre(null);
                        cuenta.setCuentaPadre(null);
                    } else {
//                        Cuenta padre = new Cuenta();
//                        padre.setCodigo(codigoCtaPadre);
//                        cuenta.setIdctapadre(padre);
                        Cuenta padre = new Cuenta();
                        CuentaPK pk = new CuentaPK(codigoCtaPadre, 0);
                        padre.setCuentaPK(pk);
                        cuenta.setCuentaPadre(padre);
                    }
                    cuenta.setCuentaList(new ArrayList<>());
                    cuentas.add(cuenta);
                }

                indexFila++;
            }

            libro.close();

//            Cuenta ctaRaiz = new Cuenta();
//            ctaRaiz.setCodigo(null);
//            ctaRaiz.setCuentaList(new ArrayList<>());
//            anidarCuentas(ctaRaiz, cuentas);
            Cuenta ctaRaiz = new Cuenta();
            CuentaPK pk = new CuentaPK(null, 0);
            ctaRaiz.setCuentaPK(pk);
            ctaRaiz.setCuentaList(new ArrayList<>());
            anidarCuentas(ctaRaiz, cuentas);

            return ctaRaiz.getCuentaList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentas;
    }

    public void anidarCuentas(Cuenta ctaPadre, List<Cuenta> ctsAanidar) {
        for (int x = 0; x < ctsAanidar.size(); x++) {
            Cuenta cta = ctsAanidar.get(x);
            String codigoPadredCta = cta.getCuentaPadre() == null ? null : cta.getCuentaPadre().getCuentaPK().getCodigo();
            String codigoPadre = ctaPadre.getCuentaPK().getCodigo();

            if (codigoPadredCta == null ? codigoPadre == null : codigoPadredCta.equals(codigoPadre)) {
                ctsAanidar.remove(cta);
                cta.setCuentaPadre(ctaPadre);//--->
                ctaPadre.getCuentaList().add(cta);
                anidarCuentas(cta, ctsAanidar);
                x = -1;
            }

        }
//        for (int x = 0; x < ctsAanidar.size(); x++) {
//            Cuenta cta = ctsAanidar.get(x);
//            String codigoCta = cta.getIdctapadre() == null ? null : cta.getIdctapadre().getCodigo();
//            String codigoPadre = ctaPadre.getCodigo();
//
//            if (codigoCta == null ? codigoPadre == null : codigoCta.equals(codigoPadre)) {
//                ctsAanidar.remove(cta);
//                cta.setIdctapadre(ctaPadre);//--->
//                ctaPadre.getCuentaList().add(cta);
//                anidarCuentas(cta, ctsAanidar);
//                x = -1;
//            }
//
//        }

    }

    public String getCodigoPadre(String codigoHijo) {
        List<Integer> codificacionAP = new ArrayList<>();
        codificacionAP.add(1);//Grupo
        codificacionAP.add(1);//Subgrupo
        codificacionAP.add(1);//Rubro
        codificacionAP.add(1);//Cuenta
        codificacionAP.add(1);//Subcuenta

        List<Integer> codificacionGastos = new ArrayList<>();
        codificacionGastos.add(1);//Grupo
        codificacionGastos.add(1);//Subgrupo
        codificacionGastos.add(2);//Rubro
        codificacionGastos.add(2);//Cuenta
        codificacionGastos.add(2);//Subcuenta

        List<Integer> codificacion = codificacionAP;

        int rubro = Integer.parseInt(codigoHijo.substring(0, 1));
        if (rubro > 1 && rubro < 4) {
            codificacion = codificacionAP;
        } else if (rubro == 5) {
            codificacion = codificacionGastos;
        }

        //---------------
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
    }

//    @Override
//    public void procesarArchivo(InputStream archivo) {
//        try {
//            //FileInputStream fis=new FileInputStream(archivo);
//            XSSFWorkbook libro = new XSSFWorkbook(archivo);
//
//            //if(archivo.isFile() && archivo.exists()){
//            XSSFSheet hoja = libro.getSheetAt(0);
//
//            Iterator<Row> filasHoja = hoja.iterator();
//
//            List<Cuenta> cuentas = new ArrayList<>();
//
//            int indexFila = 0;
//            while (filasHoja.hasNext()) {
//                Row fila = filasHoja.next();
//
//                if (indexFila > 0) {
//                    Cuenta cuenta = new Cuenta();
//
//                    for (int i = 0; i <= 1; i++) {
//                        Cell celda = fila.getCell(i);
//                        celda.setCellType(CellType.STRING);
//
//                        switch (i) {
//                            case 0:
//                                cuenta.setNombre(celda.getStringCellValue());
//                                break;
//                            case 1:
//                                cuenta.setCodigo(celda.getStringCellValue());
//                                break;
//                        }
//                    }
//
//                    String codigoCtaPadre = obtenerCodigoPadre(cuenta.getCodigo());
//                    if (codigoCtaPadre == null) {
//                        cuenta.setIdctapadre(null);
//                    } else {
//                        cuenta.setIdctapadre(new Cuenta(Long.parseLong(codigoCtaPadre)));
//                    }
//                    cuentas.add(cuenta);
//
////                    Iterator<Cell> celdas = fila.cellIterator();
////
////                    Cuenta cuenta = new Cuenta();
////                    int indexCelda = 0;
////                    while (celdas.hasNext()) {
////                        Cell celda = celdas.next();
////                        celda.setCellType(CellType.STRING);
////
////                        switch (indexCelda) {
////                            case 0:
////                                cuenta.setNombre(celda.getStringCellValue());
////                                break;
////                            case 1:
////                                cuenta.setCodigo(celda.getStringCellValue());
////                                break;
////                        }
////
////                        indexCelda++;
////                    }
////
////                    String codigoCtaPadre = obtenerCodigoPadre(cuenta.getCodigo());
////                    if (codigoCtaPadre == null) {
////                        cuenta.setCodigoctapadre(null);
////                    } else {
////                        cuenta.setCodigoctapadre(new Cuenta(codigoCtaPadre));
////                    }
////                    cuenta.setEstado(true);
////                    cuentas.add(cuenta);
//                }
//
//                indexFila++;
//            }
//            //}
//
//            libro.close();
//
//            registrarCuentasDelArchivo(cuentas);
//            //fis.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public String obtenerCodigoPadre(String codigoHijo) {
//        List<Integer> codificacion = new ArrayList<>();
//        codificacion.add(1);//Grupo
//        codificacion.add(1);//Subgrupo
//        codificacion.add(1);//Rubro
//        codificacion.add(1);//Cuenta
//        codificacion.add(1);//Subcuenta
//
//        int longitudCodigo = codigoHijo.length();
//        int contadorDigitosCodigo = 0;
//        int indiceCodificacion = 0;
//
//        while (contadorDigitosCodigo < longitudCodigo) {
//            if (indiceCodificacion > codificacion.size() - 1) {
//                contadorDigitosCodigo += codificacion.get(codificacion.size() - 1);
//            } else {
//                contadorDigitosCodigo += codificacion.get(indiceCodificacion);
//            }
//            indiceCodificacion++;
//        }
//
//        //Substring indiceInicial: Comienza desde cero
//        //indice final: es el numero del indice - 1
//        //Ejemplo:
//        //"Chaitanya".substring(2,5) retorna "ait"
//        int indiceFinal = codigoHijo.length() - codificacion.get(indiceCodificacion - 1);
//
//        //No tiene padre
//        if (indiceFinal == 0) {
//            return null;
//        } else {
//            return codigoHijo.substring(0, indiceFinal);
//        }
//
//        /*
//        System.out.println("Indice Final: "+indiceFinal);
//        String codigoPadre=codigoHijo.substring(0, codigoHijo.length()- codificacion.get(indiceCodificacion-1));
//        
//        return codigoPadre;
//         */
//    }
//
//    private void registrarCuentasDelArchivo(List<Cuenta> cuentas) {
//        //Ordenando las cuentas por codigo
//        for (int i = 0; i < cuentas.size() - 1; i++) {
//
//            for (int j = 0; j < cuentas.size() - 1; j++) {
//                int codigoPos1 = Integer.parseInt(cuentas.get(j).getCodigo());
//                int codigoPos2 = Integer.parseInt(cuentas.get(j + 1).getCodigo());
//                if (codigoPos1 > codigoPos2) {
//
//                    Cuenta tmp = cuentas.get(j + 1);
//
//                    cuentas.set(j + 1, cuentas.get(j));
//
//                    cuentas.set(j, tmp);
//                }
//            }
//        }
//
//        //cuentaDao.InsersionPorLotes(cuentas);
////        cuentaDao.insertarDesdeArchivoPorNativeQuery(cuentas);
//        for (Cuenta c : cuentas) {
//            cuentaDao.create(c);
//        }
//    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
