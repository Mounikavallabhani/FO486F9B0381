package com.dinosoftlabs.dnews.SavedArticles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.TopNews.DataModels.NewsDataMode;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;

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
    TextView text_no_record;
    boolean is_swipe_view;
    private List<NewsDataMode> News_List;
    private String TAG = "Saved";
    boolean is_wifi_availeable;
    EditText save_search;
    SwipeRefreshLayout pullToRefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.saved, container, false);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        text_no_record = view.findViewById(R.id.no_record);

        save_search = view.findViewById(R.id.saved_search_ET_id);
        save_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
        });
        is_wifi_availeable= Variables.is_internet_avail(getContext());
        RV = (RecyclerView) view.findViewById(R.id.saved_RV_id);
        News_List = new ArrayList<>();

        //// Swipe Refreash
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(is_wifi_availeable == true){
                    is_swipe_view = false;
                    News_List.clear();
                    Get_Saved_News(is_swipe_view);
                 }else{
                    Variables.alert_dialogue(getContext(),
                            "" + getResources().getString(R.string.info),
                            "" + getResources().getString(R.string.wifi_connected_info)
                            );
                    pullToRefresh.setRefreshing(false);
                }


               // pullToRefresh.setRefreshing(false);
            }
        });

        ///  End Swipe Refresh



        String Saved_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_saved_news_key);

        if(Saved_json_offline != null){
            // If Data is save in Offline
            get_saved_offline();


        }else{
            // If Data is not save in Offline
            if(is_wifi_availeable == true){
                // If Wifi Available
                is_swipe_view = false;
                Get_Saved_News(is_swipe_view);

            }else{
                // If wifi not available
                get_saved_offline();
            }


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
try{
    adapter.updateList((ArrayList<NewsDataMode>) temp);
}catch (Exception c){

}


    }



    public void get_saved_offline(){

        String Saved_json_offline = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_saved_news_key);
        try {
            JSONObject response = new JSONObject(Saved_json_offline);

            JSONArray Arr= response.getJSONArray("msg");
                for(int i=0;i< Arr.length();i++){
                    JSONObject news_obj= Arr.getJSONObject(i);
                    JSONObject News = news_obj.getJSONObject("News");
                    JSONObject cate = News.getJSONObject("Category");
                    // cate.getString("name");
                    News.getString("title");
                    NewsDataMode a = new NewsDataMode(News.getString("attachment")
                            ,News.getString("title"),
                            News.getString("description"),
                            cate.getString("name"),
                            News.getInt("id")
                    );
                    News_List.add(a);
                }
                adapter = new Saved_Adapter(new Saved_Adapter.onClick() {
                    @Override
                    public void clickAction(int pos) {

                        startActivity(new Intent(getContext(), NewsDetail_F.class));

                    }
                },News_List, getContext());

                RV.setLayoutManager(new LinearLayoutManager(getContext()));
                RV.setAdapter(adapter);

            }catch (Exception v){
                Variables.toast_msg(getContext(),""+v.toString());
                Variables.Log_d_msg(getContext(),""+v.toString());
            }


        }


    // Get Data From API
    public void Get_Saved_News(boolean is_swipe){

        if(is_swipe == true){
            Variables.showpDialog(pDialog);
        }else {
            pullToRefresh.setRefreshing(true);
        }

        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,getContext());
        JSONObject sendObj = null;



        try {
            String user_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);
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
        Variables.mVolleyService.postDataVolley("POSTCALL", Variables.SavedNewsAPI, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){

        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

              ///  Variables.toast_msg(getContext(),""+response);

                if(response.length() == 0){
                    // If Response is Empty
                    text_no_record.setVisibility(View.VISIBLE);

                }else{
                    // If response is not Null

                    SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_saved_news_key);

                   try{
                        Variables.hidepDialog(pDialog);
                        pullToRefresh.setRefreshing(false);

                        JSONArray Arr= response.getJSONArray("msg");
                        for(int i=0;i< Arr.length();i++){
                            JSONObject news_obj= Arr.getJSONObject(i);
                            JSONObject News = news_obj.getJSONObject("News");
                            JSONObject cate = News.getJSONObject("Category");
                            cate.getString("name");
                            News.getString("title");

                            NewsDataMode a = new NewsDataMode(News.getString("attachment")
                                    ,News.getString("title"),
                                    News.getString("description"),
                                    cate.getString("name"),
                                    News.getInt("id")
                            );
                            News_List.add(a);

                        }
                        adapter = new Saved_Adapter(new Saved_Adapter.onClick() {
                            @Override
                            public void clickAction(int pos) {

                                startActivity(new Intent(getContext(), NewsDetail_F.class));

                            }
                        },News_List,getContext());

                        RV.setLayoutManager(new LinearLayoutManager(getContext()));
                        RV.setAdapter(adapter);

                    }catch (Exception v){

                        Variables.hidepDialog(pDialog);
                        pullToRefresh.setRefreshing(false);

                        Variables.toast_msg(getContext(),"" + v.toString());
                        Variables.Log_d_msg(getContext(),"" + v.toString());
                    }


                }



            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                /// Seperate methods to shoe log.d and Toast.
                Variables.hidepDialog(pDialog);
                pullToRefresh.setRefreshing(false);

                Variables.toast_msg(getContext(),""+error.toString());
                Variables.Log_d_msg(getContext(),""+error.toString());
            }
        };
    }
    // ENd Data From API



}
