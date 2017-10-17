package com.sinha.saarth.restmycase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_one);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView= (TextView) findViewById(R.id.descriptionOne);
        Intent intent=getIntent();
        text=intent.getStringExtra("mytext");
        textView.setText(text);
        FloatingActionButton fab_back = (FloatingActionButton) findViewById(R.id.fabBack);
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // fab.setOnClickListener(new View.OnClickListener() {
        //  @Override
        // public void onClick(View view) {
        //     Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show();
        //  }
        //  });
    }

}