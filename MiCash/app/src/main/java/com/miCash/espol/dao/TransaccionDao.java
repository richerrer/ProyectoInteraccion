package com.miCash.espol.dao;

import com.miCash.espol.conection.DbConection;
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

    public ArrayList<Transaccion> getTransactions(Usuario usuario,Calendar fecha){
       // String query = "select * from \"transaccion\" where idusuario = '"+usuario.getIdUsusario()+"' and fecha = '"+fecha.get(Calendar.YEAR)+"/"+
                //fecha.get(Calendar.MONTH)+"/"+fecha.get(Calendar.DATE)+"'";
        String query = "select * from \"transaccion\" where idusuario = '"+usuario.getIdUsusario()+"' and date_part('month', fecha) = "+(fecha.get(Calendar.MONTH)+1)+
                " and date_part('year', fecha) = "+fecha.get(Calendar.YEAR)+" order by idtransaccion desc";
        //String query = "select * from \"transaccion\" where idusuario = '"+usuario.getIdUsusario()+"'";
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

    public boolean addTransaction(Transaccion transaccion){
        String query ="INSERT INTO \"transaccion\"(descripcion, categoria, valor, ingreso, fecha, idusuario) VALUES (?,?,?,?,?,?)";

        int filasAfectadas =0;
        try{
            PreparedStatement statement = conection.prepareStatement(query);
            statement.setString(1,transaccion.getDescripcion());
            statement.setString(2,transaccion.getCategoria());
            statement.setBigDecimal(3, transaccion.getValor());
            statement.setBoolean(4, transaccion.isIngreso());
            statement.setDate(5, new java.sql.Date(transaccion.getFecha().getTime().getTime()));
            statement.setLong(6,transaccion.getIdusuario());
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
