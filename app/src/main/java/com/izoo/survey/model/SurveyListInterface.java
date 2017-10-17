package com.izoo.survey.model;

public interface SurveyListInterface {
    void setAdapter(ListAdapter adapter);
    SurveyList getItem(int position);
    void setErrorToast();
    void setSurveyFragment(int ID_Survey);
}
