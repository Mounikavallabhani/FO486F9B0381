package com.example.hani.social_app.SavedArticles;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hani.social_app.R;

public class Saved extends Fragment {

    View view;
    RecyclerView RV;
    Saved_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.saved, container, false);

        RV = (RecyclerView) view.findViewById(R.id.saved_RV_id);
        adapter = new Saved_Adapter();

        RV.setLayoutManager(new LinearLayoutManager(getContext()));
        RV.setAdapter(adapter);
        return view;

    }

}
