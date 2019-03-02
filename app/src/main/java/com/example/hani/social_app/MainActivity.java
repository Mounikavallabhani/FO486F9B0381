package com.example.hani.social_app;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.Category.Catergory;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.SavedArticles.Saved;
import com.example.hani.social_app.Search.Search;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.TopNews.Discover;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RelativeLayout RL;

    private String TAG = "MainActivity";
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    boolean is_wifi_availeable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is_wifi_availeable=Variables.is_internet_avail(this);

        RL = (RelativeLayout) findViewById(R.id.mainactivity_RL_id);

        MainFragment F = new MainFragment();
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.mainactivity_RL_id,F).commit();

        Variables.toast_msg(this,"Internet "+is_wifi_availeable);

      //  SharedPrefrence.save_response_share(this,"Usama Daood");

//        SharedPrefrence.get_offline(this,"name");
        // Get Response
      //  get_response();
    }
    // Testing Purpose.

    public void get_response(){
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,this);
      //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'user_id': 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", "http://bringthings.com/news/api/showNews", sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                //Variables.toast_msg(MainActivity.this,""+error.toString());
                Variables.Log_d_msg(MainActivity.this,""+response.toString());
//
//                Log.d(TAG, "Volley requester " + requestType);
//                Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Volley JSON post" + response);
                // Get Objects
//                try{
//                    Object obj = response.getJSONArray("msg");
//                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
//                }catch (Exception v){
//
//                }


            }


            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.toast_msg(MainActivity.this,""+error.toString());
                Variables.Log_d_msg(MainActivity.this,""+error.toString());
                Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(MainActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
