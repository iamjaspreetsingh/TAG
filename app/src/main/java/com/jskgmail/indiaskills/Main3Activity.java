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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

        AddGeofencebody(Main2Activity.test_id_selected, MainActivity.userid, MainActivity.apikey);

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

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);


        // disallow cancel of AlertDialog on click of back button and outside touch
        //   alert.setTitle("Password ");
        //  alert.setIcon(R.drawable.ic_lock_outline_black_24dp);
        final AlertDialog dialog = alert.create();
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
