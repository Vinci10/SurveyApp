package com.izoo.survey.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 08.08.17.
 */

public class Survey {
    private int ID_Survey;
    private String Name;
    private String Date_Create;
    private String Date_Update;
    private int Anonymous;
    private List<Section> sections;

    public Survey(int ID_Survey, String name, String date_Create, String date_Update, int Anonymous, List<Section> sections) {
        this.ID_Survey = ID_Survey;
        Name = name;
        Date_Create = date_Create;
        Date_Update = date_Update;
        this.Anonymous = Anonymous;
        this.sections = sections;
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

    public String getDate_Create() {
        return Date_Create;
    }

    public void setDate_Create(String date_Create) {
        Date_Create = date_Create;
    }

    public String getDate_Update() {
        return Date_Update;
    }

    public void setDate_Update(String date_Update) {
        Date_Update = date_Update;
    }

    public void setAnonymous (int Anonymous){this.Anonymous = Anonymous;}

    public int isAnonymous(){return Anonymous;}

    public List<Section> getSections() {return sections;}

    public void setSections(List<Section> sections) {this.sections = sections;}
}
