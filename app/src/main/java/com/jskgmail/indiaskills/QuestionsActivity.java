package com.jskgmail.indiaskills;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
String url="http://staging.tagusp.com/api/users/GetQuestion";
    String url1="http://staging.tagusp.com/api/users/SaveAnswer";
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









    protected void AddGeofencebody1(final String testid, final String userid, final String api_key, String langcode, String u_test_id) {

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
                    centerLockHorizontalScrollview = (CenterLockHorizontalScrollview) findViewById(R.id.scrollView);

                    customListAdapter = new CustomListAdapter(getApplicationContext(),
                            R.layout.news_list_item, q_list);
                    centerLockHorizontalScrollview.setAdapter(getApplicationContext(), customListAdapter);
                    if (currIndex < q_list.size()) {
                        centerLockHorizontalScrollview.setCenter(currIndex);
                        currIndex++;
                        //    text.setText(list.get(currIndex-1));
                    }







                    final int[] i = {0};
                    {
                        ArrayList<String> arrayList_img=new ArrayList<>();
                        ArrayList<String> arrayList_vid=new ArrayList<>();
                        ArrayList<String> arrayList_ans_id=new ArrayList<>();
                        ArrayList<String> arrayList_ans_value=new ArrayList<>();

                        qno.setText((int)(i[0]+1)+".)");

                        JSONObject question = (JSONObject) questionlist.get(i[0]);
                        String quest= (String) question.get("question");
                        String questvid= (String) question.get("questionVedio");
                        String questimg= (String) question.get("questionImage");
                        if (questvid.equals(""))
                        {
                            q_img.setVisibility(View.GONE);

                        }
                        else if (questimg.equals(""))
                        {
                            q_img.setVisibility(View.GONE);

                        }
                        else
                            q_img.setVisibility(View.VISIBLE);



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
                                    centerLockHorizontalScrollview.setCenter(currIndex);
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
                                    String ansid="";
                                    try {
                                        quest = (String) question.get("question");
                                        //qid=(String) question.get("qid");
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
                                            ansid=ans_id;
                                        }
                                        ListViewAdapteroptions adapter=new ListViewAdapteroptions(QuestionsActivity.this,arrayList_ans_value,arrayList_img,arrayList_vid,arrayList_ans_id);
                                        List_options.setAdapter(adapter);

                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ques_textview.setText(quest);


                                    String q_id="";
                                    AddGeofencebody11(testid,userid,api_key,q_id,Main2Activity.unique_id_selected,ansid,"P");

                            }
                            }
                        });

                        prev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                if (currIndex != 0) {
                                    currIndex--;
                                    centerLockHorizontalScrollview.setCenter(currIndex);
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
                                String quest= null;
                                try {
                                    quest = (String) question.get("question");
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
                                ques_textview.setText(quest);
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



    protected void AddGeofencebody11(String testid,String userid,String api_key,String q_id,String u_test_id,String ans_id,String ver) {

        final TextView qno=findViewById(R.id.qno);

        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("questionID",q_id);
        params.put("answerID",ans_id);
        params.put("version",ver);
        params.put("uniqueID",u_test_id);

        Log.e("params :",params.toString());


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

}
