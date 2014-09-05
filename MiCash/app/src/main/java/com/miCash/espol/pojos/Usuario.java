package com.miCash.espol.pojos;

import com.miCash.espol.conection.DbConection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by usuario on 19/08/2014.
 */
public class Usuario {
    private long idusuario;
    private String username;
    private String password;
    private String nombre;
    private String apellido;

    public Usuario(String username,String Password){
        this.username = username;
        this.password = password;
    }

    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }

    public long getIdUsusario(){
        return this.idusuario;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
