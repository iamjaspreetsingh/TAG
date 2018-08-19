package com.jskgmail.indiaskills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    String url="http://staging.tagusp.com/api/users/TestDetail";
  //  final boolean[] processing = {true};

    String testName;
    String industry;
    String course;
    int questionCount;
    int totalMark;
    String testType;
    String testPrice;
    String testValidity;
    String testDuration;
    String testDetails;
    String testDescriptions;
    String endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



Toast.makeText(getApplicationContext(),MainActivity.online+"",Toast.LENGTH_LONG).show();

      if (MainActivity.online) {
            AddGeofencebody(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey);
        }
       else
        {


            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            Type type1 = new TypeToken<ArrayList<ArrayList<String>>>() {
            }.getType();
            Type type2 = new TypeToken<ArrayList<ArrayList<ArrayList<String>>>>() {
            }.getType();
            Gson gson = new Gson();


            DatabaseHandleroff db = new DatabaseHandleroff(getApplicationContext());
            String test_det_str = null,test_q_str=null,test_ans_str=null,instr = null;

            ArrayList<String> testdet=new ArrayList<>();

            List<TestDetailoff> contacts = db.getAllContacts();
            for (TestDetailoff cn : contacts) {
                test_det_str = cn.getTestDetailss_array();

                testdet = gson.fromJson(test_det_str, type);

                if(testdet.get(0).equals(MyTEST_IDs.unique_id_selected))
                {
                    test_q_str = cn.getArrayList_3_all_questions();
                    test_ans_str = cn.getArrayList_3_all_options();
                    instr = cn.getInstructionList();













                     testName=testdet.get(1);
                     industry=testdet.get(2);
                     course=testdet.get(3);
                     questionCount= Integer.parseInt(testdet.get(4));
                     totalMark= Integer.parseInt(testdet.get(5));
                     testType=testdet.get(6);
                     testPrice=testdet.get(7);
                     testValidity=testdet.get(8);
                     testDuration=testdet.get(9);
                     testDetails=testdet.get(10);
                     testDescriptions=testdet.get(11);
                     endTime=testdet.get(12);

                    Resume();

                }


            }
  //          ArrayList<ArrayList<String>> testdet1 = gson.fromJson(test_q_str, type1);
//            ArrayList<ArrayList<ArrayList<String>>> testdet2 = gson.fromJson(test_ans_str, type2);

            //    Log.e("jjjjjjjjjj", String.valueOf(testdet));
            //   Log.e("jjjjjjjjjjaa", String.valueOf(testdet1));
            //      Log.e("jjjjjjjjjjbb", String.valueOf(testdet2));
            //       Log.e("jjjjjjjjjjccc", String.valueOf(instr));




        }





























        ((Button)findViewById(R.id.cntinue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main3Activity.this,Main4Activity.class));
            }
        });




    }



    // disallow cancel of AlertDialog on click of back button and outside touch
 //   alert.setTitle("Password ");




    protected void AddGeofencebody(String testid,String userid,String api_key) {


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading, null);
        //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
        AlertDialog.Builder alert= new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);



        final AlertDialog dialog= alert.create();
        dialog.show();




        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);

        Log.e("params :",params.toString());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Data response", String.valueOf(response.toString()));
               // Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();

                try {
                    response=response.getJSONObject("test");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    testName=(String)response.get("testName");
                     industry=(String)response.get("industry");
                     course=(String)response.get("course");
                     questionCount=(int)  response.get("questionCount");
                     totalMark=(int)response.get("totalMark");
                     testType=(String)response.get("testType");
                     testPrice=(String)response.get("testPrice");
                     testValidity=(String)response.get("testValidity");
                     testDuration=(String)response.get("testDuration");
                     testDetails=(String)response.get("testDetails");
                     testDescriptions=(String)response.get("testDescriptions");
                     endTime=(String)response.get("endTime");
//processing[0] =false;
                    Toast.makeText(getApplicationContext(),"success"+testName+industry,Toast.LENGTH_SHORT).show();
            //   handler=new Handler(callback);
                //    Message msg = null;
                 //   handler.handleMessage(msg);
                    Resume();
                    dialog.cancel();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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



 public void Resume()
{
    ((TextView) findViewById(R.id.testname)).setText(testName+"");
    ((TextView) findViewById(R.id.industry)).setText(industry+"");
    ((TextView) findViewById(R.id.course)).setText(course+"");
    ((TextView) findViewById(R.id.qcount)).setText(questionCount+"");
    ((TextView) findViewById(R.id.totalmark)).setText(totalMark+"");
    ((TextView) findViewById(R.id.testtype)).setText(testType+"");
    ((TextView) findViewById(R.id.testprice)).setText(testPrice+"");
    ((TextView) findViewById(R.id.testvalidity)).setText(testValidity+"");
    ((TextView) findViewById(R.id.testduration)).setText(testDuration+"");
    ((TextView) findViewById(R.id.endtime)).setText(endTime+"");


}


        //    handler.sendEmptyMessage(0);


}
