package com.example.hani.social_app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.Category.Catergory;
import com.example.hani.social_app.SavedArticles.Saved;
import com.example.hani.social_app.Search.Search;
import com.example.hani.social_app.TopNews.Discover;

public class MainFragment extends Fragment {

    View view;
    TabLayout tabLayout;
    ViewPager pager;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.main_fragment, container, false);


        tabLayout = (TabLayout) view.findViewById(R.id.mainfragment_tabs);
        pager = (ViewPager) view.findViewById(R.id.mainfragment_viewpager_id);

        View_pager_Adapter viewpageradapter = new View_pager_Adapter(getChildFragmentManager());
        viewpageradapter.addfragment(new Discover(), "");
        viewpageradapter.addfragment(new Catergory(), "");
//        viewpageradapter.addfragment(new Search(), "");
        viewpageradapter.addfragment(new Saved(), "");

        pager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(pager);

        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
        ImageView imageView1 = (ImageView) view1.findViewById(R.id.icon);
        TextView textView1 = (TextView) view1.findViewById(R.id.text);
        imageView1.setImageResource(R.drawable.ic_discover_color);
        textView1.setText("Discover");
        textView1.setTextColor(getResources().getColor(R.color.blue));
        tabLayout.getTabAt(0).setCustomView(view1);

        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
        ImageView imageView2 = (ImageView) view2.findViewById(R.id.icon);
        TextView textView2 = (TextView) view2.findViewById(R.id.text);
        imageView2.setImageResource(R.drawable.ic_category_gray);
        textView2.setText("Category");
        tabLayout.getTabAt(1).setCustomView(view2);

//        View view3 = LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
//        ImageView imageView3 = (ImageView) view3.findViewById(R.id.icon);
//        TextView textView3 = (TextView) view3.findViewById(R.id.text);
//        imageView3.setImageResource(R.drawable.ic_search_gray);
//        textView3.setText("Search");
//        tabLayout.getTabAt(2).setCustomView(view3);

        View view4 = LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
        ImageView imageView4 = (ImageView) view4.findViewById(R.id.icon);
        TextView textView4 = (TextView) view4.findViewById(R.id.text);
        imageView4.setImageResource(R.drawable.ic_saved_gray);
        textView4.setText("Saved");
        tabLayout.getTabAt(2).setCustomView(view4);


        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                View view = tab.getCustomView();
                ImageView image = view.findViewById(R.id.icon);
                TextView textView = view.findViewById(R.id.text);
                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_discover_color));
                        textView.setTextColor(getResources().getColor(R.color.blue));
                        break;

                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_category_color));
                        textView.setTextColor(getResources().getColor(R.color.blue));
                        break;

//                    case 2:
//                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_color));
//                        textView.setTextColor(getResources().getColor(R.color.blue));
//                        break;

                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_saved_color));
                        textView.setTextColor(getResources().getColor(R.color.blue));
                        break;
                }
                tab.setCustomView(view);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                ImageView image = view.findViewById(R.id.icon);
                TextView textView = view.findViewById(R.id.text);

                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_discover_gray));
                        textView.setTextColor(getResources().getColor(R.color.gray));
                        break;

                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_category_gray));
                        textView.setTextColor(getResources().getColor(R.color.gray));
                        break;

//                    case 2:
//                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_gray));
//                        textView.setTextColor(getResources().getColor(R.color.gray));
//                        break;

                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_saved_gray));
                        textView.setTextColor(getResources().getColor(R.color.gray));
                        break;
                }
                tab.setCustomView(view);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.getTabAt(0).select();

        return view;

    }

}
