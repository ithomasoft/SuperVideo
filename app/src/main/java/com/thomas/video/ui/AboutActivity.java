package com.thomas.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.tencent.bugly.beta.Beta;
import com.thomas.video.R;
import com.thomas.video.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_version)
    AppCompatTextView tvVersion;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        toolbar.setTitle(getString(R.string.menu_about));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        tvVersion.setText("V"+AppUtils.getAppVersionName());
        applyDebouncingClickListener(tvCheck, tvFeedback, tvOpen, tvShare);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ActivityUtils.finishActivity(mActivity, true);
        }
        return true;

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onDebouncingClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_check) {
            Beta.checkUpgrade();
        } else if (viewId == R.id.tv_feedback) {
            ActivityUtils.startActivity(FeedbackActivity.class);
        } else if (viewId == R.id.tv_open) {
            ActivityUtils.startActivity(OpenSourceActivity.class);
        } else if (viewId == R.id.tv_share) {
            Intent intent = IntentUtils.getShareTextIntent("这里有超全的影视资源，快来看一下吧(https://dwz.cn/xtDm6nRA)");
            startActivity(intent);
        }
    }

}
