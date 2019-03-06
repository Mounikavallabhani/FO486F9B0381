package com.example.hani.social_app.TopNews.LoginSignup;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
    private String TAG = "ForgotPassword";
    ImageView IV;
    TextView TV;
    EditText EditEmail;
    boolean is_wifi_availeable;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        //// Check Network Availability
        is_wifi_availeable=Variables.is_internet_avail(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        IV = (ImageView) findViewById(R.id.forgot_password_back_IV_id);
        //TV = (TextView) findViewById(R.id.forgot_password_TV4_id);
        EditEmail = findViewById(R.id.forgot_password_email_ET_id);
        Spannable wordtoSpan = new SpannableString(" Go to Help Center.");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 6, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        TV.setText(wordtoSpan);

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword.super.onBackPressed();
            }
        });
    }

    public void forgot_password(View forgot)
    {
        if(EditEmail.getText().toString().trim().equalsIgnoreCase("")){
            EditEmail.setError("Email cant be empty.");
        }else{

            if(is_wifi_availeable==true){
                // If internet Available
                Forgot_password();
            }else{
                String title="Info";
                String msg="You are connected to internet. Please turn on internet and try again later. Thanks.";
                try{
                    Variables.alert_dialogue(this,""+title,""+msg);
                }catch (Exception t){

                }


            }


        }
    }


    public void Forgot_password(){
        Variables.showpDialog(pDialog);
        String email = EditEmail.getText().toString();
      //  password = EditPassword.getText().toString();
        initVolleyCallback();

//        email":"a@a.com
        mVolleyService = new VolleyService(mResultCallback,this);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'email': "+email+" }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.LoginApi, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                // Add User info Json into Shared Prefrence

                SharedPrefrence.save_response_share(ForgotPassword.this,response.toString(),SharedPrefrence.shared_user_login_detail_key);


                Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(ForgotPassword.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + response);

            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(ForgotPassword.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }


}
