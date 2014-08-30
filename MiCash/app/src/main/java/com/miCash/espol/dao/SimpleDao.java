package com.miCash.espol.dao;

import com.miCash.espol.conection.DbConection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by usuario on 19/08/2014.
 */
public class SimpleDao {
    Connection conection = DbConection.getInstance().getConection();

    public ResultSet buitQuery (String query){
        ResultSet result = null;
        try {
            Statement statement = conection.createStatement();
            result =  statement.executeQuery(query);
        } catch (Exception e) {

        }return result;
    }
}
