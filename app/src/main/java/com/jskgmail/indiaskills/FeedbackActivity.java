package com.jskgmail.indiaskills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class FeedbackActivity extends AppCompatActivity {

    String url="http://staging.tagusp.com/api/users/Get_feedback_questions";
    ArrayList<String> arrayList_quest=new ArrayList<String>();
    ArrayList<String> arrayList_quest_id=new ArrayList<String>();

RecyclerView recyclerView;
CustomAdapterfeedback adapter;
Button submit;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
            recyclerView=findViewById(R.id.listview);

            submit=findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.batch_complete, null);
                    final FloatingTextButton complete=alertLayout.findViewById(R.id.fab3);
                    final FloatingTextButton another=alertLayout.findViewById(R.id.fab2);

                    AlertDialog.Builder alert = new AlertDialog.Builder(FeedbackActivity.this);

                    complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(FeedbackActivity.this,TeststartActivity.class));
                        }
                    });

                    another.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(FeedbackActivity.this,QuestionsActivity.class));

                        }
                    });


                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    AlertDialog dialog = alert.create();
                    dialog.show();





















                }
            });


            AddGeofencebody44(MainActivity.userid,MainActivity.apikey);

    }








    protected void AddGeofencebody44(String userid,String apikey)
    {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading, null);
        final TextView text=alertLayout.findViewById(R.id.text);

        text.setText("Preparing the feedback form!");
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);


        // disallow cancel of AlertDialog on click of back button and outside touch
        //   alert.setTitle("Password ");
        //  alert.setIcon(R.drawable.ic_lock_outline_black_24dp);
        final AlertDialog dialog = alert.create();
        dialog.show();










        Map<String, String> params = new HashMap<>();

        params.put("userId", userid);
        params.put("api_key",apikey);

        Log.e("params :",params.toString());
        ListViewAdapteroptions.ans_clicked=new ArrayList<String>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
                dialog.cancel();
                final int[] qno = {0};
                Toast.makeText(getApplicationContext(),"succ submit",Toast.LENGTH_LONG).show();
                JSONArray jsonArray = null;
                try {
                   jsonArray= response.getJSONArray("feedback_question");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JSONObject[] question = {null};
                final String[] ques_str = {null};
                final String[] quest_id = { null };

                assert jsonArray != null;
                for(int i=0;i<jsonArray.length();i++) {
                    if (qno[0] < jsonArray.length()) {

                        try {
                            question[0] = (JSONObject) jsonArray.get(qno[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            assert question[0] != null;

                            ques_str[0] = (String) question[0].get("question");
                            quest_id[0] = (String) question[0].get("id");

                            arrayList_quest.add(String.valueOf(ques_str[0]));
                            arrayList_quest_id.add(String.valueOf(quest_id[0]));
                            //    qu.setText(ques_str[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //    Log.e("qqqqqqq",ques_str);
                    }
                    qno[0]++;
                }
                adapter= new CustomAdapterfeedback(FeedbackActivity.this,arrayList_quest,arrayList_quest_id);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(FeedbackActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.addItemDecoration(new DividerItemDecoration(FeedbackActivity.this, DividerItemDecoration.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                //    Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();
                //     try {
                //      instructionList=(String)response.get("instructionList");

                //    Toast.makeText(getApplicationContext(),"success"+instructionList,Toast.LENGTH_SHORT).show();
                //   handler=new Handler(callback);
                //    Message msg = null;
                //   handler.handleMessage(msg);

                // } catch (JSONException e) {e.printStackTrace();}
                // Store the JSON object in JSON array as objects (For level 1 array element i.e Results)

              //  ListViewAdapterfeed adapter=new ListViewAdapterfeed(FeedbackActivity.this,arrayList_quest,arrayList_quest_id);
               // listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError","Error response", error);
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() {

                HashMap<String, String> headers = new HashMap<>();
                String credentials = "tagusp:t@g$c0re";
                String auth = "Basic"+" "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                //  headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;

            }

        };
        // Adding request to request queue
        String tag_json_obj = "json_obj_req";
        //  VolleyAppController.getInstance().getRequestQueue().getCache().remove(url);
        VolleyAppController.getInstance().addToRequestQueue(request,tag_json_obj);

    }











}
