package com.app.ayushmittal.usipdtu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ayushmittal.usipdtu.object.intern;
import com.app.ayushmittal.usipdtu.object.mentor;
import com.app.ayushmittal.usipdtu.object.work_report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class submit_report extends Fragment {

    private AppCompatSpinner spinner;
    String current_duration;
    TextInputLayout month,hours,remarks;
    work_report repo;
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         v=inflater.inflate(R.layout.fragment_submit_report, container, false);




        spinner=v.findViewById(R.id.spinner);
        spinner.setPrompt("Choose intern");

        month=v.findViewById(R.id.month);
        hours=v.findViewById(R.id.hours);
        remarks=v.findViewById(R.id.remarks);

        repo = new work_report();



        current_duration=((home)getActivity()).current_duration;






        final ArrayList<String> arrayList=new ArrayList<String>();

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity().getBaseContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(arrayAdapter);
        FirebaseDatabase.getInstance().getReference("database").child(current_duration+"/intern").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s1) {
                arrayList.add(dataSnapshot.getKey());
                spinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            FirebaseDatabase.getInstance().getReference("database").child(current_duration+"/intern").orderByKey().equalTo(spinner.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    intern i=dataSnapshot.getChildren().iterator().next().getValue(intern.class);
                    setinterndetails(i);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });



    v.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            repo.setHours(hours.getEditText().getText().toString());
            repo.setRemarks(remarks.getEditText().getText().toString());
            repo.setMonth(month.getEditText().getText().toString());

            FirebaseDatabase.getInstance().getReference("database").child(current_duration).child(repo.getMonth()+"/"+repo.getStudent().getRoll())
                    .setValue(repo);

        }
    });


        return v;
    }


    private void setinterndetails(intern i){

        TextView name=v.findViewById(R.id.name);
        name.setText("NAME   :  "+i.getName());
        TextView mob=v.findViewById(R.id.mobile);
        mob.setText("Mobile   :  "+i.getMobile());
        TextView duration=v.findViewById(R.id.duration);
        duration.setText("Duration:  "+i.getDuration());
        TextView email=v.findViewById(R.id.email);
        email.setText("Email     :  "+i.getEmail());

        FirebaseDatabase.getInstance().getReference("database").child("mentor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mentor  m = dataSnapshot.getChildren().iterator().next().getValue(mentor.class);

                setmentordetails(m);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        repo.setStudent(i);
    }

    void setmentordetails(mentor m){


        TextView name1=v.findViewById(R.id.mentor_name);
        name1.setText("NAME   :  "+m.getName());
        TextView mob1=v.findViewById(R.id.mentor_mobile);
        mob1.setText("Mobile   :  "+m.getMoblie());
        TextView duration1=v.findViewById(R.id.mentor_dept);
        duration1.setText("Department:  "+m.getDept());
        TextView email1=v.findViewById(R.id.mentor_email);
        email1.setText("Email     :  "+m.getEmail());
        repo.setMentor(m);



    }

}
