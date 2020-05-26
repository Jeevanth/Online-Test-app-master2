package com.example.nagar.onlinetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addtest extends AppCompatActivity {
    public static String QuesBuffer="";

    public void mcq(View view)
    {

        Intent intent = new Intent(getApplicationContext(), addMcq.class);
        startActivity(intent);
    }

    public void descriptive(View view)
    {
        Intent intent = new Intent(getApplicationContext(),descriptive.class);
        startActivity(intent);
    }
    public void next(View view)
    {

        Intent intent = new Intent(getApplicationContext(),addrules.class);
        intent.putExtra("QUES",QuesBuffer);
        intent.putExtra("FUSN",FUSN);
        startActivity(intent);
    }
    String ques="" ;
    String optA="" ;
    String optB="" ;
    String optC="" ;
    String optD="" ;
    String ans="" ;
    String FUSN;
    static  String[] ListElements = new String[]{"Questions : "};
    ListView listview;
    int i=0;
    static  List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));

    int k=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtest);
        listview = findViewById(R.id.listView1);

        Intent intent=getIntent();
        ques=intent.getStringExtra("ques");
        optA=intent.getStringExtra("optA");
        optB=intent.getStringExtra("optB");
        optC=intent.getStringExtra("optC");
        optD=intent.getStringExtra("optD");
        ans=intent.getStringExtra("ans");
        //FUSN=intent.getStringExtra("FUSN");
        FUSN=faculty.FUSN;
        Log.e("USNNNNNNNNNNNNNAddTest",FUSN);



        if(ques!=null&&optA!=null){
            QuesBuffer=QuesBuffer+ques+"~`#^"+optA+",,,"+optB+",,,"+optC+",,,"+optD+",,,"+ans+"~`^";
        }
        else if(ques!=null){
            QuesBuffer=QuesBuffer+ques+"~`^";
        }

        Log.e("Successss",QuesBuffer);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(addtest.this,android.R.layout.simple_list_item_1, ListElementsArrayList);
        listview.setAdapter(adapter);


        if(ques!=null) {
            k++;

            ListElementsArrayList.add(k+") "+ques);

            adapter.notifyDataSetChanged();
        }

    }
}
