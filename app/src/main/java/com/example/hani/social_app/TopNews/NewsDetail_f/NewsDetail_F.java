package com.example.hani.social_app.TopNews.NewsDetail_f;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.hani.social_app.R;

public class NewsDetail_F extends AppCompatActivity {

    ImageView IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        IV = (ImageView) findViewById(R.id.item_TB_IV_id);

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
