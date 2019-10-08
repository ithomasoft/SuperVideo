package com.thomas.video.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.base.BaseActivity;
import com.thomas.video.bean.SearchResultBean;
import com.thomas.video.helper.JsoupHelper;
import com.thomas.video.widget.EmptyView;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ResultActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String key;
    private int currentPage = 1;
    private boolean isLoad = false;

    private BaseQuickAdapter<SearchResultBean.DataBean, BaseViewHolder> adapter;
    private List<SearchResultBean.DataBean> datas = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        key = bundle.getString("key");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_result;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        toolbar.setTitle(key);
        toolbar.setSubtitle("——搜索结果");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        adapter = new BaseQuickAdapter<SearchResultBean.DataBean, BaseViewHolder>(R.layout.item_search_result, datas) {
            @Override
            protected void convert(BaseViewHolder helper, SearchResultBean.DataBean item) {
                ClickUtils.applyScale(helper.itemView);
                helper.setText(R.id.item_tv_name, item.getName());
                helper.setText(R.id.item_tv_type, item.getType());
                helper.setText(R.id.item_tv_time, "更新时间：" + item.getUpdateTime());
                helper.setText(R.id.item_tv_sharp, item.getSharp());
                helper.setText(R.id.item_tv_last, item.getLast().equals("完结") ? item.getLast() : ("更新至：" + item.getLast()));
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(0);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnLoadMoreListener(() -> {
            currentPage++;
            isLoad = true;
            getResult();
        }, recyclerView);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", datas.get(position).getName());
            bundle.putString("id", datas.get(position).getId());
            bundle.putString("url", datas.get(position).getTargetUrl());
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });
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
        getResult();
    }

    private void getResult() {
        String realKey = EncodeUtils.urlEncode(key);
        Kalle.get(ApiConstant.Search.SEARCH_URL + currentPage + ApiConstant.SEARCH_KEY + realKey + ApiConstant.END_URL)
                .perform(new SimpleCallback<String>() {

                    @Override
                    public void onStart() {
                        if (!isLoad) {
                            showLoading();
                        }
                    }

                    @Override
                    public void onResponse(SimpleResponse<String, String> response) {
                        SearchResultBean resultBean = JsoupHelper.parseSearchResult(response.succeed());
                        adapter.loadMoreComplete();
                        if (resultBean.getPage().getPageindex() < resultBean.getPage().getPagecount()) {
                            //可以加载更多
                            adapter.setEnableLoadMore(true);
                        } else {
                            adapter.setEnableLoadMore(false);
                            //已经加载完了
                            adapter.loadMoreEnd(false);
                        }
                        adapter.addData(resultBean.getData());

                        if (adapter.getData().size() == 0) {
                            EmptyView emptyView = new EmptyView(mActivity);
                            emptyView.setInfo("暂无结果", "换个关键词试试？");
                            adapter.setEmptyView(emptyView);
                        }
                    }

                    @Override
                    public void onException(Exception e) {
                        EmptyView emptyView = new EmptyView(mActivity);
                        emptyView.setInfo("请求失败", e.toString());
                        adapter.setEmptyView(emptyView);
                    }

                    @Override
                    public void onEnd() {
                        hideLoading();
                    }
                });
    }

    @Override
    public void onDebouncingClick(View view) {

    }
}
