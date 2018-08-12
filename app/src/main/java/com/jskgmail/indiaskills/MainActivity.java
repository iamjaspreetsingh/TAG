package com.jskgmail.indiaskills;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
   static ArrayList<String> arrayList_test_name;
   static ArrayList<String> arrayList_test_id;
    static ArrayList<String> arrayList_u_test_id;

    static boolean online=true;
    static  String u_test_id,test_id,type;
    private Button login;
    static String login_name,login_email,login_phone,userid,apikey,login_role;
    private String deviceid;
    String TAG = "MAINACTIVITY";
    String url="http://staging.tagusp.com/api/users/login";
    String url1="http://staging.tagusp.com/api/users/TestList";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

online=isNetworkAvailable();
         arrayList_test_name=new ArrayList<>();
         arrayList_test_id=new ArrayList<>();
         arrayList_u_test_id=new ArrayList<>();


        deviceid = getdeviceID();
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGeofencebody("twgzl","94588");
//AddGeofencebody1();
            }
        });


    }


    protected void AddGeofencebody1() {


        //    Log.e("params :",params.toString());
        String ur2l1="http://192.168.4.1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,ur2l1, null
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Data response", String.valueOf(response.toString()));
                try {
                  //  Toast.makeText(getApplicationContext(),response.toString()+"yo",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), (String)response.get("test"),Toast.LENGTH_LONG).show();
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


        };
        // Adding request to request queue
        String tag_json_obj = "json_obj_req";
        //  VolleyAppController.getInstance().getRequestQueue().getCache().remove(url);
        VolleyAppController.getInstance().addToRequestQueue(request,tag_json_obj);

    }






    protected void AddGeofencebody(String name,String password) {
            Map<String, String> params = new HashMap<>();
            params.put("username", name);
            params.put("password", password);
            Log.e("params :",params.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                    //   null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Data response", String.valueOf(response.toString()));
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

                    try {
                         userid=(String)response.get("userID");
                         apikey=(String)response.get("api_key");
                        Log.e("arrgrrrruser", userid);
                        Log.e("arrgrapik", apikey);

                        JSONArray array=response.getJSONArray("profile");
                        JSONObject jsonobj_2 = (JSONObject) array.get(0);
                         login_name = (String) jsonobj_2.get("name");
                         login_email=(String)jsonobj_2.get("email");
                         login_phone=(String)jsonobj_2.get("phone");
                         login_role=(String)jsonobj_2.get("role");

                     //    Toast.makeText(getApplicationContext(),jsonobj_2.toString(),Toast.LENGTH_LONG).show();
                        Testdetail(apikey,userid);

                        Log.e("arrgrrr", login_name);

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





    String getdeviceID() {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }

            return telephonyManager.getDeviceId();












    }









    protected void Testdetail(String apikey,String userid) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", apikey);
        params.put("userId", userid);
        Log.e("params :",params.toString());
        arrayList_test_id=new ArrayList<>();
        arrayList_u_test_id=new ArrayList<>();
        arrayList_test_name=new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url1, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Data response", String.valueOf(response.toString()));
                Toast.makeText(getApplicationContext(),"succes1s",Toast.LENGTH_SHORT).show();


                try {
                    JSONArray array=response.getJSONArray("testList");

for (int i=0;i<array.length();i++) {
    JSONObject jsonobj_2 = (JSONObject) array.get(i);
     test_id = (String) jsonobj_2.get("id");
     u_test_id = (String) jsonobj_2.get("uniqueID");
    String purchasedTime = (String) jsonobj_2.get("purchasedTime");
    String testName = (String) jsonobj_2.get("testName");

    arrayList_test_id.add(test_id);
    arrayList_u_test_id.add(u_test_id);
    arrayList_test_name.add(testName);


    if (i==array.length()-1)
    {
        startActivity(new Intent(MainActivity.this, Main5Activity.class));
        Log.e("ttttttteeeeeeeeesssst",arrayList_test_name.toString());

    }


}

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












    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
