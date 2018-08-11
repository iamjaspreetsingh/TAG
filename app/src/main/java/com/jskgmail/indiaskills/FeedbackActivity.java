package com.jskgmail.indiaskills;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
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

ListView listView;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
            listView=findViewById(R.id.listview);

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

        final TextView qu=findViewById(R.id.quest);
        final FloatingTextButton next=findViewById(R.id.nextt);
        final FloatingTextButton pre=findViewById(R.id.back);

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

                if( qno[0] <jsonArray.length())
                {

                    try {
                        question[0] = (JSONObject) jsonArray.get(qno[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        assert question[0] != null;
                        ques_str[0] = (String) question[0].get("question");
                     quest_id[0] =(String) question[0].get("id");
                  //   arrayList_quest.add(ques_str);
                  //   arrayList_quest_id.add(quest_id);
                       qu.setText(ques_str[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final JSONArray finalJsonArray = jsonArray;
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if( qno[0] < finalJsonArray.length())
                            {
                                qno[0]++;

                                try {
                                    question[0] = (JSONObject) finalJsonArray.get(qno[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    assert question[0] != null;
                                    ques_str[0] = (String) question[0].get("question");
                                    quest_id[0] =(String) question[0].get("id");
                                    //   arrayList_quest.add(ques_str);
                                    //   arrayList_quest_id.add(quest_id);
                                    qu.setText(ques_str[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                //    Log.e("qqqqqqq",ques_str);
                            }


                            if( qno[0] == finalJsonArray.length())
                            {
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(),"Your feedback has been successfully submitted! Please login again for another test.",Snackbar.LENGTH_LONG).show();
                                        startActivity(new Intent(FeedbackActivity.this,MainActivity.class));
                                    }
                                });
                            }







                        }
                    });




                    pre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if( qno[0] >0)
                            {
                                qno[0]--;

                                try {
                                    question[0] = (JSONObject) finalJsonArray.get(qno[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    assert question[0] != null;
                                    ques_str[0] = (String) question[0].get("question");
                                    quest_id[0] =(String) question[0].get("id");
                                    //   arrayList_quest.add(ques_str);
                                    //   arrayList_quest_id.add(quest_id);
                                    qu.setText(ques_str[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                //    Log.e("qqqqqqq",ques_str);
                            }
                        }
                    });

                //    Log.e("qqqqqqq",ques_str);
                }
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
