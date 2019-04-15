package com.dinosoftlabs.dnews.TopNews.LoginSignup;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.social_app.R;

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
        is_wifi_availeable= Variables.is_internet_avail(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        IV = (ImageView) findViewById(R.id.forgot_password_back_IV_id);
        //TV = (TextView) findViewById(R.id.forgot_password_TV4_id);
        EditEmail = findViewById(R.id.forgot_password_email_ET_id);
        Spannable wordtoSpan = new SpannableString(" Go to Help Center.");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 6, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        TV.setText(wordtoSpan);

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
                ForgotPassword.super.onBackPressed();
            }
        });
    }

    public void forgot_password(View forgot)
    {
        if(EditEmail.getText().toString().trim().equalsIgnoreCase("")){
            EditEmail.setError(getResources().getString(R.string.email_set_errors));
        }else{

            if(is_wifi_availeable==true){
                // If internet Available
                Forgot_password();
            }else{
                String title = getResources().getString(R.string.info);
                String msg = getResources().getString(R.string.wifi_connected_info);
                try{
                    Variables.alert_dialogue(this,"" + title,"" + msg);
                }catch (Exception t){

                }


            }


        }
    }


    public void Forgot_password(){
        Variables.showpDialog(pDialog);
        String email = EditEmail.getText().toString();
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,this);
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
                try{
                    Toast.makeText(ForgotPassword.this, "" + response.getString("msg"), Toast.LENGTH_SHORT).show();
                }catch (Exception v){

                }



            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                Toast.makeText(ForgotPassword.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        };
    }


}
