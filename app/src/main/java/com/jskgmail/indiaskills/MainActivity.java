package com.jskgmail.indiaskills;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String url3="http://staging.tagusp.com/api/users/Forgotpassword";

    private EditText name,pass;
    private TextView forgot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

online=isNetworkAvailable();

         arrayList_test_name=new ArrayList<>();
         arrayList_test_id=new ArrayList<>();
         arrayList_u_test_id=new ArrayList<>();

name=findViewById(R.id.name);
pass=findViewById(R.id.pass);
        SharedPreferences prefs = getSharedPreferences("TAGSCORE",MODE_PRIVATE);
        final String naam = prefs.getString("name", null);
        final String paas = prefs.getString("password", null);
        login = findViewById(R.id.login);
        deviceid = getdeviceID();
        if (!isNetworkAvailable())
        {
            online=false;
            assert naam != null;
            assert paas != null;
        //   Toast.makeText(getApplicationContext(),naam+":"+paas,Toast.LENGTH_LONG).show();


                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (naam.equals(name.getText().toString()) && paas.equals(pass.getText().toString())) {
                            startActivity(new Intent(MainActivity.this, Main5Activity.class));
                        }
                    }});
        }
        else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    online=true;
                    AddGeofencebody("twgzl", "94588");
                }
            });
        }


        forgot=findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"The recovery link has been sent to your registered email ID.",Snackbar.LENGTH_LONG).show();
              //  AddGeofencebodypass("twgzl");

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
        });

        // Adding request to request queue
        String tag_json_obj = "json_obj_req";
        //  VolleyAppController.getInstance().getRequestQueue().getCache().remove(url);
        VolleyAppController.getInstance().addToRequestQueue(request,tag_json_obj);

    }






    protected void AddGeofencebody(final String name, final String password) {
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
                         userid= String.valueOf(response.get("userID"));
                         apikey= String.valueOf(response.get("api_key"));
                        Log.e("arrgrrrruser", userid);
                        Log.e("arrgrapik", apikey);

                        JSONArray array=response.getJSONArray("profile");
                        JSONObject jsonobj_2 = (JSONObject) array.get(0);
                         login_name = String.valueOf(jsonobj_2.get("name"));
                         login_email= String.valueOf(jsonobj_2.get("email"));
                         login_phone= String.valueOf(jsonobj_2.get("phone"));
                         login_role= String.valueOf(jsonobj_2.get("role"));


                        SharedPreferences.Editor editor= getSharedPreferences("TAGSCORE",MODE_PRIVATE).edit();
                        editor.putString("name",name);
                        editor.putString("password",password);


                        editor.apply();




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
    {//TODO
        Intent io=new Intent(MainActivity.this,PatternActivity.class);
        io.putExtra("p", "p");
        startActivity(io);


       // startActivity(new Intent(MainActivity.this, Main5Activity.class));
     //   startActivity(new Intent(MainActivity.this, BatchListActivity.class));

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







    protected void AddGeofencebodypass(final String name) {
        Map<String, String> params = new HashMap<>();
        params.put("username", name);
        Log.e("params :",params.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url3, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Data response", String.valueOf(response.toString()));
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();


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

@Override
public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this,LogoutActivity.class));
}


}
