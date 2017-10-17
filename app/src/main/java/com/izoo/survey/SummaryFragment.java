package com.izoo.survey;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.izoo.survey.model.Answers_To_Question;
import com.izoo.survey.model.DBAdapter;
import com.izoo.survey.model.Question;
import com.izoo.survey.model.Section;
import com.izoo.survey.model.SummaryInterface;
import com.izoo.survey.model.Survey;
import com.izoo.survey.model.Users;

import java.util.List;

public class SummaryFragment extends Fragment implements SummaryInterface{
    private static int ID_Survey;
    private View view;
    private Presenter presenter;
    private LinearLayout linearLayout;

    public SummaryFragment() {}
    public static void setID_Survey(int id){
        ID_Survey = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_survey, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        try{
            presenter = new Presenter(ID_Survey,this,getActivity());
        }catch (Exception e){
            Toast.makeText(getActivity(),"Błąd bazy danych",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void setSurveyName(String text){
        TextView name = (TextView) view.findViewById(R.id.survey_name);
        name.setText(text);
    }

    @Override
    public void addTextView(CharSequence text, float size, boolean isBlack,boolean layout){
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        if(isBlack) tv.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 19, 0, 5);
        if(layout) tv.setLayoutParams(layoutParams);
        tv.setTextSize(size);
        linearLayout.addView(tv);
    }

    @Override
    public Button addButton(String text){
        Button button = new Button(getActivity());
        button.setText(text);
        button.setTextSize(20);
        button.setGravity(Gravity.CENTER);
        return button;
    }

    @Override
    public void setSummaryViewButtons(){
        Button back = addButton("Wstecz");
        LinearLayout.LayoutParams layoutParams1 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, 5, 0, 15);
        back.setLayoutParams(layoutParams1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setStatisticsFragment(true);
            }
        });
        linearLayout.addView(back);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroy();
    }

    class Presenter{
        Users user;
        DBAdapter dbAdapter;
        SummaryInterface summaryFragment;
        Survey survey;

        Presenter(int ID_Survey, SummaryInterface summaryFragment, Activity activity)throws Exception{
            this.summaryFragment = summaryFragment;
            user = MainActivity.getLoggedUser();
            dbAdapter = new DBAdapter(activity);
            if(user != null && ID_Survey != 0) setSurvey(ID_Survey);
        }

        void setSurvey(int ID_Survey) throws Exception{
            dbAdapter.openReadableDatabase();
            survey = dbAdapter.getSurvey(ID_Survey);
            String historyIDList = dbAdapter.getHistoryIDListBySurveyID(ID_Survey);
            List<Section> sections = survey.getSections();
            for(Section section: sections){
                List<Question> questions = section.getQuestions();
                for(Question question: questions){
                    List<Answers_To_Question> availableAnswers = question.getAvailableAnswers();
                    for(Answers_To_Question answer: availableAnswers){
                        dbAdapter.countGivenAnswers(historyIDList,answer);
                    }
                }
            }
            setSummaryView();
        }

        void setSummaryView(){
            summaryFragment.setSurveyName(survey.getName());
            List<Section> sections = survey.getSections();
            for(Section section: sections){
                summaryFragment.addTextView("Sekcja: "+section.getName(),30,false,true);
                List<Question> questions = section.getQuestions();
                for(Question question: questions){
                    addQuestion(question);
                }
            }
            summaryFragment.setSummaryViewButtons();
        }

        void addQuestion(Question question){
            String typeAnswer="";
            switch (question.getType_Question()){
                case "Single":
                    typeAnswer = " [Jednokrotnego wyboru]";
                    break;
                case "Multiple":
                    typeAnswer = " [Wielokrotnego wyboru]";
                    break;
                case "Open":
                    typeAnswer = " [Otwarte]";
                    break;
            }
            SpannableString text;
            if(question.isRequired() == 1){
                text = new SpannableString(question.getText_Question()+ typeAnswer + " *");
                text.setSpan(new RelativeSizeSpan(0.8f),question.getText_Question().length(),text.length()-2,0);
                text.setSpan(new ForegroundColorSpan(Color.RED), text.length()-1,text.length(),0);
            }else{
                text = new SpannableString(question.getText_Question() + typeAnswer);
                text.setSpan(new RelativeSizeSpan(0.8f),question.getText_Question().length(),text.length(),0);
            }
            summaryFragment.addTextView(text,25,false,true);
            if(question.getTips()!= null && !question.getTips().equals(""))
                summaryFragment.addTextView("Wskazówka: " + question.getTips(),15,false,false);

            addAnswersToQuestion(question);
        }

        void addAnswersToQuestion(Question question){
            List<Answers_To_Question> availableAnswers = question.getAvailableAnswers();
            int count = question.countGivenAsnwers();

            for(Answers_To_Question answer: availableAnswers){
                if(!question.getType_Question().equals("Open")){
                    int percent = (int) Math.round((double) answer.getSummaryAmountAnswers() * 100 / count);
                    if(answer.getHas_Text() == 0)
                        summaryFragment.addTextView(" - " + answer.getText_Answers() + "  (" + percent + "%)",20,true,true);
                    else
                        summaryFragment.addTextView(" - " + answer.getText_Answers() + "  [otwarta]  (" + percent + "%)",20,true,true);
                }else summaryFragment.addTextView(answer.getText_Answers(), 20, true,true);
                if(answer.getHas_Text() == 1){
                    List<String> textAnswers = answer.getSummaryTextAnswers();
                    if(textAnswers != null){
                        summaryFragment.addTextView("    Odpowiedzi udzielone przez użytkowników:",15,false,false);
                        for(String textAnswer: textAnswers){
                            summaryFragment.addTextView("   -> " + textAnswer,20,true,false);
                        }
                    }
                }
            }
        }

        void onDestroy(){
            dbAdapter.Close();
        }
    }

}
