package com.izoo.survey;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.izoo.survey.model.Answers_To_Question;
import com.izoo.survey.model.DBAdapter;
import com.izoo.survey.model.History;
import com.izoo.survey.model.Question;
import com.izoo.survey.model.Section;
import com.izoo.survey.model.Survey;
import com.izoo.survey.model.SurveyInterface;
import com.izoo.survey.model.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SurveyFragment extends Fragment implements SurveyInterface {
    private static int ID_Survey;
    private static History history;
    private View view;
    private LinearLayout linearLayout;
    private Presenter presenter;
    private LayoutParams layoutParams;
    private RadioGroup radioGroup;
    private EditText _editText;
    private CompoundButton compoundButton;

    public SurveyFragment() {}
    public static void set(int id_Survey, History _history){
        ID_Survey = id_Survey;
        history = _history;
    }
    public static History getHistory(){
        return history;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_survey, container, false);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 19, 0, 5);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        try{
            if(savedInstanceState != null){
                presenter = new Presenter(this,ID_Survey,getActivity(),true,history);
            }else{
                presenter = new Presenter(this,ID_Survey,getActivity(),false,history);
            }
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
    public void addTextView(CharSequence text, float size, boolean layout, boolean center){
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        if(layout) tv.setLayoutParams(layoutParams);
        if(center) tv.setGravity(Gravity.CENTER);
        tv.setTextSize(size);
        linearLayout.addView(tv);
    }
    @Override
    public void createRadioGroup(){
        radioGroup = new RadioGroup(getActivity());
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setLayoutParams(layoutParams);
    }
    @Override
    public void addRadioGroup(boolean isEditText, boolean isSummary){
        linearLayout.addView(radioGroup);
        if(isEditText && !isSummary){
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    for(int j = 0; j < linearLayout.getChildCount(); j++) {
                        View child = linearLayout.getChildAt(j);
                        if (child instanceof EditText) {
                            EditText et = (EditText) child;
                            if(et.getId() == i) et.setEnabled(true);
                            else et.setEnabled(false);
                            break;
                        }
                    }
                }
            });
            linearLayout.addView(_editText);
        }
        else if(isEditText && isSummary){
            _editText.setEnabled(false);
            _editText.setTextColor(Color.BLACK);
            linearLayout.addView(_editText);
        }
    }
    @Override
    public void addCheckBoxButton(boolean isEditText, boolean isSummary){
        linearLayout.addView(compoundButton);
        if(isEditText && !isSummary){
            compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    for(int j = 0; j < linearLayout.getChildCount(); j++) {
                        View child = linearLayout.getChildAt(j);
                        if (child instanceof EditText) {
                            EditText et = (EditText) child;
                            if(et.getId() == compoundButton.getId()){
                                et.setEnabled(compoundButton.isChecked());
                                break;
                            }
                        }
                    }
                }
            });
            linearLayout.addView(_editText);
        }
        else if(isEditText && isSummary){
            _editText.setEnabled(false);
            _editText.setTextColor(Color.BLACK);
            linearLayout.addView(_editText);
        }
    }
    @Override
    public boolean setCompoundButton(String Type_Question, Answers_To_Question answer,
                                  boolean isSummary, Answers_To_Question givenAnswer){
        boolean isEditText = false;
        if(Type_Question.equals("Single")) compoundButton  = new RadioButton(getActivity());
        else compoundButton  = new CheckBox(getActivity());
        compoundButton.setText(answer.getText_Answers());
        compoundButton.setTextSize(20);
        compoundButton.setId(answer.getId_Answers_To_Question());
        if(isSummary) compoundButton.setEnabled(false);
        if(givenAnswer != null){
            compoundButton.setChecked(true);
            if(isSummary) compoundButton.setTextColor(Color.BLACK);
            if(answer.getHas_Text() == 1){
                _editText = newEditText(null,answer.getId_Answers_To_Question(),givenAnswer.getText(),isSummary);
                isEditText = true;
            }
        }else{
            if(answer.getHas_Text() == 1){
                _editText = newEditText(null,answer.getId_Answers_To_Question(),null,isSummary);
                isEditText = true;
            }
        }
        if(Type_Question.equals("Single")) radioGroup.addView(compoundButton);
        return isEditText;
    }
    @Override
    public void addEditText(Answers_To_Question answer, Answers_To_Question givenAnswer, boolean isSummary){
        addTextView(answer.getText_Answers(),25,true,false);
        if(givenAnswer != null){
            _editText = newEditText(layoutParams,answer.getId_Answers_To_Question(),givenAnswer.getText(),isSummary);
        }else{
            _editText = newEditText(layoutParams,answer.getId_Answers_To_Question(),"",isSummary);
        }
        if(isSummary){
            _editText.setEnabled(false);
            _editText.setTextColor(Color.BLACK);
        }
        _editText.setTextSize(25);
        linearLayout.addView(_editText);
    }
    @Override
    public void setSurveyViewButtons(boolean isBackButtonExcluded){
        addBackButtonToPreviousQuestion(false,isBackButtonExcluded);
        Button next = new Button(getActivity());
        next.setText("Dalej");
        next.setTextSize(20);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getNextQuestion(false);
            }
        });
        linearLayout.addView(next);
    }

    @Override
    public void setProgressBar(int currentQuestionNumber, int allQuestionsNumber) {
        addTextView(currentQuestionNumber + "/" + allQuestionsNumber + " pytań",20,false,true);
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, 5, 0, 15);
        ProgressBar progressBar = new ProgressBar(getActivity(),null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgress(100*currentQuestionNumber/allQuestionsNumber);
        progressBar.setLayoutParams(layoutParams1);
        linearLayout.addView(progressBar);
    }

    @Override
    public void setErrorToast() {
        String text = "Odpowiedź na to pytanie jest wymagane";
        SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
        biggerText.setSpan(new RelativeSizeSpan(1.45f), 0, text.length(), 0);
        Toast.makeText(getActivity(), biggerText, Toast.LENGTH_SHORT).show();
    }
    @Override
    public RadioGroup getRadioGroup(){
        RadioGroup radio = new RadioGroup(getActivity());
        for(int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            if (child instanceof RadioGroup) {
                radio = (RadioGroup) child;
                break;
            }
        }
        return radio;
    }
    @Override
    public EditText getEditText(int id){
        EditText editText = new EditText(getActivity());
        for(int j = 0; j < linearLayout.getChildCount(); j++) {
            View child = linearLayout.getChildAt(j);
            if (child instanceof EditText) {
                editText = (EditText) child;
                if(editText.getId() == id) break;
            }
        }
        return editText;
    }
    @Override
    public List<CheckBox> getCheckBoxes() {
        List<CheckBox> checkBoxes = new ArrayList<>();
        for(int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            if (child instanceof CheckBox) {
                checkBoxes.add( (CheckBox) child );
            }
        }
        return checkBoxes;
    }
    @Override
    public void setEndViewButtons(int ID_User, boolean assignButton){
        TextView tv = new TextView(getActivity());
        tv.setText("Pomyślnie wypełniono ankietę");
        tv.setTextColor(Color.GREEN);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        linearLayout.addView(tv);
        addBackButtonToPreviousQuestion(true,false);
        addSaveButton(ID_User);
        if(assignButton){
            Button assignTo = addButton("Przypisz do:");
            assignTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        presenter.setAssignView();
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"Błąd bazy danych",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            linearLayout.addView(assignTo);
        }
        Button showSummary = addButton("Wyświetl podsumowanie");
        showSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setSummaryView(false);
            }
        });
        linearLayout.addView(showSummary);
    }

    @Override
    public void setSummaryViewButtons(boolean isStatistics){
        if(!isStatistics){
            Button back = addButton("Wstecz");
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.setEndView();
                }
            });
            linearLayout.addView(back);
            LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(0, 5, 0, 15);
            Button edit = addButton("Edytuj od pierwszego");
            edit.setLayoutParams(layoutParams1);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.setFirstQuestion();
                    presenter.setSurveyView(false);
                }
            });
            linearLayout.addView(edit);
        }else{
            Button back = addButton("Wstecz");
            LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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

    }
    @Override
    public void setAssignViewButtons(Cursor cursor){
        addTextView("Wybierz użytkownika",25,false,true);
        final Spinner spinner = new Spinner(getActivity());
        spinner.setGravity(Gravity.CENTER);
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.spinner_list,cursor, new String[]{"Login"},new int[] { android.R.id.text1 },0);
        spinner.setAdapter(cursorAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(int j = 0; j < linearLayout.getChildCount(); j++) {
                    View child = linearLayout.getChildAt(j);
                    if (child instanceof Button) {
                        if(((Button) child).getText().toString().equals("Zapisz")){
                            linearLayout.removeView(child);
                            break;
                        }
                    }
                }
                addSaveButton(((Cursor) spinner.getSelectedItem()).getInt(0));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        linearLayout.addView(spinner);
        Button back = addButton("Wstecz");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setEndView();
            }
        });
        linearLayout.addView(back);
    }

    @Override
    public Button addButton(String text){
        Button button = new Button(getActivity());
        button.setText(text);
        button.setTextSize(20);
        button.setGravity(Gravity.CENTER);
        return button;
    }

    private void exit(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.content_frame,new SurveyListFragment(),"visible_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private EditText newEditText(LayoutParams layoutParams,int id, String text, boolean isSummary){
        EditText et = new EditText(getActivity());
        if(layoutParams != null) et.setLayoutParams(layoutParams);
        et.setMinLines(1);
        et.setMaxLines(5);
        et.setId(id);
        if(!isSummary){
            et.setHint("Wpisz odpowiedź");
            et.setTextSize(25);
        }
        if(text != null) et.setText(text);
        else et.setEnabled(false);
        return et;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        presenter.onSaveInstanceState();
    }

    private void addBackButtonToPreviousQuestion(final boolean isEnd, boolean isBackButtonExcluded){
        Button back = new Button(getActivity());
        back.setText("Wstecz");
        back.setTextSize(20);
        if(isBackButtonExcluded) back.setEnabled(false);
        else back.setEnabled(true);
        if(isEnd) back.setGravity(Gravity.CENTER);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getPreviousQuestion(isEnd);
            }
        });
        linearLayout.addView(back);
    }

    @Override
    public void cleanLayout(boolean visibility){
        linearLayout.removeAllViews();
        if(visibility){
            view.findViewById(R.id.required_message).setVisibility(View.VISIBLE);
            view.findViewById(R.id.survey_name).setVisibility(View.VISIBLE);
        }
        else{
            view.findViewById(R.id.required_message).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.survey_name).setVisibility(View.INVISIBLE);
        }
    }

    private void addSaveButton(final int ID_User){
        Button save = new Button(getActivity());
        save.setText("Zapisz");
        save.setTextSize(20);
        save.setGravity(Gravity.CENTER);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveAnswersToDatabase(ID_User,SurveyFragment.this);
            }
        });
        linearLayout.addView(save);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroy();
    }

    private static class SaveAnswersTask extends AsyncTask<Object, Void, Boolean> {
        SurveyFragment fragment;
        @Override
        protected Boolean doInBackground(Object... params) {
            Survey survey = (Survey) params[0];
            int ID_User = (int) params[1];
            DBAdapter dbAdapter = (DBAdapter) params[2];
            fragment = (SurveyFragment) params[3];
            try {
                dbAdapter.openWritableDatabase();
                dbAdapter.saveAnswers(survey,ID_User);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                String text = "Błąd bazy danych";
                SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
                biggerText.setSpan(new RelativeSizeSpan(1.45f), 0, text.length(), 0);
                Toast.makeText(fragment.getActivity(),biggerText,Toast.LENGTH_SHORT).show();
            }
            else{
                String text = "Dziękujemy za wypełnienie ankiety";
                SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
                biggerText.setSpan(new RelativeSizeSpan(1.45f), 0, text.length(), 0);
                Toast.makeText(fragment.getActivity(),biggerText,Toast.LENGTH_SHORT).show();
                fragment.exit();
            }
        }
    }

    static class Presenter{
        DBAdapter dbAdapter;
        static Survey survey;
        Users user;
        SurveyInterface surveyFragment;
        Question currentQuestion;
        static String currentView;
        static Date start;

        Presenter(SurveyInterface surveyFragment, int ID_Survey, Activity activity, boolean isRestored, History history)throws Exception{
            this.surveyFragment = surveyFragment;
            dbAdapter = new DBAdapter(activity);
            user = MainActivity.getLoggedUser();
            if(history != null){
                dbAdapter.openReadableDatabase();
                survey = dbAdapter.getSurvey(ID_Survey);
                List<Section> sections = survey.getSections();
                for(Section section: sections){
                    List<Question> questions = section.getQuestions();
                    for(Question question: questions){
                        List<Answers_To_Question> availableAnswers = question.getAvailableAnswers();
                        List<Answers_To_Question> givenAnswers = new ArrayList<>();
                        for(Answers_To_Question answer: availableAnswers){
                            Answers_To_Question _answer = dbAdapter.getGivenAnswer(history.getId_History(),answer);
                            if(_answer != null) givenAnswers.add(_answer);
                        }
                        question.setGivenAnswers(givenAnswers);
                    }
                }
                setSummaryView(true);
            }else{
                if(!isRestored){
                    dbAdapter.openReadableDatabase();
                    survey = dbAdapter.getSurvey(ID_Survey);
                    currentView = "SURVEY";
                    start = new Date();
                }
                switch (currentView){
                    case "SURVEY":
                        this.surveyFragment.setSurveyName(survey.getName());
                        currentQuestion = survey.getCurrentQuestion();
                        setSurveyView(false);
                        break;
                    case "END":
                        setEndView();
                        break;
                    case "SUMMARY":
                        this.surveyFragment.setSurveyName(survey.getName());
                        setSummaryView(false);
                        break;
                    case "ASSIGN":
                        setAssignView();
                        break;
                }
            }
        }
        void setSurveyView(boolean isSummary){
            if(!isSummary) currentView = "SURVEY";
            surveyFragment.setSurveyName(survey.getName());
            if(!isSummary) surveyFragment.cleanLayout(true);
            surveyFragment.addTextView(survey.getSectionName(),30,false,false);

            if(currentQuestion.isRequired() == 1){
                SpannableString text = new SpannableString(currentQuestion.getText_Question()+ " *");
                text.setSpan(new ForegroundColorSpan(Color.RED), text.length()-1,text.length(),0);
                surveyFragment.addTextView(text,25,true,false);
            }else{
                surveyFragment.addTextView(currentQuestion.getText_Question(),25,true,false);
            }

            if(currentQuestion.getTips()!= null && !currentQuestion.getTips().equals(""))
                surveyFragment.addTextView("Wskazówka: " + currentQuestion.getTips(),15,false,false);

            if(currentQuestion.getType_Question().equals("Single")) addRadioButton(isSummary);
            else if (currentQuestion.getType_Question().equals("Multiple")) addCheckBoxButton(isSummary);
            else addEditText(isSummary);

            if(!isSummary){
                surveyFragment.setSurveyViewButtons( survey.getCurrentQuestionNumber() == 1 );
                surveyFragment.setProgressBar(survey.getCurrentQuestionNumber(),survey.getAllQuestionsNumber());
            }
        }
        void setEndView(){
            currentView = "END";
            surveyFragment.cleanLayout(false);
            int id = user.getID_User();
            if(!user.getType_Users().equals("User"))
                surveyFragment.setEndViewButtons(id,true);
            else
                surveyFragment.setEndViewButtons(id,false);
            Date end = new Date();
            int hoursEnd = end.getHours();
            int minutesEnd = end.getMinutes();
            int secondsEnd = end.getSeconds();
            int hoursStart = start.getHours();
            int minutesStart = start.getMinutes();
            int secondsStart = start.getSeconds();
            int seconds = secondsEnd - secondsStart;
            if(seconds < 0){
                seconds += 60;
                minutesEnd--;
            }
            if(minutesEnd < 0){
                minutesEnd += 60;
                hoursEnd--;
            }
            if(hoursEnd < 0) hoursEnd += 24;
            int minutes = minutesEnd - minutesStart;
            if(minutes < 0){
                minutes += 60;
                hoursEnd--;
            }
            int hours = hoursEnd - hoursStart;
            StringBuilder text = new StringBuilder("Czas przeprowadzenia ankiety:\n");
            if(hours > 0) text.append(hours + " godz. ");
            if(minutes > 0) text.append(minutes + " min. ");
            if(seconds > 0) text.append(seconds + " sek.");
            surveyFragment.addTextView(text,20,false,true);
        }
        void setSummaryView(boolean isStatistics){
            currentView = "SUMMARY";
            surveyFragment.cleanLayout(true);
            setFirstQuestion();
            setSurveyView(true);
            for(int i = 1; i < survey.getAllQuestionsNumber(); i++){
                getNextQuestion(true);
                setSurveyView(true);
            }
            getNextQuestion(true);
            surveyFragment.setSummaryViewButtons(isStatistics);
        }
        void setAssignView()throws Exception{
            currentView = "ASSIGN";
            surveyFragment.cleanLayout(false);
            dbAdapter.openReadableDatabase();
            Cursor cursor = dbAdapter.getOrdinaryUsers();
            surveyFragment.setAssignViewButtons(cursor);
        }
        void addRadioButton(boolean isSummary){
            boolean isEditText = false;
            Answers_To_Question answer;
            int amount = currentQuestion.getAvailableAnswers().size();
            surveyFragment.createRadioGroup();
            for(int i = 0; i < amount; i++){
                answer = currentQuestion.getAvailableAnswers().get(i);
                Answers_To_Question givenAnswer = currentQuestion.getGivenAnswer(answer.getId_Answers_To_Question());
                if(surveyFragment.setCompoundButton(currentQuestion.getType_Question(), answer, isSummary, givenAnswer))
                    isEditText = true;
            }
            surveyFragment.addRadioGroup(isEditText,isSummary);
        }
        void addCheckBoxButton(boolean isSummary){
            Answers_To_Question answer;
            int amount = currentQuestion.getAvailableAnswers().size();
            for(int i = 0; i < amount; i++){
                answer = currentQuestion.getAvailableAnswers().get(i);
                Answers_To_Question givenAnswer = currentQuestion.getGivenAnswer(answer.getId_Answers_To_Question());
                boolean isEditText = surveyFragment.setCompoundButton(currentQuestion.getType_Question(), answer, isSummary, givenAnswer);
                surveyFragment.addCheckBoxButton(isEditText,isSummary);
            }
        }
        void addEditText(boolean isSummary){
            Answers_To_Question answer = currentQuestion.getAvailableAnswers().get(0);
            Answers_To_Question givenAnswer = currentQuestion.getGivenAnswer(answer.getId_Answers_To_Question());
            surveyFragment.addEditText(answer, givenAnswer, isSummary);
        }
        void getNextQuestion(boolean isSummary) {
            if (isSummary) currentQuestion = survey.getNextQuestion();
            else {
                if (saveAnswers())
                    currentQuestion = survey.getNextQuestion();
                else surveyFragment.setErrorToast();
            }
            if(!isSummary && currentQuestion != null) setSurveyView(isSummary);
            else if(!isSummary)setEndView();
        }
        void getPreviousQuestion(boolean isEnd){
            if(!isEnd) saveAnswers();
            currentQuestion = survey.getPreviousQuestion();
            setSurveyView(false);
        }
        boolean saveRadioButtonAnswer(int id){
            boolean isEmptyEditText = false;
            if(id == -1 && currentQuestion.isRequired() == 1) return false;
            else if(id == -1 && currentQuestion.isRequired() == 0){
                currentQuestion.setGivenAnswers(null);
                return true;
            }
            else{
                List <Answers_To_Question> list = new ArrayList<>();
                Answers_To_Question answer = new Answers_To_Question(currentQuestion.getAnswer(id));
                if(answer.getHas_Text() == 1){
                    EditText et = surveyFragment.getEditText(id);
                    if(et.getText().toString() == null || et.getText().toString().equals(""))
                        isEmptyEditText = true;
                    answer.setText(et.getText().toString());
                }
                list.add(answer);
                currentQuestion.setGivenAnswers(list);
                if(isEmptyEditText) return false;
                return true;
            }
        }
        boolean saveCheckBoxAnswers(){
            boolean isEmptyEditText = false;
            boolean checked = false;
            List<Answers_To_Question> list = new ArrayList<>();
            List<CheckBox> checkBoxes = surveyFragment.getCheckBoxes();
            for(CheckBox cb: checkBoxes){
                if(cb.isChecked()){
                    checked = true;
                    int id = cb.getId();
                    Answers_To_Question answer = new Answers_To_Question(currentQuestion.getAnswer(id));
                    if(answer.getHas_Text() == 1){
                        EditText et = surveyFragment.getEditText(id);
                        if(et.getText().toString() == null || et.getText().toString().equals(""))
                            isEmptyEditText = true;
                        answer.setText(et.getText().toString());
                    }
                    list.add(answer);
                }
            }
            if(isEmptyEditText){
                currentQuestion.setGivenAnswers(list);
                return false;
            }
            if(!checked && currentQuestion.isRequired() == 1){
                currentQuestion.setGivenAnswers(null);
                return false;
            }
            else if(!checked && currentQuestion.isRequired() == 0){
                currentQuestion.setGivenAnswers(null);
                return true;
            }
            else{
                currentQuestion.setGivenAnswers(list);
                return true;
            }
        }
        boolean saveEditTextAnswer(int id){
            List<Answers_To_Question> list = new ArrayList<>();
            EditText et = surveyFragment.getEditText(id);
            if(et.getText().toString() == null || et.getText().toString().equals("")){
                if(currentQuestion.isRequired() == 1) return false;
                else currentQuestion.setGivenAnswers(null);
            }else{
                Answers_To_Question answer = new Answers_To_Question(currentQuestion.getAnswer(id));
                answer.setText(et.getText().toString());
                list.add(answer);
                currentQuestion.setGivenAnswers(list);
            }
            return true;
        }

        boolean saveAnswers(){
            if(currentQuestion.getType_Question().equals("Single")) {
                int id = surveyFragment.getRadioGroup().getCheckedRadioButtonId();
                return saveRadioButtonAnswer(id);
            }else if (currentQuestion.getType_Question().equals("Multiple")){
                return saveCheckBoxAnswers();
            }else{
                int id = currentQuestion.getAvailableAnswers().get(0).getId_Answers_To_Question();
                return saveEditTextAnswer(id);
            }
        }

        void saveAnswersToDatabase(int ID_User, SurveyFragment fragment){
            new SaveAnswersTask().execute(survey,ID_User,dbAdapter,fragment);
        }
        void onSaveInstanceState(){
            if(currentView.equals("SURVEY")) saveAnswers();
            surveyFragment.cleanLayout(false);
        }
        void setFirstQuestion(){
            survey.setFirstQuestion();
            currentQuestion = survey.getCurrentQuestion();
        }
        void onDestroy(){
            surveyFragment.cleanLayout(false);
            dbAdapter.Close();
        }
    }
}
