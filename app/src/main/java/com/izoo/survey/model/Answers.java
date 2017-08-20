package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Answers {
    private int id_Answers;
    private String text_Answers;

    public Answers() {
    }

    public Answers(int ID_Answers, String text_Answers) {
        id_Answers = ID_Answers;
        this.text_Answers = text_Answers;
    }

    public int getId_Answers() {
        return id_Answers;
    }

    public void setId_Answers(int ID_Answers) {
        id_Answers = ID_Answers;
    }

    public String getText_Answers() {
        return text_Answers;
    }

    public void setText_Answers(String Text_Answers) {
        text_Answers = Text_Answers;
    }
}
