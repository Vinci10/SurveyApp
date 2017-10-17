package com.izoo.survey.model;

import android.widget.Button;

public interface SummaryInterface {
    void setSurveyName(String text);
    void addTextView(CharSequence text, float size, boolean isBlack,boolean layout);
    Button addButton(String text);
    void setSummaryViewButtons();
}
