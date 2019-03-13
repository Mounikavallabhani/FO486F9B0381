package com.example.hani.social_app.TopNews.LoginSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.TouchDelegate;
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
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    ImageView IV;
    TextView TV;

    private String TAG = "MainActivity";
    EditText EditEmail, EditPassword;
    String email, password;
    // Progress dialog
    // Progress dialog
    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    //defining AwesomeValidation object
   // private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        IV = (ImageView) findViewById(R.id.login_back_IV_id);
        TV = (TextView) findViewById(R.id.login_TV2_id);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        //// Check Network Availability
        is_wifi_availeable=Variables.is_internet_avail(this);
        //     Variables.hide(loading,loading_text);

        EditEmail = findViewById(R.id.login_username_ET_id);
        EditPassword = findViewById(R.id.login_password_ET_id);
       // animationView = findViewById(R.id.btn_play_pause);


        final View parent = (View) IV.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                IV.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , IV));
            }
        });

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
            EditEmail.setError(getResources().getString(R.string.email_set_errors));
        }else if(EditPassword.getText().toString().trim().equalsIgnoreCase("")){
            EditPassword.setError(getResources().getString(R.string.password_set_errors));
                }else if(Variables.isEmailValid(EditEmail.getText().toString()) == false ){
            EditEmail.setError(getResources().getString(R.string.email_validation_errors));
        }else{

            if(is_wifi_availeable==true){
                // If internet Available
                LogIn();
            }else{
                String title="Info";
                String msg="You are connected to internet.Please turn on internet and try again later. Thanks.";
                try{
                    Variables.alert_dialogue(this,""+title,""+msg);
                }catch (Exception t){

                }


            }

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
                // Add User info Json into Shared Prefrence

                SharedPrefrence.save_response_share(Login.this,response.toString(),SharedPrefrence.shared_user_login_detail_key);

                if(response.toString().contains("incorrect") || response.toString().contains("201") ){
                    String title = "Info";
                    String msg = "Invalid Credentials.";
                    Variables.alert_dialogue(Login.this,""+title,""+msg);


                }else{
                    Log.d(TAG, "Volley requester " + requestType);
                    Variables.toast_msg(Login.this,"Login Successfull.");
                    Toast.makeText(Login.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Volley JSON post" + response);

                    finish();

                }




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
