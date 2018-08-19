package com.jskgmail.indiaskills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class BatchListActivity extends AppCompatActivity {
String url="http://staging.tagusp.com/api/users/BatchList";
ListView listView;

    RecyclerView recyclerView;
    CustomAdapterbatch adapter;

    static  ArrayList<String> login_name_arr=new ArrayList<>(),login_username_arr=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);
        recyclerView=findViewById(R.id.recyclerview);

        //listView=findViewById(R.id.listview);
        recyclervie(MainActivity.userid,MainActivity.apikey);
        Button next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BatchListActivity.this,QuestionsActivity.class));
            }
        });
      //  AddGeofencebody(MainActivity.userid,MainActivity.apikey);
    }




    protected void AddGeofencebody(final String name, final String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", name);
        params.put("api_key", password);
        Log.e("params :",params.toString());

         login_name_arr=new ArrayList<>();login_username_arr=new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Data response", String.valueOf(response.toString()));
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                String batchId,batchName,uniqueID,testID,login_name,login_username,login_email;

                try {

                    JSONArray array=response.getJSONArray("batchList");
                    JSONObject jsonobj_2 = (JSONObject) array.get(0);

                    batchId=(String)jsonobj_2.get("batchId");
                    batchName=(String)jsonobj_2.get("batchName");

                    uniqueID=(String)jsonobj_2.get("uniqueID");
                    testID=(String)jsonobj_2.get("testID");

                    Log.e("arrgrrrruserss", batchName);
                    Log.e("arrgrapikjj", batchId);



                    JSONArray userList=jsonobj_2.getJSONArray("userList");
                    JSONObject batch ;

                    for (int i=0;i<userList.length();i++) {
                        batch=userList.getJSONObject(i);
                        login_name = (String) batch.get("name");
                        login_username  = (String) batch.get("username");
                        login_email = (String) batch.get("email");



                        login_name_arr.add(login_name);
                        login_username_arr.add(login_username);
                    }

                  //  Log.e("arrgrapikjjloginnaa", String.valueOf(login_name_arr));

                    ListViewAdapterBatch adapter = new ListViewAdapterBatch(BatchListActivity.this, login_name_arr, login_username_arr);
                    listView.setAdapter(adapter);


                    //    Toast.makeText(getApplicationContext(),jsonobj_2.toString(),Toast.LENGTH_LONG).show();

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




void recyclervie(final String name, final String password) {




    Map<String, String> params = new HashMap<>();
    params.put("userId", name);
    params.put("api_key", password);
    Log.e("params :",params.toString());

    login_name_arr=new ArrayList<>();login_username_arr=new ArrayList<>();

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
            //   null
            , new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.e("Data response", String.valueOf(response.toString()));
            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            String batchId,batchName,uniqueID,testID,login_name,login_username,login_email;

            try {

                JSONArray array=response.getJSONArray("batchList");
                JSONObject jsonobj_2 = (JSONObject) array.get(0);

                batchId=(String)jsonobj_2.get("batchId");
                batchName=(String)jsonobj_2.get("batchName");

                uniqueID=(String)jsonobj_2.get("uniqueID");
                testID=(String)jsonobj_2.get("testID");

                Log.e("arrgrrrruserss", batchName);
                Log.e("arrgrapikjj", batchId);



                JSONArray userList=jsonobj_2.getJSONArray("userList");
                JSONObject batch ;

                for (int i=0;i<userList.length();i++) {
                    batch=userList.getJSONObject(i);
                    login_name = (String) batch.get("name");
                    login_username  = (String) batch.get("username");
                    login_email = (String) batch.get("email");



                    login_name_arr.add(login_name);
                    login_username_arr.add(login_username);
                }
                adapter= new CustomAdapterbatch(BatchListActivity.this,login_name_arr,login_username_arr);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(BatchListActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.addItemDecoration(new DividerItemDecoration(BatchListActivity.this, DividerItemDecoration.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //  Log.e("arrgrapikjjloginnaa", String.valueOf(login_name_arr));


                //    Toast.makeText(getApplicationContext(),jsonobj_2.toString(),Toast.LENGTH_LONG).show();

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





}
