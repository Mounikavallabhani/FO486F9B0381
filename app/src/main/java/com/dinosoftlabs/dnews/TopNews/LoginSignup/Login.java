package com.dinosoftlabs.dnews.TopNews.LoginSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.social_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    ImageView IV;
    TextView TV;

    private String TAG = "MainActivity";
    EditText EditEmail, EditPassword;
    String email, password;
    // Progress dialog
    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
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
        is_wifi_availeable= Variables.is_internet_avail(this);


        EditEmail = findViewById(R.id.login_username_ET_id);
        EditPassword = findViewById(R.id.login_password_ET_id);


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
                String title=getResources().getString(R.string.info);
                String msg=getResources().getString(R.string.wifi_connected_info);
                try{
                    Variables.alert_dialogue(this,""+title,""+msg);
                }catch (Exception t){

                }


            }

        }
    }
    public void LogIn(){
        Variables.showpDialog(pDialog);
        email = EditEmail.getText().toString();
        password = EditPassword.getText().toString();
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,this);
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
                   Variables.toast_msg(Login.this,"Login Successfull.");
                                      finish();

                }




            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                Toast.makeText(Login.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        };
    }

}
