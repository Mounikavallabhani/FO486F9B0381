package com.example.hani.social_app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class View_pager_Adapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmenttitlelist = new ArrayList<>();

    public View_pager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmenttitlelist.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return fragmenttitlelist.get(position);
    }

    public void addfragment(android.support.v4.app.Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmenttitlelist.add(title);
    }
}
