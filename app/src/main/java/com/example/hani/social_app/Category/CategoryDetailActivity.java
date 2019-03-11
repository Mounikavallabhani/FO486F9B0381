package com.example.hani.social_app.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.squareup.picasso.Picasso;

public class CategoryDetailActivity extends AppCompatActivity {
    String categoruy_name,category_img_url;
    TextView category_title;
    ImageView category_img;
    ImageView IV ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        Intent intent = getIntent();
        categoruy_name = intent.getStringExtra("category_name"); //if it's a string you stored.
        category_img_url = intent.getStringExtra("category_img_url");

        // Bind Views
        category_title = findViewById(R.id.category_title);
        category_img = findViewById(R.id.more_details_item_IV_id);
        IV = findViewById(R.id.item_TB_IV_id);
        category_title.setText(""+categoruy_name);
        // Back Button
        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
        Picasso.get()
                .load(category_img_url).fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(category_img);


    }
}
