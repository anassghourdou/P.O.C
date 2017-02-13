package com.example.lenovo.POC;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.Vector;

public class MainActivity extends FragmentActivity {

    private android.support.v4.view.PagerAdapter mPagerAdapter;

    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager_view);
        final List fragments = new Vector();

        fragments.add(Fragment.instantiate(this, PlaylistFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, AlbumFragment.class.getName()));

        this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = (ViewPager) super.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(pager);

        pager.setAdapter(this.mPagerAdapter);


    }


}