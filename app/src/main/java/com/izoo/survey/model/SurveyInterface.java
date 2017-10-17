package com.izoo.survey.model;

import android.database.Cursor;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.List;

public interface SurveyInterface {
    void setSurveyName(String text);
    void setSurveyViewButtons(boolean isBackButtonExcluded);
    void addTextView(CharSequence text, float size, boolean layout, boolean center);
    void cleanLayout(boolean visibility);
    void createRadioGroup();
    void addRadioGroup(boolean isEditText, boolean isSummary);
    boolean setCompoundButton(String Type_Question, Answers_To_Question answer, boolean isSummary, Answers_To_Question givenAnswer);
    void addEditText(Answers_To_Question answer, Answers_To_Question givenAnswer, boolean isSummary);
    void addCheckBoxButton(boolean isEditText, boolean isSummary);
    void setErrorToast();
    void setProgressBar(int currentQuestionNumber, int allQuestionsNumber);
    void setEndViewButtons(int ID_User, boolean assignButton);
    void setSummaryViewButtons(boolean isStatistics);
    RadioGroup getRadioGroup();
    EditText getEditText(int id);
    List<CheckBox> getCheckBoxes();
    Button addButton(String text);
    void setAssignViewButtons(Cursor cursor);
}
