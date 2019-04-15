package com.dinosoftlabs.dnews.TopNews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
//import com.dinosoftlabs.dnews.CodeClasses.AdMob;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.TopNews.DataModels.NewsDataMode;
import com.dinosoftlabs.dnews.TopNews.DataModels.News_Section_Model;
import com.dinosoftlabs.dnews.TopNews.DataModels.Sliders_data_model;
import com.dinosoftlabs.dnews.TopNews.LoginSignup.Already_Login_f;
import com.dinosoftlabs.dnews.TopNews.LoginSignup.Login_details;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
//import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private List<Sliders_data_model> Slider_List;

    private ProgressDialog pDialog;
    TextView current_date;
    RelativeLayout main_lauout;
    FrameLayout dim_area;
    int section_id_api;
    private List<News_Section_Model> News_section_List;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    SwipeRefreshLayout pullToRefresh;
    private static final Integer[] IMAGES= {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    boolean id_loading_dialogue;

    public void clear_lists(){
        News_section_List.clear();
        News_List.clear();
        Slider_List.clear();

    }

   // ArrayList<Integer> Section_ids = new ArrayList<Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.discover, container, false);
        init();
    try{
        Variables.createNewIntAd(getContext());

    }catch (Exception c){

    }

        main_lauout = view.findViewById(R.id.bac_dim_layout);
        News_section_List = new ArrayList<>();
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                clear_lists();

                 id_loading_dialogue = true;
//                refreshData(); // your code
                if(is_wifi_availeable == true){

                    Get_News(id_loading_dialogue);
                    Get_Sections_News(id_loading_dialogue);
                    pullToRefresh.setRefreshing(false);

                }else{

                    pullToRefresh.setRefreshing(false);

                    Variables.alert_dialogue(getContext(),
                            ""+getResources().getString(R.string.info),
                            ""+getResources().getString(R.string.wifi_connected_info)
                    );
                   // pullToRefresh.setRefreshing(false);

                }




              //  pullToRefresh.setRefreshing(false);

            }
        });


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
        is_wifi_availeable = Variables.is_internet_avail(getContext());



         //Variables.isRTL();

       //init_2();

        String Cate_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_news_sections_key);

        if(Cate_json_offline != null ){
             // If Data is save in Offline

            get_discover_offline();
            get_slider_offline();
            pullToRefresh.setRefreshing(false);

        }else{
            // If Data is not save in Offline

            if(is_wifi_availeable==true){
                clear_lists();
                // ==> If wifi available
                id_loading_dialogue = false;
                Get_Sections_News(id_loading_dialogue);
                // Get News
                Get_News(id_loading_dialogue);
                pullToRefresh.setRefreshing(false);

            }else{
                /// ==> If Wifi is not available.
                // ==> Data get from SharedPreference
                get_discover_offline();
                get_slider_offline();
                pullToRefresh.setRefreshing(false);
            }

            pullToRefresh.setRefreshing(false);


        }



        return view;

    }

    public void get_discover_offline() {
        String Cate_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_news_sections_key);
        if(Cate_json_offline != null){
            // ==> If Values is not Null
            try {
                JSONObject response = new JSONObject(Cate_json_offline);

                JSONArray Arr = response.getJSONArray("msg");
                //Arr.length()

                NewsDataMode add;
                for (int i = 0; i < Arr.length(); i++) {
                    JSONObject news_obj_section = Arr.getJSONObject(i);
                    // Get News Sections
                    JSONObject News_Sections = news_obj_section.getJSONObject("Section");
                    News_Sections.getString("name");
                    News_Sections.getInt("s_order");
                    section_id_api = News_Sections.getInt("id");

                    JSONArray section_news = news_obj_section.getJSONArray("SectionNews");

                    if(news_obj_section.getJSONArray("SectionNews").length() == 0){


                    }else {

                        add = new NewsDataMode(
                                News_Sections.getString("name")
                                , section_news,
                                News_Sections.getInt("id")
                        );

                        News_List.add(add);


                    }




                }


                adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
                    @Override
                    public void clickAction(int pos) {
                        //   News_List.get(pos);
                        NewsDataMode News = News_List.get(pos);
//                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
//                            myIntent.putExtra("news_id",  News.getId());
//                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
//                            //Optional parameters
//                            getContext().startActivity(myIntent);

                    }
                }, News_List, getContext());

                final GridLayoutManager GLM = new GridLayoutManager(getContext(), 1);

                RV2.setLayoutManager(GLM);
                RV2.setAdapter(adapter2);
            }catch (Exception v){

                }
        }else{
            // If Values is Null
            // Get News
           // Get_News();
//            Get_Sections_News();
        }

    }

    public void init(){

        TL = (TabLayout) view.findViewById(R.id.mainfragment_tabs);

        RV1 = (RecyclerView) view.findViewById(R.id.RV_id);
        dim_area = view.findViewById(R.id.mainmenu);
        RV2 = (RecyclerView) view.findViewById(R.id.discover_RL2_RV2_id);
        current_date = view.findViewById(R.id.dicover_date_id);

        RV2.setNestedScrollingEnabled(false);
        News_List = new ArrayList<>();
        Slider_List = new ArrayList<>();
        LL = (RelativeLayout) view.findViewById(R.id.discover_LL_id);
        ViewGroup.LayoutParams lp = LL.getLayoutParams();
        lp.height = (int) (Variables.height * 0.6);
        LL.setLayoutParams(lp);

        IV = (ImageView) view.findViewById(R.id.circle_image);
        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_info_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);

               // Login_details bottomSheetFragment = new Login_details();
                //bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
               // bottomSheetFragment.setPeekHeight(300);

                if(User_info_json != null){
                    // If user Already Login    ==> :-)

                //    layout_MainMenu.getForeground().setAlpha( 0); // restore
                  //  main_lauout.setVisibility(View.VISIBLE);

//                    BottomSheetDialog dialog = new BottomSheetDialog(getContext());
//                    dialog.setContentView(view);
//                    dialog.show();

                    Already_Login_f sign_up = new Already_Login_f();
                    sign_up.show(getActivity().getSupportFragmentManager(), sign_up.getTag());

                }else{
                    // If user is not Login  ==> :D

                    Login_details sign_up = new Login_details();
                    sign_up.show(getActivity().getSupportFragmentManager(), sign_up.getTag());
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
    public void Get_News(boolean id_loading_dialogue){

//        Variables.toast_msg(getContext()," Loading "+id_loading_dialogue);

        pullToRefresh.setRefreshing(true);

        if(id_loading_dialogue == true){
           // Variables.showpDialog(pDialog);
            pullToRefresh.setRefreshing(true);
        }else{
            pullToRefresh.setRefreshing(true);
        }


        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,getContext());
        JSONObject sendObj = null;

        try {
            String user_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);
           // Variables.toast_msg(getContext(),"User ID "+SharedPrefrence.get_user_id_from_json(getContext()));
            if(user_json != null){

                // If user is Login
                sendObj = new JSONObject("{'user_id': "+SharedPrefrence.get_user_id_from_json(getContext())+" }");

            }else{
                // If user is not login

                sendObj = new JSONObject("{'user_id': 2 }");
            }



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

                if(id_loading_dialogue == true){
                    Variables.hidepDialog(pDialog);

                }else{
                    pullToRefresh.setRefreshing(false);
                }

                Variables.Log_d_msg(getContext()," res "+response);

                SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_discover_news_key);
                //Variables.toast_msg(getContext(),""+response);

                    try{
                    JSONArray Arr_1= response.getJSONArray("msg");
                      for(int i=0; i< Arr_1.length(); i++){
                          JSONObject news_obj= Arr_1.getJSONObject(i);



                          JSONObject News = news_obj.getJSONObject("News");
                          News.getString("title");
                          News.getInt("id");
                          News.getInt("favourite");

                          JSONObject News_Category = News.getJSONObject("Category");
                          News_Category.getString("name");

//                          JSONObject category_obj = news_obj.getJSONObject("Category");
//                          category_obj.getString("name");
                          Sliders_data_model a = new Sliders_data_model(
                                  News.getString("attachment")
                                  ,News.getString("title"),
                                  News.getString("description"),
                                  News.getInt("id"),
                                  News.getInt("favourite"),
                                  News_Category.getString("name")
                                  );
                          Slider_List.add(a);
                       }


                       /// ==> Slider Adapters

                    adapter1 = new Discover_Adapter_one(new Discover_Adapter_one.onClick() {
                        @Override
                        public void clickAction(int pos) {
                            //   News_List.get(pos);
                            Sliders_data_model News = Slider_List.get(pos);
                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
//                            myIntent.putExtra("news_id",  News.getId());
//                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
                            //Optional parameters
                            getContext().startActivity(myIntent);
                           // Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
                            //                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },Slider_List,getContext());
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
                    Toast.makeText(getContext(), " "+v.toString(), Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
               // error.get
            //    int status_code = error.networkResponse.statusCode;
    try{
        int status_code = Variables.volley_get_response_code(error);
        if(id_loading_dialogue == true){
            Variables.hidepDialog(pDialog);

        }else{
            pullToRefresh.setRefreshing(false);
        }


    }catch (Exception v){
        if(id_loading_dialogue == true){
            Variables.hidepDialog(pDialog);

        }else{
            pullToRefresh.setRefreshing(false);
        }
            }

                 }
        };
    }


    public void get_slider_offline() {
        String Cate_json_offline = SharedPrefrence.get_offline(getContext(), SharedPrefrence.shared_discover_news_key);
        if (Cate_json_offline != null) {
            // ==> If Values is not Null
            try {
                JSONObject response = new JSONObject(Cate_json_offline);

                try{
                    JSONArray Arr_1= response.getJSONArray("msg");
                    for(int i=0; i< Arr_1.length(); i++){
                        JSONObject news_obj= Arr_1.getJSONObject(i);
                        JSONObject News = news_obj.getJSONObject("News");
                        News.getString("title");
                        News.getInt("id");
                        News.getInt("favourite");
//                          JSONObject category_obj = news_obj.getJSONObject("Category");
//                          category_obj.getString("name");

                        JSONObject News_Category = News.getJSONObject("Category");
                        News_Category.getString("name");


                        Sliders_data_model a = new Sliders_data_model(
                                News.getString("attachment")
                                ,News.getString("title"),
                                News.getString("description"),
                                News.getInt("id"),
                                News.getInt("favourite"),
                                News_Category.getString("name")
                        );
                        Slider_List.add(a);
                    }


                    /// ==> Slider Adapters

                    adapter1 = new Discover_Adapter_one(new Discover_Adapter_one.onClick() {
                        @Override
                        public void clickAction(int pos) {
                            //   News_List.get(pos);
                            Sliders_data_model News = Slider_List.get(pos);
                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
//                            myIntent.putExtra("news_id",  News.getId());
//                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
                            //Optional parameters
                            getContext().startActivity(myIntent);
                            // Toast.makeText(getContext(), ""+News.getId(), Toast.LENGTH_SHORT).show();
                            //                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },Slider_List,getContext());
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
                    //Variables.hidepDialog(pDialog);
                   // Toast.makeText(getContext(), " "+v.toString(), Toast.LENGTH_SHORT).show();

                }




            } catch (Exception v) {

            }

        }
    }


    // ENd Data From API
    // ======= Display Sections News
    // Get Data From API
    public void Get_Sections_News(boolean is_loading){
//        pullToRefresh.setRefreshing(false);
//        Variables.showpDialog(pDialog);
        pullToRefresh.setRefreshing(true);
        if(id_loading_dialogue == true){
            //Variables.showpDialog(pDialog);
        }else{
            pullToRefresh.setRefreshing(false);
        }



        initVolleyCallback_for_section_news();

        mVolleyService = new VolleyService(mResultCallback,getContext());
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;
        String user_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);


            if(user_json != null){

                try{
                    // If user is Login
                    sendObj = new JSONObject("{'user_id': "+SharedPrefrence.get_user_id_from_json(getContext())+" }");

                }catch (Exception b){

                }

            }else {
                try{
                    // If user is Login
                    sendObj = new JSONObject("{'user_id': 2 }");
                }catch (Exception b){

                }



            }

        mVolleyService.postDataVolley("POSTCALL", Variables.Sections_News_API, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback_for_section_news(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                if(id_loading_dialogue == true){
                    Variables.hidepDialog(pDialog);
                }else{
                    pullToRefresh.setRefreshing(false);
                }




                // Add news Section
                SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_news_sections_key);

                try{
                    JSONArray Arr= response.getJSONArray("msg");
                    //Arr.length()

                    NewsDataMode add;
                    for(int i=0; i< Arr.length(); i++){
                        JSONObject news_obj_section= Arr.getJSONObject(i);
                        // Get News Sections
                        JSONObject News_Sections = news_obj_section.getJSONObject("Section");
                        News_Sections.getString("name");
                        News_Sections.getInt("s_order");
                        section_id_api = News_Sections.getInt("id");

                        if(news_obj_section.getJSONArray("SectionNews").length() == 0){



                        }else {



                            JSONArray section_news = news_obj_section.getJSONArray("SectionNews");

                             add = new NewsDataMode(
                                    News_Sections.getString("name")
                                    ,section_news,
                                    News_Sections.getInt("id")
                            );

                            News_List.add(add);


                        }




                    }



                    adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
                        @Override
                        public void clickAction(int pos) {
                         //   News_List.get(pos);
                            NewsDataMode News = News_List.get(pos);
//                            Intent myIntent = new Intent(getContext(), NewsDetail_F.class);
//                            myIntent.putExtra("news_id",  News.getId());
//                            myIntent.putExtra("like_or_dislike",  News.getLike_dislile());
//                            //Optional parameters
//                            getContext().startActivity(myIntent);

                          }
                    },News_List,getContext());

                    final GridLayoutManager GLM = new GridLayoutManager(getContext(),1);

                    RV2.setLayoutManager(GLM);
                    RV2.setAdapter(adapter2);
                    try{


                    }catch (Exception v){

                    }

                }catch (Exception v){

                    if(id_loading_dialogue == true){
                        Variables.hidepDialog(pDialog);
                    }else{
                        pullToRefresh.setRefreshing(false);
                    }

                    Toast.makeText(getContext(), ""+v.toString(), Toast.LENGTH_SHORT).show();

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

        }
    }



}

