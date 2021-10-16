package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private SimpleFragmentPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To remove Shadow belove the ActionBar
        getSupportActionBar().setElevation(0);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        FragmentManager fm = getSupportFragmentManager();
        pagerAdapter = new SimpleFragmentPagerAdapter(fm, getLifecycle());

        viewPager.setAdapter(pagerAdapter);

        // Adding Tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.numbers));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.family));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.colors));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.phrases));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

//
//        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.view_pager);
//
//        SimpleFragmentPagerAdapter pagerAdapter =
//                new SimpleFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
//
//        viewPager.setAdapter(pagerAdapter);





        
//        TextView numbersTextView = findViewById(R.id.numbersTextView);
//
//        numbersTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        TextView familyTextView = findViewById(R.id.familyTextView);
//        familyTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, FamilyActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        TextView colorsTextView = findViewById(R.id.colorsTextView);
//        colorsTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ColorsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        TextView phrasesTextView = findViewById(R.id.phrasesTextView);
//        phrasesTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, PhrasesActivity.class);
//                startActivity(intent);
//            }
//        });


    }

}