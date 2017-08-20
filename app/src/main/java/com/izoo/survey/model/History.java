package com.izoo.survey.model;


import java.util.Date;

/**
 * Created by mateusz on 08.08.17.
 */

class History {
    private int id_History;
    private Date date_Hour;
    private int  id_User;
    private int id_Survey;

    public History() {
    }

    public History(int ID_History, Date Date_Hour, int ID_User, int ID_Survey) {
        id_History = ID_History;
        date_Hour = Date_Hour;
        id_User = ID_User;
        id_Survey = ID_Survey;
    }

    public int getId_History() {
        return id_History;
    }

    public void setId_History(int ID_History) {
        id_History = ID_History;
    }

    public Date getDate_Hour() {
        return date_Hour;
    }

    public void setDate_Hour(Date Date_Hour) {
        date_Hour = Date_Hour;
    }

    public int getId_User() {
        return id_User;
    }

    public void setId_User(int ID_User) {
        id_User = ID_User;
    }

    public int getId_Survey() {return id_Survey; }

    public void setId_Survey(int ID_Survey) {
        id_Survey = ID_Survey;
    }
}
