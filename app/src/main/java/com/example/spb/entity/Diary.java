package com.example.spb.entity;

public class Diary {

    public int id;
    public String user_account;
    public String dia_date;
    public int dia_weather;
    public String dia_message;
    public String dia_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getDia_date() {
        return dia_date;
    }

    public void setDia_date(String dia_date) {
        this.dia_date = dia_date;
    }

    public int getDia_weather() {
        return dia_weather;
    }

    public void setDia_weather(int dia_weather) {
        this.dia_weather = dia_weather;
    }

    public String getDia_message() {
        return dia_message;
    }

    public void setDia_message(String dia_message) {
        this.dia_message = dia_message;
    }

    public String getDia_image() {
        return dia_image;
    }

    public void setDia_image(String dia_image) {
        this.dia_image = dia_image;
    }
}
