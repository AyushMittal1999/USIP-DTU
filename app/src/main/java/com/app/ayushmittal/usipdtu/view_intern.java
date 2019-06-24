package com.app.ayushmittal.usipdtu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.ayushmittal.usipdtu.adapter.collapsinglistitemforintern;
import com.app.ayushmittal.usipdtu.http.fetchdata;
import com.app.ayushmittal.usipdtu.object.intern;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class view_intern extends Fragment {

    View v;
    String current_duration,category;

   public ArrayList<intern> list;



    public view_intern() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_view_intern, container, false);

         current_duration=((home)getActivity()).current_duration;
         category=((home)getActivity()).category;
        ((home)getActivity()).getSupportActionBar().setTitle("View_intern");

        list=new ArrayList<intern>();


        ExpandableListView expandableListView=v.findViewById(R.id.listview);

        collapsinglistitemforintern adapter=new collapsinglistitemforintern(getContext(),list);
        expandableListView.setAdapter(adapter);




        fetchdata f=new fetchdata(getContext(),expandableListView);
        f.execute();


        return v;
    }


   void addtolist(){


       intern i=new intern("name","roll","email","mobile","dur","mentor");

       list.add(i);


       final ExpandableListView expandableListView=v.findViewById(R.id.listview);

       final collapsinglistitemforintern adapter=new collapsinglistitemforintern(getContext(),list);
       expandableListView.setAdapter(adapter);


       if(category.equalsIgnoreCase("admin")){

           FirebaseDatabase.getInstance().getReference("database").child(current_duration+"/intern").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       intern i=snapshot.getValue(intern.class);
                       list.add(i);
                       expandableListView.setAdapter(adapter);
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });



       }
       else if(category.equalsIgnoreCase("mentor")){


           FirebaseDatabase.getInstance().getReference("database").child(current_duration+"/intern").orderByChild("mentor").equalTo("current mentor").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       intern i=snapshot.getValue(intern.class);
                       list.add(i);
                       expandableListView.setAdapter(adapter);
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });



       }


   }

    public void setadapter(ArrayList<intern> list){


         ExpandableListView expandableListView=v.findViewById(R.id.listview);

         collapsinglistitemforintern adapter=new collapsinglistitemforintern(getContext(),list);
        expandableListView.setAdapter(adapter);



    }

}
