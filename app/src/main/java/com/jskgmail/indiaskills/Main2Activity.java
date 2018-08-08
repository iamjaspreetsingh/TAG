package com.jskgmail.indiaskills;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    static String test_id_selected="";
    static String unique_id_selected="";
    private FloatingTextButton test1,test2,test3,test4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView=findViewById(R.id.listview);
        ListViewAdapter adapter=new ListViewAdapter(Main2Activity.this,MainActivity.arrayList_test_name,MainActivity.arrayList_test_id,MainActivity.arrayList_u_test_id);
        listView.setAdapter(adapter);
/*        test1=findViewById(R.id.fab1);
        test2=findViewById(R.id.fab2);
        test3=findViewById(R.id.fab3);
        test4=findViewById(R.id.fab4);


        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Main2Activity.this,Main3Activity.class));
            }
        });

        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Main3Activity.class));
            }
        });

        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Main3Activity.class));
            }
        });

        test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,Main3Activity.class));
            }
        });
*/
    }















}













