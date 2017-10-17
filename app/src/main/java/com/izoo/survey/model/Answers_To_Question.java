package com.izoo.survey.model;

import java.util.List;

/**
 * Created by mateusz on 08.08.17.
 */

public class Answers_To_Question {
    private int id_Answers_To_Question;
    private String text_Answers;
    private int has_Text;
    private int ID_Question;
    private String text;
    private List<String> summaryTextAnswers;
    private int summaryAmountAnswers;

    public Answers_To_Question(int id_Answers_To_Question, String text_Answers, int has_Text, int ID_Question) {
        this.id_Answers_To_Question = id_Answers_To_Question;
        this.text_Answers = text_Answers;
        this.has_Text = has_Text;
        this.ID_Question = ID_Question;
        text = null;
        summaryAmountAnswers = 0;
        summaryTextAnswers = null;
    }

    public Answers_To_Question(Answers_To_Question answers_to_question){
        id_Answers_To_Question = answers_to_question.getId_Answers_To_Question();
        text_Answers = answers_to_question.getText_Answers();
        has_Text = answers_to_question.getHas_Text();
        ID_Question = answers_to_question.getID_Question();
        text = null;
        summaryAmountAnswers = 0;
        summaryTextAnswers = null;
    }

    public int getId_Answers_To_Question() {
        return id_Answers_To_Question;
    }

    public void setId_Answers_To_Question(int id_Answers_To_Question) {
        this.id_Answers_To_Question = id_Answers_To_Question;
    }

    public String getText_Answers() {
        return text_Answers;
    }

    public void setText_Answers(String text_Answers) {
        this.text_Answers = text_Answers;
    }

    public int getHas_Text() {
        return has_Text;
    }

    public void setHas_Text(int has_Text) {
        this.has_Text = has_Text;
    }

    public int getID_Question() {
        return ID_Question;
    }

    public void setID_Question(int ID_Question) {
        this.ID_Question = ID_Question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getSummaryTextAnswers() {return summaryTextAnswers;}

    public void setSummaryTextAnswers(List<String> summaryTextAnswers) {this.summaryTextAnswers = summaryTextAnswers;}

    public int getSummaryAmountAnswers() {return summaryAmountAnswers;}

    public void setSummaryAmountAnswers(int summaryAmountAnswers) {this.summaryAmountAnswers = summaryAmountAnswers;}
}
