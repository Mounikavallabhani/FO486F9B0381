package com.example.hani.social_app.TopNews;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.LoginSignup.Login_details;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;

import java.util.Objects;

public class Discover extends Fragment implements View.OnClickListener {

    View view;
    TabLayout TL;
    RecyclerView RV1,RV2;
    RelativeLayout LL;
    ImageView IV;
    Discover_Adapter_one adapter1;
    Discover_Adapter_two adapter2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.discover, container, false);
        init();
        return view;

    }




    public void init(){

        TL = (TabLayout) view.findViewById(R.id.mainfragment_tabs);

        RV1 = (RecyclerView) view.findViewById(R.id.RV_id);

        RV2 = (RecyclerView) view.findViewById(R.id.discover_RL2_RV2_id);
        RV2.setNestedScrollingEnabled(false);

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
        adapter2 = new Discover_Adapter_two(new Discover_Adapter_two.onClick() {
            @Override
            public void clickAction(int pos) {

                startActivity(new Intent(getContext(), NewsDetail_F.class));

            }
        });

        LinearLayoutManager LLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RV1.setLayoutManager(LLM);
        RV1.setAdapter(adapter1);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(RV1);


        GridLayoutManager GLM = new GridLayoutManager(getContext(),2);
        RV2.setLayoutManager(GLM);
        RV2.setAdapter(adapter2);

        int spanCount = 2;
        int spacing = 40;
        boolean includeEdge = true;
        RV2.addItemDecoration(new itemdecoration(spacing, spanCount, includeEdge));

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all:

                break;

        }
    }


}

