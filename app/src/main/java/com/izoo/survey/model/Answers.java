package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Answers {
    int ID_Answers;
    String Text_Answers;
    int ID_Answers_To_Question;

    public Answers() {
    }

    public Answers( String text_Answers, int ID_Answers_To_Question) {
        Text_Answers = text_Answers;
        this.ID_Answers_To_Question = ID_Answers_To_Question;
    }

    public int getID_Answers() {
        return ID_Answers;
    }

    public void setID_Answers(int ID_Answers) {
        this.ID_Answers = ID_Answers;
    }

    public String getText_Answers() {
        return Text_Answers;
    }

    public void setText_Answers(String text_Answers) {
        Text_Answers = text_Answers;
    }

    public int getID_Answers_To_Question() {
        return ID_Answers_To_Question;
    }

    public void setID_Answers_To_Question(int ID_Answers_To_Question) {
        this.ID_Answers_To_Question = ID_Answers_To_Question;
    }
}
