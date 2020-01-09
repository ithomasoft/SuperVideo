package com.thomas.video.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.base.BaseActivity;
import com.thomas.video.helper.ImageHelper;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/26
 * @updatelog
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.ll_slogan)
    LinearLayout llSlogan;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setNavBarVisibility(this, false);
    }

    @Override
    public void doBusiness() {
        new Handler().postDelayed(() -> getSplash(), 1500);
    }


    private void getSplash() {
        Kalle.get(ApiConstant.Others.ONE_URL).perform(new SimpleCallback<String>() {

            @Override
            public void onResponse(SimpleResponse<String, String> response) {
                if (response.isSucceed()) {
                    llSlogan.setVisibility(View.VISIBLE);
                    Document document = Jsoup.parse(response.succeed());
                    String slogan = document.getElementById("quote").text();
                    String url = document.getElementsByClass("home-img").outerHtml()
                            .replace("<div class=\"home-img\" style=\"background-image:url(", "")
                            .replace("div", "")
                            .replace("</>", "")
                            .replace(")\"> ", "").trim();
                    tvSlogan.setText(" 『  " + slogan + " 』");
                    ImageHelper.displayImage(ivSplash, url);
                }
            }

            @Override
            public void onEnd() {
                super.onEnd();
                new Handler().postDelayed(() -> {
                    ActivityUtils.startActivity(MainActivity.class);
                    ActivityUtils.finishActivity(SplashActivity.class, true);
                }, 3500);
            }
        });

    }

    @Override
    public void onDebouncingClick(View view) {

    }
}
