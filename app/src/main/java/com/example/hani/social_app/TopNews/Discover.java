package com.example.hani.social_app.TopNews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.MainActivity;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.LoginSignup.Already_Login_f;
import com.example.hani.social_app.TopNews.LoginSignup.Login_details;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Discover extends Fragment implements View.OnClickListener {
    View view;
    TabLayout TL;
    RecyclerView RV1,RV2;
    RelativeLayout LL;
    ImageView IV;
    Discover_Adapter_one adapter1;
    Discover_Adapter_two adapter2;
    boolean is_wifi_availeable;
    private String TAG = "Discover";
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    String User_info_json;
    private List<NewsDataMode> News_List;
    private ProgressDialog pDialog;
    TextView current_date;
    RelativeLayout main_lauout;
    FrameLayout dim_area;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.discover, container, false);
        init();
        main_lauout = view.findViewById(R.id.bac_dim_layout);

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
        String formattedDate = df.format(c);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        current_date.setText(dayOfTheWeek+" "+month_name+" "+formattedDate);
        //// Check Network Availability
        is_wifi_availeable=Variables.is_internet_avail(getContext());
        Get_Sections_News();
        if(is_wifi_availeable==true){
            // ==> If wifi available

            // Get News
            Get_News();
        }else{
            /// ==> If Wifi is not available.
            // ==> Data get from SharedPreference
            get_discover_offline();

        }
        return view;

    }

    public void get_discover_offline() {

        String Cate_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_discover_news_key);

        if(Cate_json_offline != null){
            // ==> If Values is not Null
            try {
                JSONObject response = new JSONObject(Cate_json_offline);
                JSONArray Arr = response.getJSONArray("msg");
                for(int i=0; i< Arr.length(); i++){
                    JSONObject news_obj= Arr.getJSONObject(i);
                    JSONObject News = news_obj.getJSONObject("News");
                    News.getString("title");
                    News.getInt("id");
                    JSONObject category_obj = news_obj.getJSONObject("Category");
                    category_obj.getString("name");
                    NewsDataMode a = new NewsDataMode(News.getString("attachment")
                            ,News.getString("title"),
                            News.getString("description"),
                            category_obj.getString("name"),
                            News.getInt("id")

                    );
                    News_List.add(a);
                }
                adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
                    @Override
                    public void clickAction(int pos) {
                        //   News_List.get(pos);
                        NewsDataMode News = News_List.get(pos);
                        Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
                        myIntent.putExtra("news_id",  News.getId()); //Optional parameters
                        getContext().startActivity(myIntent);
                        Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
                        //                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                    }
                },News_List);
                GridLayoutManager GLM = new GridLayoutManager(getContext(),2);
                RV2.setLayoutManager(GLM);
                RV2.setAdapter(adapter2);

            }catch (Exception b){

            }
        }else{
            // If Values is Null
            // Get News
            Get_News();
        }

    }

    public void init(){

//        main_lauout = view.findViewById(R.id.discover_parent_RL_id);
//        main_lauout.setAlpha(0.9f);

        TL = (TabLayout) view.findViewById(R.id.mainfragment_tabs);

        RV1 = (RecyclerView) view.findViewById(R.id.RV_id);
        dim_area = view.findViewById(R.id.mainmenu);
        RV2 = (RecyclerView) view.findViewById(R.id.discover_RL2_RV2_id);
        current_date = view.findViewById(R.id.dicover_date_id);

        RV2.setNestedScrollingEnabled(false);
        News_List = new ArrayList<>();
        LL = (RelativeLayout) view.findViewById(R.id.discover_LL_id);
        ViewGroup.LayoutParams lp = LL.getLayoutParams();
        lp.height = (int) (Variables.height * 0.6);
        LL.setLayoutParams(lp);

        IV = (ImageView) view.findViewById(R.id.circle_image);
        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_info_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);

                if(User_info_json != null){
                    // If user Already Login    ==> :-)

                //    layout_MainMenu.getForeground().setAlpha( 0); // restore
                  //  main_lauout.setVisibility(View.VISIBLE);

                    Already_Login_f F = new Already_Login_f();
                    FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.bottom_to_top, 0);
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.mainfragment_RL_id, F).commit();


                }else{
                    // If user is not Login  ==> :D

//                    dim_area.setForeground(getResources().getDrawable(R.drawable.dim));
////                    dim_area.setAnimation((Animation) getResources().getAnimation(R.anim.bottom_to_top));
//                    dim_area.getForeground().setAlpha(180);
                   // main_lauout.setVisibility(View.VISIBLE);
                    Login_details F = new Login_details();
                    FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.bottom_to_top, 0);
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.mainfragment_RL_id, F).commit();

                }





            }
        });

//        adapter1 = new Discover_Adapter_one(new Discover_Adapter_one.onClick() {
//            @Override
//            public void clickAction(int pos) {
//
//                //   News_List.get(pos);
//                NewsDataMode News = News_List.get(pos);
//                Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
//                myIntent.putExtra("news_id",  News.getId());
//                myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
//                //Optional parameters
//                getContext().startActivity(myIntent);
//                Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
//                //                            startActivity(new Intent(getContext(), NewsDetail_F.class));
//
//            }
//        },News_List);
////        adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
////            @Override
////            public void clickAction(int pos) {
////
////                startActivity(new Intent(getContext(), NewsDetail_F.class));
////
////            }
////        },News_List);
//
//        LinearLayoutManager LLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        RV1.setLayoutManager(LLM);
//        RV1.setAdapter(adapter1);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(RV1);


//        GridLayoutManager GLM = new GridLayoutManager(getContext(),2);
//        RV2.setLayoutManager(GLM);
//        RV2.setAdapter(adapter2);

        int spanCount = 2;
        int spacing = 40;
        boolean includeEdge = true;
        RV2.addItemDecoration(new itemdecoration(spacing, spanCount, includeEdge));

    }

    // Get Data From API
    public void Get_News(){

        Variables.showpDialog(pDialog);
        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,getContext());
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'user_id': 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.Slider_News_API, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                Variables.Log_d_msg(getContext()," res "+response);
                SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_discover_news_key);
                //Variables.toast_msg(getContext(),""+response);
                    try{


                    JSONArray Arr= response.getJSONArray("msg");
                      for(int i=0; i< Arr.length(); i++){
                          JSONObject news_obj= Arr.getJSONObject(i);
                          JSONObject News = news_obj.getJSONObject("News");
                          News.getString("title");
                          News.getInt("id");
                         // News.getInt("favourite");
//                          JSONObject category_obj = news_obj.getJSONObject("Category");
//                          category_obj.getString("name");

                          NewsDataMode a = new NewsDataMode(
                                  News.getString("attachment")
                                  ,News.getString("title"),
                                  News.getString("description"),
                                 "",
                                  News.getInt("id"),
                                 0

                                  );
                          News_List.add(a);
                       }

                       /// ==> Slider Adapters

                    adapter1 = new Discover_Adapter_one(new Discover_Adapter_one.onClick() {
                        @Override
                        public void clickAction(int pos) {
                            //   News_List.get(pos);
                            NewsDataMode News = News_List.get(pos);
                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
                            myIntent.putExtra("news_id",  News.getId());
                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
                            //Optional parameters
                            getContext().startActivity(myIntent);
                            Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
                            //                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },News_List);
//        adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
//            @Override
//            public void clickAction(int pos) {
//
//                startActivity(new Intent(getContext(), NewsDetail_F.class));
//
//            }
//        },News_List);

                    LinearLayoutManager LLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    RV1.setLayoutManager(LLM);
                    RV1.setAdapter(adapter1);



                    //// ==> End Sliders


//                       adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
//                        @Override
//                        public void clickAction(int pos) {
//
//                         //   News_List.get(pos);
//                            NewsDataMode News = News_List.get(pos);
//                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
//                            myIntent.putExtra("news_id",  News.getId());
//                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
//                            //Optional parameters
//                            getContext().startActivity(myIntent);
//                            Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
//                            //                            startActivity(new Intent(getContext(), NewsDetail_F.class));
//
//                        }
//                    },News_List);
//                    GridLayoutManager GLM = new GridLayoutManager(getContext(),2);
//                    RV2.setLayoutManager(GLM);
//                    RV2.setAdapter(adapter2);

                }catch (Exception v){
                        Variables.hidepDialog(pDialog);
                    Toast.makeText(getContext(), "Error in c "+v.toString(), Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
               // error.get
            //    int status_code = error.networkResponse.statusCode;
    try{
        int status_code = Variables.volley_get_response_code(error);
        Variables.hidepDialog(pDialog);
        Variables.toast_msg(getContext(),""+error+" Response Code "+status_code);

    }catch (Exception v){
        Variables.hidepDialog(pDialog);
        Variables.toast_msg(getContext(),""+error+" Response Code "+v.toString());
        Variables.Log_d_msg(getContext(),""+error+" Response Code "+v.toString());
    }

                 }
        };
    }
    // ENd Data From API

    // ======= Display Sections News

    // Get Data From API
    public void Get_Sections_News(){

        Variables.showpDialog(pDialog);
        initVolleyCallback_for_section_news();

        mVolleyService = new VolleyService(mResultCallback,getContext());
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'user_id': 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.Sections_News_API, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback_for_section_news(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
             //  Variables.Log_d_msg(getContext()," res "+response);
               // SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_discover_news_key);
               // Variables.toast_msg(getContext(),""+response);
                try{
                    JSONArray Arr= response.getJSONArray("msg");
                    for(int i=0; i< Arr.length(); i++){
                        JSONObject news_obj_section= Arr.getJSONObject(i);
                        // Get News Sections
                        JSONObject News_Sections = news_obj_section.getJSONObject("Section");
                        News_Sections.getString("name");
                        News_Sections.getInt("s_order");
                        // End Get News Sections..
                        JSONArray section_news = news_obj_section.getJSONArray("SectionNews");
                            for(int a=0; a < section_news.length(); a++){
                                JSONObject news_obj = section_news.getJSONObject(a);
                                JSONObject news_detail_obj = news_obj.getJSONObject("News");
                                news_detail_obj.getString("description");
                                news_detail_obj.getString("attachment");
                                news_detail_obj.getString("title");


                                NewsDataMode add = new NewsDataMode(
                                        news_detail_obj.getString("attachment")
                                        ,news_detail_obj.getString("title"),
                                        news_detail_obj.getString("description"),
                                        ""+News_Sections.getString("name"),
                                        news_detail_obj.getInt("id"),
                                        0
                                );
                                News_List.add(add);

                                //Variables.toast_msg(getContext(),""+news_detail_obj.getString("description"));
                            }

                    }

                    /// ==> Slider Adapters
//        adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
//            @Override
//            public void clickAction(int pos) {
//
//                startActivity(new Intent(getContext(), NewsDetail_F.class));
//
//            }
//        },News_List);
//
//                    LinearLayoutManager LLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//                    RV1.setLayoutManager(LLM);
//                    RV1.setAdapter(adapter2);



                    //// ==> End Sliders

                    adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
                        @Override
                        public void clickAction(int pos) {
                         //   News_List.get(pos);
                            NewsDataMode News = News_List.get(pos);
                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
                            myIntent.putExtra("news_id",  News.getId());
                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
                            //Optional parameters
                            getContext().startActivity(myIntent);
                            Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
                            //                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },News_List);
                    GridLayoutManager GLM = new GridLayoutManager(getContext(),2);
                    RV2.setLayoutManager(GLM);
                    RV2.setAdapter(adapter2);

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Toast.makeText(getContext(), "Error in c "+v.toString(), Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                // error.get
                //    int status_code = error.networkResponse.statusCode;
                try{
                    int status_code = Variables.volley_get_response_code(error);
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(getContext(),""+error+" Response Code "+status_code);

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(getContext(),""+error+" Response Code "+v.toString());
                    Variables.Log_d_msg(getContext(),""+error+" Response Code "+v.toString());
                }

            }
        };
    }
    // ENd Data From API




    ///= ============ End Display Sections news


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all:

                break;

        }
    }


}

