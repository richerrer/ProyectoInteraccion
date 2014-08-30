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
    private long idEstudiante;
    private String username;
    private String password;



    public Usuario(String username,String Password){
        this.username = username;
        this.password = password;
    }

    public static Usuario getUsuario(String username,String password){
        GetUusario u = new GetUusario();
        u.start();

        if(u.usuario!=null){System.out.println("si se encontro");}
        else{System.out.println("no se encontro ");}
        return u.usuario;
    }

    public void setIdEstudiante(long idEstudiante){
        this.idEstudiante = idEstudiante;
    }

    public long getIdEstudiante(){
        return this.idEstudiante;
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

}
 class GetUusario extends Thread{
     Connection conexionPostgres = null;
     String statementPostgrasql = "select * from \"usuario\" where username = 'ricardo' and password = '1234'";
     Usuario usuario = null;

    public void run(){
        conexionPostgres = DbConection.getInstance().getConection();
        if(conexionPostgres==null){
            System.out.println("oops! No se puede conectar. Error: ");
        }else{
            Statement select = null;
            try {
                select = conexionPostgres.createStatement();
                ResultSet result = select.executeQuery(statementPostgrasql);
                while (result.next()) {
                    usuario = new Usuario(result.getString(2),result.getString(3));
                   // usuario.idEstudiante = result.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Coneccion Exitosa ");
            if(usuario!=null)
                System.out.println("Si se encontro ");

        }
    }
}