package com.izoo.survey;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.izoo.survey.model.DatabaseHelper;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {


    private DatabaseHelper myDb;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list);

        String[] from = new String[] {"_id", "Name","Sequence"};
        int[] to = new int[] {R.id.textx1,R.id.textx2,R.id.textx3};
        myDb = new DatabaseHelper(getActivity());
        Cursor answers =myDb.getAllSection();
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.test, answers, from, to,0);

//        ArrayAdapter <String> listviewAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                menuItem
//        );
//        listView.setAdapter(listviewAdapter);
        listView.setAdapter(cursorAdapter);
        return view;

    }

    private View getView(View parentView, int requestedViewId) {
        View ret = parentView.findViewById(requestedViewId);
        if (ret == null) {
            throw new RuntimeException("View with ID: " + requestedViewId + " could not be found!");
        }
        return ret;
    }


}
