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
import com.example.hani.social_app.SharedPref.SharedPrefrence;

import org.json.JSONArray;
import org.json.JSONObject;

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

      // boolean is_avail = Variables.is_internet_avail(Splashscreen.this);

        String user_info_json = SharedPrefrence.get_offline(Splashscreen.this,SharedPrefrence.shared_user_login_detail_key);
        if(user_info_json != null){
            // ==> If Values is not Null
            try {
               // Variables.toast_msg(Splashscreen.this,"User Info "+user_info_json);
                JSONObject response = new JSONObject(user_info_json);
                JSONObject Arr= response.getJSONObject("msg");
                JSONObject Arr_1 = Arr.getJSONObject("User");
                Arr_1.getString("first_name");

            }catch (Exception b){

            }
        }

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
