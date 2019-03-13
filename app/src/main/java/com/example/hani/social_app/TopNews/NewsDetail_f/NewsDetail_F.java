package com.example.hani.social_app.TopNews.NewsDetail_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TouchDelegate;
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
    ImageView like_button, dislike_button;
    private ProgressDialog pDialog;
    int num_of_click_for_font_size = 0;
    int like_dislike = 0;
    //int Dis_liked = 0;
    boolean is_wifi_availeable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        Intent intent = getIntent();
        News_id = intent.getIntExtra("news_id",0); //if it's a string you stored.
        like_dislike = intent.getIntExtra("like_or_dislike",0);
        //Toast.makeText(this, ""+News_id, Toast.LENGTH_SHORT).show();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        IV = (ImageView) findViewById(R.id.item_TB_IV_id);
        title = findViewById(R.id.item_inner_RL_TV1_id);
        description = findViewById(R.id.item_inner_RL_TV6_id);
        image_view = findViewById(R.id.item_inner_RL_IV_id);
        like_button = findViewById(R.id.item_bottom_RL_like_IV_id);

        dislike_button = findViewById(R.id.item_bottom_RL_dislike_IV_id);
//        dislike_button.setVisibility(View.GONE);

        if(like_dislike==1){
            // If News like
            dislike_button.setVisibility(View.VISIBLE);
            like_button.setVisibility(View.GONE);
        }
        if(like_dislike==0)
        {
            // If News Dislike
            dislike_button.setVisibility(View.GONE);
            like_button.setVisibility(View.VISIBLE);
        }

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
        // Get News Description By Using API
        Get_Saved_News(News_id);

    }

    // Get Data From API
    public void Get_Saved_News(int news_id){
       // Variables.toast_msg(this,"Inside Method "+news_id);
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
                Variables.Log_d_msg(NewsDetail_F.this,""+"Volley requester " + requestType);
                Variables.Log_d_msg(NewsDetail_F.this,"Volley JSON post" + response);

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
                    Variables.hidepDialog(pDialog);
                     Variables.toast_msg(NewsDetail_F.this,"Please try again later. "+v.toString());
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
               Variables.Log_d_msg(NewsDetail_F.this,"Volley requester " + requestType);
               Variables.toast_msg(NewsDetail_F.this,"Error "+error.toString());

            }
        };
    }
    // ENd Data From API

    public void Like_News(int like,int news_id)
    {
        //Variables.toast_msg(this,"Inside like Method "+News_id);
        Variables.showpDialog(pDialog);
        initVolleyCallback_for_like_news();

        mVolleyService = new VolleyService(mResultCallback,NewsDetail_F.this);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'news_id': "+news_id+"," +
                    " 'favourite' : "+like+"  , " +
                    " 'user_id' : 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.AddFavouriteNews, sendObj);
    }


    public void like_button(View like)
    {
        is_wifi_availeable=Variables.is_internet_avail(this);
        if(is_wifi_availeable==true)
        {
            // If Wifi Available
            Like_News(1, News_id);
        }else {
            // => If wifi is not Available
            String title="Info";
            String msg="You are connected to internet.Please turn on internet and try again later. Thanks.";
            try{
                Variables.alert_dialogue(this,""+title,""+msg);
            }catch (Exception t){

            }

        }

    }

    public void DisLike_News(View V)
    {
        is_wifi_availeable=Variables.is_internet_avail(this);
        if(is_wifi_availeable==true)
        {
            // If Wifi Available
            Like_News(0, News_id);
        }else {
            // => If wifi is not Available
            String title="Info";
            String msg="You are connected to internet.Please turn on internet and try again later. Thanks.";
            try{
                Variables.alert_dialogue(this,""+title,""+msg);
            }catch (Exception t){

            }

        }




    }

    void initVolleyCallback_for_like_news(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                Variables.Log_d_msg(NewsDetail_F.this,""+"Volley requester " + requestType);
                Variables.Log_d_msg(NewsDetail_F.this,"Volley JSON post" + response.toString());


                try{
                    JSONObject Arr= response.getJSONObject("msg");
                    JSONObject Arr_1 = Arr.getJSONObject("Favourite");
                    String fav  = Arr_1.getString("favourite");
                  //  title.setText(""+Arr_1.getString("title"));
                    //description.setText(""+Arr_1.getString("description"));
                    // Add Picasso
//                    Picasso.get()
//                            .load(Arr_1.getString("attachment"))
//                            .placeholder(R.mipmap.ic_dnews)
//                            .error(R.mipmap.ic_dnews)
//                            .into(image_view);
                    if(fav.equals("1")){
                        Variables.toast_msg(NewsDetail_F.this,"Favourite Added.");
                        like_button.setVisibility(View.GONE);
                        dislike_button.setVisibility(View.VISIBLE);
                    }else if(fav.equals("0"))
                       {
                           // Dislike
                           Variables.toast_msg(NewsDetail_F.this,"Dislike.");
                           like_button.setVisibility(View.VISIBLE);
                           dislike_button.setVisibility(View.GONE);
                       }

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(NewsDetail_F.this,"Please try again later. "+v.toString());
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                try{
                    int status_code=Variables.volley_get_response_code(error);
                    Variables.Log_d_msg(NewsDetail_F.this,"Volley requester " + requestType);
                    Variables.toast_msg(NewsDetail_F.this,"Like Error "+error.toString()+" Code "+status_code);
                }catch (Exception v){

                }
                // Get Response HTTP code
            }
        };
    }

    /// ===> Method for increaing the font size
    public void increase_font_size(View size){

        float px = title.getTextSize();
        float desc_px = description.getTextSize();

        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        float desc_sp = desc_px / getResources().getDisplayMetrics().scaledDensity;

        Variables.toast_msg(NewsDetail_F.this,"Size in SP "+sp);
//
//        if(num_of_click_for_font_size==4){
//            num_of_click_for_font_size=0;
//        }

//        num_of_click_for_font_size = num_of_click_for_font_size+1;
       // Variables.toast_msg(NewsDetail_F.this,"Num of Click "+num_of_click_for_font_size);
        Variables.increase_font_size(sp,desc_sp,title,description);

//        int title_text = (int) title.getTextSize();
//        int desc_text = (int) description.getTextSize();
//        Variables.toast_msg(NewsDetail_F.this,""+title_text);
//        // description.getTextSize();
//        Variables.increase_font_size(title_text,desc_text,title,description);


    }


    public void decrease_font_size(View decrease){
//      int title_text = (int) title.getTextSize();
//        int desc_text = (int) description.getTextSize();
//     // description.getTextSize();
//        Variables.descrease_font_size(title_text,desc_text,title,description);

        float px = title.getTextSize();
        float desc_px = description.getTextSize();

        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        float desc_sp = desc_px / getResources().getDisplayMetrics().scaledDensity;

        Variables.toast_msg(NewsDetail_F.this,"Size in SP "+sp);
//
//        if(num_of_click_for_font_size==4){
//            num_of_click_for_font_size=0;
//        }

//        num_of_click_for_font_size = num_of_click_for_font_size+1;
        // Variables.toast_msg(NewsDetail_F.this,"Num of Click "+num_of_click_for_font_size);
        Variables.descrease_font_size(sp,desc_sp,title,description);


    }



}
