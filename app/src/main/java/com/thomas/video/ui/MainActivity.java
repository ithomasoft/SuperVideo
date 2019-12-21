package com.thomas.video.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.thomas.video.R;
import com.thomas.video.base.BaseActivity;
import com.thomas.video.base.BaseFragment;
import com.thomas.video.fragment.DownloadFragment;
import com.thomas.video.fragment.FollowFragment;
import com.thomas.video.fragment.HistoryFragment;
import com.thomas.video.fragment.SearchFragment;
import com.thomas.video.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.home_container)
    NoScrollViewPager homeContainer;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private int homeId;
    private FragmentPagerAdapter adapter;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            showCurrentFragment(0);
            toolbar.setTitle(getString(R.string.menu_search));
        } else if (id == R.id.nav_follow) {
            showCurrentFragment(1);
            toolbar.setTitle(getString(R.string.menu_follow));
        } else if (id == R.id.nav_history) {
            showCurrentFragment(2);
            toolbar.setTitle(getString(R.string.menu_history));
        } else if (id == R.id.nav_download) {
            showCurrentFragment(3);
            toolbar.setTitle(getString(R.string.menu_download));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {
            startSetting();
        } else if (id == R.id.nav_about) {
            startAbout();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startAbout() {
        new Handler().postDelayed(() -> ActivityUtils.startActivity(AboutActivity.class), 250);
    }

    private void startSetting() {
        new Handler().postDelayed(() -> ActivityUtils.startActivity(SettingActivity.class), 250);
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        homeId = SPUtils.getInstance("setting").getInt("home", 0);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(homeId).setChecked(true);
        homeContainer.setScroll(false);
    }

    @Override
    public void doBusiness() {
        mFragments.add(new SearchFragment());
        mFragments.add(new FollowFragment());
        mFragments.add(new HistoryFragment());
        mFragments.add(new DownloadFragment());
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        homeContainer.setAdapter(adapter);
        homeContainer.setOffscreenPageLimit(mFragments.size());
    }

    @Override
    public void onDebouncingClick(View view) {

    }

    private void showCurrentFragment(int index) {
        new Handler().postDelayed(() -> homeContainer.setCurrentItem(index, false), 250);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private long timeMillis;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                ToastUtils.showShort("再按一次退出【够看影视】");
                timeMillis = System.currentTimeMillis();
            } else {
                AppUtils.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
