package com.example.nagar.onlinetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class faculty extends AppCompatActivity {
    private TextView textViewResult;
    ProgressDialog progressDialog;

    public void addtest(View view)
    {
        Intent intent = new Intent(getApplicationContext(),addtest.class);
        intent.putExtra("FUSN",FUSN);

        startActivity(intent);
    }
    public static String FUSN="";
    ListView list,list2;
    private static ArrayAdapter<String> adapter;
    private static ArrayAdapter<String> adaptter2;
    private static ArrayList<String> arrayList;
    private static ArrayList<String> arrayList2;
    List tid;
    int k=1;

    List<String> ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        Intent intent=getIntent();
        FUSN=intent.getStringExtra("FUSN");

        list = (ListView) findViewById(R.id.Listview);
        list2=(ListView)findViewById(R.id.Listview1);
        arrayList2=new ArrayList<String>();
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        adaptter2=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList2);
        list2.setAdapter(adaptter2);
        list.setAdapter(adapter);
        arrayList.add("Finished Tests :");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        tid=new ArrayList<String>();
       ques=new ArrayList<String>();
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }




        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fusn", FUSN);



        IRetrofitTestList jsonPostService = ServiceGenerator.createService(IRetrofitTestList.class, "http://ec2-13-233-208-238.ap-south-1.compute.amazonaws.com/");
        Call<List<FacultyTestQuesList>> call = jsonPostService.postRawJSON(jsonObject);
        Log.e("call",jsonObject.toString());
        call.enqueue(new Callback<List<FacultyTestQuesList>>() {

            @Override
            public void onResponse(Call<List<FacultyTestQuesList>> call, Response<List<FacultyTestQuesList>> response) {
                try{
                    progressDialog.dismiss();
                    List<FacultyTestQuesList> list1 = response.body();

                    int j = 0;
                    for(FacultyTestQuesList i:list1){
                        if(i.getTid().equals("000")){
                            Toast.makeText(faculty.this, "No Tests available", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else {
                            ques.add(i.getTname());
                            String end_time=i.getEtime();
                            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end_time);


                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date();
                            String d;
                            d=formatter.format(date);
                            Date date2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
                            System.out.println(date2);

                            long duration  = date2.getTime() - date1.getTime();
                            if(duration<0){
                                arrayList2.add(k+") "+i.getTname());
                                k++;
                                adaptter2.notifyDataSetChanged();

                                continue;


                            }

                            j=j+1;
                            tid.add(i.getTid());
                            /*String content ="Quetions :\n";
                            int j=1;
                            content=content+j+") "+i.getQns()+"\n";
                            textViewResult.append(content);*/
                            arrayList.add(j+") "+i.getTname());
                            adapter.notifyDataSetChanged();



                        }


                    }

                }catch (Exception e){

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<FacultyTestQuesList>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("response-success", "22222222222222222222222222");
                Log.e("response-failure", call.toString());
            }

        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                //Dessert dessert = desserts.get(i);
                //Toast.makeText(faculty.this, tid.get(i-1).toString(), Toast.LENGTH_SHORT).show();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("tid", tid.get(i-1).toString());
                IRetrofitEmail jsonPostService = ServiceGenerator.createService(IRetrofitEmail.class, "http://ec2-13-233-208-238.ap-south-1.compute.amazonaws.com/");
                Call<Objec> call = jsonPostService.postRawJSON(jsonObject);
                Log.e("call",jsonObject.toString());
                call.enqueue(new Callback<Objec>() {

                    @Override
                    public void onResponse(Call<Objec> call, Response<Objec> response) {
                        try{
                            progressDialog.dismiss();
                            if(response.body().getSt().equals("True")){
                                Toast.makeText(faculty.this, "The Results of "+ques.get(i-1).toString()+" has been emailed to you", Toast.LENGTH_SHORT).show();


                            }


                        }catch (Exception e){

                            e.printStackTrace();
                            return;
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
        });
    }
}
