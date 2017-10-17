package com.sinha.saarth.restmycase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sinha.saarth.restmycase.adapter.MyArreyAdapter;
import com.sinha.saarth.restmycase.model.MyDataModel;
import com.sinha.saarth.restmycase.parser.JSONParser;
import com.sinha.saarth.restmycase.util.InternetConnection;
import com.sinha.saarth.restmycase.util.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArreyAdapter adapter;
    FloatingActionButton fab,fabCivil,fabProperty,fabFamily,fabCriminal,fabBare;
    Animation fabOpen, fabClose,rotateForward,rotateBackward;
    Boolean isOpen=false;
    TextView textFamily,textProperty,textCriminal,textCivil,textBare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Added for custom toolbar
        getSupportActionBar().setTitle("Rest My Case");
        // getSupportActionBar().setIcon(R.drawable.law);
        //for enabling back button
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();

        adapter = new MyArreyAdapter(this, list);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position).getCountry(), Snackbar.LENGTH_LONG).show();
                String text;

                text = list.get(position).getDescription();

                Intent myIntent = new Intent(view.getContext(),DescriptionActivity.class);
                myIntent.putExtra("mytext",text);
                // /startActivityForResult(myIntent,0);
                startActivity(myIntent);
            }
        });


        //Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Load JSON", Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER, 0, 0);
        //toast.show();
        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        fabFamily = (FloatingActionButton) findViewById(R.id.fabFamily);
        fabProperty = (FloatingActionButton) findViewById(R.id.fabProperty);
        fabCivil = (FloatingActionButton) findViewById(R.id.fabCivil);
        fabBare = (FloatingActionButton) findViewById(R.id.fabBareAct);
        fabCriminal = (FloatingActionButton) findViewById(R.id.fabCriminal);
        textCriminal=(TextView)findViewById(R.id.textCriminal);
        textFamily=(TextView)findViewById(R.id.textFamily);
        textProperty=(TextView)findViewById(R.id.textProperty);
        textCivil=(TextView)findViewById(R.id.textCivil);
        textBare=(TextView)findViewById(R.id.textBare);


        fabOpen= AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose=AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward=AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward=AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                animateFab();

                //if (InternetConnection.checkConnection(getApplicationContext())) {
                  //  new GetDataTask().execute();
                //} else {
                  //  Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
               // }
            }
        });
        fabBare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                  new GetDataTask().execute();
                } else {
                  Snackbar.make(v, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                 }
            }
        });
    }
    private void animateFab()
    {
        if(isOpen)
        {

            fab.startAnimation(rotateForward);
            fabBare.startAnimation(fabClose);
            fabFamily.startAnimation(fabClose);
            fabCriminal.startAnimation(fabClose);
            fabCivil.startAnimation(fabClose);
            fabProperty.startAnimation(fabClose);
            textFamily.startAnimation(fabClose);
            textBare.startAnimation(fabClose);
            textProperty.startAnimation(fabClose);
            textCivil.startAnimation(fabClose);
            textCriminal.startAnimation(fabClose);
            fabBare.setClickable(false);
            fabFamily.setClickable(false);
            fabCriminal.setClickable(false);
            fabCivil.setClickable(false);
            fabProperty.setClickable(false);
            isOpen=false;

        }
        else{
            fab.startAnimation(rotateBackward);
            fabBare.startAnimation(fabOpen);
            fabFamily.startAnimation(fabOpen);
            fabCriminal.startAnimation(fabOpen);
            fabCivil.startAnimation(fabOpen);
            fabProperty.startAnimation(fabOpen);
            textFamily.startAnimation(fabOpen);
            textBare.startAnimation(fabOpen);
            textProperty.startAnimation(fabOpen);
            textCivil.startAnimation(fabOpen);
            textCriminal.startAnimation(fabOpen);
            fabBare.setClickable(true);
            fabFamily.setClickable(true);
            fabCriminal.setClickable(true);
            fabCivil.setClickable(true);
            fabProperty.setClickable(true);
            isOpen=true;

        }
    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // x=list.size();

            //if(x==0)
              //  jIndex=0;
            //else
              //  jIndex=x;

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Please Wait...");
            dialog.setMessage("Slow Internet Connection");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = JSONParser.getDataFromWeb();

            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);


                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                MyDataModel model = new MyDataModel();

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String section = innerObject.getString(Keys.KEY_SECTION);
                                String definition = innerObject.getString(Keys.KEY_DEFINITION);
                                String description=innerObject.getString(Keys.KEY_DESCRIPTION);

                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);


                                model.setSection(section);
                                model.setDefinition(definition);
                                model.setDescription(description);

                                list.add(model);
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}