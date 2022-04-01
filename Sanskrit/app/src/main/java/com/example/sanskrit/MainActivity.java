package com.example.sanskrit;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabItem numbers , family, colors, phrases;
private PageAdapter pageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.vpager);


        pageAdapter = new PageAdapter(this,getSupportFragmentManager());

        viewPager.setAdapter(pageAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Number"));
        tabLayout.addTab(tabLayout.newTab().setText("Family"));
        tabLayout.addTab(tabLayout.newTab().setText("Colors"));
        tabLayout.addTab(tabLayout.newTab().setText("Phrases"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);


    }
}