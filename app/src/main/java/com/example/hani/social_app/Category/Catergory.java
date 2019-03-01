package com.example.hani.social_app.Category;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.itemdecoration;

public class Catergory extends Fragment {

    View view;
    RecyclerView recyclerView;
    Category_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.category, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.category_RV_id);
        adapter = new Category_Adapter();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new itemdecoration(spacing, spanCount, includeEdge));
        return view;

    }

}
