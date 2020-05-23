package com.example.nagar.onlinetest;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addrules extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    EditText editText15;
    EditText editText16;
    EditText editText19;
    EditText editText17;
    int p=0;
    String FUSN;
    String STIME;
    String ETIME;
    ProgressDialog progressDialog;
    String QUES;



    public void publish(View view)
    {
        String name = editText19.getText().toString();
        String code = editText17.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, " Test name is missing ", Toast.LENGTH_SHORT).show();
        }
        if (code.isEmpty()) {
            Toast.makeText(this, " Test code is missing ", Toast.LENGTH_SHORT).show();
        }
        if (!name.isEmpty() && !code.isEmpty())
        {

            editText19.setText("");
            editText17.setText("");
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fusn", FUSN);

            jsonObject.addProperty("ques",QUES);
            jsonObject.addProperty("stime", "2020-05-21 "+STIME);
            jsonObject.addProperty("etime","2020-05-21 "+ETIME);
            jsonObject.addProperty("tname",name);
            jsonObject.addProperty("tcode",code);
            IRetrofitQuesUpload jsonPostService = ServiceGenerator.createService(IRetrofitQuesUpload.class, "http://ec2-13-233-208-238.ap-south-1.compute.amazonaws.com/");
            Call<Objec> call = jsonPostService.postRawJSON(jsonObject);
            Log.e("call",jsonObject.toString());
            call.enqueue(new Callback<Objec>() {

                @Override
                public void onResponse(Call<Objec> call, Response<Objec> response) {
                    try{
                        progressDialog.dismiss();


                       // Log.e("response-success", response.body().getName());
                        Log.e("response-success", response.body().getStatus());
                        Log.e("response-success", "11111111111111111111111111111111111111111111111111111");
                        if(response.body().getStatus().equals("False")){
                            Toast.makeText(addrules.this, "Rules not added", Toast.LENGTH_SHORT).show();

                        }






                    }catch (Exception e){

                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<Objec> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("response-success", "22222222222222222222222222");
                    Log.e("response-failure", call.toString());
                }

            });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrules);

        editText15 = (EditText)findViewById(R.id.editText15);
        editText16 = (EditText)findViewById(R.id.editText16);
        editText19 = (EditText)findViewById(R.id.editText19);
        editText17 = (EditText)findViewById(R.id.editText17);
        Intent intent=getIntent();
        FUSN=intent.getStringExtra("FUSN");
        QUES=intent.getStringExtra("QUES");


        editText15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p=1;
                android.support.v4.app.DialogFragment timePicker  = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Timer Picker");
            }
        });

        editText16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p=0;
                android.support.v4.app.DialogFragment timePicker  = new TimePickerFragment1();
                timePicker.show(getSupportFragmentManager(),"Timer Picker1");
            }
        });



    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if(p==1)
        {
            editText15.setText("Hour : "+  i  + " Minute : " + i1 );
            STIME= i  + ":" + i1+":00";
        }
       if(p==0)
        {
            editText16.setText("Hour : "+  i  + " Minute : " + i1 );
            ETIME=  i  + ":" + i1+":00";
        }

    }




}
