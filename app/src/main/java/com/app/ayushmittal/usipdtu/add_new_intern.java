package com.app.ayushmittal.usipdtu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import com.app.ayushmittal.usipdtu.object.intern;
import com.app.ayushmittal.usipdtu.object.mentor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class add_new_intern extends Fragment {


    TextInputLayout name,duration,mob,email,roll;
    Button submit;
    ProgressBar progressBar;
    private AppCompatSpinner spinner;



    public add_new_intern() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=  inflater.inflate(R.layout.fragment_add_new_intern, container, false);

        spinner=v.findViewById(R.id.spinner);
        progressBar=v.findViewById(R.id.progressbar);

        name=v.findViewById(R.id.name);
        duration=v.findViewById(R.id.duration);
        mob=(TextInputLayout)v.findViewById(R.id.mobile);
        email=(TextInputLayout)v.findViewById(R.id.email);
        submit=(Button) v.findViewById(R.id.submit);
        roll=v.findViewById(R.id.roll);

        String current_duration=((home)getActivity()).current_duration;
        duration.getEditText().setText(current_duration.toString());


        final ArrayList<String> arrayList=new ArrayList<String>();

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity().getBaseContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(arrayAdapter);
        FirebaseDatabase.getInstance().getReference("database/mentor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.add(dataSnapshot.getKey());
                for( DataSnapshot d:dataSnapshot.getChildren()){
                    arrayList.add(d.getKey().toString());
                spinner.setAdapter(arrayAdapter);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(v);
            }
        });

        return v;
    }


    boolean checkempty(){

        boolean flag=false;

        if(name.getEditText().getText().toString().trim().isEmpty()){
            name.setError("Cant be empty");
            flag=true;
        }
        if(roll.getEditText().getText().toString().trim().isEmpty()){
            roll.setError("Cant be empty");
            flag=true;
        } if(mob.getEditText().getText().toString().trim().isEmpty()){
            mob.setError("Cant be empty");
            flag=true;
        } if(email.getEditText().getText().toString().trim().isEmpty()){
            email.setError("Cant be empty");
            flag=true;
        }
        if(spinner.getSelectedItem().toString().equalsIgnoreCase("mentor")){
            email.setError("Choose valid mentor");
            Snackbar.make(getView(),"Choose mentor",Snackbar.LENGTH_SHORT).show();
            flag=true;
        }
        if(flag)
        {    progressBar.setVisibility(View.GONE);
            return false;
        }

        return true;
    }


    public void submit(final View v){
        progressBar.setVisibility(View.VISIBLE);

        if(checkempty()){


            intern m=new intern(name.getEditText().getText().toString().toUpperCase().trim(), roll.getEditText().getText().toString().toUpperCase().trim(),email.getEditText().getText().toString().trim(),mob.getEditText().getText().toString().trim(),duration.getEditText().getText().toString().trim(),spinner.getSelectedItem().toString());

            FirebaseDatabase.getInstance().getReference("database")
                    .child(duration.getEditText().getText().toString().trim())
                    .child("intern")
                    .child(name.getEditText().getText().toString().trim())
                    .setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(v,"Updated",Snackbar.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        else{
            progressBar.setVisibility(View.GONE);
        }

    }

}
