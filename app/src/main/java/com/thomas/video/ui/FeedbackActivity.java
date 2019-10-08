package com.thomas.video.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.base.BaseActivity;
import com.thomas.video.widget.AdvancedWebView;

import butterknife.BindView;

public class FeedbackActivity extends BaseActivity implements AdvancedWebView.Listener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    AdvancedWebView webView;

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
        webView.setListener(this, this);
        webView.setGeolocationEnabled(false);
        webView.setMixedContentAllowed(true);
        webView.setCookiesEnabled(true);
        webView.setThirdPartyCookiesEnabled(true);
        String openid = DeviceUtils.getAndroidID(); // 用户的openid
        String nickname = DeviceUtils.getModel(); // 用户的nickname
        String headimgurl = "https://support.qq.com/data/78952/2019/0818/59ca3a7e8deb3ed08d849adcd7f80ec4.png";  // 用户的头像url
        String url = "https://support.qq.com/product/78952"; // 把1221数字换成你的产品ID，否则会不成功
        String clientInfo = DeviceUtils.getSDKVersionName() + "|" + DeviceUtils.getManufacturer() + "|" + DeviceUtils.getModel();
            String clientVersion = AppUtils.getAppVersionName();
            String customInfo = "root["+DeviceUtils.isDeviceRooted()+"]";
            String postData = "clientInfo=" + clientInfo + "&avatar=" + headimgurl + "&openid=" + openid
                    + "&nickname=" + nickname + "&clientVersion=" + clientVersion
                    +"&customInfo="+customInfo;
        webView.postUrl(url, postData.getBytes());


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtils.finishActivity(FeedbackActivity.class, true);
                break;
        }
        return true;

    }
    @Override
    public void doBusiness() {

    }

    @Override
    public void onDebouncingClick(View view) {

    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        webView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        if (!webView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        webView.setVisibility(View.INVISIBLE);
        showLoading();
    }

    @Override
    public void onPageFinished(String url) {
        webView.setVisibility(View.VISIBLE);
        hideLoading();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        ToastUtils.showLong("onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")");
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        ToastUtils.showShort("onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")");
    }

    @Override
    public void onExternalPageRequest(String url) {
        try {
            if (url.startsWith("weixin://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                ActivityUtils.startActivity(intent);
            }
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

}
