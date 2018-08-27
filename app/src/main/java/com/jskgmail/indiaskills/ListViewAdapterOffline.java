package com.jskgmail.indiaskills;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static android.content.Context.MODE_PRIVATE;


public class ListViewAdapterOffline extends BaseAdapter {
    Activity context;
    ArrayList<String> name;
    ArrayList<String> uid;
    public ListViewAdapterOffline(Activity context, ArrayList<String> name, ArrayList<String> uid)
    {
        super();
        this.context=context;
        this.name=name;
        this.uid=uid;

    }


    @Override
    public int getCount() {

        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtviewname;
      //  TextView txtviewcity;
        FloatingTextButton test;
        ImageView delete;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_off_tests,null);
            holder=new ViewHolder();
            holder.txtviewname=(TextView)convertView.findViewById(R.id.nam);
            holder.test=convertView.findViewById(R.id.fab1);

            holder.delete=convertView.findViewById(R.id.delete);
          //  holder.txtviewcity=(TextView)convertView.findViewById(R.id.city);

            holder.txtviewname.setText(name.get(position));

            SharedPreferences prefs = context.getSharedPreferences("testcompleted",MODE_PRIVATE);
            String IDTestCompletedoff = prefs.getString("uid", null);

            if (uid.get(position).equals(IDTestCompletedoff)) {

                holder.test.setTitle("UPLOAD TEST");
                holder.test.setLeftIconDrawable(Drawable.createFromPath(String.valueOf(R.drawable.ic_file_upload_black_24dp)));

                holder.delete.setVisibility(View.GONE);

                holder.test.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseHandleroffanswers dbb = new DatabaseHandleroffanswers(context);

                        Log.e("offtesting","start");

                        List<TestDetailoffans> contactss = dbb.getAllContacts();
                        for (TestDetailoffans cn : contactss) {




                            uploadtest(cn.getCandidateid(), cn.getTestDetailss_array(), cn.getArrayList_3_all_pics(), cn.getArrayList_3_all_options());
                        }


                    }
                });

            }










else


            holder.test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity.online=false;
                    MyTEST_IDs.unique_id_selected=uid.get(position);
                    MyTEST_IDs.test_name_selected=name.get(position);

                    context.startActivity(new Intent(context,Main3Activity.class));


                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    LayoutInflater inflater = context.getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.loading_ques, null);
                    //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
                    Button yes=alertLayout.findViewById(R.id.yes);
                    Button no=alertLayout.findViewById(R.id.no);

                    AlertDialog.Builder alert= new AlertDialog.Builder(context);
                    alert.setView(alertLayout);



                    final AlertDialog dialog= alert.create();
                    dialog.show();


                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            Type type = new TypeToken<ArrayList<String>>() {
                            }.getType();

                            Gson gson = new Gson();
                            DatabaseHandleroff db = new DatabaseHandleroff(context);
                            String test_det_str = null;

                            ArrayList<String> testdet=new ArrayList<>();

                            List<TestDetailoff> contacts = db.getAllContacts();
                            for (TestDetailoff cn : contacts) {
                                test_det_str = cn.getTestDetailss_array();

                                testdet = gson.fromJson(test_det_str, type);

                                if (testdet.get(0).equals(uid.get(position))) {
                                    db.deleteContact(cn);



                                }
                            }

                            dialog.cancel();
                            context.finish();
                            context.startActivity(new Intent(context,Main5Activity.class));
                            Toast.makeText(context,"Test Removed",Toast.LENGTH_LONG).show();











                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();

                        }
                    });




                }
            });

           // holder.txtviewcity.setText(id.get(position));

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


       // notifyDataSetChanged();
        return convertView;
    }















    private void uploadtest(final String candidateID, final String testdetail,String allpics,String ans_options) {

        LayoutInflater inflater = context.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.loading, null);
        TextView text=alertLayout.findViewById(R.id.text);
        text.setText("Uploading the test...");
        //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
        AlertDialog.Builder alert= new AlertDialog.Builder(context);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);



        final AlertDialog dialog= alert.create();
        dialog.show();


        Map<String, String> params = new HashMap<>();
        params.put("userId", MainActivity.userid);
        params.put("TestDetail", testdetail);
        params.put("All_Pics",allpics);
        params.put("CandidateID",candidateID);
        params.put("Answers", ans_options);
        params.put("api_key", MainActivity.apikey);

        Log.e("params :",params.toString());

        //login_name_arr=new ArrayList<>();login_username_arr=new ArrayList<>();

        String url="http://staging.tagusp.com/api/users/OfflineTest";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("offtestupload", String.valueOf(response.toString()));

                dialog.cancel();
                // Store the JSON object in JSON array as objects (For level 1 array element i.e Results)
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError","Error response", error);
                //   Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
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
















