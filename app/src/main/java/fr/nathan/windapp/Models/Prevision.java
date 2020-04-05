package fr.nathan.windapp.Models;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Prevision implements Serializable {

    private String dt_txt;
    private Main main;
    private List<Weather> weather;
    private Wind wind;

    public Prevision(String dt_txt, Main main, List<Weather> weather, Wind wind) {
        this.dt_txt = dt_txt;
        this.main = main;
        this.weather = weather;
        this.wind = wind;
    }
    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String toString() {
        try {
            return "Date : " + getFormatedDate() + " Température : " + main.getFeels_like() + "\n";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Erreur format date";
    }

    public String getFormatedDate() throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sdf.parse(dt_txt);
        sdf.applyPattern("EEEE à HH:mm");
        return sdf.format(d);
    }

    public String getWeatherActivityFormatedDate() throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sdf.parse(dt_txt);
        sdf.applyPattern("EEEE dd MMMM à HH:mm");
        return sdf.format(d);
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
