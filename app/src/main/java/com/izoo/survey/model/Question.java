package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Question {
    int ID_Question;
    String Text_Question;
    boolean Required;
    int Sequence;
    String Tips;
    int ID_Section;
    int ID_Type_Answers;

    public Question() {
    }

    public Question(int ID_Question, String text_Question, boolean required, int sequence, String tips) {
        this.ID_Question = ID_Question;
        Text_Question = text_Question;
        Required = required;
        Sequence = sequence;
        Tips = tips;
    }

    public Question(int ID_Question, String text_Question, boolean required, int sequence, String tips, int ID_Section, int ID_Type_Answers) {
        this.ID_Question = ID_Question;
        Text_Question = text_Question;
        Required = required;
        Sequence = sequence;
        Tips = tips;
        this.ID_Section = ID_Section;
        this.ID_Type_Answers = ID_Type_Answers;
    }

    public int getID_Question() {
        return ID_Question;
    }

    public void setID_Question(int ID_Question) {
        this.ID_Question = ID_Question;
    }

    public String getText_Question() {
        return Text_Question;
    }

    public void setText_Question(String text_Question) {
        Text_Question = text_Question;
    }

    public boolean isRequired() {
        return Required;
    }

    public void setRequired(boolean required) {
        Required = required;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }

    public int getID_Section() {
        return ID_Section;
    }

    public void setID_Section(int ID_Section) {
        this.ID_Section = ID_Section;
    }

    public int getID_Type_Answers() {
        return ID_Type_Answers;
    }

    public void setID_Type_Answers(int ID_Type_Answers) {
        this.ID_Type_Answers = ID_Type_Answers;
    }
}
