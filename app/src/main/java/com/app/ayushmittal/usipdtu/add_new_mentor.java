package com.app.ayushmittal.usipdtu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ayushmittal.usipdtu.object.mentor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_new_mentor extends Fragment {

    TextInputLayout name,dept,mob,email;
    Button submit;
    public add_new_mentor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_new_mentor, container, false);

        name=(TextInputLayout)v.findViewById(R.id.name);
        dept=(TextInputLayout)v.findViewById(R.id.dept);
        mob=(TextInputLayout)v.findViewById(R.id.mobile);
        email=(TextInputLayout)v.findViewById(R.id.email);
        submit=(Button) v.findViewById(R.id.submit);



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
         if(dept.getEditText().getText().toString().trim().isEmpty()){
            dept.setError("Cant be empty");
             flag=true;
         } if(mob.getEditText().getText().toString().trim().isEmpty()){
            mob.setError("Cant be empty");
            flag=true;
        } if(email.getEditText().getText().toString().trim().isEmpty()){
            email.setError("Cant be empty");
            flag=true;
        }
    if(flag)
    return false;

    return true;
    }

   public void submit(final View v){

    if(checkempty()){
        mentor m=new mentor(name.getEditText().getText().toString().toUpperCase().trim(), mob.getEditText().getText().toString().trim(),email.getEditText().getText().toString().trim(),dept.getEditText().getText().toString().trim());

        FirebaseDatabase.getInstance().getReference("database/mentor")
                .child(name.getEditText().getText().toString().trim()).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(v,"Updated",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    }

}
