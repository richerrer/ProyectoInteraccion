package com.miCash.espol.pojos;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Usuario on 07/09/2014.
 */
public class Lugar {
    private long idLugar ;
    private String nombre;
    private String categoria;
    private long latitud;
    private long longitud;
    private long idusuario;

    public Lugar(String nombre, String categoria, long latitud, long longitud, long idusuario) {
        this.idusuario = idusuario;
        this.longitud = longitud;
        this.categoria = categoria;
        this.nombre = nombre;
        this.idLugar = idLugar;
        this.latitud = latitud;
    }

    public long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(long idLugar) {
        this.idLugar = idLugar;
    }
}
