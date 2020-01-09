package com.thomas.video.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.thomas.core.utils.ActivityUtils;
import com.thomas.video.R;
import com.thomas.video.base.ThomasActivity;

import butterknife.BindView;

public class FeedbackActivity extends ThomasActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
//        webView.setListener(this, this);
//        webView.setGeolocationEnabled(false);
//        webView.setMixedContentAllowed(true);
//        webView.setCookiesEnabled(true);
//        webView.setThirdPartyCookiesEnabled(true);
//        String openid = DeviceUtils.getAndroidID(); // 用户的openid
//        String nickname = DeviceUtils.getModel(); // 用户的nickname
//        String headimgurl = "https://support.qq.com/data/78952/2019/0818/59ca3a7e8deb3ed08d849adcd7f80ec4.png";  // 用户的头像url
//        String url = "https://support.qq.com/product/78952"; // 把1221数字换成你的产品ID，否则会不成功
//        String clientInfo = DeviceUtils.getSDKVersionName() + "|" + DeviceUtils.getManufacturer() + "|" + DeviceUtils.getModel();
//            String clientVersion = AppUtils.getAppVersionName();
//            String customInfo = "root["+DeviceUtils.isDeviceRooted()+"]";
//            String postData = "clientInfo=" + clientInfo + "&avatar=" + headimgurl + "&openid=" + openid
//                    + "&nickname=" + nickname + "&clientVersion=" + clientVersion
//                    +"&customInfo="+customInfo;
//        webView.postUrl(url, postData.getBytes());


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtils.finishActivity(FeedbackActivity.class);
                break;
        }
        return true;

    }
    @Override
    public void doBusiness() {

    }

}
