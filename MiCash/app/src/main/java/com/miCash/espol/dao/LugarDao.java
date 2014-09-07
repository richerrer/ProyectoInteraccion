package com.miCash.espol.dao;

import com.miCash.espol.conection.DbConection;
import com.miCash.espol.pojos.Lugar;
import com.miCash.espol.pojos.Transaccion;
import com.miCash.espol.pojos.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Usuario on 07/09/2014.
 */
public class LugarDao {
    private static LugarDao instance = null;
    private Connection conection = DbConection.getInstance().getConection();

    private LugarDao(){}

    public static LugarDao getInstance(){
        if(instance==null)
            instance = new LugarDao();
        return instance;
    }

    public boolean addLugar(Lugar lugar){
        String query ="INSERT INTO \"lugar\"(nombre, categoria, latitud, longitud, idusuario) VALUES (?,?,?,?,?,?)";

        int filasAfectadas =0;
        try{
            PreparedStatement statement = conection.prepareStatement(query);
            statement.setString(1, lugar.getNombre());
            statement.setString(2, lugar.getCategoria());
            statement.setLong(3,lugar.getLatitud());
            statement.setLong(4,lugar.getLongitud());
            statement.setLong(5,lugar.getIdusuario());
            filasAfectadas = statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        if(filasAfectadas!=0)
            return true;
        return false;

}

    public Connection getConection(){
        return conection;
    }

}
