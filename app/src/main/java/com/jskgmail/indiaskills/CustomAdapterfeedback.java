package com.jskgmail.indiaskills;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterfeedback extends RecyclerView.Adapter<CustomAdapterfeedback.ViewHolder> {
    //int height,width;
    Context context;
    ArrayList<String> infos,infos1;



    public CustomAdapterfeedback(Context context, ArrayList<String> infos, ArrayList<String> infos1) {
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
    public CustomAdapterfeedback.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.list_feedback,parent,false);
        CustomAdapterfeedback.ViewHolder holder= new CustomAdapterfeedback.ViewHolder(itemView);
        // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

      //  getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final CustomAdapterfeedback.ViewHolder holder, int position) {

    //    batch_info info= infos.get(position);

        //  Toast.makeText(context, feed.getUsername(), Toast.LENGTH_SHORT).show();


       holder.title.setText((position+1)+".)  "+infos.get(position));
     //  holder.desc.setText(infos1.get(position));

       // for(int i=0;i<info.getPhoto().length;i++){//flipper.photoflipper(holder.getAdapterPosition());

       // Glide.with(context).load(info.getPhoto()).into(holder.photo);



    }




    @Override
    public int getItemCount() {
        return (infos==null)?0:infos.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        EditText desc;

        TextView title;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            title= itemView.findViewById(R.id.quest);
            desc= itemView.findViewById(R.id.editText2);


        }
    }
}
