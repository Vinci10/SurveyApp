package com.izoo.survey;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.izoo.survey.model.DBAdapter;
import com.izoo.survey.model.ListAdapter;
import com.izoo.survey.model.SurveyList;
import com.izoo.survey.model.SurveyListInterface;
import com.izoo.survey.model.Users;

public class SurveyListFragment extends ListFragment implements SurveyListInterface {

    private Presenter presenter;
    public SurveyListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            presenter = new Presenter(this,getActivity());
        }catch (Exception e){
            Toast.makeText(getActivity(),"Błąd bazy danych",Toast.LENGTH_SHORT).show();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setAdapter(ListAdapter adapter){
        setListAdapter( adapter );
    }

    @Override
    public SurveyList getItem(int position){
        return (SurveyList) getListAdapter().getItem(position);
    }

    @Override
    public void setErrorToast(){
        String text = "Ta ankieta może zostać wypełniona tylko 1 raz";
        SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
        biggerText.setSpan(new RelativeSizeSpan(1.45f), 0, text.length(), 0);
        Toast.makeText(getActivity(),biggerText,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            presenter.onListItemClick(position);
        } catch (Exception e) {
            Toast.makeText(getActivity(),"Błąd bazy danych",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSurveyFragment(int ID_Survey){
        ((MainActivity) getActivity()).setSurveyFragment(ID_Survey,null);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroy();
    }

    class SurveyListTask extends AsyncTask<Object, Void, Boolean> {
        ListAdapter<SurveyList> adapter;
        @Override
        protected Boolean doInBackground(Object... params) {
            Users user = (Users) params[0];
            DBAdapter dbAdapter = (DBAdapter) params[1];
            try {
                dbAdapter.openReadableDatabase();
                adapter = new ListAdapter<>(getActivity(),
                        dbAdapter.getSurveyList(user,false),R.layout.survey_list);
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
                Toast.makeText(getActivity(),biggerText,Toast.LENGTH_SHORT).show();
            }else{
                setAdapter(adapter);
            }
        }
    }

    class Presenter{
        DBAdapter dbAdapter;
        Users user;
        SurveyListInterface surveyListFragment;

        Presenter(SurveyListInterface surveyListFragment, Activity activity)throws Exception{
            this.surveyListFragment = surveyListFragment;
            user = MainActivity.getLoggedUser();
            dbAdapter = new DBAdapter(activity);
            createList();
        }
        void createList(){
            if(user != null){
                new SurveyListTask().execute(user,dbAdapter);
            }
        }
        void onListItemClick(int position)throws Exception{
            SurveyList surveyList = surveyListFragment.getItem(position);
            if(surveyList.getLp() >= 1 && user.getType_Users().equals("User")){
                surveyListFragment.setErrorToast();
            }else{
                int ID_Survey = surveyList.getSurvey().getID_Survey();
                surveyListFragment.setSurveyFragment(ID_Survey);
            }
        }
        void onDestroy(){
            dbAdapter.Close();
        }
    }
}