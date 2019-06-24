package com.app.ayushmittal.usipdtu.http;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ayushmittal.usipdtu.R;
import com.app.ayushmittal.usipdtu.adapter.collapsinglistitemforintern;
import com.app.ayushmittal.usipdtu.home;
import com.app.ayushmittal.usipdtu.object.intern;
import com.app.ayushmittal.usipdtu.view_intern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class fetchdata extends AsyncTask<Void,Void,Void> {

    public ArrayList<intern> arrayList;
    String json="";
    Context context;
    String data2;
    ExpandableListView expandableListView;

    public fetchdata(Context context, ExpandableListView expandableListView) {
        this.context = context;
        arrayList=new ArrayList<intern>();
        this.expandableListView=expandableListView;

    }



    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url=new URL("https://script.google.com/macros/s/AKfycbxGpSS4I51XwNx6rrg_m_ykz9YTgKBXmSHoFBZDNQ/exec");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line ="";

            while (line!=null){

                line=bufferedReader.readLine();
                json=json+line;
            }

            JSONArray jsonArray=new JSONArray(json);
            data2="";



            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                intern st=new intern(jsonObject.getString("Name"),jsonObject.getString("Roll"),jsonObject.getString("Email"),
                      jsonObject.getString("Mobile"),jsonObject.getString("Duration"),jsonObject.getString("Mentor"));
                arrayList.add(st);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid){




        collapsinglistitemforintern adapter=new collapsinglistitemforintern(context,arrayList);
        expandableListView.setAdapter(adapter);
        super.onPostExecute(aVoid);
    }
}
