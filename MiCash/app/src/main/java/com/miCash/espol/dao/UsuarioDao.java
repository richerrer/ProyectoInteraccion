package com.miCash.espol.dao;

import com.miCash.espol.conection.DbConection;
import com.miCash.espol.pojos.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by usuario on 19/08/2014.
 */
public class UsuarioDao{

    private static UsuarioDao instance = null;
    private Connection conection = DbConection.getInstance().getConection();

    private UsuarioDao(){}

    public static UsuarioDao getInstance(){
        if(instance==null)
            instance = new UsuarioDao();
        return instance;
    }

    public Usuario getUsuario(String username){
        String query = "select * from \"usuario\" where username = '"+username+"'";
        Usuario usuario = null;
        try{
            Statement statement = conection.createStatement();
            ResultSet result =  statement.executeQuery(query);
            while (result.next()) {
                usuario = new Usuario(result.getString(2),result.getString(3));
                usuario.setIdusuario(result.getLong(1));
            }
            return  usuario;
        }catch (Exception e){return null;}

    }

    public Usuario getUsuario(String username,String password){
        String query = "select * from \"usuario\" where username = '"+username+"' and password = '"+password+"'";
        Usuario usuario = null;
        try{
            Statement statement = conection.createStatement();
            ResultSet result =  statement.executeQuery(query);
            while (result.next()) {
                usuario = new Usuario(result.getString(2),result.getString(3));
                usuario.setIdusuario(result.getLong(1));
                usuario.setNombre(result.getString(4));
                usuario.setApellido(result.getString(5));
            }
            return  usuario;
        }catch (Exception e){return null;}

    }

    public boolean addUsuario(String username,String password,String nombre,String apellido){
        String query ="INSERT INTO \"usuario\"(username, password, nombre, apellido) VALUES (?,?,?,?)";
        Usuario usuario = null;
        int filasAfectadas =0;
        try{
            PreparedStatement statement = conection.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,nombre);
            statement.setString(4,apellido);
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
