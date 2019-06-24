package com.app.ayushmittal.usipdtu.loginfrag;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ayushmittal.usipdtu.R;
import com.app.ayushmittal.usipdtu.home;
import com.app.ayushmittal.usipdtu.login_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class login_fragment extends Fragment {

    private EditText usernmae,password;
    private Button button;
    private FirebaseAuth mAuth;
    private LinearLayout progressBar;

    public login_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_login_fragment, container, false);

        mAuth=FirebaseAuth.getInstance();

        usernmae=(EditText)v.findViewById(R.id.user_name);
        password=(EditText)v.findViewById(R.id.pass);
        button=(Button)v.findViewById(R.id.login);
        progressBar=(LinearLayout)v.findViewById(R.id.progressBar);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                button.setEnabled(false);

                isValid(usernmae.getText().toString(),password.getText().toString());


            }
        });



        return v;
    }


    private void isValid(final String user, final String pass) {
        if (isPassValid(pass)) {
            mAuth = FirebaseAuth.getInstance();



            mAuth.signInWithEmailAndPassword(user.trim(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getActivity(), "login succesful", Toast.LENGTH_SHORT).show();

                        if(mAuth.getCurrentUser().isEmailVerified()){

                        changeactivity();
                        button.setEnabled(true);}

                        else{

                            Snackbar s= Snackbar.make(getView(),"Please verify email",Snackbar.LENGTH_LONG);
                            progressBar.setVisibility(View.GONE);
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mAuth.signOut();
                                }
                            },Snackbar.LENGTH_LONG);

                            s.setAction("Send Verification link", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Snackbar.make(getView(),"Verification Link Sent",Snackbar.LENGTH_INDEFINITE).show();
                                            mAuth.signOut();

                                        }
                                    });
                                }
                            });

                            s.show();

                        }


                    } else {
                        // If sign in fails, display a message to the user.
                        progressBar.setVisibility(View.GONE);
                        button.setEnabled(true);
                        Toast.makeText(getActivity(), "Authentication failed. ",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else {
            progressBar.setVisibility(View.GONE);
            button.setEnabled(true);
        }
    }

    public void changeactivity(){

        final Intent i=new Intent(getActivity(),home.class);

        String  user [] =new String[2];
         user=FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@",2);

        FirebaseDatabase.getInstance().getReference("user").orderByKey().equalTo(user[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildren().iterator().hasNext()) {
                    String cat = dataSnapshot.getChildren().iterator().next().getValue().toString();


                    i.putExtra("category", cat);
                    Log.i("cat", cat);
                    startActivity(i);
                    getActivity().finish();
                }

                else{
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(getView(),"You are Not allowed to Access ",Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public boolean isPassValid(String p){
        if(p.length()<6){
            Toast.makeText(getActivity(),"Password must be greater than 5",Toast.LENGTH_SHORT).show();
            password.setFocusable(true);

            return false;
        }
        return true;
    }



}
