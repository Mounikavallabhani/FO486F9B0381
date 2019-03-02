package com.example.hani.social_app.Category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.Category.CategoryModel.CategoryModel;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SavedArticles.Saved_Adapter;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.example.hani.social_app.TopNews.itemdecoration;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Catergory extends Fragment {

    View view;
    RecyclerView recyclerView;
    Category_Adapter adapter;

    // Define List
    private List<CategoryModel> Category_List;
    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.category, container, false);
        // Progress Dialogue init
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        Category_List = new ArrayList<>();
        is_wifi_availeable=Variables.is_internet_avail(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.category_RV_id);
//        adapter = new Category_Adapter();
//
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.setAdapter(adapter);


        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new itemdecoration(spacing, spanCount, includeEdge));
 if(is_wifi_availeable==true){
     // If Wifi Available
     Get_Categories();

 }else{
     // If wifi not available
     get_category_date_offline();
 }
            //Get_Categories();

        return view;
    }


    public void get_category_date_offline(){

        String Cate_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_category_key);
        try {
            JSONObject response = new JSONObject(Cate_json_offline);
            JSONArray Arr= response.getJSONArray("msg");

            for(int i=0; i< Arr.length(); i++){
                JSONObject news_obj= Arr.getJSONObject(i);
                JSONObject News = news_obj.getJSONObject("Category");
                News.getString("name");
                News.getString("image");
                News.getInt("id");

                CategoryModel Category = new CategoryModel(
                        News.getString("name"),
                        News.getString("image"),
                        News.getInt("id")
                );
                Category_List.add(Category);
            }

            adapter = new Category_Adapter(new Category_Adapter.onClick() {
                @Override
                public void clickAction(int pos) {

                    startActivity(new Intent(getContext(), NewsDetail_F.class));

                }
            },Category_List);

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(adapter);


        } catch (JSONException e) {
            Variables.toast_msg(getContext(),""+e.toString());
            e.printStackTrace();
        }

    }


    // Get Data From API
    public void Get_Categories(){
        Variables.showpDialog(pDialog);

        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,getContext());
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'user_id': 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Variables.mVolleyService.postDataVolley("POSTCALL", Variables.NewsCategories, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                try{
                    JSONArray Arr= response.getJSONArray("msg");
                    // Save Response for Offline Showing Data

                    SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_category_key);


                    for(int i=0; i< Arr.length(); i++){
                        JSONObject news_obj= Arr.getJSONObject(i);
                        JSONObject News = news_obj.getJSONObject("Category");
                        News.getString("name");
                        News.getString("image");
                        News.getInt("id");

                        CategoryModel Category = new CategoryModel(
                                News.getString("name"),
                                News.getString("image"),
                                News.getInt("id")
                        );
                        Category_List.add(Category);
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

                    adapter = new Category_Adapter(new Category_Adapter.onClick() {
                        @Override
                        public void clickAction(int pos) {

                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },Category_List);

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(adapter);

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(getContext(),"Error in Saved "+v.toString());
                    Variables.Log_d_msg(getContext(),"Error in Saved "+v.toString());
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
                Variables.toast_msg(getContext(),"cate "+error.toString());
                Variables.Log_d_msg(getContext(),"cate "+error.toString());
            }
        };
    }
    // ENd Data From API





}
