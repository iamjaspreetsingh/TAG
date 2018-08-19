package com.jskgmail.indiaskills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class PatternActivity extends AppCompatActivity {
    PatternLockView  mPatternLockView;
    TextView heading;
    static String mypattern="";
    String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

heading=findViewById(R.id.heading);
        Intent intent = getIntent();
        p= intent.getStringExtra("p");

         mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }


    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.e(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            if (p.equals("p"))
            {
               mypattern= PatternLockUtils.patternToString(mPatternLockView, pattern);
               finish();
                startActivity(new Intent(PatternActivity.this,Main5Activity.class));

            }
            else  if (p.equals("c"))
            {
                if (mypattern.equals(PatternLockUtils.patternToString(mPatternLockView, pattern)))
                    finish();
            }
            else  Toast.makeText(getApplicationContext(),"Pattern is wrong",Toast.LENGTH_LONG);

        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

}
