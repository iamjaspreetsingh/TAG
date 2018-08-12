package com.jskgmail.indiaskills;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveTestOfflineActivity  {
 //   AlertDialog.Builder alert = null;
 //   AlertDialog dialog = null;
static Context context;
    private static ArrayList<String> TestDetailss_array=new ArrayList<>();
    private ArrayList<ArrayList<ArrayList>> arrayList_3_all_options=new ArrayList<>();
    private ArrayList<ArrayList> arrayList_3_all_questions=new ArrayList<>();



    protected void onCreate() {

    //    LayoutInflater inflater = getLayoutInflater();
     //   View alertLayout = inflater.inflate(R.layout.loading, null);
        //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
     //   TextView text=alertLayout.findViewById(R.id.text);
     //   text.setText("Downloading the test....");
     //    alert = new AlertDialog.Builder(this);

        // this is set the view from XML inside AlertDialog
     //   alert.setView(alertLayout);


        // disallow cancel of AlertDialog on click of back button and outside touch
        //   alert.setTitle("Password ");
        //  alert.setIcon(R.drawable.ic_lock_outline_black_24dp);
      //   dialog = alert.create();
     //    dialog.setCancelable(false);
     //   dialog.show();








        AddGeofencebody(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey);

     languageCode=new ArrayList<>();
   languageName=new ArrayList<>();
    arrayList_3_all_options=new ArrayList<>();
  arrayList_3_all_questions=new ArrayList<>();
        TestDetailss_array=new ArrayList<>();




    }
















    String urlq="http://staging.tagusp.com/api/users/GetQuestion";

    String url="http://staging.tagusp.com/api/users/TestDetail";
    //  final boolean[] processing = {true};
    String url2="http://staging.tagusp.com/api/users/Language";
    String url1="http://staging.tagusp.com/api/users/Instruction";
   //static ArrayList<String> Languages_array=new ArrayList<>();

    String testName;
    String industry;
    String course;
    private int questionCount;
    int totalMark;
    private String testType;
    private String testPrice;
    private String testValidity;
    String testDuration;
    String testDetails;
    String testDescriptions;
    String endTime;

    ArrayList<String> instructionsss=new ArrayList<String>();
    static String id,languageCode_selected="en";


    static ArrayList<String> languageCode=new ArrayList<>();
    static ArrayList<String> languageName=new ArrayList<>();

    //save in  sqlite










    // disallow cancel of AlertDialog on click of back button and outside touch
    //   alert.setTitle("Password ");




    private void AddGeofencebody(String testid, String userid, String api_key) {








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


                  //  TestDetailss_array.add(MyTEST_IDs.test_id_selected);
                    TestDetailss_array.add(MyTEST_IDs.unique_id_selected);
                    TestDetailss_array.add(testName);
                    TestDetailss_array.add(industry);
                    TestDetailss_array.add(course);
                    TestDetailss_array.add(questionCount+"");
                    TestDetailss_array.add(totalMark+"");
                    TestDetailss_array.add(testType);
                    TestDetailss_array.add(testPrice);
                    TestDetailss_array.add(testValidity);
                    TestDetailss_array.add(testDuration);
                    TestDetailss_array.add(testDetails);
                    TestDetailss_array.add(testDescriptions);
                    TestDetailss_array.add(endTime);





//processing[0] =false;
              //      Toast.makeText(getApplicationContext(),"success"+testName+industry,Toast.LENGTH_SHORT).show();
                    //   handler=new Handler(callback);
                    //    Message msg = null;
                    //   handler.handleMessage(msg);
                    Resume();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Store the JSON object in JSON array as objects (For level 1 array element i.e Results)
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError","Error response", error);
           //     Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

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



    private void Resume()
    {
        AddGeofencebody4(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey);

    }








    private void AddGeofencebody4(String testid, String userid, String api_key) {



        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);

        Log.e("params :",params.toString());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url2, new JSONObject(params)
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
                    getquests();
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
            //    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

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



    private void AddGeofencebody1(String testid, String userid, String api_key, String langcode) {


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
             //   Toast.makeText(getApplicationContext(),"success"+response.toString(),Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray=response.getJSONArray("instructionList");
                    for (int i=0;i<jsonArray.length();i++)
                    {   instr[0] = (JSONObject) jsonArray.get(i);


                //        Toast.makeText(getApplicationContext(),"success"+jsonArray.length(),Toast.LENGTH_SHORT).show();
                        //   handler=new Handler(callback);
                        //    Message msg = null;
                        //   handler.handleMessage(msg);
                     //   getquests();
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




private void getquests()
{
    AddGeofencebody6(MyTEST_IDs.test_id_selected, MainActivity.userid, MainActivity.apikey,languageCode_selected, MyTEST_IDs.unique_id_selected);

}





    private void AddGeofencebody6(final String testid, final String userid, final String api_key, String langcode, final String u_test_id) {


        // disallow cancel of AlertDialog on click of back button and outside touch
        //   alert.setTitle("Password ");
        //  alert.setIcon(R.drawable.ic_lock_outline_black_24dp);



        Map<String, String> params = new HashMap<>();
        params.put("testID", testid);
        params.put("userId", userid);
        params.put("api_key",api_key);
        params.put("languageCode",langcode);
        params.put("uniqueId",u_test_id);

        Log.e("params :",params.toString());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,urlq, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //
                try {

                    final JSONArray questionlist=  response.getJSONArray("question");
                    ArrayList<String> questions_Arr_list=new ArrayList<>();
                    ArrayList<String> questions_Arr_id=new ArrayList<>();
                    ArrayList<String> questions_Arr_video=new ArrayList<>();
                    ArrayList<String> questions_Arr_img=new ArrayList<>();


                    ArrayList<ArrayList> arrayListArrayList_ans_ids=new ArrayList<>();
                    ArrayList<ArrayList> arrayListArrayList_ans_values=new ArrayList<>();
                    ArrayList<ArrayList> arrayListArrayList_ans_imgs=new ArrayList<>();
                    ArrayList<ArrayList> arrayListArrayList_ans_videos=new ArrayList<>();

                        ArrayList<String> arrayList_img=new ArrayList<>();
                        ArrayList<String> arrayList_vid=new ArrayList<>();
                        ArrayList<String> arrayList_ans_id=new ArrayList<>();
                        ArrayList<String> arrayList_ans_value=new ArrayList<>();


                    for (int i=0;i<questionlist.length();i++) {
    JSONObject question = (JSONObject) questionlist.get(i);
    questions_Arr_list.add((String) question.get("question"));
    questions_Arr_id.add( (String) question.get("id"));

    questions_Arr_video.add( (String) question.get("questionVedio"));
    questions_Arr_img.add ((String) question.get("questionImage"));


    JSONArray answersArray = question.getJSONArray("answer");
    for (int ii = 0; ii < answersArray.length(); ii++) {
        JSONObject option = (JSONObject) answersArray.get(ii);

        String img = (String) option.get("answerImage");
        String video = (String) option.get("answerVideo");
        String ans_id = (String) option.get("id");
        String ans_value = (String) option.get("value");

        arrayList_ans_id.add(ans_id);
        arrayList_ans_value.add(ans_value);
        arrayList_img.add(img);
        arrayList_vid.add(video);


    }



                        arrayListArrayList_ans_ids.add(arrayList_ans_id);
    arrayListArrayList_ans_imgs.add(arrayList_img);
    arrayListArrayList_ans_values.add(arrayList_ans_value);
    arrayListArrayList_ans_videos.add(arrayList_vid);



}


                    arrayList_3_all_questions.add(questions_Arr_list);
                    arrayList_3_all_questions.add(questions_Arr_id);
                    arrayList_3_all_questions.add(questions_Arr_img);
                    arrayList_3_all_questions.add(questions_Arr_video);

                    arrayList_3_all_options.add(arrayListArrayList_ans_ids);
                    arrayList_3_all_options.add(arrayListArrayList_ans_imgs);
                    arrayList_3_all_options.add(arrayListArrayList_ans_values);
                    arrayList_3_all_options.add(arrayListArrayList_ans_videos);

                    saveoffline();
                    Log.e("aaaaaaaaaa",arrayList_3_all_options.toString());

                    Log.e("aaaaaaaaaannnnnn",arrayList_3_all_questions.toString());

                }
                catch (JSONException e) {
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
           //     Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

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

    private void saveoffline() {


//Set the values

        Gson gson = new Gson();

        String TestDetail= gson.toJson(TestDetailss_array);
        String all_question= gson.toJson(arrayList_3_all_questions);
        String all_options= gson.toJson(arrayList_3_all_options);



        DatabaseHandleroff db = new DatabaseHandleroff(context);
   //TODO
            String instructionList = "Be truthful!";
        db.addContact(new TestDetailoff(instructionList,TestDetail,all_question,all_options ));


      //  dialog.cancel();

//finish();

//startActivity(new Intent(SaveTestOfflineActivity.this,Main5Activity.class));


        context.startActivity(new Intent(context,Main5Activity.class));




    }


}
