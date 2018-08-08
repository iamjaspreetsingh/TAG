package com.jskgmail.indiaskills;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionListAdapter extends BaseAdapter {
    Activity context;
    ArrayList<String> no;

    public QuestionListAdapter(QuestionsActivity context, ArrayList<String> no)
    {
        super();
        this.context=context;
        this.no=no;


    }


    @Override
    public int getCount() {

        return no.size();
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
        TextView txtviewno;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.questionlist,null);
            holder=new ViewHolder();
            holder.txtviewno=(TextView)convertView.findViewById(R.id.qno);
            holder.txtviewno.setRotation(-90);

;




        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


        // notifyDataSetChanged();
        return convertView;
    }










}
