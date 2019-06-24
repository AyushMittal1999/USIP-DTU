package com.app.ayushmittal.usipdtu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.ayushmittal.usipdtu.adapter.collapsinglistitemforintern;
import com.app.ayushmittal.usipdtu.adapter.collapsinglistitemformentor;
import com.app.ayushmittal.usipdtu.object.intern;
import com.app.ayushmittal.usipdtu.object.mentor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class view_mentor extends Fragment {


    View v;

    public view_mentor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_view_mentor, container, false);

        ((home)getActivity()).getSupportActionBar().setTitle("View_Mentor");


        final ArrayList<mentor> list=new ArrayList<mentor>();


        final ExpandableListView expandableListView=v.findViewById(R.id.listview);

        final collapsinglistitemformentor adapter=new collapsinglistitemformentor(getContext(),list);
        expandableListView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("database").child("mentor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    mentor i=snapshot.getValue(mentor.class);
                    list.add(i);
                    expandableListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return v;
    }

}
