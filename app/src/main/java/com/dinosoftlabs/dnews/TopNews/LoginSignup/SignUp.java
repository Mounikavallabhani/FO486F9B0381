package com.dinosoftlabs.dnews.TopNews.LoginSignup;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.VolleyReq.IResult;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    ImageView IV;
    TextView TV;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private String TAG = "MainActivity";
   String firebase_tokem;
    String email, password;
    // Progress dialog
    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    EditText edit_first_name, edit_last_name, edit_email, edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        IV = (ImageView) findViewById(R.id.login_back_IV_id);
        TV = (TextView) findViewById(R.id.login_TV2_id);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        //// Check Network Availability
        is_wifi_availeable= Variables.is_internet_avail(this);


        edit_email = findViewById(R.id.login_email_ET_id);
        edit_password = findViewById(R.id.login_password_ET_id);
        edit_first_name = findViewById(R.id.login_first_name_ET_id);
        edit_last_name = findViewById(R.id.login_last_name_ET_id);

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
    }




    public void signup_check(View v){

        if(edit_first_name.getText().toString().trim().equalsIgnoreCase("")){
            edit_first_name.setError(getResources().getString(R.string.first_name_set_errors));
        }else if(edit_last_name.getText().toString().trim().equalsIgnoreCase("")){
            edit_last_name.setError(getResources().getString(R.string.last_name_set_errors));
        }else if(edit_email.getText().toString().trim().equalsIgnoreCase("")){
            edit_email.setError(getResources().getString(R.string.email_set_errors));
        }else if(edit_password.getText().toString().trim().equalsIgnoreCase("")){
            edit_password.setError(getResources().getString(R.string.password_set_errors));
        }else if(Variables.isEmailValid(edit_email.getText().toString()) == false ){
            edit_email.setError(getResources().getString(R.string.email_validation_errors));
        }
        else{
            if(is_wifi_availeable == true){
                // If internet Available
                SignUp();
            }else{
                String title = getResources().getString(R.string.info);
                String msg = getResources().getString(R.string.wifi_connected_info);
                try{
                    Variables.alert_dialogue(this,""+title,""+msg);
                }catch (Exception t){

                }


            }

        }
    } // End Check Method


    public void SignUp(){

        Variables.showpDialog(pDialog);
        String first_name = edit_first_name.getText().toString();
        String last_name = edit_last_name.getText().toString();
        email = edit_email.getText().toString();
        password = edit_password.getText().toString();
        int role = 1;

        firebase_tokem = Variables.get_firebase_token(SignUp.this);


        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,this);
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'email': "+email+", 'password': "+password+"," +
                    " 'first_name': "+first_name+", 'last_name': "+last_name+", " +
                    "'role': "+role+" , 'firebase_toke' : "+firebase_tokem+" }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.SignUpApi, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                // Add User info Json into Shared Prefrence

                SharedPrefrence.save_response_share(SignUp.this,response.toString(), SharedPrefrence.shared_user_login_detail_key);




                finish();

            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                Variables.toast_msg(SignUp.this,""+error.toString());

            }
        };
    }



}
