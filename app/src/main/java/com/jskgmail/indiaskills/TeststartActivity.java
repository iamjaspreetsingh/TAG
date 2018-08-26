package com.jskgmail.indiaskills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class TeststartActivity extends AppCompatActivity {
 static String IDTestCompletedoff="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teststart);

        FloatingTextButton theory=findViewById(R.id.fab1);
        FloatingTextButton practical=findViewById(R.id.fab2);
        FloatingTextButton complete=findViewById(R.id.fab3);

        theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        practical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=(new Intent(TeststartActivity.this,Main5Activity.class));
          //      i.putExtra("completed_test",testidselected);
                startActivity(i);
              IDTestCompletedoff=  MyTEST_IDs.test_id_selected;
            }
        });


    }
}
