package com.miCash.espol.menu;

/**
 * Created by usuario on 30/08/2014.
 */
public class Item {
    private int idImage;
    private String texto;

    public Item(int idImage, String texto) {
        this.idImage = idImage;
        this.texto = texto;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
