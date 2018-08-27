package com.jskgmail.indiaskills;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraActivity;
import com.androidhiddencamera.HiddenCameraFragment;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends HiddenCameraActivity {
String url="http://staging.tagusp.com/api/users/GetQuestion";
    String url1="http://staging.tagusp.com/api/users/SaveAnswer";
    String url2="http://staging.tagusp.com/api/users/TestSubmit";
static String candidateID="";
   static String studentSelected ="";

    //    byte[] imageByteArray = Base64.decode(imageBytes, Base64.DEFAULT);
    static Camera camera = null;
    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;
    private CameraConfig mCameraConfig;
    private HiddenCameraFragment mHiddenCameraFragment;
    String url7="http://staging.tagusp.com/api/users/user_upload_video_photo";

 static    int total_no_of_quest=6;
TextView ques_textview;
Button next,prev;
ImageView q_img;
VideoView q_video;
RecyclerView List_options;
    CustomAdapteroptions adapter;
     TextView bookmark;

     TextView qno;

    CenterLockHorizontalScrollview centerLockHorizontalScrollview;
    CustomListAdapter customListAdapter;
    Button btnPrev, btnNext;
    int currIndex = 0;
    TextView timeremaining;



    ArrayList<String> q_lists_arr=new ArrayList<>();
    ArrayList<String> q_ids=new ArrayList<>();
    ArrayList<String> q_imgs=new ArrayList<>();
    ArrayList<String> q_vids=new ArrayList<>();



    ArrayList<ArrayList<String>> arrayListArrayList_ans_ids= new ArrayList<>();
    ArrayList<ArrayList<String>> arrayListArrayList_ans_values= new ArrayList<>();
    ArrayList<ArrayList<String>> arrayListArrayList_ans_imgs= new ArrayList<>();
    ArrayList<ArrayList<String>> arrayListArrayList_ans_videos= new ArrayList<>();


    ArrayList<String> ArrayList_ans_ids= new ArrayList<>();
    ArrayList<String> ArrayList_ans_values= new ArrayList<>();
    ArrayList<String> ArrayList_ans_imgs= new ArrayList<>();
    ArrayList<String> ArrayList_ans_videos= new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);



        if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mHiddenCameraFragment)
                    .commit();
            mHiddenCameraFragment = null;
        }






        mCameraConfig = new CameraConfig()
                .getBuilder(QuestionsActivity.this)
                .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .setImageRotation(CameraRotation.ROTATION_270)
                .build();
        // HiddenCameraUtils.openDrawOverPermissionSetting(this);
        if (ActivityCompat.checkSelfPermission(QuestionsActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Start camera preview
            startCamera(mCameraConfig);
        } else {
            ActivityCompat.requestPermissions(QuestionsActivity.this, new String[]{Manifest.permission.CAMERA},
                    REQ_CODE_CAMERA_PERMISSION);
        }





        //////////////////////////////////////////////////////////////////////













        CustomAdapteroptions.ansforquest=new ArrayList<>();

        ques_textview = findViewById(R.id.ques);
        next = findViewById(R.id.nxt);
        prev = findViewById(R.id.prv);
        List_options = findViewById(R.id.lv_options);
        q_img = findViewById(R.id.video);
        q_video=findViewById(R.id.video1);
        timeremaining = findViewById(R.id.t);
        bookmark = findViewById(R.id.bookmark);
currIndex=0;
        qno = findViewById(R.id.qno);


        q_lists_arr = new ArrayList<>();
        q_ids = new ArrayList<>();
        q_imgs = new ArrayList<>();
        q_vids = new ArrayList<>();


        arrayListArrayList_ans_ids = new ArrayList<>();
        arrayListArrayList_ans_values = new ArrayList<>();
        arrayListArrayList_ans_imgs = new ArrayList<>();
        arrayListArrayList_ans_videos = new ArrayList<>();


        ArrayList_ans_ids = new ArrayList<>();
        ArrayList_ans_values = new ArrayList<>();
        ArrayList_ans_imgs = new ArrayList<>();
        ArrayList_ans_videos = new ArrayList<>();




        Spinner currsemin = (Spinner) findViewById(R.id.spinner);
      //  final String[] studentSelected = new String[1];

        List<String> category1 = BatchListActivity.login_name_arr;
        final List<String> category11 = BatchListActivity.login_username_arr;

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currsemin.setAdapter(dataAdapter1);




        currsemin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 studentSelected = (parent.getItemAtPosition(position).toString().replaceAll(" ", ""));
candidateID=category11.get(position);
                 Intent i=new Intent(QuestionsActivity.this,PatternActivity.class);
                 i.putExtra("p","c");
                 startActivity(i);

                 Toast.makeText(getApplicationContext(),"Selected Student: "+studentSelected,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




















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
        if (MainActivity.online)
            AddGeofencebody1(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey, Main4Activity.languageCode_selected, MyTEST_IDs.unique_id_selected);
        else {

            final ArrayList<String> q_list;
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            Type type1 = new TypeToken<ArrayList<ArrayList<String>>>() {
            }.getType();
            Type type2 = new TypeToken<ArrayList<ArrayList<ArrayList<String>>>>() {
            }.getType();
            Gson gson = new Gson();


            DatabaseHandleroff db = new DatabaseHandleroff(getApplicationContext());
            String test_det_str = null, test_q_str = null, test_ans_str = null;

            ArrayList<String> testdet = new ArrayList<>();

            List<TestDetailoff> contacts = db.getAllContacts();
            for (TestDetailoff cn : contacts) {
                test_det_str = cn.getTestDetailss_array();

                testdet = gson.fromJson(test_det_str, type);

                if (testdet.get(0).equals(MyTEST_IDs.unique_id_selected)) {
                    test_q_str = cn.getArrayList_3_all_questions();
                    test_ans_str = cn.getArrayList_3_all_options();

                    //   Resume();
                }

            }
            ArrayList<ArrayList<String>> All_Questions_arr = gson.fromJson(test_q_str, type1);
            ArrayList<ArrayList<ArrayList<String>>> All_Options_arr = gson.fromJson(test_ans_str, type2);


            q_lists_arr = (All_Questions_arr.get(0));
            q_ids = (All_Questions_arr.get(1));
            q_imgs = (All_Questions_arr.get(2));
            q_vids = (All_Questions_arr.get(3));

            arrayListArrayList_ans_ids=All_Options_arr.get(0);
            arrayListArrayList_ans_imgs=All_Options_arr.get(1);
            arrayListArrayList_ans_values=All_Options_arr.get(2);
            arrayListArrayList_ans_videos=All_Options_arr.get(3);



            final int[] qno_ = {0};

            total_no_of_quest = q_lists_arr.size();

             q_list = new ArrayList<String>();
            for (int q = 1; q <= total_no_of_quest; q++) {
                q_list.add(q + "");

            }

            centerLockHorizontalScrollview = (CenterLockHorizontalScrollview) findViewById(R.id.scrollView);

            customListAdapter = new CustomListAdapter(getApplicationContext(),
                    R.layout.news_list_item, q_list);
            centerLockHorizontalScrollview.setAdapter(getApplicationContext(), customListAdapter);



///////////////////////////////////////////////////////////////////////////////////////////////////


            final int[] i = {0};
            {

                    centerLockHorizontalScrollview.setCenter(i[0], 0);
                    //    text.setText(list.get(currIndex-1));

Log.e("tttttestd",Main3Activity.testDuration);
                new CountDownTimer(Integer.parseInt(Main3Activity.testDuration)*60000, 1000) {
                    int ii=0;
                    public void onTick(long millisUntilFinished) {
                        String text = String.format(Locale.getDefault(), "Time Remaining %02d min: %02d sec",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                        timeremaining.setText(text);
                  //      if (Main3Activity.rendomclick!="null")
                    //        if (ii%Integer.parseInt(Main3Activity.rendomclick)==0)
                      //      takePicture();
                        ii++;
                    }
                    public void onFinish() {
                        timeremaining.setText("TIME FINISHED !");
                    }

                }.start();



                qno.setText((int) (i[0] + 1) + ".)");

                String questvid = q_vids.get(i[0]);
                String questimg_str = q_imgs.get(i[0]);
                String questid = q_ids.get(i[0]);
                String quest_str = q_lists_arr.get(i[0]);


                ArrayList_ans_ids=arrayListArrayList_ans_ids.get(i[0]);
                ArrayList_ans_values=arrayListArrayList_ans_values.get(i[0]);
                ArrayList_ans_videos=arrayListArrayList_ans_videos.get(i[0]);
                ArrayList_ans_imgs=arrayListArrayList_ans_imgs.get(i[0]);



                if (questvid.equals("") && (questimg_str.equals(""))) {
                    q_img.setVisibility(View.INVISIBLE);
                    q_video.setVisibility(View.INVISIBLE);

                } else if (questvid.equals("")){
                    q_img.setVisibility(View.VISIBLE);
                    //Picasso.get().load(questimg).into(q_img);
                    Glide.with(getApplicationContext()).load(questimg_str).into(q_img);

                }
                else
                {
                    q_video.setVisibility(View.VISIBLE);
                    q_video.start();

                }


                bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        centerLockHorizontalScrollview.setBookmark(currIndex, 1);
                        //   AddGeofencebody22(testid,userid,api_key,quest_id, MyTEST_IDs.unique_id_selected,"10","P");
                    }
                });


                ques_textview.setText(quest_str);

Log.e("aaaaaaaaaansnaa",ArrayList_ans_values+"");




                adapter= new CustomAdapteroptions(QuestionsActivity.this,(i[0] + 1),ArrayList_ans_values, ArrayList_ans_imgs, ArrayList_ans_videos, ArrayList_ans_ids);

                List_options.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                List_options.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.VERTICAL, false));
                List_options.addItemDecoration(new DividerItemDecoration(QuestionsActivity.this, DividerItemDecoration.VERTICAL));
                List_options.setItemAnimator(new DefaultItemAnimator());











                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            //    text.setText(list.get(currIndex-1));



                        if (i[0] < total_no_of_quest - 1) {


                            i[0]++;
                            centerLockHorizontalScrollview.setCenter(i[0], 0);

                            qno.setText((int) (i[0] + 1) + ".)");


                            String questvid = q_vids.get(i[0]);
                            String questimg_str = q_imgs.get(i[0]);
                            String questid = q_ids.get(i[0]);
                            String quest_str = q_lists_arr.get(i[0]);



                            ArrayList_ans_ids=arrayListArrayList_ans_ids.get(i[0]);
                            ArrayList_ans_values=arrayListArrayList_ans_values.get(i[0]);
                            ArrayList_ans_videos=arrayListArrayList_ans_videos.get(i[0]);
                          ArrayList_ans_imgs=arrayListArrayList_ans_imgs.get(i[0]);


                            Log.e("aaaanswerlsisinn", String.valueOf(arrayListArrayList_ans_values));

                            Log.e("aaaanswerlsisi", String.valueOf(ArrayList_ans_values));



                            if (questvid.equals("") && (questimg_str.equals(""))) {
                                q_img.setVisibility(View.INVISIBLE);
                                q_video.setVisibility(View.INVISIBLE);

                            } else if (questvid.equals("")){
                                q_img.setVisibility(View.VISIBLE);
                                //Picasso.get().load(questimg).into(q_img);
                                Glide.with(getApplicationContext()).load(questimg_str).into(q_img);

                            }
                            else
                            {
                                q_video.setVisibility(View.VISIBLE);
                                q_video.start();

                            }

                            bookmark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    centerLockHorizontalScrollview.setBookmark(currIndex, 1);
                                    //   AddGeofencebody22(testid,userid,api_key,quest_id, MyTEST_IDs.unique_id_selected,"10","P");
                                }
                            });


                            ques_textview.setText(quest_str);



                            adapter= new CustomAdapteroptions(QuestionsActivity.this, (i[0] + 1),ArrayList_ans_values, ArrayList_ans_imgs, ArrayList_ans_videos, ArrayList_ans_ids);

                            List_options.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            List_options.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.VERTICAL, false));
                            List_options.addItemDecoration(new DividerItemDecoration(QuestionsActivity.this, DividerItemDecoration.VERTICAL));
                            List_options.setItemAnimator(new DefaultItemAnimator());



                        }


                            //  Toast.makeText(getApplicationContext(),quest_id,Toast.LENGTH_LONG).show();

                        if (i[0] == total_no_of_quest - 1) {
                                next.setText("Submit");
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //   AddGeofencebody44(testid,userid,api_key,Main4Activity.latitude+":"+Main4Activity.longitude, MyTEST_IDs.unique_id_selected,"dd","P");
                                        Toast.makeText(getApplicationContext(), "Your answers has been successfully submitted!", Snackbar.LENGTH_LONG).show();


                                        Gson gson = new Gson();

                                        String TestDetail= gson.toJson(Main3Activity.testdet);
                                        String all_ans_opt= gson.toJson(CustomAdapteroptions.ans_clicked);

                                        String all_batch_pics= gson.toJson
                                                (CustomAdapterbatch.picsforoffline);



                                        DatabaseHandleroffanswers db = new DatabaseHandleroffanswers(getApplicationContext());
                                        //TODO feedback left
                                        db.addContact(new TestDetailoffans(candidateID,TestDetail,all_batch_pics,all_ans_opt));








                                        startActivity(new Intent(QuestionsActivity.this, FeedbackActivity.class));
                                    }
                                });
                            } else
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();

                        }

                });





              ///////////////////////preeee//////////////////////////////////////






                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (i[0] >0) {

                            i[0]--;

                            centerLockHorizontalScrollview.setCenter(i[0],0);
                            //  text.setText(list.get(currIndex==0?0:currIndex-1));




                        ArrayList<String> arrayList_img = new ArrayList<>();
                        ArrayList<String> arrayList_vid = new ArrayList<>();
                        ArrayList<String> arrayList_ans_id = new ArrayList<>();
                        ArrayList<String> arrayList_ans_value = new ArrayList<>();





                            qno.setText((int) (i[0] + 1) + ".)");


                            String questvid = q_vids.get(i[0]);
                            String questimg_str = q_imgs.get(i[0]);
                            String questid = q_ids.get(i[0]);
                            String quest_str = q_lists_arr.get(i[0]);


                            ArrayList_ans_ids=arrayListArrayList_ans_ids.get(i[0]);
                            ArrayList_ans_values=arrayListArrayList_ans_values.get(i[0]);
                           ArrayList_ans_videos=arrayListArrayList_ans_videos.get(i[0]);
                           ArrayList_ans_imgs=arrayListArrayList_ans_imgs.get(i[0]);




                            if (questvid.equals("") && (questimg_str.equals(""))) {
                                q_img.setVisibility(View.INVISIBLE);
                                q_video.setVisibility(View.INVISIBLE);

                            } else if (questvid.equals("")){
                                q_img.setVisibility(View.VISIBLE);
                                //Picasso.get().load(questimg).into(q_img);
                                Glide.with(getApplicationContext()).load(questimg_str).into(q_img);

                            }
                            else
                            {
                                q_video.setVisibility(View.VISIBLE);
                                q_video.start();

                            }

                            bookmark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    centerLockHorizontalScrollview.setBookmark(currIndex, 1);
                                    //   AddGeofencebody22(testid,userid,api_key,quest_id, MyTEST_IDs.unique_id_selected,"10","P");
                                }
                            });


                            ques_textview.setText(quest_str);



                            adapter= new CustomAdapteroptions(QuestionsActivity.this, (i[0] + 1),ArrayList_ans_values, ArrayList_ans_imgs, ArrayList_ans_videos, ArrayList_ans_ids);
                            List_options.setHasFixedSize(false);

                            List_options.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.VERTICAL, false));
                            List_options.addItemDecoration(new DividerItemDecoration(QuestionsActivity.this, DividerItemDecoration.VERTICAL));
                            List_options.setItemAnimator(new DefaultItemAnimator());


                            List_options.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        }


                        //  Toast.makeText(getApplicationContext(),quest_id,Toast.LENGTH_LONG).show();



                    }

                });












            }
        }
    }
















/////////////////////////////////////////////////////////////////////////////////














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
Log.e("qqquestion",response.toString());
                    final JSONArray questionlist=  response.getJSONArray("question");
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
                        Log.e("ttttttttt",Main3Activity.testDuration+"min");


                        new CountDownTimer(Integer.parseInt(Main3Activity.testDuration)*60000, 1000) {
                            int ii=5;

                            public void onTick(long millisUntilFinished) {

                                String text = String.format(Locale.getDefault(), "Time Remaining %02d min: %02d sec",
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                                timeremaining.setText(text);
                           //     if (Main3Activity.rendomclick!="null")
                                //here you can have your logic to set text to edittext
                     //           if (ii%Integer.parseInt(Main3Activity.rendomclick)==0)
                                   if (ii%10==0)
                                    takePicture();

                                ii++;
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

                            q_video.setVisibility(View.INVISIBLE);
                        }

                        else {
                            q_img.setVisibility(View.VISIBLE);

                            q_video.setVisibility(View.VISIBLE);
                            //BASE 64 to img
                            //byte[] questimgg=Base64.decode(questimg,Base64.DEFAULT);

                            //Picasso.get().load(questimg).into(q_img);
                            Glide.with(getApplicationContext()).load(questimg).into(q_img);//.asBitmap


                        }

                        bookmark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                centerLockHorizontalScrollview.setBookmark(currIndex,1);
                                AddGeofencebody22(testid,userid,api_key,quest_id, MyTEST_IDs.unique_id_selected,"10","P");
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
                        adapter= new CustomAdapteroptions(QuestionsActivity.this, (i[0] + 1),arrayList_ans_value, arrayList_img, arrayList_vid, arrayList_ans_id);

                        List_options.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        List_options.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.VERTICAL, false));
                        List_options.addItemDecoration(new DividerItemDecoration(QuestionsActivity.this, DividerItemDecoration.VERTICAL));
                        List_options.setItemAnimator(new DefaultItemAnimator());






                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {





                                ArrayList<String> arrayList_img=new ArrayList<>();
                                ArrayList<String> arrayList_vid=new ArrayList<>();
                                ArrayList<String> arrayList_ans_id=new ArrayList<>();
                                ArrayList<String> arrayList_ans_value=new ArrayList<>();


                                if (i[0]<total_no_of_quest-1)
                                {


                                    i[0]++;
                                    centerLockHorizontalScrollview.setCenter(i[0],0);

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
                                        quest = String.valueOf( question.get("question"));
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
                                        adapter= new CustomAdapteroptions(QuestionsActivity.this,(i[0] + 1), arrayList_ans_value, arrayList_img, arrayList_vid, arrayList_ans_id);

                                        List_options.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        List_options.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.VERTICAL, false));
                                        List_options.addItemDecoration(new DividerItemDecoration(QuestionsActivity.this, DividerItemDecoration.VERTICAL));
                                        List_options.setItemAnimator(new DefaultItemAnimator());



                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                    if (questvid.equals("")&&(questimg.equals("")))
                                    {
                                        q_img.setVisibility(View.INVISIBLE);

                                    }

                                    else {
                                        q_img.setVisibility(View.VISIBLE);

                                        //BASE 64 to img
                                        //byte[] questimgg=Base64.decode(questimg,Base64.DEFAULT);
                                        Glide.with(getApplicationContext()).load(questimg).into(q_img);//.asBitmap


                                    }
                                ques_textview.setText(quest);
                                    final String finalQuest_id = quest_id;
                                    bookmark.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            centerLockHorizontalScrollview.setBookmark(currIndex,1);

                                            AddGeofencebody22(testid,userid,api_key, finalQuest_id, MyTEST_IDs.unique_id_selected,"10","P");
                                        }
                                    });

                                  //  Toast.makeText(getApplicationContext(),quest_id,Toast.LENGTH_LONG).show();

                                    if (i[0]==total_no_of_quest-1)
                                    {
                                        next.setText("Submit");
                                     next.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             AddGeofencebody44(testid,userid,api_key,Main4Activity.latitude+":"+Main4Activity.longitude, MyTEST_IDs.unique_id_selected,"dd","P");
                                             Toast.makeText(getApplicationContext(),"Your answers has been successfully submitted!",Snackbar.LENGTH_LONG).show();

                                             startActivity(new Intent(QuestionsActivity.this,FeedbackActivity.class));
                                         }
                                     });
                                    }

                                    else

                                    AddGeofencebody11(testid,userid,api_key,quest_id, MyTEST_IDs.unique_id_selected,ListViewAdapteroptions.ans_clicked,"P");







                            }
                            }
                        });

                        prev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {





                                 ArrayList<String> arrayList_img=new ArrayList<>();
                                 ArrayList<String> arrayList_vid=new ArrayList<>();
                                 ArrayList<String> arrayList_ans_id=new ArrayList<>();
                                ArrayList<String> arrayList_ans_value=new ArrayList<>();

                                if (i[0]>0)
                                {


                                        //  text.setText(list.get(currIndex==0?0:currIndex-1));

                                i[0]--;
                                    centerLockHorizontalScrollview.setCenter(i[0],0);

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
                                    adapter= new CustomAdapteroptions(QuestionsActivity.this, (i[0] + 1),arrayList_ans_value, arrayList_img, arrayList_vid, arrayList_ans_id);

                                    List_options.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    List_options.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.VERTICAL, false));
                                    List_options.addItemDecoration(new DividerItemDecoration(QuestionsActivity.this, DividerItemDecoration.VERTICAL));
                                    List_options.setItemAnimator(new DefaultItemAnimator());



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                    if (questvid.equals("")&&(questimg.equals("")))
                                    {
                                        q_img.setVisibility(View.INVISIBLE);

                                    }

                                    else {
                                        //BASE 64 to img
                                        //byte[] questimgg=Base64.decode(questimg,Base64.DEFAULT);//.asBitmap

                                        q_img.setVisibility(View.VISIBLE);
                                        Glide.with(getApplicationContext()).load(questimg).into(q_img);


                                    }


                                    final String finalQuest_id = quest_id;
                                    bookmark.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                                centerLockHorizontalScrollview.setBookmark(currIndex,1);
                                                //  text.setText(list.get(currIndex==0?0:currIndex-1));

                                            AddGeofencebody22(testid,userid,api_key, finalQuest_id, MyTEST_IDs.unique_id_selected,"10","P");
                                        }
                                    });

                                    ques_textview.setText(quest);

                                    AddGeofencebody11(testid,userid,api_key,quest_id, MyTEST_IDs.unique_id_selected,ListViewAdapteroptions.ans_clicked,"P");



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




    protected void AddGeofencebody44(String testid,String userid,String api_key,String endlatlong,String u_test_id,String endlocation,String ver)
    {

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







    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera(mCameraConfig);
            } else {
                Toast.makeText(this,"error_camera_permission_denied", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onImageCapture(@NonNull File imageFile) {
        Toast.makeText(getApplicationContext(),"pic captured",Toast.LENGTH_SHORT).show();

        // Convert file to bitmap.
        // Do something.
        AddGeofencebody44(MainActivity.userid,MainActivity.apikey,candidateID,imageFile);

Log.e("fffffffff",MainActivity.userid+"eeee"+MainActivity.apikey+"eeee"+MainActivity.test_id+"eee"+MainActivity.u_test_id);
        //Display the image to the image view
     //   ((ImageView) findViewById(R.id.cam_prev)).setImageBitmap(bitmap);


    }

    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera
                Toast.makeText(this, "error_cannot_open", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_IMAGE_WRITE_FAILED:
                //Image write failed. Please check if you have provided WRITE_EXTERNAL_STORAGE permission
                Toast.makeText(this, "error_cannot_write", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                //camera permission is not available
                //Ask for the camera permission before initializing it.
                Toast.makeText(this, "error_cannot_get_permission", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                //Display information dialog to the user with steps to grant "Draw over other app"
                //permission for the app.
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, "error_not_having_camera", Toast.LENGTH_LONG).show();
                break;
        }
    }














    private void AddGeofencebody44(String userid, String apikey,String candidateID,File bm) {


    final Map<String, String> params = new HashMap<>();

        params.put("userId", userid);
        params.put("api_key",apikey);
        params.put("testID",MyTEST_IDs.test_id_selected);
        params.put("uniqueID",MyTEST_IDs.unique_id_selected);
        params.put("picType","pic");
        params.put("candidate_id",candidateID);
        params.put("version","m");
      //  params.put("media", String.valueOf(bm));

        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        params.put("path", encodedString);

        Log.e("params :",params.toString());
        //   ListViewAdapteroptions.ans_clicked=new ArrayList<String>();

        Toast.makeText(getApplicationContext(),candidateID,Toast.LENGTH_SHORT).show();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url7, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                     Toast.makeText(getApplicationContext(), response+"",Toast.LENGTH_LONG).show();

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
