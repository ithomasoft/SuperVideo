package com.thomas.video.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.video.R;
import com.thomas.video.adapter.OpenSourceAdapter;
import com.thomas.video.base.ThomasActivity;
import com.thomas.video.bean.OpenSourceBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OpenSourceActivity extends ThomasActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private OpenSourceAdapter adapter;
    private List<OpenSourceBean> datas = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_open_source;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        toolbar.setTitle("开源项目");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        adapter = new OpenSourceAdapter(datas);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        AppCompatTextView footerView = new AppCompatTextView(mActivity);
        footerView.setGravity(Gravity.CENTER);
        footerView.setPadding(36, 36, 36, 36);
        footerView.setText("开源世界，万物皆精彩");
        adapter.setFooterView(footerView);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(datas.get(position).getUrl());
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    @Override
    public void doBusiness() {
        datas.add(new OpenSourceBean("AndroidUtilCode", "https://github.com/Blankj/AndroidUtilCode"));
        datas.add(new OpenSourceBean("BaseRecyclerViewAdapterHelper", "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"));
        datas.add(new OpenSourceBean("SmartRefreshLayout", "https://github.com/scwang90/SmartRefreshLayout"));
        datas.add(new OpenSourceBean("Butterknife", "https://github.com/JakeWharton/butterknife"));
        datas.add(new OpenSourceBean("LitePal", "https://github.com/LitePalFramework/LitePal"));
        datas.add(new OpenSourceBean("JiaoZiVideoPlayer", "https://github.com/Jzvd/JiaoZiVideoPlayer"));
        datas.add(new OpenSourceBean("ExoPlayer", "https://github.com/google/ExoPlayer"));
        datas.add(new OpenSourceBean("Jsoup", "https://github.com/jhy/jsoup"));
        datas.add(new OpenSourceBean("Kalle", "https://github.com/yanzhenjie/Kalle"));
        datas.add(new OpenSourceBean("Glide", "https://github.com/bumptech/glide"));
        datas.add(new OpenSourceBean("Fastjson", "https://github.com/alibaba/fastjson"));
        datas.add(new OpenSourceBean("OkDownload", "https://github.com/lingochamp/okdownload"));
        datas.add(new OpenSourceBean("EventBus", "https://github.com/greenrobot/EventBus"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ActivityUtils.finishActivity(mActivity);
        }
        return true;

    }

}
