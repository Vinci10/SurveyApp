package com.izoo.survey;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.izoo.survey.model.Answers_To_Question;
import com.izoo.survey.model.DBAdapter;
import com.izoo.survey.model.Question;
import com.izoo.survey.model.Section;
import com.izoo.survey.model.Survey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DbTest {
    DBAdapter dbAdapter;
    Context context;
    long id;

    @Before
    public void setUp()throws Exception{
        context = InstrumentationRegistry.getTargetContext();
        dbAdapter = new DBAdapter(context);
        Survey survey = new Survey(1,"Test Survey","2017-08-10 01:25:00","2018-08-10 01:25:00",0,null);
        List<Section> sections = new ArrayList<>();

        Section section1 = new Section(1,"Section 1",null);

        List<Question> questions1 = new ArrayList<>();
        Question question1 = new Question(1,"Question 1", 0, "Tips 1","Multiple",null);
        List<Answers_To_Question> answers1 = new ArrayList<>();
        Answers_To_Question answers_to_question1 = new Answers_To_Question(1,"Answer 1", 0,1);
        Answers_To_Question answers_to_question2 = new Answers_To_Question(2,"Answer 2", 1,1);
        answers1.add(answers_to_question1);
        answers1.add(answers_to_question2);
        question1.setAvailableAnswers(answers1);

        Question question2 = new Question(2,"Question 2", 0, "Tips 2","Open",null);
        List<Answers_To_Question> answers2 = new ArrayList<>();
        Answers_To_Question answers_to_question3 = new Answers_To_Question(3,"Answer 3", 0,2);
        Answers_To_Question answers_to_question4 = new Answers_To_Question(4,"Answer 4", 0,2);
        answers2.add(answers_to_question3);
        answers2.add(answers_to_question4);
        question2.setAvailableAnswers(answers2);

        questions1.add(question1);
        questions1.add(question2);
        section1.setQuestions(questions1);
        sections.add(section1);

        Section section2 = new Section(2,"Section 2",null);

        List<Question> questions2 = new ArrayList<>();
        Question question3 = new Question(3,"Question 3", 0, "Tips 3","Multiple",null);
        List<Answers_To_Question> answers3 = new ArrayList<>();
        Answers_To_Question answers_to_question5 = new Answers_To_Question(5,"Answer 5", 0,3);
        answers3.add(answers_to_question5);
        question3.setAvailableAnswers(answers3);

        questions2.add(question3);
        section2.setQuestions(questions2);
        sections.add(section2);
        survey.setSections(sections);

        dbAdapter.openWritableDatabase();
        id = dbAdapter.insertSurvey(survey);
    }
    @After
    public void finish(){
        dbAdapter.Close();
    }

    @Test
    public void displaySurvey()throws Exception{
        if(id != -1){
            Survey survey = dbAdapter.getSurvey((int) id);
            System.out.println(survey.getName());
            List<Section> sections = survey.getSections();
            for(Section section: sections){
                System.out.println(section.getName());
                List<Question> questions = section.getQuestions();
                for(Question question: questions){
                    System.out.println("   " + question.getText_Question());
                    System.out.println("   " + question.getTips());
                    List<Answers_To_Question> answers_to_questions = question.getAvailableAnswers();
                    for(Answers_To_Question answers_to_question: answers_to_questions){
                        System.out.println("     " + answers_to_question.getText_Answers());
                    }
                }
            }
        }
    }
}
