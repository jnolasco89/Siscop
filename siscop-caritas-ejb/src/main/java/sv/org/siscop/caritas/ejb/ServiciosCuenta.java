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
            if (cuenta.getCuentaPadre() != null ? cuenta.getCuentaPadre().getCuentaPK().getCodigo() == null : false) {
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
    
    @Override
    public List<Cuenta> buscarCuentasList(Map filtro) {
        return cuentaDao.buscarCuentas(filtro);
    }
    
    @Override
    public Cuenta buscarCuentasNodos(Map filtro) {
        List<Cuenta> cuentas = cuentaDao.buscarCuentas(filtro);
        Cuenta ctaRaiz = new Cuenta();
        ctaRaiz.setCuentaPK(new CuentaPK(null, 0));
        ctaRaiz.setCuentaList(new ArrayList<>());
        
        for (Cuenta cuenta : cuentas) {
            ctaRaiz.getCuentaList().add(cuenta);
        }
        
        return ctaRaiz;
    }
    
    @Override
    public Cuenta leerArchivorToCuenta(InputStream archivo) {
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
                                break;
                        }
                    }
                    
                    String codigoCtaPadre = getCodigoPadre(cuenta.getCuentaPK().getCodigo());
                    if (codigoCtaPadre == null) {
                        cuenta.setCuentaPadre(null);
                    } else {
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
            
            Cuenta ctaRaiz = new Cuenta();
            CuentaPK pk = new CuentaPK(null, 0);
            ctaRaiz.setCuentaPK(pk);
            ctaRaiz.setCuentaList(new ArrayList<>());
            anidarCuentas(ctaRaiz, cuentas);
            
            return ctaRaiz;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiciosCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @Override
    public List<Cuenta> leerArchivoToList(InputStream archivo){
        Cuenta c=this.leerArchivorToCuenta(archivo);
        return c.getCuentaList();
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
    
}
