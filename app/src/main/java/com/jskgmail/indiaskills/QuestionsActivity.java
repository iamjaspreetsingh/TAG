package com.jskgmail.indiaskills;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
String url="http://staging.tagusp.com/api/users/GetQuestion";
    String url1="http://staging.tagusp.com/api/users/SaveAnswer";
    String url2="http://staging.tagusp.com/api/users/TestSubmit";

    int total_no_of_quest=6;
TextView ques_textview;
Button next,prev;
ImageView q_img;
ListView List_options;
    CenterLockHorizontalScrollview centerLockHorizontalScrollview;
    CustomListAdapter customListAdapter;
    Button btnPrev, btnNext;
    int currIndex = 0;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ques_textview=findViewById(R.id.ques);
        next=findViewById(R.id.nxt);
        prev=findViewById(R.id.prv);
        List_options=findViewById(R.id.lv_options);
        q_img=findViewById(R.id.video);

      /*  ListView lv=findViewById(R.id.lv);
        lv.setRotation(90);
        ArrayList<String> arrayList1=new ArrayList<>();

        arrayList1.add("1");
        arrayList1.add("2");

        arrayList1.add("3");
        arrayList1.add("4");

        QuestionListAdapter adapter=new QuestionListAdapter(this,arrayList1);
        lv.setAdapter(adapter);
*/

        AddGeofencebody1(Main2Activity.test_id_selected, MainActivity.userid, MainActivity.apikey,Main4Activity.languageCode,Main2Activity.unique_id_selected);


    }









    protected void AddGeofencebody1(final String testid, final String userid, final String api_key, String langcode, final String u_test_id) {


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading, null);
        final TextView text=alertLayout.findViewById(R.id.text);

        text.setText("Loading the questions");
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);


        // disallow cancel of AlertDialog on click of back button and outside touch
        //   alert.setTitle("Password ");
        //  alert.setIcon(R.drawable.ic_lock_outline_black_24dp);
        final AlertDialog dialog = alert.create();
        dialog.show();








    final TextView timeremaining=findViewById(R.id.t);
    final TextView bookmark=findViewById(R.id.bookmark);

final TextView qno=findViewById(R.id.qno);

        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("languageCode",langcode);
        params.put("uniqueId",u_test_id);

        Log.e("params :",params.toString());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
                try {
                    final JSONArray questionlist= (JSONArray) response.get("question");
                     total_no_of_quest=questionlist.length();

              //question ki list at upper

                    final ArrayList<String> q_list = new ArrayList<String>() ;
                    for (int q=1;q<=total_no_of_quest;q++)
                    {
                        q_list.add(q+"");

                    }
                    dialog.cancel();
                    centerLockHorizontalScrollview = (CenterLockHorizontalScrollview) findViewById(R.id.scrollView);

                    customListAdapter = new CustomListAdapter(getApplicationContext(),
                            R.layout.news_list_item, q_list);
                    centerLockHorizontalScrollview.setAdapter(getApplicationContext(), customListAdapter);
                    if (currIndex < q_list.size()) {
                        centerLockHorizontalScrollview.setCenter(currIndex,0);
                        currIndex++;
                        //    text.setText(list.get(currIndex-1));
                    }







                    final int[] i = {0};
                    {


                        new CountDownTimer(30000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                timeremaining.setText("TIME REMAINING: " + millisUntilFinished / 1000);
                                //here you can have your logic to set text to edittext
                            }

                            public void onFinish() {
                                timeremaining.setText("TIME FINISHED !");
                            }

                        }.start();

                        ArrayList<String> arrayList_img=new ArrayList<>();
                        ArrayList<String> arrayList_vid=new ArrayList<>();
                        ArrayList<String> arrayList_ans_id=new ArrayList<>();
                        ArrayList<String> arrayList_ans_value=new ArrayList<>();

                        qno.setText((int)(i[0]+1)+".)");

                        JSONObject question = (JSONObject) questionlist.get(i[0]);
                        String quest= (String) question.get("question");
                        final String quest_id=(String) question.get("id");

                        String questvid= (String) question.get("questionVedio");
                        String questimg= (String) question.get("questionImage");
                        if (questvid.equals("")&&(questimg.equals("")))
                        {
                            q_img.setVisibility(View.INVISIBLE);

                        }

                        else {
                            q_img.setVisibility(View.VISIBLE);
                            //Picasso.get().load(questimg).into(q_img);
                            Glide.with(getApplicationContext()).load(questimg).into(q_img);


                        }

                        bookmark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                centerLockHorizontalScrollview.setBookmark(currIndex,1);
                                AddGeofencebody22(testid,userid,api_key,quest_id,Main2Activity.unique_id_selected,"10","P");
                            }
                        });


                        ques_textview.setText(quest);
                        JSONArray answersArray=question.getJSONArray("answer");
                        for (int ii=0;ii<answersArray.length();ii++)
                        {
                            JSONObject option= (JSONObject) answersArray.get(ii);

                            String img= (String) option.get("answerImage");
                            String video= (String) option.get("answerVideo");
                            String ans_id= (String) option.get("id");
                            String ans_value= (String) option.get("value");

                            arrayList_ans_id.add(ans_id);
                            arrayList_ans_value.add(ans_value);
                            arrayList_img.add(img);
                            arrayList_vid.add(video);

                        }
                        ListViewAdapteroptions adapter=new ListViewAdapteroptions(QuestionsActivity.this,arrayList_ans_value,arrayList_img,arrayList_vid,arrayList_ans_id);
                        List_options.setAdapter(adapter);




                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (currIndex < q_list.size()) {
                                    centerLockHorizontalScrollview.setCenter(currIndex,0);
                                    currIndex++;
                                //    text.setText(list.get(currIndex-1));
                                }

                                ArrayList<String> arrayList_img=new ArrayList<>();
                                ArrayList<String> arrayList_vid=new ArrayList<>();
                                ArrayList<String> arrayList_ans_id=new ArrayList<>();
                                ArrayList<String> arrayList_ans_value=new ArrayList<>();


                                if (i[0]<total_no_of_quest-1)
                                {


                                    i[0]++;
                                    qno.setText((int)(i[0]+1)+".)");
                                JSONObject question = null;

                                try {
                                    question = (JSONObject) questionlist.get(i[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String quest= null;
                                    String quest_id= null,questvid = null,questimg=null;

                                    String ansid="";
                                    try {
                                        quest = (String) question.get("question");
                                         questvid= (String) question.get("questionVedio");
                                         questimg= (String) question.get("questionImage");
                                        quest_id=(String) question.get("id");





                                        JSONArray answersArray=question.getJSONArray("answer");
                                        for (int i=0;i<answersArray.length();i++)
                                        {
                                            JSONObject option= (JSONObject) answersArray.get(i);

                                            String img= (String) option.get("answerImage");
                                            String video= (String) option.get("answerVideo");
                                            String ans_id= (String) option.get("id");
                                            String ans_value= (String) option.get("value");

                                            arrayList_ans_id.add(ans_id);
                                            arrayList_ans_value.add(ans_value);
                                            arrayList_img.add(img);
                                            arrayList_vid.add(video);
                                        }
                                        ListViewAdapteroptions adapter=new ListViewAdapteroptions(QuestionsActivity.this,arrayList_ans_value,arrayList_img,arrayList_vid,arrayList_ans_id);
                                        List_options.setAdapter(adapter);

                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                    if (questvid.equals("")&&(questimg.equals("")))
                                    {
                                        q_img.setVisibility(View.INVISIBLE);

                                    }

                                    else {
                                        q_img.setVisibility(View.VISIBLE);
                                        Glide.with(getApplicationContext()).load(questimg).into(q_img);


                                    }
                                ques_textview.setText(quest);
                                    final String finalQuest_id = quest_id;
                                    bookmark.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            centerLockHorizontalScrollview.setBookmark(currIndex,1);

                                            AddGeofencebody22(testid,userid,api_key, finalQuest_id,Main2Activity.unique_id_selected,"10","P");
                                        }
                                    });

                                  //  Toast.makeText(getApplicationContext(),quest_id,Toast.LENGTH_LONG).show();

                                    if (i[0]==total_no_of_quest-2)
                                    {
                                        next.setText("Submit");
                                        AddGeofencebody44(testid,userid,api_key,Main4Activity.latitude+":"+Main4Activity.longitude,Main2Activity.unique_id_selected,"dd","P");

startActivity(new Intent(QuestionsActivity.this,FeedbackActivity.class));
                                    }
                                   else

                                    AddGeofencebody11(testid,userid,api_key,quest_id,Main2Activity.unique_id_selected,ListViewAdapteroptions.ans_clicked,"P");

                            }
                            }
                        });

                        prev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                if (currIndex != 0) {
                                    currIndex--;
                                    centerLockHorizontalScrollview.setCenter(currIndex,0);
                                  //  text.setText(list.get(currIndex==0?0:currIndex-1));
                                }



                                 ArrayList<String> arrayList_img=new ArrayList<>();
                                 ArrayList<String> arrayList_vid=new ArrayList<>();
                                 ArrayList<String> arrayList_ans_id=new ArrayList<>();
                                ArrayList<String> arrayList_ans_value=new ArrayList<>();

                                if (i[0]>0)
                                {

                                i[0]--;
                                    qno.setText((int)(i[0]+1)+".)");

                                    JSONObject question = null;
                                try {
                                    question = (JSONObject) questionlist.get(i[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String quest= null, quest_id= null,questvid = null,questimg=null;
                                    ;
                                try {
                                    quest = (String) question.get("question");
                                    questvid= (String) question.get("questionVedio");
                                    quest_id=(String) question.get("id");
                                    questimg= (String) question.get("questionImage");
                                    JSONArray answersArray=question.getJSONArray("answer");
                                    for (int i=0;i<answersArray.length();i++)
                                    {
                                        JSONObject option= (JSONObject) answersArray.get(i);

                                        String img= (String) option.get("answerImage");
                                        String video= (String) option.get("answerVideo");
                                        String ans_id= (String) option.get("id");
                                        String ans_value= (String) option.get("value");

                                        arrayList_ans_id.add(ans_id);
                                        arrayList_ans_value.add(ans_value);
                                        arrayList_img.add(img);
                                        arrayList_vid.add(video);

                                    }
                                    ListViewAdapteroptions adapter=new ListViewAdapteroptions(QuestionsActivity.this,arrayList_ans_value,arrayList_img,arrayList_vid,arrayList_ans_id);
                                    List_options.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                    if (questvid.equals("")&&(questimg.equals("")))
                                    {
                                        q_img.setVisibility(View.INVISIBLE);

                                    }

                                    else {
                                        q_img.setVisibility(View.VISIBLE);
                                        Glide.with(getApplicationContext()).load(questimg).into(q_img);


                                    }


                                    final String finalQuest_id = quest_id;
                                    bookmark.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                                centerLockHorizontalScrollview.setBookmark(currIndex,1);
                                                //  text.setText(list.get(currIndex==0?0:currIndex-1));

                                            AddGeofencebody22(testid,userid,api_key, finalQuest_id,Main2Activity.unique_id_selected,"10","P");
                                        }
                                    });

                                    ques_textview.setText(quest);

                                    AddGeofencebody11(testid,userid,api_key,quest_id,Main2Activity.unique_id_selected,ListViewAdapteroptions.ans_clicked,"P");



                                }
                            }
                        });
                        Log.e("ssssssss",quest);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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




























//Saveanser



    protected void AddGeofencebody11(String testid,String userid,String api_key,String q_id,String u_test_id,ArrayList<String> ans_id,String ver) {

        final TextView qno=findViewById(R.id.qno);
Log.e("ccccaaaaaaaasds", String.valueOf(ans_id));

        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("questionID",q_id);
        params.put("answerID", String.valueOf(ans_id));
        params.put("version",ver);
        params.put("uniqueID",u_test_id);

        Log.e("params :",params.toString());
        ListViewAdapteroptions.ans_clicked=new ArrayList<String>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
              Toast.makeText(getApplicationContext(),"succ answer",Toast.LENGTH_LONG).show();

                //    Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();
                //     try {
                //      instructionList=(String)response.get("instructionList");

                //    Toast.makeText(getApplicationContext(),"success"+instructionList,Toast.LENGTH_SHORT).show();
                //   handler=new Handler(callback);
                //    Message msg = null;
                //   handler.handleMessage(msg);

                // } catch (JSONException e) {e.printStackTrace();}
                // Store the JSON object in JSON array as objects (For level 1 array element i.e Results)

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








    //bookmarker...............





    protected void AddGeofencebody22(String testid,String userid,String api_key,String q_id,String u_test_id,String bookmark_time,String ver) {

        final TextView qno=findViewById(R.id.qno);
        //Log.e("ccccaaaaaaaasds", String.valueOf(ans_id));

        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("questionID",q_id);
        params.put("bookmark_time", bookmark_time);
        params.put("version",ver);
        params.put("uniqueID",u_test_id);

        Log.e("params :",params.toString());
        ListViewAdapteroptions.ans_clicked=new ArrayList<String>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
                Toast.makeText(getApplicationContext(),"succ answer",Toast.LENGTH_LONG).show();

                //    Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();
                //     try {
                //      instructionList=(String)response.get("instructionList");

                //    Toast.makeText(getApplicationContext(),"success"+instructionList,Toast.LENGTH_SHORT).show();
                //   handler=new Handler(callback);
                //    Message msg = null;
                //   handler.handleMessage(msg);

                // } catch (JSONException e) {e.printStackTrace();}
                // Store the JSON object in JSON array as objects (For level 1 array element i.e Results)

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

















//for test submit




    protected void AddGeofencebody44(String testid,String userid,String api_key,String endlatlong,String u_test_id,String endlocation,String ver) {

        final TextView qno=findViewById(R.id.qno);
        //Log.e("ccccaaaaaaaasds", String.valueOf(ans_id));

        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("endLatLone",endlatlong);
        params.put("endlocation", endlocation);
        params.put("version",ver);
        params.put("uniqueID",u_test_id);

        Log.e("params :",params.toString());
        ListViewAdapteroptions.ans_clicked=new ArrayList<String>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url2, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
                Toast.makeText(getApplicationContext(),"succ submit",Toast.LENGTH_LONG).show();

                //    Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();
                //     try {
                //      instructionList=(String)response.get("instructionList");

                //    Toast.makeText(getApplicationContext(),"success"+instructionList,Toast.LENGTH_SHORT).show();
                //   handler=new Handler(callback);
                //    Message msg = null;
                //   handler.handleMessage(msg);

                // } catch (JSONException e) {e.printStackTrace();}
                // Store the JSON object in JSON array as objects (For level 1 array element i.e Results)

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
