package com.dinosoftlabs.dnews.Category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.Category.CategoryModel.CategoryModel;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;
import com.dinosoftlabs.dnews.TopNews.itemdecoration;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Catergory extends Fragment {

    View view;
    RecyclerView recyclerView;
    Category_Adapter adapter;
    EditText search_pad;
    SwipeRefreshLayout pullToRefresh;
    // Define List
    private List<CategoryModel> Category_List;
    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    boolean is_swipe_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.category, container, false);
        // Progress Dialogue init
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);
        search_pad = view.findViewById(R.id.category_search_ET_id);

        search_pad.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
        });

        Category_List = new ArrayList<>();
        is_wifi_availeable=Variables.is_internet_avail(getContext());

        //// Swipe Refreash
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//       // your code

                if(is_wifi_availeable == true){
                  is_swipe_view = false;
                    Category_List.clear();
                    Get_Categories(is_swipe_view);
                }else{
                    Variables.alert_dialogue(getContext(),
                            "" + getResources().getString(R.string.info),
                            "" + getResources().getString(R.string.wifi_connected_info)
                    );

                    pullToRefresh.setRefreshing(false);

                }


            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.category_RV_id);

        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new itemdecoration(spacing, spanCount, includeEdge));


        String Cate_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_category_key);

        if(Cate_json_offline != null){
            // If Data is save in Offline

            get_category_date_offline();

        }else{
            // If Data is not save in Offline
            if(is_wifi_availeable == true){
                // If Wifi Available
                is_swipe_view = false;
                Get_Categories(is_swipe_view);

            }else{
                // If wifi not available


                if(Cate_json_offline != null){
                    get_category_date_offline();
                }else{
                    is_swipe_view = false;
                    Get_Categories(is_swipe_view);
                }

            }
            //Get_Categories();


        }



        return view;
    }


    void filter(String text){
        List<CategoryModel> temp = new ArrayList();

        for(CategoryModel d: Category_List){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList((ArrayList<CategoryModel>) temp);
    }

    public void get_category_date_offline(){

        String Cate_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_category_key);
        try {
            JSONObject response = new JSONObject(Cate_json_offline);
            JSONArray Arr = response.getJSONArray("msg");

            for(int i = 0; i < Arr.length(); i++){
                JSONObject news_obj= Arr.getJSONObject(i);
                JSONObject News = news_obj.getJSONObject("Category");

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
            },Category_List,getContext());

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(adapter);


        } catch (JSONException e) {
            Variables.toast_msg(getContext(),""+e.toString());
            e.printStackTrace();
        }

    }


    // Get Data From API
    public void Get_Categories(boolean is_swipe){

        if(is_swipe == true){
            Variables.showpDialog(pDialog);
        }else {
            pullToRefresh.setRefreshing(true);
        }


        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,getContext());

        JSONObject sendObj = null;

        String user_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);

        try{
            if(user_json != null){
                // If user is Login
                sendObj = new JSONObject("{'user_id': "+SharedPrefrence.get_user_id_from_json(getContext())+" }");
            }else{
                // If user is not login
                sendObj = new JSONObject("{'user_id': 2 }");
            }


        }catch (Exception v){
            v.printStackTrace();
            }

        Variables.mVolleyService.postDataVolley("POSTCALL", Variables.NewsCategories, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                pullToRefresh.setRefreshing(false);
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

                    adapter = new Category_Adapter(new Category_Adapter.onClick() {
                        @Override
                        public void clickAction(int pos) {

                            startActivity(new Intent(getContext(), NewsDetail_F.class));

                        }
                    },Category_List,getContext());

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(adapter);

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    pullToRefresh.setRefreshing(false);
                    Variables.toast_msg(getContext(),"Error in Saved "+v.toString());
                    Variables.Log_d_msg(getContext(),"Error in Saved "+v.toString());

                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {

                Variables.hidepDialog(pDialog);
                pullToRefresh.setRefreshing(false);
                Variables.toast_msg(getContext(),"" + error.toString());

            }
        };
    }
    // ENd Data From API





}
