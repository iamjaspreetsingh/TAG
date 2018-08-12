package com.jskgmail.indiaskills;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class ListViewAdapterOffline extends BaseAdapter {
    Activity context;
    ArrayList<String> name;
    ArrayList<String> uid;
    public ListViewAdapterOffline(Activity context, ArrayList<String> name, ArrayList<String> uid)
    {
        super();
        this.context=context;
        this.name=name;
        this.uid=uid;

    }


    @Override
    public int getCount() {

        return name.size();
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
        FloatingTextButton test;
        ImageView delete;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_off_tests,null);
            holder=new ViewHolder();
            holder.txtviewname=(TextView)convertView.findViewById(R.id.nam);
            holder.test=convertView.findViewById(R.id.fab1);

            holder.delete=convertView.findViewById(R.id.delete);
          //  holder.txtviewcity=(TextView)convertView.findViewById(R.id.city);

            holder.txtviewname.setText(name.get(position));
            holder.test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity.online=false;
                    MyTEST_IDs.unique_id_selected=uid.get(position);
                    MyTEST_IDs.test_name_selected=name.get(position);

                    context.startActivity(new Intent(context,Main3Activity.class));

                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    LayoutInflater inflater = context.getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.loading_ques, null);
                    //final ProgressBar progressBar=alertLayout.findViewById(R.id.progressBar);
                    Button yes=alertLayout.findViewById(R.id.yes);
                    Button no=alertLayout.findViewById(R.id.no);

                    AlertDialog.Builder alert= new AlertDialog.Builder(context);
                    alert.setView(alertLayout);



                    final AlertDialog dialog= alert.create();
                    dialog.show();


                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            Type type = new TypeToken<ArrayList<String>>() {
                            }.getType();

                            Gson gson = new Gson();
                            DatabaseHandleroff db = new DatabaseHandleroff(context);
                            String test_det_str = null;

                            ArrayList<String> testdet=new ArrayList<>();

                            List<TestDetailoff> contacts = db.getAllContacts();
                            for (TestDetailoff cn : contacts) {
                                test_det_str = cn.getTestDetailss_array();

                                testdet = gson.fromJson(test_det_str, type);

                                if (testdet.get(0).equals(uid.get(position))) {
                                    db.deleteContact(cn);



                                }
                            }

                            dialog.cancel();
                            context.finish();
                            context.startActivity(new Intent(context,Main5Activity.class));
                            Toast.makeText(context,"Test Removed",Toast.LENGTH_LONG).show();











                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();

                        }
                    });




                }
            });

           // holder.txtviewcity.setText(id.get(position));

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


       // notifyDataSetChanged();
        return convertView;
    }


}
