package com.example.hani.social_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.CodeClasses.Variables;

public class Splashscreen extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen);

        imageView = (ImageView) findViewById(R.id.splashscreen_imageview_id);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Variables.height = displayMetrics.heightPixels;
        Variables.width = displayMetrics.widthPixels;



        Animation splashscreen = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageView.startAnimation(splashscreen);

        Thread thread = new Thread() {
            @Override
            public void run() {

                try {

                    sleep(3000);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                startActivity(new Intent(Splashscreen.this, MainActivity.class));
                finish();

            }

        };

        thread.start();

    }
}
