package com.miCash.espol.dao;

import com.miCash.espol.conection.DbConection;
import com.miCash.espol.pojos.Transaccion;
import com.miCash.espol.pojos.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by usuario on 30/08/2014.
 */
public class TransaccionDao {

    private static TransaccionDao instance = null;
    private Connection conection = DbConection.getInstance().getConection();

    private TransaccionDao(){}

    public static TransaccionDao getInstance(){
        if(instance==null)
            instance = new TransaccionDao();
        return instance;
    }

    public ArrayList<Transaccion> getTransactions(Usuario usuario){
        String query = "select * from \"transaccion\" where idusuario = '"+usuario.getIdUsusario()+"' and fecha = '2014/08/30'";
        ArrayList<Transaccion> transaccions = new ArrayList<Transaccion>();
        Transaccion transaccion;
        try{
            Statement statement = conection.createStatement();
            ResultSet result =  statement.executeQuery(query);
            while (result.next()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(result.getDate(6));
                transaccion = new Transaccion(result.getString(2),result.getString(3),result.getBigDecimal(4),result.getBoolean(5),calendar,result.getLong(7));
                transaccion.setIdtransaccion(result.getLong(1));
                transaccions.add(transaccion);
            }
            if(transaccions.isEmpty())
                return null;
            return  transaccions;
        }catch (Exception e){return null;}

    }



    public Connection getConection(){
        return conection;
    }
}
