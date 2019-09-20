package com.hanglinpai.ui.user;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.hanglinpai.R;
;
import com.hanglinpai.ui.home.fragment.HomeFragment;
import com.hanglinpai.ui.user.constract.UserConstract;
import com.hanglinpai.ui.user.fragment.LoginFragment;
import com.hanglinpai.ui.user.fragment.ProfileFragment;
import com.hanglinpai.ui.user.fragment.RegFragment;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import www.meiyaoni.com.common.base.BaseActivity;

public class LoginOrRegActivity extends BaseActivity {
    XTabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.r_top)
    RelativeLayout r_top;
    LoginFragment loginFragment;
    RegFragment regFragment;
    private List<String> tabs;
    private List<Fragment> fragments;
    //当标签数目小于等于4个时，标签栏不可滑动
    public static final int MOVABLE_COUNT = 2;

    private int tabCount = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_reg;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        StatusBarTranslucent();
        loginFragment = new LoginFragment();
        regFragment = new RegFragment();
        tabLayout = findViewById(R.id.tabLayout);
        initViewPager();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("登录");
        titles.add("注册");
        fragments = new ArrayList<>();
        fragments.add(loginFragment);
        fragments.add(regFragment);
        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adatper);
        viewPager.setOffscreenPageLimit(2);
        //将TabLayout和ViewPager关联起来。
        XTabLayout tabLayout = (XTabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setupWithViewPager(viewPager);
    }


    public class FragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }



}
