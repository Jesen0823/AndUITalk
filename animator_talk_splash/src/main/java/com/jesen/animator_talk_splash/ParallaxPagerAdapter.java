package com.jesen.animator_talk_splash;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ParallaxPagerAdapter extends FragmentPagerAdapter {

    private List<ParallaxFragment> fragmentList;

    public ParallaxPagerAdapter(@NonNull FragmentManager fm, int behavior, List<ParallaxFragment> fragments) {
        super(fm, behavior);
        fragmentList = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
