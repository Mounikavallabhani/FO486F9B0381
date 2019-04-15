package com.dinosoftlabs.dnews.Category.Category_Wise_News;

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
import com.dinosoftlabs.dnews.CodeClasses.TimeAgo2;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.TopNews.DataModels.News_Section_Model;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
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

        //// Check Network Availability
        is_wifi_availeable = Variables.is_internet_avail(More_details.this);

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


         // ==> Get data from Previous Activity using Intent

        Intent intent = getIntent();
        category_id = intent.getIntExtra("cat_id",0);
        is_section = intent.getStringExtra("is_section");
        cate_name = intent.getStringExtra("cate_name");

        heading = findViewById(R.id.More_details_TV_id);
        heading.setText("" + cate_name);  // Set Heading in Header
        RV = (RecyclerView) findViewById(R.id.more_details_RV_id);
        RV.setHasFixedSize(false);

        RV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Get_Categories();

    }

    // Get Data From API
    public void Get_Categories(){
        Variables.showpDialog(pDialog);
        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,More_details.this);

        JSONObject sendObj = null;

       if(is_section.equals("0")){

            // 0 Means Categorise wise News Showed
            String user_json = SharedPrefrence.get_offline(More_details.this,SharedPrefrence.shared_user_login_detail_key);
            Variables.Log_d_msg(More_details.this,"User ID " + SharedPrefrence.get_user_id_from_json(More_details.this));

            if(user_json != null){
                    // ==> If user Already Login
                try {
                    sendObj = new JSONObject("{'user_id': "+SharedPrefrence.get_user_id_from_json(More_details.this)+" , 'cat_id': "+category_id+"  }");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                // =>  If User is not login
                try {
                    sendObj = new JSONObject("{'user_id': 2 , 'cat_id': "+category_id+"  }");

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }



            Variables.mVolleyService.postDataVolley("POSTCALL", Variables.CategoriesWiseNews, sendObj);
        }else{
            // 1 Means Section wise News Display

            String user_json = SharedPrefrence.get_offline(More_details.this,SharedPrefrence.shared_user_login_detail_key);
            Variables.Log_d_msg(More_details.this,"User ID "+SharedPrefrence.get_user_id_from_json(More_details.this));


            if(user_json != null){
                // ==> If user Already Login
                try {
                    sendObj = new JSONObject("{'user_id': "+SharedPrefrence.get_user_id_from_json(More_details.this)+" , 'section_id': "+category_id+"  }");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                // =>  If User is not login
                try {
                    sendObj = new JSONObject("{'user_id': 2 , 'section_id': "+category_id+"  }");

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }



            Variables.mVolleyService.postDataVolley("POSTCALL", Variables.SectionWiseAllNews, sendObj);

        }



    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                // ==> Value Save into Shared Prefrence
                SharedPrefrence.save_response_share(More_details.this, response.toString() ,SharedPrefrence.shared_Cate_Wise_news_key);

               // Variables.toast_msg(More_details.this,"" + response.toString());
                Variables.Log_d_msg(More_details.this,"Res " + response.toString());

                Variables.hidepDialog(pDialog);
                try{
                    JSONArray get_news= response.getJSONArray("msg");



                  //  Variables.toast_msg(More_details.this,"" + Variables.get_time_ago("") );
                  //  Variables.Log_d_msg(More_details.this,"uu " + Variables.get_time_ago(""));

    if(get_news.length() == 0){

        // ==> If there is no News
        String title = "Info";
        String msg = "No News in "+cate_name+" Yet";

        empty_info.setVisibility(View.VISIBLE);


}else{

           Variables.get_time_ago("");
       // Variables.toast_msg(More_details.this," " + Variables.get_time_ago("") );
        // ==> If there are news
        for(int a = 0; a < get_news.length(); a++){
            JSONObject news_obj = get_news.getJSONObject(a);
            JSONObject news = news_obj.getJSONObject("News");
            news.getString("title");
            news.getString("attachment");
            news.getInt("id");

            String time = news.getString("created");

            TimeAgo2 timeAgo2 = new TimeAgo2();
            String MyFinalValue = timeAgo2.covertTimeToText(time);
          //  Variables.toast_msg(More_details.this,"Ok " + MyFinalValue);

            News_Section_Model add = new News_Section_Model(news.getString("attachment")
                    ,news.getString("title"),
                    news.getInt("id"),
                    news.getInt("favourite"),
                    MyFinalValue

            );
            News_section_List.add(add);


        }

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
                    Variables.toast_msg(More_details.this,"" + v.toString());
                    }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {

                Variables.hidepDialog(pDialog);
                Variables.toast_msg(More_details.this ,"" + error.toString());
                Variables.Log_d_msg(More_details.this ,"" + error.toString());
            }
        };
    }
    // ENd Data From API




}
