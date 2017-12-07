package com.tiwipabmin.eatallday.controller.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tiwipabmin.eatallday.EatAllDayPreferences;
import com.tiwipabmin.eatallday.controller.fragment.LobbyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;

import com.tiwipabmin.eatallday.controller.fragment.KitchenFragment;
import com.tiwipabmin.eatallday.controller.fragment.VerificationLogoutDialogFragment;
import com.tiwipabmin.eatallday.model.User;

public class TabBarActivity extends AppCompatActivity {

    public static final int SELECT_IMAGE = 1969;
    public static User USER;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        USER = intent.getParcelableExtra("user");

        if(EatAllDayPreferences.getUserName(this).equals("")){
            EatAllDayPreferences.setUserName(this, USER.getUserId());
        }


        setUp();
    }

    private void setUp(){

        setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LobbyFragment(), "หน้ากระดาน");
        adapter.addFragment(new KitchenFragment(), "ห้องครัว");
        viewPager.setAdapter(adapter);
    }

    @OnClick(R.id.iv_logout)
    void onLogOutTouched(){

        DialogFragment verifyLogoutDialog = new VerificationLogoutDialogFragment();
        verifyLogoutDialog.show(this.getSupportFragmentManager(), "verifyLogoutDialog");
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
