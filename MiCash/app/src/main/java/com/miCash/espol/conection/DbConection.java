package com.miCash.espol.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by usuario on 19/08/2014.
 */
public class DbConection {
    private static DbConection instance = null;
    private static final String url="jdbc:postgresql://192.168.1.103:5432/MiCash";
    private static final String usuario="postgres";
    private static final String password="1234";
    private static Connection conection = null;

    private DbConection(){};

    public static DbConection getInstance(){
        if(instance==null)
            instance = new DbConection();
        return instance;
    }

    public Connection getConection(){
        if(conection==null)
            conection = conectar();
        return conection;
    }

    private Connection conectar(){
        Connection conect = null;
        try{
            Class.forName("org.postgresql.Driver");
            conect = DriverManager.getConnection(url, "postgres", "1234");
        } catch (ClassNotFoundException e) {}
          catch (SQLException e) {}
        return conect;
    }

}
