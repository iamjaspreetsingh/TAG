package com.jskgmail.indiaskills;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class ListViewAdapter extends BaseAdapter {
    Activity context;
    ArrayList<String> name;
    ArrayList<String> id;
    ArrayList<String> uid;
    public ListViewAdapter(Activity context, ArrayList<String>uid , ArrayList<String> id,ArrayList<String> name)
    {
        super();
        this.context=context;
        this.name=name;
        this.id=id;
        this.uid=uid;

    }


    @Override
    public int getCount() {

        return uid.size();
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
        FloatingTextButton test,download;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_tests,null);
            holder=new ViewHolder();
            holder.txtviewname=(TextView)convertView.findViewById(R.id.nam);
            holder.test=convertView.findViewById(R.id.fab1);
            holder.download=convertView.findViewById(R.id.fab11);

          //  holder.txtviewcity=(TextView)convertView.findViewById(R.id.city);

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



                    LayoutInflater inflater = context.getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.loading, null);
                    TextView text=alertLayout.findViewById(R.id.text);
                    text.setText("Downloading the test...");
                    //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
                    AlertDialog.Builder alert= new AlertDialog.Builder(context);

                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);



                    final AlertDialog dialog= alert.create();
                    dialog.show();


                    SaveTestOfflineActivity saveTestOfflineActivity = new SaveTestOfflineActivity();
                   SaveTestOfflineActivity.context=context;
                    saveTestOfflineActivity.onCreate();



                }
            });
           // holder.txtviewcity.setText(id.get(position));

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


        notifyDataSetChanged();
        return convertView;
    }


}
