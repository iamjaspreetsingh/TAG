package com.jskgmail.indiaskills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.jskgmail.indiaskills.CustomAdapterbatch.ViewHolder.cam;

public class CustomAdapterbatch extends RecyclerView.Adapter<CustomAdapterbatch.ViewHolder>  {
    //int height,width;
    Context context;

    ArrayList<String> infos,infos1;



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
    public void onBindViewHolder(final CustomAdapterbatch.ViewHolder holder, int position) {

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

            }
        });

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
        ImageButton b2;

        final static int cam=0;

        Bitmap bm;




        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            title= itemView.findViewById(R.id.name);
            desc= itemView.findViewById(R.id.username);
            b2= (ImageButton)itemView. findViewById(R.id.imageButton);


        }

         protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub

   //         super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK) {
                Bundle bt = data.getExtras();
                bm = (Bitmap) bt.get("data");
                b2.setImageBitmap(bm);
                notifyDataSetChanged();
            }}







    }


      }
