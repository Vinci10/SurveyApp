package com.izoo.survey;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.izoo.survey.model.DatabaseHelper;
import com.izoo.survey.model.ListAdapter;
import com.izoo.survey.model.SurveyList;
import com.izoo.survey.model.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyListFragment extends ListFragment {


    public SurveyListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Users user = MainActivity.getLoggedUser();
        if(user != null ){
            SQLiteOpenHelper databasehelper = new DatabaseHelper(getActivity());
            SQLiteDatabase db = databasehelper.getReadableDatabase();
            ListAdapter<SurveyList> adapter = new ListAdapter<>(getContext(),DatabaseHelper.getSurveyList(db,user.getID_User()),R.layout.survey_list);
            setListAdapter(adapter);
            db.close();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        SurveyFragment fragment = new SurveyFragment();
        SQLiteDatabase db = new DatabaseHelper(getActivity()).getReadableDatabase();
        int ID_Survey = ((SurveyList) getListAdapter().getItem(position)).get_id();
        fragment.setSurvey(DatabaseHelper.getSurvey(db,ID_Survey));
        db.close();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment,"visible_fragment")
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}