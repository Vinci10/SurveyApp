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
    private String Type_Question;
    private List<Answers_To_Question> availableAnswers;
    private List<Answers_To_Question> givenAnswers;

    public Question(int ID_Question, String text_Question, int required, String tips, String type_Question, List<Answers_To_Question> AvailableAnswers) {
        this.ID_Question = ID_Question;
        Text_Question = text_Question;
        Required = required;
        Tips = tips;
        availableAnswers = AvailableAnswers;
        Type_Question = type_Question;
        givenAnswers = null;
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

    public String getType_Question() {return Type_Question;}

    public void setType_Question(String type_Question) {Type_Question = type_Question;}

    public List<Answers_To_Question> getAvailableAnswers() {return availableAnswers;}

    public void setAvailableAnswers(List<Answers_To_Question> availableAnswers) {this.availableAnswers = availableAnswers;}

    public List<Answers_To_Question> getGivenAnswers() {return givenAnswers;}

    public void setGivenAnswers(List<Answers_To_Question> givenAnswers) {this.givenAnswers = givenAnswers;}

    public Answers_To_Question getGivenAnswer(int id){
        if(givenAnswers != null){
            for (Answers_To_Question a: givenAnswers){
                if(a.getId_Answers_To_Question() == id) return a;
            }
        }
        return null;
    }

    public Answers_To_Question getAnswer(int id){
        for(Answers_To_Question a: availableAnswers){
            if(a.getId_Answers_To_Question() == id) return a;
        }
        return null;
    }

    public int countGivenAsnwers(){
        int count = 0;
        for(Answers_To_Question answer: availableAnswers){
            count += answer.getSummaryAmountAnswers();
        }
        return count;
    }
}
