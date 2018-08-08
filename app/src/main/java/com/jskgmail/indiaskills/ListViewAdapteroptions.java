package com.jskgmail.indiaskills;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
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
        RadioButton txtviewname;
        ImageView img;
        VideoView vid;
TextView opno;
RelativeLayout rl;

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

            holder.rl=convertView.findViewById(R.id.rl);
            holder.opno=convertView.findViewById(R.id.opno);
            holder.opno.setText(op[position]+".");

            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.txtviewname.isChecked())
                        Log.e("ccccccc","dddd");
                    else
                        Log.e("ccccccc","dgggddd");

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
