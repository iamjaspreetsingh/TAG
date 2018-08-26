package com.jskgmail.indiaskills;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class CustomAdaptertestlist extends RecyclerView.Adapter<CustomAdaptertestlist.ViewHolder> {
    //int height,width;
    Context context;
    ArrayList<String> name;
    ArrayList<String> id;
    ArrayList<String> uid;
    String url7="http://staging.tagusp.com/api/users/OfflineTest";


    public CustomAdaptertestlist(Context context,ArrayList<String>uid , ArrayList<String> id,ArrayList<String> name) {
        this.context = context;
        this.name=name;
        this.id=id;
        this.uid=uid;

    }

//    public void getScreenSize(){
//        DisplayMetrics displayMetrics=new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        height=displayMetrics.heightPixels;
//        width=displayMetrics.widthPixels;
//    }

    @Override
    public CustomAdaptertestlist.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.list_tests,parent,false);
        CustomAdaptertestlist.ViewHolder holder= new CustomAdaptertestlist.ViewHolder(itemView);
        // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

      //  getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final CustomAdaptertestlist.ViewHolder holder, final int position) {

    //    batch_info info= infos.get(position);

        //  Toast.makeText(context, feed.getUsername(), Toast.LENGTH_SHORT).show();


        holder.txtviewname.setText(name.get(position));
        holder.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyTEST_IDs.test_id_selected=id.get(position);
                MyTEST_IDs.unique_id_selected=uid.get(position);
                MyTEST_IDs.test_name_selected=name.get(position);

                MainActivity.online=true;
                context.startActivity(new Intent(context,Main3Activity.class));

            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTEST_IDs.test_id_selected=id.get(position);
                MyTEST_IDs.unique_id_selected=uid.get(position);
                MyTEST_IDs.test_name_selected=name.get(position);





                SaveTestOfflineActivity saveTestOfflineActivity = new SaveTestOfflineActivity();
                SaveTestOfflineActivity.context=context;
                saveTestOfflineActivity.onCreate();



            }
        });

       // for(int i=0;i<info.getPhoto().length;i++){//flipper.photoflipper(holder.getAdapterPosition());

       // Glide.with(context).load(info.getPhoto()).into(holder.photo);



    }




    @Override
    public int getItemCount() {
        return (id==null)?0:id.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtviewname;
        //  TextView txtviewcity;
        FloatingTextButton test,download;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            txtviewname=(TextView)itemView.findViewById(R.id.nam);
            test=itemView.findViewById(R.id.fab1);
            download=itemView.findViewById(R.id.fab11);


        }
    }


















    private void AddGeofencebody44(String userid, String apikey,String candidateID) {


        final Map<String, String> params = new HashMap<>();

        params.put("userId", userid);
        params.put("api_key",apikey);
        params.put("testID",MyTEST_IDs.test_id_selected);
        params.put("uniqueID",MyTEST_IDs.unique_id_selected);
        params.put("version","m");

        params.put("data",SaveTestOfflineActivity.alldata);

        //  params.put("media", String.valueOf(bm));


        Log.e("params :",params.toString());
        //   ListViewAdapteroptions.ans_clicked=new ArrayList<String>();


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url7, new JSONObject(params)
                //   null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError","Error response", error);

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
