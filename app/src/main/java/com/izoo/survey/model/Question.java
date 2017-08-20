package com.izoo.survey.model;

import java.util.List;

/**
 * Created by mateusz on 08.08.17.
 */

public class Question {
    private int ID_Question;
    private String Text_Question;
    private int Required;
    private String Tips;
    private String Type_Answers;
    private List<Answers> availableAnswers;
    private List<Integer> providedAnswers;
    private String openAnswer;


    public Question() {
    }

    public Question(int ID_Question, String text_Question, int required, String tips, String type_Answers, List<Answers> AvailableAnswers) {
        this.ID_Question = ID_Question;
        Text_Question = text_Question;
        Required = required;
        Tips = tips;
        availableAnswers = AvailableAnswers;
        Type_Answers = type_Answers;
        openAnswer = null;
        providedAnswers = null;
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

    public int isRequired() {
        return Required;
    }

    public void setRequired(int required) {
        Required = required;
    }

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }

    public String getType_Answers() {
        return Type_Answers;
    }

    public void setType_Answers(String Type_Answers) {this.Type_Answers = Type_Answers;}

    public List<Answers> getAvailableAnswers() {return availableAnswers;}

    public void setAvailableAnswers(List<Answers> availableAnswers) {this.availableAnswers = availableAnswers;}

    public List<Integer> getProvidedAnswers() {return providedAnswers;}

    public void setProvidedAnswers(List<Integer> providedAnswers) {this.providedAnswers = providedAnswers;}

    public String getOpenAnswer() {return openAnswer;}

    public void setOpenAnswer(String openAnswer) {this.openAnswer = openAnswer;}
}
