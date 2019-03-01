package com.example.hani.social_app.TopNews.LoginSignup;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.R;

public class ForgotPassword extends AppCompatActivity {

    ImageView IV;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        IV = (ImageView) findViewById(R.id.forgot_password_back_IV_id);
        TV = (TextView) findViewById(R.id.forgot_password_TV4_id);

        Spannable wordtoSpan = new SpannableString(" Go to Help Center.");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 6, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TV.setText(wordtoSpan);

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword.super.onBackPressed();
            }
        });

    }
}
