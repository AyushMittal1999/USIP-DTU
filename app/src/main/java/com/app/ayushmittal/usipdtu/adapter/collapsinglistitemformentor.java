package com.app.ayushmittal.usipdtu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.app.ayushmittal.usipdtu.R;
import com.app.ayushmittal.usipdtu.object.intern;
import com.app.ayushmittal.usipdtu.object.mentor;

import java.util.ArrayList;

public class collapsinglistitemformentor extends BaseExpandableListAdapter {

    Context context;
    ArrayList<mentor> mentorlist;

    public collapsinglistitemformentor(Context context, ArrayList<mentor> mentorlist) {
        this.context = context;
        this.mentorlist = mentorlist;
    }

    @Override
    public int getGroupCount() {
        return mentorlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mentorlist.get(groupPosition).getDept();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mentorlist.get(groupPosition);
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
        mentor n=(mentor) getChild(groupPosition,childPosition);
        ((TextView) convertView.findViewById(R.id.name)).setText("Name : "+n.getName());
        ((TextView) convertView.findViewById(R.id.subheading2)).setText("Mobile  :"+n.getMoblie());
        ((TextView) convertView.findViewById(R.id.subheading3)).setText("Email   :"+n.getEmail());
        ((TextView) convertView.findViewById(R.id.subheading5)).setVisibility(View.GONE);
        ((TextView) convertView.findViewById(R.id.subheading4)).setText("Dept    :"+n.getDept());
        ((TextView) convertView.findViewById(R.id.subheading6)).setVisibility(View.GONE);



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
