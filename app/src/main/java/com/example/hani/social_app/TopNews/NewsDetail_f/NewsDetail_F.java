package com.example.hani.social_app.TopNews.NewsDetail_f;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NewsDetail_F extends AppCompatActivity {

    ImageView IV;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private List<NewsDataMode> News_List;
    private String TAG = "Saved";
    int News_id;
    TextView title,description;
    ImageView image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        Intent intent = getIntent();
        News_id = intent.getIntExtra("news_id",0); //if it's a string you stored.
        //Toast.makeText(this, ""+News_id, Toast.LENGTH_SHORT).show();
        IV = (ImageView) findViewById(R.id.item_TB_IV_id);
        title = findViewById(R.id.item_inner_RL_TV1_id);
        description = findViewById(R.id.item_inner_RL_TV6_id);
        image_view = findViewById(R.id.item_inner_RL_IV_id);

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Get News Description By Using API
        Get_Saved_News(News_id);

    }

    // Get Data From API
    public void Get_Saved_News(int news_id){
        Toast.makeText(this, "Inside Method "+news_id, Toast.LENGTH_SHORT).show();
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,NewsDetail_F.this);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'news_id': "+news_id+" }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.News_detail, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                  //  Toast.makeText(NewsDetail_F.this, "Response "+response.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + response);

                try{

                    JSONObject Arr= response.getJSONObject("msg");
                    JSONObject Arr_1 = Arr.getJSONObject("News");
                    title.setText(""+Arr_1.getString("title"));
                    description.setText(""+Arr_1.getString("description"));
                    // Add Picasso
                    Picasso.get()
                            .load(Arr_1.getString("attachment"))
                            .placeholder(R.mipmap.ic_dnews)
                            .error(R.mipmap.ic_dnews)
                            .into(image_view);

                }catch (Exception v){
                       Toast.makeText(NewsDetail_F.this, "Please try again later. "+v.toString(), Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(NewsDetail_F.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
    // ENd Data From API



}
