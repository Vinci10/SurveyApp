package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Results {
    int ID_Results;
    String Text_Answers;
    int ID_Question;
    int Order_Number;

    public Results() {
    }

    public Results( String text_Answers, int ID_Question, int order_Number) {
        Text_Answers = text_Answers;
        this.ID_Question = ID_Question;
        Order_Number = order_Number;
    }

    public int getID_Results() {
        return ID_Results;
    }

    public void setID_Results(int ID_Results) {
        this.ID_Results = ID_Results;
    }

    public String getText_Answers() {
        return Text_Answers;
    }

    public void setText_Answers(String text_Answers) {
        Text_Answers = text_Answers;
    }

    public int getID_Question() {
        return ID_Question;
    }

    public void setID_Question(int ID_Question) {
        this.ID_Question = ID_Question;
    }

    public int getOrder_Number() {
        return Order_Number;
    }

    public void setOrder_Number(int order_Number) {
        Order_Number = order_Number;
    }
}
