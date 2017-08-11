package com.izoo.survey.model;


import java.util.Date;

/**
 * Created by mateusz on 08.08.17.
 */

class History {
    int ID_History;
    Date Date_Hour;
    int  ID_User;
    int ID_Survey;
    int Order_Number;

    public History() {
    }

    public History(int ID_History, Date date_Hour, int ID_User, int ID_Survey, int order_Number) {
        this.ID_History = ID_History;
        Date_Hour = date_Hour;
        this.ID_User = ID_User;
        this.ID_Survey = ID_Survey;
        Order_Number = order_Number;
    }

    public History(Date date_Hour, int ID_User, int ID_Survey, int order_Number) {
        Date_Hour = date_Hour;
        this.ID_User = ID_User;
        this.ID_Survey = ID_Survey;
        Order_Number = order_Number;
    }

    public int getID_History() {
        return ID_History;
    }

    public void setID_History(int ID_History) {
        this.ID_History = ID_History;
    }

    public Date getDate_Hour() {
        return Date_Hour;
    }

    public void setDate_Hour(Date date_Hour) {
        Date_Hour = date_Hour;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public int getID_Survey() {
        return ID_Survey;
    }

    public void setID_Survey(int ID_Survey) {
        this.ID_Survey = ID_Survey;
    }

    public int getOrder_Number() {
        return Order_Number;
    }

    public void setOrder_Number(int order_Number) {
        Order_Number = order_Number;
    }
}
