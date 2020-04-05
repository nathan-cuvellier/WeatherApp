package fr.nathan.windapp.Models;

import java.io.Serializable;

public class Main implements Serializable {

    private String temp;
    private double feels_like;

    public Main(String temp, double feels_like) {
        this.temp = temp;
        this.feels_like = feels_like;
    }


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }
}
