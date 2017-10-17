package com.izoo.survey.model;


import java.util.List;

public class SurveyList {

    private Survey survey;
    private List<History> histories;
    private int lpAll;
    private int lp;

    public SurveyList(Survey survey, List<History> histories, int lpAll, int lp) {
        this.survey = survey;
        this.histories = histories;
        this.lpAll = lpAll;
        this.lp = lp;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public int getLpAll() {
        return lpAll;
    }

    public void setLpAll(int lpAll) {
        this.lpAll = lpAll;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }
}
