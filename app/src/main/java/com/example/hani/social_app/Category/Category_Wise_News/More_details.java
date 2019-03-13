package com.example.hani.social_app.Category.Category_Wise_News;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.hani.social_app.Category.CategoryModel.CategoryModel;
import com.example.hani.social_app.Category.Category_Adapter;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.TopNews.DataModels.News_Section_Model;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class More_details extends AppCompatActivity {
    private List<DataModel> Category_List;
    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    private List<News_Section_Model> News_section_List;
    RecyclerView RV;
    String cate_name , is_section;
   More_Details_Adapter ADP;
    int category_id;
    TextView heading;
    ImageView IV;
TextView empty_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_details);
        News_section_List = new ArrayList<>();
        pDialog = new ProgressDialog(More_details.this);
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);
        empty_info = findViewById(R.id.empty_info);

        // ==> Back button

        IV = (ImageView) findViewById(R.id.item_TB_IV_id);
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


        Intent intent = getIntent();
        category_id = intent.getIntExtra("cat_id",0); //if it's a string you stored.
        cate_name = intent.getStringExtra("cate_name");
        is_section = intent.getStringExtra("is_section");

        heading = findViewById(R.id.More_details_TV_id);
        heading.setText(""+cate_name);
        RV = (RecyclerView) findViewById(R.id.more_details_RV_id);
        RV.setHasFixedSize(false);

        RV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Variables.toast_msg(More_details.this,"Section   ."+is_section);
//        ADP = new More_Details_Adapter();
//        RV.setAdapter(ADP);

        Get_Categories();

    }

    // Get Data From API
    public void Get_Categories(){
        Variables.showpDialog(pDialog);
        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,More_details.this);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

//        try {
//
//            sendObj = new JSONObject("{'user_id': 2 , 'cat_id': "+category_id+"  }");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        if(is_section.equals("0")){

            // 0 Means Categorise wise News Showed

            try {

                sendObj = new JSONObject("{'user_id': 2 , 'cat_id': "+category_id+"  }");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Variables.toast_msg(More_details.this,"Categories wise News");
            Variables.mVolleyService.postDataVolley("POSTCALL", Variables.CategoriesWiseNews, sendObj);
        }else{
            // 1 Means Section wise News Display

            try {

                sendObj = new JSONObject("{'user_id': 2 , 'section_id': "+category_id+"  }");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Variables.toast_msg(More_details.this,"Section wise News");
            Variables.mVolleyService.postDataVolley("POSTCALL", Variables.SectionWiseAllNews, sendObj);

        }



    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                try{
                    JSONArray get_news= response.getJSONArray("msg");
                    // Save Response for Offline Showing Data

                   // Variables.toast_msg(More_details.this,""+response);
                    //SharedPrefrence.save_response_share(More_details.this,response.toString(),SharedPrefrence.shared_category_key);
    if(get_news.length() == 0){

        String title = "Info";
        String msg = "No News in "+cate_name+" Yet";
       // Variables.alert_dialogue(More_details.this,""+title,""+msg);
        empty_info.setVisibility(View.VISIBLE);


}else{
        for(int a = 0; a < get_news.length(); a++){
            JSONObject news_obj = get_news.getJSONObject(a);
            JSONObject news = news_obj.getJSONObject("News");
            news.getString("title");
            news.getString("attachment");
            news.getInt("id");

            // list_News_title.add(""+news.getString("title"));
            //list_News_img_urls.add(""+news.getString("attachment"));

            News_Section_Model add = new News_Section_Model(news.getString("attachment")
                    ,news.getString("title"),
                    news.getInt("id"),
                    news.getInt("favourite")

            );
            News_section_List.add(add);


        }

//
//                    adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
//                        @Override
//                        public void clickAction(int pos) {
//
//                            startActivity(new Intent(getContext(), NewsDetail_F.class));
//
//                        }
//                    },News_List);
//                    GridLayoutManager GLM = new GridLayoutManager(getContext(),2);
//                    RV2.setLayoutManager(GLM);
//                    RV2.setAdapter(adapter2);
//                    adapter = new Saved_Adapter(new Saved_Adapter.onClick() {
//                        @Override
//                        public void clickAction(int pos) {
//
//                            startActivity(new Intent(getContext(), NewsDetail_F.class));
//
//                        }
//                    },News_List);

        ADP = new More_Details_Adapter(new More_Details_Adapter.onClick() {
            @Override
            public void clickAction(int pos) {

                startActivity(new Intent(More_details.this, NewsDetail_F.class));

            }
        },News_section_List,More_details.this);

        RV.setLayoutManager(new GridLayoutManager(More_details.this, 1));
        RV.setAdapter(ADP);

    }



                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(More_details.this,"Error in Saved "+v.toString());
                    Variables.Log_d_msg(More_details.this,"Error in Saved "+v.toString());
//                    Toast.makeText(getContext(), "Error in Saved "+v.toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("Saved Error",""+v.toString());
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                //Log.d(TAG, "Volley requester " + requestType);
//                Toast.makeText(getContext(), "Error "+error.toString(), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                /// Seperate methods to shoe log.d and Toast.
                Variables.hidepDialog(pDialog);
                Variables.toast_msg(More_details.this ,"cate "+error.toString());
                Variables.Log_d_msg(More_details.this ,"cate "+error.toString());
            }
        };
    }
    // ENd Data From API




}
