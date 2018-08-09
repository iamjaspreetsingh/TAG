package com.jskgmail.indiaskills;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapterfeed extends BaseAdapter {
    Activity context;
    ArrayList<String> q_name;
    ArrayList<String> id;
 //   ArrayList<String> uid;
    public ListViewAdapterfeed(Activity context, ArrayList<String> name, ArrayList<String> id//, ArrayList<String> uid
    )
    {
        super();
        this.context=context;
        this.q_name=name;
        this.id=id;
       // this.uid=uid;

    }


    @Override
    public int getCount() {

        return q_name.size();
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
        TextView txtviewname;
      //  TextView txtviewcity;
      //  FloatingTextButton test;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_feedback,null);
            holder=new ViewHolder();
            holder.txtviewname=(TextView)convertView.findViewById(R.id.quest);
        //    holder.test=convertView.findViewById(R.id.fab1);

          //  holder.txtviewcity=(TextView)convertView.findViewById(R.id.city);

            holder.txtviewname.setText(q_name.get(position));

           // holder.txtviewcity.setText(id.get(position));

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


       // notifyDataSetChanged();
        return convertView;
    }


}
