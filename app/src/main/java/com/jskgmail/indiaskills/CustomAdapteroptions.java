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

import java.util.ArrayList;

public class CustomAdapteroptions extends RecyclerView.Adapter<CustomAdapteroptions.ViewHolder> {
    //int height,width;
    Context context;
    ArrayList<String> text;
    ArrayList<String> pic;
    ArrayList<String> vid;
    ArrayList<String> id;
    ArrayList<String> qno;

    static ArrayList<String> ans_clicked=new ArrayList<String>();
    static ArrayList<ArrayList<String>> ansforquest=new ArrayList<>();

    String[] op={"A","B","C","D","E","F","G","H"};


    public CustomAdapteroptions(Context context,ArrayList<String> text, ArrayList<String> pic, ArrayList<String> vid, ArrayList<String> id)
    {
        this.context = context;
        this.text=text;
        this.pic=pic;
        this.vid=vid;
        this.id=id;


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


        holder.opno.setText(op[position]+".");

        if (holder.txtviewname.isChecked())
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            //     ans_clicked.add(id.get(position));
        else
            holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);
/*

for (int i=0;i<ansforquest.size();i++)
{
    if (Integer.parseInt(qno.get(position))==(i+1))
    {
        for (int j=0;j<ans_clicked.size();j++)
        {
            if (ansforquest.get(Integer.parseInt(qno.get(position))).equals(id.get(position)))
                holder.cardView.setCardBackgroundColor(Color.LTGRAY);

        }

    }
}
*/
        holder.txtviewname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtviewname.setChecked(!holder.txtviewname.isChecked());

                if (holder.txtviewname.isChecked())
                {
                    holder.cardView.setCardBackgroundColor(Color.LTGRAY);
                    ans_clicked.add(id.get(position));
                //    ansforquest.set(Integer.parseInt(qno.get(position)),ans_clicked);
                  //  Log.e("ans_saved",ansforquest+"");

                }else
                {
                    holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);


                    //if (ans_clicked.contains(id.get(position)))
                    //ans_clicked.remove(id.get(position));


                }
                Log.e("ccccccisch", String.valueOf(ans_clicked));
            }
        });



        holder.txtviewname.setText(text.get(position));

       // for(int i=0;i<info.getPhoto().length;i++){//flipper.photoflipper(holder.getAdapterPosition());

       // Glide.with(context).load(info.getPhoto()).into(holder.photo);



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
