package com.example.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.voicerecorder.Fragments.RecorderFragment;
import com.example.voicerecorder.Fragments.RecordingsFragment;
import com.example.voicerecorder.adapters.ViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setSupportActionBar(toolbar);
        setupViewpager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewpager(ViewPager viewPager){
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(new RecorderFragment(),"Recorder");
        viewpagerAdapter.addFragment(new RecordingsFragment(),"Recordings");
        viewPager.setAdapter(viewpagerAdapter);

    }
}