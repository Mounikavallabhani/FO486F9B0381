package com.example.hani.social_app.TopNews.LoginSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.VolleyError;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.MainActivity;
import com.example.hani.social_app.R;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    ImageView IV;
    TextView TV;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private String TAG = "MainActivity";
    EditText EditEmail, EditPassword;
    String email, password;
    // Progress dialog
    // Progress dialog
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        IV = (ImageView) findViewById(R.id.login_back_IV_id);
        TV = (TextView) findViewById(R.id.login_TV2_id);
//        loading = findViewById(R.id.progressbar);
//        loading_text = findViewById(R.id.loading);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


   //     Variables.hide(loading,loading_text);

        EditEmail = findViewById(R.id.login_username_ET_id);
        EditPassword = findViewById(R.id.login_password_ET_id);
       // animationView = findViewById(R.id.btn_play_pause);
        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      // animationView.setAnimation("lottie_material_loader.json");

        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });
    }

    public void check(View v){
        if(EditEmail.getText().toString().trim().equalsIgnoreCase("")){
            EditEmail.setError("Email or Username cant be empty.");
        }else if(EditPassword.getText().toString().trim().equalsIgnoreCase("")){
            EditPassword.setError("Password cant be empty.");
        }else{
            LogIn();
        }
    }
    // Testing Purpose.
    public void LogIn(){
        Variables.showpDialog(pDialog);
        email = EditEmail.getText().toString();
        password = EditPassword.getText().toString();
        initVolleyCallback();

//        email":"a@a.com
        mVolleyService = new VolleyService(mResultCallback,this);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'email': "+email+", 'password': "+password+" }");
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
                Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(Login.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + response);
            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(Login.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
