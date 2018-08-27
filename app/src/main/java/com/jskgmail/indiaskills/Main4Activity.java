package com.jskgmail.indiaskills;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main4Activity extends FragmentActivity implements OnMapReadyCallback {

    private TextView lat,longi,address;
    String instr_str="Give your test following all the rules";
    private Button next;
    private GoogleMap mMap;
   static double latitude=28 ;
    static double longitude=77 ;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;
    static String id,languageCode_selected="en",instructionList="Be truthful!";
    static ArrayList<String> languageCode=new ArrayList<>();
    static ArrayList<String> languageName=new ArrayList<>();

    String url="http://staging.tagusp.com/api/users/Language";
    String url1="http://staging.tagusp.com/api/users/Instruction";
    String url11="http://staging.tagusp.com/api/users/BatchList";
    ArrayList<String> instructionsss=new ArrayList<String>();

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {



                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Main4Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);



            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        GPSTracker gps = new GPSTracker(this);

                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        lat.setText(latitude+"");
                        longi.setText(longitude+"");
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                            String addr = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                            address.setText(addr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(this);

                        //Request location updates:
                 //       locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
 languageCode=new ArrayList<>();
languageName=new ArrayList<>();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.e("testid_uniuqid", MyTEST_IDs.test_id_selected+"   "+ MyTEST_IDs.unique_id_selected);

        provider = locationManager.getBestProvider(new Criteria(), false);
        checkLocationPermission();
        lat=findViewById(R.id.lat);
        longi=findViewById(R.id.longi);
        address=findViewById(R.id.address);
        next=findViewById(R.id.next);


        GPSTracker gps = new GPSTracker(this);

         latitude = gps.getLatitude();
         longitude = gps.getLongitude();

        lat.setText(latitude+"");
        longi.setText(longitude+"");

  String addr = "N.A.";
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size()>0)
             addr = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


            address.setText(addr);
        } catch (IOException e) {
            e.printStackTrace();
        }



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        if (MainActivity.online) {
            AddGeofencebody(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testinstructions();
                }
            });

        }
        else
        {

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Resume();
                }
            });

            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();

            Gson gson = new Gson();


            DatabaseHandleroff db = new DatabaseHandleroff(getApplicationContext());
            String test_det_str = null;

            ArrayList<String> testdet=new ArrayList<>();

            List<TestDetailoff> contacts = db.getAllContacts();
            for (TestDetailoff cn : contacts) {
                test_det_str = cn.getTestDetailss_array();

                testdet = gson.fromJson(test_det_str, type);

                if(testdet.get(0).equals(MyTEST_IDs.unique_id_selected))
                {
                  //  test_q_str = cn.getArrayList_3_all_pics();
                   // test_ans_str = cn.getArrayList_3_all_options();
                    instr_str = cn.getInstructionList();


                }


            }

            //    Log.e("jjjjjjjjjj", String.valueOf(testdet));
            //   Log.e("jjjjjjjjjjaa", String.valueOf(testdet1));
            //      Log.e("jjjjjjjjjjbb", String.valueOf(testdet2));
            //       Log.e("jjjjjjjjjjccc", String.valueOf(instr));




        }


       /// AddGeofencebody11( MainActivity.userid, MainActivity.apikey);


    }




    void Resume()
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.instruction_dialog, null);
        //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
     TextView inst=alertLayout.findViewById(R.id.instr);
     inst.setText(instr_str);

        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setView(alertLayout);

        alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(Main4Activity.this,BatchListActivity.class));
            }
        });

         AlertDialog dialog= alert.create();



        dialog.show();




    }










    private void testinstructions() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layouttestinstructions, null);
        final TextView instructions=alertLayout.findViewById(R.id.instructions);
        final Spinner languages=alertLayout.findViewById(R.id.languages);
        final String[] lang = {"English"};
        StringBuilder inst_text= new StringBuilder();
        for (int i=0;i<instructionsss.size();i++)
        {
           inst_text.append(instructionsss.get(i));
        }
        instructions.setText(inst_text);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);

        // category1.add("Hindi");
        List<String> category1 = new ArrayList<String>(languageName);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languages.setAdapter(dataAdapter1);
        languages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  lang[0] = (parent.getItemAtPosition(position).toString());
                languageCode_selected=languageCode.get(position);
                AddGeofencebody1(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey,languageCode_selected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Main4Activity.this,BatchListActivity.class));
             //   startActivity(new Intent(Main4Activity.this,HorizontalScrollViewActivity.class));
//Cappturepic
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(loc).title("Your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,17));

    }



















    protected void AddGeofencebody(String testid,String userid,String api_key) {



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
               //  Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();
                JSONArray response1 = null;
                JSONObject resp = null;
                try {
                    response1=response.getJSONArray("allLanguage");

                    for (int i = 0; i<response1.length(); i++) {
                        resp = (JSONObject) response1.get(i);

                        assert resp != null;
                        id = (String) resp.get("id");
                        languageName.add((String) resp.get("languageName"));
                        languageCode.add((String) resp.get("languageCode"));


                        // Toast.makeText(getApplicationContext(),"success"+languageCode+languageName,Toast.LENGTH_SHORT).show();
                        //   handler=new Handler(callback);
                        //    Message msg = null;
                        //   handler.handleMessage(msg);

                    }
                    AddGeofencebody1(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey,languageCode_selected);

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







//Instruction api....



    protected void AddGeofencebody1(String testid,String userid,String api_key,String langcode) {


        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("languageCode",langcode);

        Log.e("params :",params.toString());
        final JSONObject[] instr = new JSONObject[1];

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url1, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              //  Log.e("Data response", String.valueOf(response.toString()));
                  Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray=response.getJSONArray("instructionList");
                    for (int i=0;i<jsonArray.length();i++)
                    {   instr[0] = (JSONObject) jsonArray.get(i);


                    Toast.makeText(getApplicationContext(),"success"+jsonArray.length(),Toast.LENGTH_SHORT).show();
                    //   handler=new Handler(callback);
                    //    Message msg = null;
                    //   handler.handleMessage(msg);
instructionsss.add( String.valueOf(instr[0].get("instruction")));}
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














//batchlist

    protected void AddGeofencebody111(String userid,String api_key) {


        Map<String, String> params = new HashMap<>();
        params.put("userId", userid);
        params.put("api_key",api_key);
        Log.e("params :",params.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url11, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  Log.e("Data response", String.valueOf(response.toString()));
                Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();

               //     instructionList=(String)response.get("instructionList");
                   // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    //   handler=new Handler(callback);
                    //    Message msg = null;
                    //   handler.handleMessage(msg);


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
