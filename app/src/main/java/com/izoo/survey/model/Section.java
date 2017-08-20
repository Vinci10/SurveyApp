package com.izoo.survey.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 08.08.17.
 */

public class Section {
    private int ID_Section;
    private String Name;
    private List<Question> questions;

    public Section() {
    }

    public Section(int ID_Section, String name, List<Question> questions) {
        this.ID_Section = ID_Section;
        Name = name;
        this.questions = questions;
    }

    public int getID_Section() {
        return ID_Section;
    }

    public void setID_Section(int ID_Section) {
        this.ID_Section = ID_Section;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {Name = name;}

    public List<Question> getQuestions() {return questions;}

    public void setQuestions(List<Question> questions) {this.questions = questions;}
}
