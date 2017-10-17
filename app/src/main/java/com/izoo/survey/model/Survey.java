package com.izoo.survey.model;

import java.util.List;

/**
 * Created by mateusz on 08.08.17.
 */

public class Survey {
    private int ID_Survey;
    private String Name;
    private String Date_Create;
    private String Date_Update;
    private int Anonymous;
    private List<Section> sections;
    private int sectionNumber;
    private int questionNumber;
    private int allQuestionsNumber;
    private int currentQuestionNumber;

    public Survey(int ID_Survey, String name, String date_Create, String date_Update, int Anonymous, List<Section> sections) {
        this.ID_Survey = ID_Survey;
        Name = name;
        Date_Create = date_Create;
        Date_Update = date_Update;
        this.Anonymous = Anonymous;
        this.sections = sections;
        sectionNumber = 0;
        questionNumber = 0;
        currentQuestionNumber = 1;
    }

    public int getID_Survey() {
        return ID_Survey;
    }

    public void setID_Survey(int ID_Survey) {
        this.ID_Survey = ID_Survey;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate_Create() {
        return Date_Create;
    }

    public void setDate_Create(String date_Create) {
        Date_Create = date_Create;
    }

    public String getDate_Update() {
        return Date_Update;
    }

    public void setDate_Update(String date_Update) {
        Date_Update = date_Update;
    }

    public void setAnonymous (int Anonymous){this.Anonymous = Anonymous;}

    public int isAnonymous(){return Anonymous;}

    public List<Section> getSections() {return sections;}

    public void setSections(List<Section> sections) {
        this.sections = sections;

        allQuestionsNumber = 0;
        for(int i = 0; i < sections.size(); i++){
            allQuestionsNumber += sections.get(i).getQuestions().size();
        }
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }

    public Question getCurrentQuestion(){
        return sections.get(sectionNumber).getQuestions().get(questionNumber);
    }
    public String getSectionName(){
        return sections.get(sectionNumber).getName();
    }
    public Question getNextQuestion(){
        currentQuestionNumber++;
        questionNumber++;
        if(currentQuestionNumber > allQuestionsNumber){
            return null;
        } else{
            if(questionNumber == sections.get(sectionNumber).getQuestions().size()){
                sectionNumber++;
                questionNumber = 0;
            }
        }
        return sections.get(sectionNumber).getQuestions().get(questionNumber);
    }

    public Question getPreviousQuestion(){
        currentQuestionNumber--;
        questionNumber--;
        if(questionNumber == -1){
            sectionNumber--;
            questionNumber = sections.get(sectionNumber).getQuestions().size()-1;
        }
        return sections.get(sectionNumber).getQuestions().get(questionNumber);
    }

    public int getAllQuestionsNumber() {
        return allQuestionsNumber;
    }
    public void setFirstQuestion(){
        sectionNumber = 0;
        questionNumber = 0;
        currentQuestionNumber = 1;
    }
}
