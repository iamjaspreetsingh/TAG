package com.jskgmail.indiaskills;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapteroptions extends RecyclerView.Adapter<CustomAdapteroptions.ViewHolder> {
    //int height,width;
    Context context;
    ArrayList<String> text;
    ArrayList<String> pic;
    ArrayList<String> vid;
    ArrayList<String> id;
    int qno;

    static ArrayList<String> ans_clicked=new ArrayList<String>();
    static ArrayList<String> ansforquest=new ArrayList<>();

    String[] op={"A","B","C","D","E","F","G","H"};


    public CustomAdapteroptions(Context context,int qno,ArrayList<String> text, ArrayList<String> pic, ArrayList<String> vid, ArrayList<String> id)
    {
        this.context = context;
        this.text=text;
        this.pic=pic;
        this.vid=vid;
        this.id=id;
        this.qno=qno;


    }

//    public void getScreenSize(){
//        DisplayMetrics displayMetrics=new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        height=displayMetrics.heightPixels;
//        width=displayMetrics.widthPixels;
//    }

    @Override
    public CustomAdapteroptions.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= inflater.inflate(R.layout.options_test,parent,false);
        CustomAdapteroptions.ViewHolder holder= new CustomAdapteroptions.ViewHolder(itemView);
        // Toast.makeText(context, "blablabla", Toast.LENGTH_SHORT).show();

      //  getScreenSize();
        return holder;

    }

    @Override
    public void onBindViewHolder(final CustomAdapteroptions.ViewHolder holder, final int position) {

    //    batch_info info= infos.get(position);

        //  Toast.makeText(context, feed.getUsername(), Toast.LENGTH_SHORT).show();
       // ansforquest.set(qno-1,ans_clicked);

        holder.opno.setText(op[position]+".");

        if (holder.txtviewname.isChecked())
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            //     ans_clicked.add(id.get(position));
        else
            holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);


//if (ansforquest.size()>qno-1)
    for (int j=0;j<ansforquest.size();j++)
            if (ansforquest.get(j).equals(id.get(position))) {
                holder.cardView.setCardBackgroundColor(Color.LTGRAY);
                holder.txtviewname.setChecked(true);
            }




        holder.txtviewname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.txtviewname.setChecked(!holder.txtviewname.isChecked());

                if (holder.txtviewname.isChecked())
                {
                    holder.cardView.setCardBackgroundColor(Color.LTGRAY);
                    ans_clicked.add(id.get(position));
                   // ansforquest=new ArrayList<String>();

                    ansforquest.add(id.get(position));

                    Log.e("ans_saved",qno-1+"");
                    Log.e("ans_saved",ansforquest+"");

                }else
                {
                    holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);
                    ans_clicked.remove(id.get(position));
                   // ansforquest=new ArrayList<String>();

                    for (int k=0;k<ansforquest.size();k++)
                        if (ansforquest.get(k).equals(id.get(position)))
                    ansforquest.remove(k);
                    Log.e("ans_saved",ansforquest+"");


                    //if (ans_clicked.contains(id.get(position)))
                    //ans_clicked.remove(id.get(position));


                }
                Log.e("ccccccisch", String.valueOf(ans_clicked));
            }
        });



        holder.txtviewname.setText(text.get(position));

       // for(int i=0;i<info.getPhoto().length;i++){//flipper.photoflipper(holder.getAdapterPosition());

       // Glide.with(context).load(info.getPhoto()).into(holder.photo);


        if (pic.get(position).equals(""))
        {
            holder.img.setVisibility(View.GONE);
        }
        else {
            holder.img.setVisibility(View.VISIBLE);
            Glide.with(context).load(pic.get(position)).into(holder.img);


        }

        if (vid.get(position).equals(""))
        {
            holder.vid.setVisibility(View.GONE);
        }else {
            holder.vid.setVisibility(View.VISIBLE);

            holder.vid.start();
        }


    }




    @Override
    public int getItemCount() {
        return (text==null)?0:text.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        CheckedTextView txtviewname;
        ImageView img;
        VideoView vid;
        TextView opno;
        RelativeLayout rl;
        CardView cardView;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
            txtviewname=itemView.findViewById(R.id.text);
            img=itemView.findViewById(R.id.imageView);
            vid=itemView.findViewById(R.id.videoView);
            cardView=itemView.findViewById(R.id.car);
            rl=itemView.findViewById(R.id.rl);
            opno=itemView.findViewById(R.id.opno);

        }
    }
}
