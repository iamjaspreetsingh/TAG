package com.jskgmail.indiaskills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static android.app.Activity.RESULT_OK;
import static com.jskgmail.indiaskills.CustomAdapterbatch.ViewHolder.cam;

public class CustomAdapterbatch extends RecyclerView.Adapter<CustomAdapterbatch.ViewHolder>  {
    //int height,width;
    Context context;
int pos;
    ArrayList<String> infos,infos1;

    String url="http://staging.tagusp.com/api/users/Upload_candidate_photo_videos";


    public CustomAdapterbatch(Context context, ArrayList<String> infos, ArrayList<String> infos1) {
        this.context = context;
        this.infos = infos;
        this.infos1 = infos1;

    }

//    public void getScreenSize(){
//        DisplayMetrics displayMetrics=new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        height=displayMetrics.heightPixels;
//        width=displayMetrics.widthPixels;
//    }

    @Override
    public CustomAdapterbatch.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.list_batch,parent,false);
        CustomAdapterbatch.ViewHolder holder= new CustomAdapterbatch.ViewHolder(itemView);
        // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

      //  getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final CustomAdapterbatch.ViewHolder holder, final int position) {

    //    batch_info info= infos.get(position);

        //  Toast.makeText(context, feed.getUsername(), Toast.LENGTH_SHORT).show();


       holder.title.setText(infos.get(position));
       holder.desc.setText(infos1.get(position));


        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                ((Activity) context).startActivityForResult(i,cam);
                holder.b2.setVisibility(View.GONE);
pos=position;

            }
        });
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                ((Activity) context).startActivityForResult(i,cam);
                holder.b1.setVisibility(View.GONE);
                pos=position;
            }
        });

        if ((holder.b1.getVisibility()==View.GONE)&&(holder.b2.getVisibility()==View.GONE))
        {
            holder.cc.setCardBackgroundColor(Color.GREEN);
        }
       // for(int i=0;i<info.getPhoto().length;i++){//flipper.photoflipper(holder.getAdapterPosition());

       // Glide.with(context).load(info.getPhoto()).into(holder.photo);



    }




    @Override
    public int getItemCount() {
        return (infos==null)?0:infos.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements com.jskgmail.indiaskills.ViewHolder {

        TextView desc;
        ViewFlipper viewFlipper;

        TextView title;
        FloatingTextButton b1;

        FloatingTextButton b2;

        final static int cam=0;

        Bitmap bm;
        CardView cc;




        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            title= itemView.findViewById(R.id.name);
            desc= itemView.findViewById(R.id.username);
            b1= itemView. findViewById(R.id.fab);
cc=itemView.findViewById(R.id.cc);
            b2= itemView. findViewById(R.id.fab1);


        }

         protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub

   //         super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK) {
                Bundle bt = data.getExtras();
                bm = (Bitmap) bt.get("data");

                Toast.makeText(context,"Image uploaded",Toast.LENGTH_LONG).show();

                File file = new File("path");
                OutputStream os = null;
                try {
                    os = new BufferedOutputStream(new FileOutputStream(file));

                    bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                AddGeofencebody44(MainActivity.userid,MainActivity.apikey,infos1.get(pos),file);



              //  b2.setImageBitmap(bm);
        //        notifyDataSetChanged();
            }}


    }



    private void AddGeofencebody44(String userid, String apikey,String candidateID,File bm) {


            Map<String, String> params = new HashMap<>();

            params.put("userId", userid);
            params.put("api_key",apikey);
            params.put("testID",MyTEST_IDs.test_id_selected);
            params.put("uniqueID",MyTEST_IDs.unique_id_selected);
            params.put("type","1");
            params.put("candidate_id",candidateID);
            params.put("version","P");
            params.put("media", String.valueOf(bm));

            Log.e("params :",params.toString());
         //   ListViewAdapteroptions.ans_clicked=new ArrayList<String>();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params)
                    //   null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


              //      Toast.makeText(getApplicationContext(),"succ submit",Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VolleyError","Error response", error);
                    Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();

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



