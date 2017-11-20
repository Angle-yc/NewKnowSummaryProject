package com.angle.hshb.newknowsummaryproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.angle.hshb.newknowsummaryproject.fragments.NotificationFragment;
import com.angle.hshb.newknowsummaryproject.fragments.SelectImageFragment;
import com.angle.hshb.newknowsummaryproject.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    List<Fragment> mListFragment = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        addTabLayoutData();
        addFragment();
        initViewPager();
        initTabLayout();
    }

    /**
     * 添加切换按钮
     */
    private void addTabLayoutData() {
        mTabLayout.addTab(mTabLayout.newTab().setText("通知"));
        mTabLayout.addTab(mTabLayout.newTab().setText("选择照片"));
        mTabLayout.addTab(mTabLayout.newTab().setText("多媒体"));
    }

    /**
     * 添加碎片
     */
    private void addFragment() {
        NotificationFragment notificationFragment = new NotificationFragment();
        VideoFragment videoFragment = new VideoFragment();
        SelectImageFragment selectImageFragment = new SelectImageFragment();
        mListFragment.add(notificationFragment);
        mListFragment.add(selectImageFragment);
        mListFragment.add(videoFragment);
    }

    private void initViewPager() {
        MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
    }

    private void initTabLayout() {
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);
        mTabLayout.setTabTextColors(Color.GRAY,Color.BLACK);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    }


    class MyFragmentAdapter extends FragmentStatePagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }
    }
}
