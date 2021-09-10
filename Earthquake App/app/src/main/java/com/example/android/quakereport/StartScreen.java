package com.example.android.quakereport;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=4000;
    ImageView iv_logo ;
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        iv_logo =findViewById(R.id.imageView);
        textView = findViewById(R.id.text_view);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(iv_logo, PropertyValuesHolder.ofFloat("scaleX",1.2f)
                );
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(3);
        ValueAnimator.setFrameDelay(500);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        objectAnimator.start();

        //This method is used so that your splash activity
        //can cover the entire screen.
        //this will bind your MainActivity.class file with activity_main.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(StartScreen.this,EarthquakeActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}