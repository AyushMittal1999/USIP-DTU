package com.app.ayushmittal.usipdtu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String current_duration;
    public String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        category=getIntent().getStringExtra("category");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu m=navigationView.getMenu();
        navigationView.getMenu().clear();

        if(category.equalsIgnoreCase("admin"))
        navigationView.inflateMenu(R.menu.admin_menu);
        else if(category.equalsIgnoreCase("mentor"))
         navigationView.inflateMenu(R.menu.mentor_menu);


        FirebaseDatabase.getInstance().getReference("duration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                current_duration=dataSnapshot.getChildren().iterator().next().getKey().toString();
                ((TextView)findViewById(R.id.txt)).setText(current_duration);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {

            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(home.this,login_activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if(id == R.id.submit_report){
            android.support.v4.app.FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentholder,new submit_report());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(R.id.add_intern==id){
            android.support.v4.app.FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentholder,new add_new_intern());
                getSupportActionBar().setTitle("Add New Intern");



            ft.addToBackStack(null);
            ft.commit();
        }
        else if(R.id.mentor==id){
            android.support.v4.app.FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentholder,new add_new_mentor());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(id==R.id.view_intern){

            android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentholder,new view_intern()).commit();


        }

        else if(id==R.id.view_mentor){

            android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentholder,new view_mentor()).commit();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


 }
