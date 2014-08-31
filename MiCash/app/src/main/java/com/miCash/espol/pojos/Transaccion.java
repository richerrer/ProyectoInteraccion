package com.miCash.espol.pojos;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by usuario on 30/08/2014.
 */
public class Transaccion {

    private long idtransaccion ;
    private String descripcion;
    private String categoria;
    private BigDecimal valor;
    private boolean ingreso;
    private Calendar fecha;
    private long idusuario;

    public Transaccion(String descripcion, String categoria, BigDecimal valor, boolean ingreso, Calendar fecha, long idusuario) {
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.valor = valor;
        this.ingreso = ingreso;
        this.fecha = fecha;
        this.idusuario = idusuario;
    }

    public long getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(long idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }
}
