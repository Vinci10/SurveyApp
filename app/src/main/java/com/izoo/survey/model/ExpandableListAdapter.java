package com.izoo.survey.model;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.izoo.survey.R;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<SurveyList> surveyLists;
    private Users user;

    public ExpandableListAdapter(List<SurveyList> surveyLists, Users user) {
        this.surveyLists = surveyLists;
        this.user = user;
    }

    @Override
    public int getGroupCount() {
        return this.surveyLists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return surveyLists.get(groupPosition).getHistories().size();
    }

    @Override
    public SurveyList getGroup(int groupPosition) {
        return surveyLists.get(groupPosition);
    }

    @Override
    public History getChild(int groupPosition, int childPosition) {
        return surveyLists.get(groupPosition).getHistories().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(parent.getContext());

            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }

        TextView header_text = (TextView) convertView.findViewById(R.id.header);
        if(getGroup(groupPosition).getSurvey().isAnonymous() == 0){
            header_text.setText(getGroup(groupPosition).getSurvey().getName());
        }else{
            SpannableString text = new SpannableString(getGroup(groupPosition).getSurvey().getName()+ " (A)");
            text.setSpan(new ForegroundColorSpan(Color.RED), text.length()-3,text.length(),0);
            header_text.setText(text);
        }
        header_text.setTextColor(Color.BLACK);

        if (isExpanded) {
            header_text.setTypeface(null, Typeface.BOLD);
        } else {
            header_text.setTypeface(null, Typeface.NORMAL);
        }
        if(getGroup(groupPosition).getSurvey().isAnonymous() == 0)
            ((TextView)convertView.findViewById(R.id.lp)).setText("Moje wypełnienia: " + getGroup(groupPosition).getLp());
        if(!user.getType_Users().equals("User"))
            ((TextView)convertView.findViewById(R.id.lpAll)).setText("Wypełnienia wszystkich użytkowników: " + getGroup(groupPosition).getLpAll());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (childPosition + 1) + ".   " + getChild(groupPosition, childPosition).getDate_Hour();

        if (convertView == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(parent.getContext());

            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView child_text = (TextView) convertView.findViewById(R.id.child);

        child_text.setText(childText);
        child_text.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
}
