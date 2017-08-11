package com.izoo.survey.model;

import java.sql.Date;

/**
 * Created by mateusz on 08.08.17.
 */

public class Survey {
    int ID_Survey;
    String Name;
    Date Date_Create;
    Date Date_Update;

    public Survey() {
    }

    public Survey(int ID_Survey, String name) {
        this.ID_Survey = ID_Survey;
        Name = name;
    }

    public Survey(int ID_Survey, String name, Date date_Create, Date date_Update) {
        this.ID_Survey = ID_Survey;
        Name = name;
        Date_Create = date_Create;
        Date_Update = date_Update;
    }

    public int getID_Survey() {
        return ID_Survey;
    }

    public void setID_Survey(int ID_Survey) {
        this.ID_Survey = ID_Survey;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDate_Create() {
        return Date_Create;
    }

    public void setDate_Create(Date date_Create) {
        Date_Create = date_Create;
    }

    public Date getDate_Update() {
        return Date_Update;
    }

    public void setDate_Update(Date date_Update) {
        Date_Update = date_Update;
    }
}
