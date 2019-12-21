package com.thomas.video.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.thomas.video.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author Thomas
 * @date 2019/6/26
 * @updatelog
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected View mContentView;
    protected AppCompatActivity mActivity;

    private View.OnClickListener mClickListener = v -> onDebouncingClick(v);

    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        if (isNeedRegister() && (!EventBus.getDefault().isRegistered(this))) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        //默认实现沉浸式状态栏
        initStatusBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initData(bundle);
        }
        setRootLayout(bindLayout());
        ButterKnife.bind(this);
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    private void initStatusBar() {
        if (!mActivity.getClass().getName().endsWith("MainActivity")) {
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(mActivity, R.color.colorPrimary), true);
        }
        //默认强制竖屏
        ScreenUtils.setPortrait(this);
        if (BarUtils.isNavBarVisible(this)) {
            BarUtils.setNavBarVisibility(this, !isTransparent());
        }
        if (BarUtils.isSupportNavBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BarUtils.setNavBarColor(this, getResources().getColor(android.R.color.transparent));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isNeedRegister() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean isNeedAdapt() {
        return true;
    }

    protected int setAdaptScreen() {
        return 1080;
    }


    @Override
    public boolean isTransparent() {
        return true;
    }


    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(@LayoutRes int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    public void applyDebouncingClickListener(View... views) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener);
        ClickUtils.applyPressedViewScale(views);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        //强制字体大小不随着系统设置改变而改变
        Resources resource = mActivity.getResources();
        Configuration configuration = resource.getConfiguration();
        // 设置字体的缩放比例
        configuration.fontScale = 1.0f;
        if (DeviceUtils.getSDKVersionCode() < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            resource.updateConfiguration(configuration, resource.getDisplayMetrics());
        } else {
            mActivity.createConfigurationContext(configuration);
        }
        super.onResume();
    }

    @Override
    public Resources getResources() {
        if (isNeedAdapt()) {
            //今日头条屏幕适配方案
            //以宽度为基准
            return AdaptScreenUtils.adaptWidth(super.getResources(), setAdaptScreen());
        } else {
            return super.getResources();
        }
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
        }
        progressDialog.setMessage("加载中。。。");
        progressDialog.show();
    }

    public void showLoading(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
