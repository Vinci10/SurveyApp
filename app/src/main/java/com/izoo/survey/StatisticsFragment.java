package com.izoo.survey;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.izoo.survey.model.DBAdapter;
import com.izoo.survey.model.ExpandableListAdapter;
import com.izoo.survey.model.History;
import com.izoo.survey.model.StatisticsInterface;
import com.izoo.survey.model.SurveyList;
import com.izoo.survey.model.Users;

import java.util.List;

public class StatisticsFragment extends Fragment implements StatisticsInterface{

    private ExpandableListView expandableListView;
    private Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, null);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_listview);
        expandableListView.setGroupIndicator(null);
        try{
            presenter = new Presenter(getActivity(),this);
        }catch (Exception e){
            Toast.makeText(getActivity(),"Błąd bazy danych",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    void setSurveyFragment(int ID_Survey, History history){
        ((MainActivity) getActivity()).setSurveyFragment(ID_Survey,history);
    }

    @Override
    public void setAdapter(final ExpandableListAdapter expandableListAdapter, Users user){
        expandableListView.setAdapter( expandableListAdapter );

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                History history = expandableListAdapter.getChild(groupPosition,childPosition);
                setSurveyFragment(history.getId_Survey(),history);
                return true;
            }
        });
        if(!user.getType_Users().equals("User")) registerForContextMenu(expandableListView);
    }

    class StatisticsTask extends AsyncTask<Object, Void, Boolean> {
        List <SurveyList> surveyLists;
        Users user;
        @Override
        protected Boolean doInBackground(Object... params) {
            user = (Users) params[0];
            DBAdapter dbAdapter = (DBAdapter) params[1];
            try {
                dbAdapter.openReadableDatabase();
                surveyLists = dbAdapter.getSurveyList(user,true);
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
                if(user.getType_Users().equals("User")){
                    for(int i = 0; i < surveyLists.size(); i++){
                        if(surveyLists.get(i).getSurvey().isAnonymous() == 1){
                            surveyLists.remove(i);
                            i--;
                        }
                    }
                }
                ExpandableListAdapter adapter = new ExpandableListAdapter(surveyLists,user);
                setAdapter(adapter, user);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)     {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Wyświetlić podsumowanie ankiety?");
        menu.add(0, v.getId(), 0, "Tak");
        menu.add(0, v.getId(), 0, "Nie");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Tak")){
            ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
            int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int ID_Survey = ((ExpandableListAdapter)expandableListView.getExpandableListAdapter()).getGroup(groupPosition).getSurvey().getID_Survey();
            ((MainActivity) getActivity()).setSummaryFragment(ID_Survey);
        }
        else return false;
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroy();
    }

    class Presenter{
        Users user;
        DBAdapter dbAdapter;
        StatisticsInterface statisticsFragment;

        Presenter(Activity activity, StatisticsInterface statisticsFragment) throws Exception{
            user = MainActivity.getLoggedUser();
            dbAdapter = new DBAdapter(activity);
            this.statisticsFragment = statisticsFragment;
            if(user != null) setAdapter();
        }

        void setAdapter(){
            new StatisticsTask().execute(user,dbAdapter);
        }
        void onDestroy(){
            dbAdapter.Close();
        }
    }

}