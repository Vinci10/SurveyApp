package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Answers_To_Question {
    int ID_Answers_To_Question;
    String Text_Answers;
    int Sequence;
    boolean Has_Text;
    int ID_Answers;

    public Answers_To_Question() {
    }

    public Answers_To_Question(int ID_Answers_To_Question, String text_Answers, int sequence, boolean has_Text, int ID_Answers) {
        this.ID_Answers_To_Question = ID_Answers_To_Question;
        Text_Answers = text_Answers;
        Sequence = sequence;
        Has_Text = has_Text;
        this.ID_Answers = ID_Answers;
    }

    public int getID_Answers_To_Question() {
        return ID_Answers_To_Question;
    }

    public void setID_Answers_To_Question(int ID_Answers_To_Question) {
        this.ID_Answers_To_Question = ID_Answers_To_Question;
    }

    public String getText_Answers() {
        return Text_Answers;
    }

    public void setText_Answers(String text_Answers) {
        Text_Answers = text_Answers;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public boolean isHas_Text() {
        return Has_Text;
    }

    public void setHas_Text(boolean has_Text) {
        Has_Text = has_Text;
    }

    public int getID_Answers() {
        return ID_Answers;
    }

    public void setID_Answers(int ID_Answers) {
        this.ID_Answers = ID_Answers;
    }
}
