package com.jskgmail.indiaskills;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
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
        Map<String, String> params = new HashMap<>();

        params.put("userId", userid);
        params.put("api_key",apikey);

        Log.e("params :",params.toString());
        ListViewAdapteroptions.ans_clicked=new ArrayList<String>();

        final TextView qu=findViewById(R.id.quest);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
                Toast.makeText(getApplicationContext(),"succ submit",Toast.LENGTH_LONG).show();
                JSONArray jsonArray = null;
                try {
                   jsonArray= response.getJSONArray("feedback_question");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
JSONObject question = null;
                String ques_str = null,quest_id=null;

                assert jsonArray != null;
                for (int i = 0; i<jsonArray.length(); i++)
                {

                    try {
                        question= (JSONObject) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        assert question != null;
                        ques_str= (String) question.get("question");
                     quest_id=(String) question.get("id");
                     arrayList_quest.add(ques_str);
                     arrayList_quest_id.add(quest_id);
                      //  qu.setText(ques_str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

                ListViewAdapterfeed adapter=new ListViewAdapterfeed(FeedbackActivity.this,arrayList_quest,arrayList_quest_id);
                listView.setAdapter(adapter);
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
