package com.example.hani.social_app.SavedArticles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.Category.CategoryModel.CategoryModel;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.Discover_Adapter_two;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Saved extends Fragment {
    private ProgressDialog pDialog;
    View view;
    RecyclerView RV;
    Saved_Adapter adapter;

//    IResult mResultCallback = null;
//    VolleyService mVolleyService;
    private List<NewsDataMode> News_List;
    private String TAG = "Saved";
    boolean is_wifi_availeable;
    EditText save_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.saved, container, false);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        save_search = view.findViewById(R.id.saved_search_ET_id);
        save_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
        });
        is_wifi_availeable=Variables.is_internet_avail(getContext());
        RV = (RecyclerView) view.findViewById(R.id.saved_RV_id);
        News_List = new ArrayList<>();

        if(is_wifi_availeable==true){
            // If Wifi Available
            Get_Saved_News();

        }else{
            // If wifi not available
            get_category_date_offline();
        }
        return view;

    }

    void filter(String text){
        List<NewsDataMode> temp = new ArrayList();
        for(NewsDataMode d: News_List){

            if(d.getDescription().toLowerCase().contains(text.toLowerCase())
                    ||
            d.getTitle().toLowerCase().contains(text.toLowerCase()))
            {
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList((ArrayList<NewsDataMode>) temp);
    }



    public void get_category_date_offline(){

        String Saved_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_saved_news_key);
        try {
            JSONObject response = new JSONObject(Saved_json_offline);

            JSONArray Arr= response.getJSONArray("msg");
            for(int i=0;i< Arr.length();i++){
                JSONObject news_obj= Arr.getJSONObject(i);
                JSONObject News = news_obj.getJSONObject("News");
                News.getString("title");
                //  News.getString("id");
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
            // => Set Adapter
            adapter = new Saved_Adapter(new Saved_Adapter.onClick() {
                @Override
                public void clickAction(int pos) {

                    startActivity(new Intent(getContext(), NewsDetail_F.class));

                }
            },News_List);
            //   Variables.hidepDialog(getContext());
            RV.setLayoutManager(new LinearLayoutManager(getContext()));
            RV.setAdapter(adapter);

        }catch (Exception c){

        }

    }


    // Get Data From API
    public void Get_Saved_News(){
        Variables.showpDialog(pDialog);
        //Variables.showpDialog(getContext());
        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,getContext());
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'user_id': 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Variables.mVolleyService.postDataVolley("POSTCALL", Variables.SavedNewsAPI, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){

        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
              //  Log.d(TAG, "Volley requester " + requestType);
                //    Toast.makeText(getContext(), ""+response.toString(), Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "Volley JSON post" + response);
                Variables.Log_d_msg(getContext(),""+response);

                SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_saved_news_key);

                // Variables.hidepDialog(getContext());
                try{
                    Variables.hidepDialog(pDialog);

                    JSONArray Arr= response.getJSONArray("msg");
                    for(int i=0;i< Arr.length();i++){
                        JSONObject news_obj= Arr.getJSONObject(i);
                        JSONObject News = news_obj.getJSONObject("News");
                        News.getString("title");
                      //  News.getString("id");
//                        JSONObject category_obj = news_obj.getJSONObject("Category");
//                        category_obj.getString("name");
                        NewsDataMode a = new NewsDataMode(News.getString("attachment")
                                ,News.getString("title"),
                                News.getString("description"),
                                "",
                                News.getInt("id")
                        );
                        News_List.add(a);
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
                    adapter = new Saved_Adapter(new Saved_Adapter.onClick() {
                        @Override
                        public void clickAction(int pos) {

                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },News_List);
                 //   Variables.hidepDialog(getContext());
                    RV.setLayoutManager(new LinearLayoutManager(getContext()));
                    RV.setAdapter(adapter);

                }catch (Exception v){
                   // Variables.hidepDialog(getContext());
                    Variables.hidepDialog(pDialog);

//                    Toast.makeText(getContext(), "Error in Saved "+v.toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("Saved Error",""+v.toString());

                    Variables.toast_msg(getContext(),""+v.toString());
                    Variables.Log_d_msg(getContext(),""+v.toString());
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                //Log.d(TAG, "Volley requester " + requestType);
//                Toast.makeText(getContext(), "Error "+error.toString(), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                /// Seperate methods to shoe log.d and Toast.
                Variables.hidepDialog(pDialog);
             ///   Variables.hidepDialog(getContext());
                Variables.toast_msg(getContext(),""+error.toString());
                Variables.Log_d_msg(getContext(),""+error.toString());
            }
        };
    }
    // ENd Data From API



}
