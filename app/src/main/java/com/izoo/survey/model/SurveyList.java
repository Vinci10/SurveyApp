package com.izoo.survey.model;


public class SurveyList {
    private int _id;
    private String name;
    private String date;
    private int lp;

    public SurveyList(int _id, String name, String date, int lp) {
        this._id = _id;
        this.name = name;
        this.date = date;
        this.lp = lp;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }
}
