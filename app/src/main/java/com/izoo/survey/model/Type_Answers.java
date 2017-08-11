package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Type_Answers {
    int ID_Type_Answers;
    String Name;

    public Type_Answers() {
    }

    public Type_Answers(int ID_Type_Answers, String name) {
        this.ID_Type_Answers = ID_Type_Answers;
        Name = name;
    }

    public int getID_Type_Answers() {
        return ID_Type_Answers;
    }

    public void setID_Type_Answers(int ID_Type_Answers) {
        this.ID_Type_Answers = ID_Type_Answers;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
