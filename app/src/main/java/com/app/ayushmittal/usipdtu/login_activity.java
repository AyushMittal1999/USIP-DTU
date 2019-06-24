package com.app.ayushmittal.usipdtu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.app.ayushmittal.usipdtu.loginfrag.login_fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        if(FirebaseAuth.getInstance()!=null&&FirebaseAuth.getInstance().getCurrentUser()!=null){

            ((LinearLayout)findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
            changeactivity();

        }

        else {
            login_fragment fragment = new login_fragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updatepass: {

            }
            break;
            case R.id.login: {
                login_fragment fragment = new login_fragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_holder, fragment);
                fragmentTransaction.commit();
            }


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_in_menu, menu);

        return true;
    }


    public void changeactivity() {

        final Intent i = new Intent(login_activity.this, home.class);

        String user[] = new String[2];
        user = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@", 2);

        FirebaseDatabase.getInstance().getReference("user").orderByKey().equalTo(user[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildren().iterator().hasNext()) {
                    String cat = dataSnapshot.getChildren().iterator().next().getValue().toString();
                    i.putExtra("category", cat);
                    Log.i("cat", cat);
                    startActivity(i);
                    finish();
                } else {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    Snackbar.make(getCurrentFocus(), "You are Not allowed to Access ", Snackbar.LENGTH_SHORT).show();
                    ((LinearLayout)findViewById(R.id.progressBar)).setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
