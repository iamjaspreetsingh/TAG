package com.jskgmail.indiaskills;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;


public class ListViewAdapteroptions extends BaseAdapter {
    Activity context;
    ArrayList<String> text;
    ArrayList<String> pic;
    ArrayList<String> vid;
    ArrayList<String> id;
    static ArrayList<String> ans_clicked=new ArrayList<String>();

    String[] op={"A","B","C","D","E","F"};

    public ListViewAdapteroptions(Activity context, ArrayList<String> text, ArrayList<String> pic, ArrayList<String> vid, ArrayList<String> id)
    {
        super();
        this.context=context;
        this.text=text;
        this.pic=pic;
        this.vid=vid;
        this.id=id;


    }


    @Override
    public int getCount() {

        return text.size();
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
        CheckedTextView txtviewname;
        ImageView img;
        VideoView vid;
TextView opno;
RelativeLayout rl;
CardView cardView;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.options_test,null);
            holder=new ViewHolder();
            holder.txtviewname=convertView.findViewById(R.id.text);
            holder.img=convertView.findViewById(R.id.imageView);
            holder.vid=convertView.findViewById(R.id.videoView);
            holder.txtviewname.setChecked(false);

            holder.cardView=convertView.findViewById(R.id.car);
            holder.rl=convertView.findViewById(R.id.rl);
            holder.opno=convertView.findViewById(R.id.opno);
            holder.opno.setText(op[position]+".");

            if (holder.txtviewname.isChecked())
                holder.cardView.setCardBackgroundColor(Color.LTGRAY);
                //     ans_clicked.add(id.get(position));
            else
                holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);


                holder.txtviewname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       holder.txtviewname.setChecked(!holder.txtviewname.isChecked());

                       if (holder.txtviewname.isChecked())
                       {
                           holder.cardView.setCardBackgroundColor(Color.LTGRAY);
                         ans_clicked.add(id.get(position));

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
if (pic.get(position).equals(""))
{
    holder.img.setVisibility(View.GONE);
}
else     holder.img.setVisibility(View.VISIBLE);

            if (vid.get(position).equals(""))
{
    holder.vid.setVisibility(View.GONE);
}else     holder.vid.setVisibility(View.VISIBLE);


        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }





      //  notifyDataSetChanged();
        return convertView;
    }










}
