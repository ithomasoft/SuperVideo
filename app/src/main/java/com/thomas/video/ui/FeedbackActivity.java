package com.thomas.video.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.AppUtils;
import com.thomas.core.utils.DeviceUtils;
import com.thomas.core.utils.NetworkUtils;
import com.thomas.video.R;
import com.thomas.video.base.ThomasActivity;

import butterknife.BindView;

public class FeedbackActivity extends ThomasActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root_feedback)
    ConstraintLayout rootFeedback;

    protected AgentWeb mAgentWeb;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;


    String url = "https://support.qq.com/product/78952";

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        toolbar.setTitle("意见反馈");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        initWebClient();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!mAgentWeb.back()){
                    ActivityUtils.finishActivity(FeedbackActivity.class);
                }

                break;
        }
        return true;

    }

    @Override
    public void doBusiness() {

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(rootFeedback, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(mActivity,R.color.thomas_color_app_title_background))
                .closeWebViewClientHelper()
                .setWebViewClient(webViewClient)
                .setWebChromeClient(webChromeClient)
                .setMainFrameErrorView(R.layout.view_status, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url);


        String postData = "clientInfo=" + DeviceUtils.getManufacturer() + "-" + DeviceUtils.getModel()
                + "&clientVersion=" + AppUtils.getAppVersionName()
                + "&os=" + DeviceUtils.getSDKVersionName()
                + "&osVersion=" + DeviceUtils.getSDKVersionCode()
                + "&netType=" + NetworkUtils.getNetworkType().name()
                + "&imei=" + DeviceUtils.getUniqueDeviceId()
                + "&nickname=" + DeviceUtils.getManufacturer() + "|" + DeviceUtils.getModel()+"|"+DeviceUtils.getSDKVersionName()
                + "&avatar=" + "http://i-3.99youmeng.com/2016/11/9/7dac6331-ac5f-4d43-87a3-69dce07c4081.jpg"
                + "&openid=" + DeviceUtils.getAndroidID();
        mAgentWeb.getUrlLoader().postUrl(url, postData.getBytes());
    }
    private void initWebClient() {
        webViewClient = new WebViewClient() {

        };
        webChromeClient = new WebChromeClient() {

        };
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
