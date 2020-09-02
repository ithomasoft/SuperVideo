package com.thomas.video.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.BarUtils;
import com.thomas.core.utils.SPUtils;
import com.thomas.core.utils.TimeUtils;
import com.thomas.video.ApiService;
import com.thomas.video.R;
import com.thomas.video.base.ThomasActivity;
import com.thomas.video.helper.ImageHelper;
import com.thomas.video.retrofit.RetrofitHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thomas
 * @date 2019/6/26
 * @updatelog
 */
public class SplashActivity extends ThomasActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_slogan)
    TextView tvSlogan;
    @BindView(R.id.ll_slogan)
    LinearLayout llSlogan;
    private String flag;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        flag = TimeUtils.date2String(new Date(), "yyyyMMdd");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void initStatusBar() {
        super.initStatusBar();
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setNavBarVisibility(this, false);
    }

    @Override
    public void doBusiness() {
        new Handler().postDelayed(() -> {

            String splash = SPUtils.getInstance("splash").getString(flag);
            if (TextUtils.isEmpty(splash)) {
                getSplash();
            } else {
                llSlogan.setVisibility(View.VISIBLE);
                tvSlogan.setText(" 『  " + splash.split("_")[0] + " 』");
                ImageHelper.displayImage(ivSplash, splash.split("_")[1]);
                new Handler().postDelayed(() -> {
                    ActivityUtils.startActivity(MainActivity.class);
                    ActivityUtils.finishActivity(SplashActivity.class, true);
                }, 3500);
            }

        }, 1500);
    }


    private void getSplash() {

        RetrofitHelper.createApi(ApiService.class)
                .getSplash().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    llSlogan.setVisibility(View.VISIBLE);
                    Document document = Jsoup.parse(response.body());
                    String slogan = document.getElementById("quote").text();
                    String url = document.getElementsByClass("home-img").outerHtml()
                            .replace("<div class=\"home-img\" style=\"background-image:url(", "")
                            .replace("div", "")
                            .replace("</>", "")
                            .replace(")\"> ", "").trim();
                    tvSlogan.setText(" 『  " + slogan + " 』");
                    ImageHelper.displayImage(ivSplash, url);
                    SPUtils.getInstance("splash").put(flag, slogan + "_" + url);
                }


                new Handler().postDelayed(() -> {
                    ActivityUtils.startActivity(MainActivity.class);
                    ActivityUtils.finishActivity(SplashActivity.class, true);
                }, 3500);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                new Handler().postDelayed(() -> {
                    ActivityUtils.startActivity(MainActivity.class);
                    ActivityUtils.finishActivity(SplashActivity.class, true);
                }, 3500);
            }
        });


    }

}
