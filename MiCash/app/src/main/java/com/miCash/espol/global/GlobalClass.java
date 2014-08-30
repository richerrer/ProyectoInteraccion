package com.miCash.espol.global;

import android.app.Application;

import com.miCash.espol.pojos.Usuario;

/**
 * Created by usuario on 20/08/2014.
 */
public class GlobalClass extends Application {

    private Usuario usuario;

    public void setUsername(Usuario usuario){
        this.usuario = usuario;
    }
    public Usuario getUsername(){
        return this.usuario;
    }
}
