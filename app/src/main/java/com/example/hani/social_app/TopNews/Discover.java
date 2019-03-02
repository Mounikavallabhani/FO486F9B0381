package com.example.hani.social_app.TopNews;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.MainActivity;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.LoginSignup.Login_details;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private List<NewsDataMode> News_List;
    private ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.discover, container, false);
        init();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //// Check Network Availability
        is_wifi_availeable=Variables.is_internet_avail(getContext());

        if(is_wifi_availeable==true){
            // ==> If wifi available

            // Get News
            Get_News();
        }else{
            /// ==> If Wifi is not available.
            // ==> Data get from SharedPreference

        }



        return view;

    }





    public void init(){

        TL = (TabLayout) view.findViewById(R.id.mainfragment_tabs);

        RV1 = (RecyclerView) view.findViewById(R.id.RV_id);

        RV2 = (RecyclerView) view.findViewById(R.id.discover_RL2_RV2_id);
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

                Login_details F = new Login_details();
                FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.bottom_to_top, 0);
                transaction.addToBackStack(null);
                transaction.replace(R.id.mainfragment_RL_id, F).commit();
            }
        });
        adapter1 = new Discover_Adapter_one();
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
        mVolleyService.postDataVolley("POSTCALL", Variables.ShowsNewsAPI, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);

                try{

                    JSONArray Arr= response.getJSONArray("msg");
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

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Toast.makeText(getContext(), "Error in c "+v.toString(), Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);

                 }
        };
    }
    // ENd Data From API



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all:

                break;

        }
    }


}

