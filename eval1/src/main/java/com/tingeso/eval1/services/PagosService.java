package com.tingeso.eval1.services;

import com.tingeso.eval1.entities.PagosEntity;
import com.tingeso.eval1.entities.ProveedorEntity;
import com.tingeso.eval1.entities.SubirDataEntity;
import com.tingeso.eval1.entities.SubirDetailsEntity;
import com.tingeso.eval1.repositories.PagosRepository;
import com.tingeso.eval1.repositories.SubirDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PagosService {
    @Autowired
    PagosRepository pagosRepository;

    @Autowired
    SubirDataRepository subirDataRepository;



    public ArrayList<PagosEntity> obtenerPagos(){
        return (ArrayList<PagosEntity>) pagosRepository.findAll();
    }

    public ArrayList<PagosEntity> obtenerByProveedor(String codigo){
        return (ArrayList<PagosEntity>) pagosRepository.findByCodigo(codigo);
    }

    public PagosEntity obtenerByQuincena(String quincena){
        return pagosRepository.findByQuincena(quincena);
    }

    public void guardarPago(PagosEntity pago){ pagosRepository.save(pago);}

    public void borrarTodos(){pagosRepository.deleteAll();}

    public void guardarPagoDB(ProveedorEntity proveedor, SubirDataEntity data, SubirDetailsEntity details){
        PagosEntity pago = new PagosEntity();
        pago.setQuincena(data.getQuincena());
        pago.setCodigo(data.getProveedor());
        pago.setNombre(proveedor.getNombre());
        pago.setKlsLeche(totalLeche(data.getProveedor(),data.getQuincena()));
        pago.setNroDiasLeche(diasEntrega(data.getProveedor(),data.getQuincena()));
        pago.setPromedioKlsLeche(promedioLeche(data.getProveedor(),data.getQuincena()));
        pago.setVariacionLeche(varLeche(data.getProveedor(), data.getQuincena(), pago.getKlsLeche()));
        pago.setGrasa(details.getGrasa());
        pago.setVariacionGrasa(varGrasa(data.getProveedor(), data.getQuincena(), pago.getGrasa()));
        pago.setSolidos(details.getSolido());
        pago.setVariacionSolidos(varSolidos(data.getProveedor(), data.getQuincena(), pago.getSolidos()));
        pago.setPagoXLeche(pagoXLeche(proveedor.getCategoria(), pago.getKlsLeche()));
        pago.setPagoXGrasa(pagoXGrasa(pago.getGrasa(), pago.getKlsLeche()));
        pago.setPagoXST(pagoXST(details.getSolido(), pago.getKlsLeche()));
        pago.setBoniFrecuencia(calculoBonificacion(data.getProveedor(), data.getQuincena()));
        pago.setDctoLeche(descuentoXLeche(pago.getVariacionLeche()));
        pago.setDctoGrasa(descuentoXGrasa(pago.getVariacionGrasa()));
        pago.setDctoST(descuentoXST(pago.getVariacionSolidos()));
        Integer pagoAcopio = pagoAcopioLeche(pago.getPagoXLeche(), pago.getPagoXGrasa(), pago.getPagoXST(), pago.getBoniFrecuencia());
        Integer pagoTotal = pagoAcopio
                - descuentos(pago.getDctoLeche(), pago.getDctoGrasa(), pago.getDctoST(), pagoAcopio);
        pago.setPagoTotal(Integer.toString(pagoTotal));
        pago.setRetencion(Integer.toString(retencion(pagoTotal, proveedor.getRetencion())));
        pago.setPagoFinal(pagoFinal(pago.getPagoTotal(), pago.getRetencion()));

        guardarPago(pago);
    }

    public String totalLeche(String proveedor, String quincena){
        ArrayList<SubirDataEntity> entregasLeche = subirDataRepository.findByProveedor(proveedor);
        Integer total = 0;
        for(SubirDataEntity i : entregasLeche) {
            if (i.getQuincena().equals(quincena)) {
                total = total + Integer.parseInt(i.getKls_leche());
            }
        }
            return Integer.toString(total);
    }

    public String diasEntrega(String proveedor, String quincena){
        ArrayList<SubirDataEntity> entregasLeche = subirDataRepository.findByProveedor(proveedor);
        Integer dias = 0;
        for(SubirDataEntity i : entregasLeche) {
            if (i.getQuincena().equals(quincena)) {
                dias = dias + 1;
            }
        }
        return Integer.toString(dias);
    }

    public String promedioLeche(String proveedor, String quincena){
        ArrayList<SubirDataEntity> entregasLeche = subirDataRepository.findByProveedor(proveedor);
        Integer promedio = 0;
        for(SubirDataEntity i : entregasLeche){
            if(i.getQuincena().equals(quincena)) {
                promedio = promedio + Integer.parseInt(i.getKls_leche());
            }
        }
        promedio = promedio / entregasLeche.size();
        return Integer.toString(promedio);
    }

    public String varLeche(String proveedor, String quincena, String klsLecheActual){

        ArrayList<PagosEntity> pagosLeche = pagosRepository.findByCodigo(proveedor);
        String quincenaPagoAnterior = quincenaAnterior(quincena);
        for(PagosEntity i: pagosLeche){
            if(i.getQuincena().equals(quincenaPagoAnterior)){
                return calcularVariacionNegativa(klsLecheActual, i.getKlsLeche());
            }
        }
        return "0";
    }

    public String varGrasa(String proveedor, String quincena, String grasaActual){

        ArrayList<PagosEntity> pagosLeche = pagosRepository.findByCodigo(proveedor);
        String quincenaPagoAnterior = quincenaAnterior(quincena);
        for(PagosEntity i: pagosLeche){
            if(i.getQuincena().equals(quincenaPagoAnterior)){
                return calcularVariacionNegativa(grasaActual, i.getGrasa());
            }
        }
        return "0";
    }

    public String varSolidos(String proveedor, String quincena, String solidoActual){

        ArrayList<PagosEntity> pagosLeche = pagosRepository.findByCodigo(proveedor);
        String quincenaPagoAnterior = quincenaAnterior(quincena);
        for(PagosEntity i: pagosLeche){
            if(i.getQuincena().equals(quincenaPagoAnterior)){
                return calcularVariacionNegativa(solidoActual, i.getSolidos());
            }
        }
        return "0";
    }

    public String pagoXLeche(String categoria, String kls_leche){
        Integer pago = 0;
        if(categoria.equals("A")){
            pago = Integer.parseInt(kls_leche) * 700;
        }

        if(categoria.equals("B")){
            pago = Integer.parseInt(kls_leche) * 550;
        }

        if(categoria.equals("C")){
            pago = Integer.parseInt(kls_leche) * 400;
        }

        if(categoria.equals("D")){
            pago = Integer.parseInt(kls_leche) * 250;
        }
        return Integer.toString(pago);
    }

    public String pagoXGrasa(String grasa, String kls_leche){
        Integer pago = 0;
        if(Integer.parseInt(grasa) <= 20){
            pago = Integer.parseInt(kls_leche) * 30;
        }

        if((Integer.parseInt(grasa) > 20) && (Integer.parseInt(grasa) <= 45)){
            pago = Integer.parseInt(kls_leche) * 80;
        }

        if(Integer.parseInt(grasa) > 45){
            pago = Integer.parseInt(kls_leche) * 120;
        }
        return Integer.toString(pago);
    }

    public String pagoXST(String st, String kls_leche){
        Integer pago = 0;
        if(Integer.parseInt(st) <= 7){
            pago = Integer.parseInt(kls_leche) * -130;
        }

        if((Integer.parseInt(st) > 7) && (Integer.parseInt(st) <= 18)){
            pago = Integer.parseInt(kls_leche) * -90;
        }

        if((Integer.parseInt(st) > 18) && (Integer.parseInt(st) <= 35)){
            pago = Integer.parseInt(kls_leche) * 95;
        }

        if(Integer.parseInt(st) > 35){
            pago = Integer.parseInt(kls_leche) * 150;
        }
        return Integer.toString(pago);
    }

    public String calculoBonificacion(String proveedor, String quincena){
        ArrayList<SubirDataEntity> entregasLeche = subirDataRepository.findByProveedor(proveedor);
        Integer dias = 0;
        boolean m = false;
        boolean t = false;
        for(SubirDataEntity i : entregasLeche) {
            if (i.getQuincena().equals(quincena)) {
                dias = dias + 1;
                if(i.getTurno().equals("M")){
                    m = true;
                }
                if(i.getTurno().equals("T")){
                    t = true;
                }
            }
        }
        return bonificacion(dias, m, t);
    }

    public String bonificacion(Integer dias, boolean m, boolean t){
        Integer porcentajeBon = 0;
        if(m && t && dias > 10){
            porcentajeBon = 20;
        }

        if(m && !t && dias > 10){
            porcentajeBon = 12;
        }

        if(!m && t && dias > 10){
            porcentajeBon = 8;
        }
        return Integer.toString(porcentajeBon);
    }

    public String descuentoXLeche(String variacion){
        Integer descuento = 0;
        Integer var = Integer.parseInt(variacion);
        if(var > 8 && var <= 25){
            descuento = 7;
        }
        if(var > 25 && var <= 45){
            descuento = 15;
        }
        if(var > 45){
            descuento = 30;
        }
        return Integer.toString(descuento);
    }

    public String descuentoXGrasa(String variacion){
        Integer descuento = 0;
        Integer var = Integer.parseInt(variacion);
        if(var > 15 && var <= 25){
            descuento = 12;
        }
        if(var > 25 && var <= 40){
            descuento = 20;
        }
        if(var > 40){
            descuento = 30;
        }
        return Integer.toString(descuento);
    }

    public String descuentoXST(String variacion){
        Integer descuento = 0;
        Integer var = Integer.parseInt(variacion);
        if(var > 6 && var <= 12){
            descuento = 18;
        }
        if(var > 12 && var <= 35){
            descuento = 27;
        }
        if(var > 35){
            descuento = 45;
        }
        return Integer.toString(descuento);
    }

    public Integer pagoAcopioLeche(String pagoLeche, String pagoGrasa, String pagoST, String boni){
        double pagoAcopio = Integer.parseInt(pagoLeche) +
                Double.parseDouble(pagoGrasa) +
                Double.parseDouble(pagoST) +
                (Double.parseDouble(pagoLeche) / 100) * Double.parseDouble(boni);
        return (int) pagoAcopio;
    }

    public Integer descuentos(String dscLeche, String dscGrasa, String dscST, Integer pagoAcopio){
        double descuentos = (Double.parseDouble(dscLeche) / 100) * pagoAcopio
                + (Double.parseDouble(dscGrasa) / 100) * pagoAcopio
                + (Double.parseDouble(dscST) / 100) * pagoAcopio;
        return (int) descuentos;
    }

    public Integer retencion(Integer pagoTotal, String afecto){
        double pago = (double) pagoTotal;
        double retencion = 0;
        if(afecto.equals("Si") && pagoTotal > 950000){
            retencion = 0.13 * pago;
        }
        return (int) retencion;
    }

    public String pagoFinal(String pagoTotal, String retencion){
        return Integer.toString(Integer.parseInt(pagoTotal) - Integer.parseInt(retencion));
    }

    public String quincenaAnterior(String quincena){
        if(!quincena.isEmpty()){
            String[] quincenaSplit = quincena.split("/");
            if(Integer.parseInt(quincenaSplit[2]) == 2){
                return quincenaSplit[0] + "/" +quincenaSplit[1] + "/" + "1";
            }
            else{
                if(Integer.parseInt(quincenaSplit[1]) == 1){
                    Integer anio = Integer.parseInt(quincenaSplit[0]);
                    anio = anio - 1;
                    return Integer.toString(anio) + "/" + "12" + "/" + "2";
                }
                else{
                    Integer mes = Integer.parseInt(quincenaSplit[1]);
                    mes = mes - 1;
                    return quincenaSplit[0] + "/" + Integer.toString(mes) + "/" + "2";
                }
            }
        }
        else{
            return null;
        }
    }

    public String calcularVariacionNegativa(String actual, String anterior){
        double valActual = Double.parseDouble(actual);
        double valAnterior = Double.parseDouble(anterior);
        double calc = ((valActual - valAnterior) / valAnterior) * 100;
        if(calc < 0){
            calc = calc * -1;
            return Integer.toString((int) calc);
        }
        else{
            return "0";
        }
    }

}
