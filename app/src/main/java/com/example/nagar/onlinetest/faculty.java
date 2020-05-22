package com.example.nagar.onlinetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class faculty extends AppCompatActivity {

    public void addtest(View view)
    {
        Intent intent = new Intent(getApplicationContext(),addtest.class);
        intent.putExtra("FUSN",FUSN);
        startActivity(intent);
    }
    public static String FUSN="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        Intent intent=getIntent();
        FUSN=intent.getStringExtra("FUSN");
        Log.e("USNNNNNNNN",FUSN);
    }
}
