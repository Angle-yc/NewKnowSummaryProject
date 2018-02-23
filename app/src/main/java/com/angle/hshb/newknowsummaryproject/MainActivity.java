package com.angle.hshb.newknowsummaryproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.angle.hshb.newknowsummaryproject.fragments.NotificationFragment;
import com.angle.hshb.newknowsummaryproject.fragments.SelectImageFragment;
import com.angle.hshb.newknowsummaryproject.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Mainactivity";
    ViewPager mViewPager;
    TabLayout mTabLayout;
    List<Fragment> mListFragment = new ArrayList<>();
    private String[] mNeedPermissionsArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static final int PERMISSION_CODE =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        addTabLayoutData();
        addFragment();
        initViewPager();
        initTabLayout();
        /**
         * 创建分支1.0
         */
    }

    /**
     * 请求权限
     */
    private void requestPermissions() {
        for (String permission : mNeedPermissionsArray){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{permission},PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i(TAG,"权限打开了");
            }else {
                Log.i(TAG,"权限被拒绝了");
            }
        }
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
//        VideoFragment videoFragment = new VideoFragment();
        VideoFragment videoFragment = null;
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
