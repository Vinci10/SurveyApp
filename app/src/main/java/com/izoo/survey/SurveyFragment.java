package com.izoo.survey;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.izoo.survey.model.DatabaseHelper;
import com.izoo.survey.model.Question;
import com.izoo.survey.model.Section;
import com.izoo.survey.model.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment  {
    private Survey survey;
    private int sectionNumber;
    private int questionNumber;
    private int allSectionsNumber;
    private int allQuestionsNumber;
    private int currentQuestionNumber;
    private View view;

    public SurveyFragment() {}
    public void setSurvey(Survey survey){this.survey = survey;}
    public Survey getSurvey(){return survey;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_survey, container, false);
        TextView name = (TextView) view.findViewById(R.id.survey_name);
        name.setText(survey.getName());
        sectionNumber = 0;
        questionNumber = 0;
        currentQuestionNumber = 1;
        allQuestionsNumber = getAllQuestionsNumber();
        allSectionsNumber = survey.getSections().size();
        init();
        return view;
    }

    private void init(){
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        if( linearLayout.getChildCount() > 0) linearLayout.removeAllViews();
        TextView tv = new TextView(getActivity());
        tv.setText(survey.getSections().get(sectionNumber).getName());
        tv.setTextSize(20);
        linearLayout.addView(tv);
        tv = new TextView(getActivity());
        final Question question = survey.getSections().get(sectionNumber).getQuestions().get(questionNumber);
        if(question.isRequired() == 1){
            SpannableString text = new SpannableString(question.getText_Question()+ " *");
            text.setSpan(new ForegroundColorSpan(Color.RED), text.length()-1,text.length(),0);
            tv.setText(text);
        }else{
            tv.setText(question.getText_Question());
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 25, 0, 0);
        tv.setLayoutParams(layoutParams);
        linearLayout.addView(tv);
        if(question.getTips()!= null && !question.getTips().equals("")){
            tv = new TextView(getActivity());
            tv.setText("Wskazówka: " + question.getTips());
            tv.setTextSize(10);
            linearLayout.addView(tv);
        }
        if(question.getType_Answers().equals("Single")){
            //add radio buttons
            int amount = question.getAvailableAnswers().size();
            final RadioButton[] rb = new RadioButton[amount];
            RadioGroup rg = new RadioGroup(getActivity());
            rg.setOrientation(RadioGroup.VERTICAL);
            rg.setLayoutParams(layoutParams);
            for(int i = 0; i < amount; i++){
                rb[i]  = new RadioButton(getActivity());
                rb[i].setText(question.getAvailableAnswers().get(i).getText_Answers());
                rb[i].setId(question.getAvailableAnswers().get(i).getId_Answers());
                if(question.getProvidedAnswers() != null){
                    if(question.getProvidedAnswers().contains((int)rb[i].getId())) rb[i].setChecked(true);
                }
                rg.addView(rb[i]);
            }
            linearLayout.addView(rg);
        }else if (question.getType_Answers().equals("Multiple")){
            //add checkboxes
            int amount = question.getAvailableAnswers().size();
            for(int i = 0; i < amount; i++) {
                CheckBox cb = new CheckBox(getActivity());
                cb.setText(question.getAvailableAnswers().get(i).getText_Answers());
                cb.setId(question.getAvailableAnswers().get(i).getId_Answers());
                if(question.getProvidedAnswers() != null){
                    if(question.getProvidedAnswers().contains((int)cb.getId())) cb.setChecked(true);
                }
                linearLayout.addView(cb);
            }
        }else{
            // add edit text
            EditText et = new EditText(getActivity());
            et.setLayoutParams(layoutParams);
            et.setMinLines(1);
            et.setMaxLines(5);
            et.setHint("Wpisz odpowiedź");
            if(question.getOpenAnswer() != null){
                et.setText(question.getOpenAnswer());
            }
            linearLayout.addView(et);
        }
        // add buttons
        Button back = new Button(getActivity());
        back.setText("Wstecz");
        if(currentQuestionNumber == 1) back.setEnabled(false);
        else back.setEnabled(true);
        back.setLayoutParams(layoutParams);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnswer(linearLayout);
                currentQuestionNumber--;
                questionNumber--;
                if(questionNumber == -1){
                    sectionNumber--;
                    questionNumber = survey.getSections().get(sectionNumber).getQuestions().size()-1;
                }
                init();
            }
        });
        linearLayout.addView(back);
        Button next = new Button(getActivity());
        next.setText("Dalej");
        next.setLayoutParams(layoutParams);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveAnswer(linearLayout)){
                    currentQuestionNumber++;
                    if(currentQuestionNumber > allQuestionsNumber){
                        end();
                        return;
                    }
                    questionNumber++;
                    if(questionNumber == survey.getSections().get(sectionNumber).getQuestions().size()){
                        sectionNumber++;
                        questionNumber = 0;
                    }
                }
                else Toast.makeText(getActivity(),"Odpowiedź na to pytanie jest wymagane",Toast.LENGTH_SHORT).show();
                init();
            }
        });
        linearLayout.addView(next);
        tv = new TextView(getActivity());
        tv.setText(currentQuestionNumber + "/" + allQuestionsNumber + " pytań");
        tv.setGravity(Gravity.CENTER);
        linearLayout.addView(tv);
        ProgressBar progressBar = new ProgressBar(getActivity(),null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgress(100*currentQuestionNumber/allQuestionsNumber);
        linearLayout.addView(progressBar);
    }

    private int getAllQuestionsNumber(){
        int x = 0;
        List<Section> sections = survey.getSections();
        for(int i = 0; i < sections.size(); i++){
            x += sections.get(i).getQuestions().size();
        }
        return x;
    }

    private boolean saveAnswer(LinearLayout parent){
        Question question = survey.getSections().get(sectionNumber).getQuestions().get(questionNumber);
        if(question.getType_Answers().equals("Single")){
            for(int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof RadioGroup) {
                    //Support for RadioGroups
                    RadioGroup radio = (RadioGroup) child;
                    int id = radio.getCheckedRadioButtonId();
                    if(id == -1 && question.isRequired() == 1) return false;
                    else if(id == -1 && question.isRequired() == 0){
                        question.setProvidedAnswers(null);
                        return true;
                    }
                    else{
                        List<Integer> list = new ArrayList<>();
                        list.add(id);
                        question.setProvidedAnswers(list);
                        survey.getSections().get(sectionNumber).getQuestions().set(questionNumber,question);
                        return true;
                    }
                }
            }
        }else if (question.getType_Answers().equals("Multiple")){
            boolean checked = false;
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof CheckBox) {
                    //Support for Checkboxes
                    CheckBox cb = (CheckBox) child;
                    if(cb.isChecked()){
                        checked = true;
                        list.add(cb.getId());
                    }
                }
            }
            if(!checked && question.isRequired() == 1){
                question.setProvidedAnswers(null);
                return false;
            }
            else if(!checked && question.isRequired() == 0){
                question.setProvidedAnswers(null);
                return true;
            }
            else{
                question.setProvidedAnswers(list);
                survey.getSections().get(sectionNumber).getQuestions().set(questionNumber,question);
                return true;
            }
        } else{
            for(int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof EditText) {
                    //Support for EditText
                    EditText et = (EditText) child;
                    if(et.getText().toString() == null || et.getText().toString().equals("")){
                        question.setOpenAnswer(null);
                        if(question.isRequired() == 1) return false;
                        else question.setOpenAnswer(null);
                    }
                    question.setOpenAnswer(et.getText().toString());
                    survey.getSections().get(sectionNumber).getQuestions().set(questionNumber, question);
                    return true;
                }
            }
        }
        return false;
    }

    private void end(){
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        linearLayout.removeAllViews();
        ((TextView)view.findViewById(R.id.required_message)).setText("");
        ((TextView)view.findViewById(R.id.survey_name)).setText("");
        TextView tv = new TextView(getActivity());
        tv.setText("Pomyślnie wypełniono ankietę");
        tv.setTextColor(Color.GREEN);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        linearLayout.addView(tv);
        Button save = new Button(getActivity());
        save.setText("Zapisz");
        save.setGravity(Gravity.CENTER);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = new DatabaseHelper(getActivity()).getWritableDatabase();
                DatabaseHelper.saveAnswers(db,survey,MainActivity.getLoggedUser().getID_User());
                db.close();
                exit();
            }
        });
        linearLayout.addView(save);
        Button abandon = new Button(getActivity());
        abandon.setGravity(Gravity.CENTER);
        abandon.setText("Odrzuć");
        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
        linearLayout.addView(abandon);
    }

    private void exit(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.content_frame,new SurveyListFragment(),"visible_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
