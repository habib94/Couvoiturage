package com.el_habib.couvoiturage.model;

/**
 * Created by el-habib on 13/11/16.
 */

public class Voiture {

    private int id;

    private String marque;

    private String color;

    private String urlImage;

    public Voiture() {
    }

    public Voiture(int id, String marque, String color,String urlImage) {
        this.id = id;
        this.marque = marque;
        this.color = color;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
