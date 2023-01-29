package com.example.voicerecorder.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final ArrayList<String> fragTitle = new ArrayList<>();


    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        fragTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      return fragTitle.get(position);
    }
}
