package com.app.ayushmittal.usipdtu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.app.ayushmittal.usipdtu.R;
import com.app.ayushmittal.usipdtu.object.intern;

import java.util.ArrayList;
import java.util.HashMap;

public class collapsinglistitemforintern extends BaseExpandableListAdapter {

    Context context;
    ArrayList<intern> internlist;

    public collapsinglistitemforintern(Context context, ArrayList<intern> internlist) {
        this.context = context;
        this.internlist = internlist;
    }

    @Override
    public int getGroupCount() {
        return internlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return internlist.get(groupPosition).getRoll();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return internlist.get(groupPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_view_group,null);
        }
        ((TextView) convertView.findViewById(R.id.heading)).setText(getGroup(groupPosition).toString());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_view_item,null);
        }
        intern n=(intern) getChild(groupPosition,childPosition);
        ((TextView) convertView.findViewById(R.id.name)).setText("Name : "+n.getName());
        ((TextView) convertView.findViewById(R.id.subheading2)).setText("Mobile  :"+n.getMobile());
        ((TextView) convertView.findViewById(R.id.subheading3)).setText("Email   :"+n.getEmail());
        ((TextView) convertView.findViewById(R.id.subheading4)).setText("Rollno  :"+n.getRoll());
        ((TextView) convertView.findViewById(R.id.subheading5)).setText("Dept    :"+n.getMentor());
        ((TextView) convertView.findViewById(R.id.subheading6)).setText("Report  :"+String.valueOf(n.isReport()));
        ((TextView) convertView.findViewById(R.id.subheading7)).setText("Duration  :"+n.getDuration());
        ((TextView) convertView.findViewById(R.id.subheading7)).setVisibility(View.VISIBLE);




        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
