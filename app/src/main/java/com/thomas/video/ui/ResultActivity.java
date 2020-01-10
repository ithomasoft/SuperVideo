package com.thomas.video.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.core.utils.Utils;
import com.thomas.video.R;
import com.thomas.video.adapter.ResultAdapter;
import com.thomas.video.base.ThomasMvpActivity;
import com.thomas.video.bean.SearchResultBean;
import com.thomas.video.helper.StatusHelper;
import com.thomas.video.ui.contract.ResultContract;
import com.thomas.video.ui.presenter.ResultPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ResultActivity extends ThomasMvpActivity<ResultPresenter> implements ResultContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private String key;
    private int currentPage = 1;
    private boolean isLoad = false;

    private ResultAdapter adapter;
    private List<SearchResultBean.DataBean> datas = new ArrayList<>();

    @Override
    protected ResultPresenter createPresenter() {
        return new ResultPresenter();
    }

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

        if (holder == null) {
            holder = StatusHelper.getDefault().wrap(smartRefreshLayout).withRetry(() -> {
                holder.showLoading();
                Utils.runOnUiThreadDelayed(() -> {
                    currentPage = 1;
                    datas.clear();
                    presenter.getData(currentPage, key);
                }, 1000);
            });
        }

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                presenter.getData(currentPage, key);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                datas.clear();
                presenter.getData(currentPage, key);
            }
        });

        adapter = new ResultAdapter(datas);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);
        adapter.setPreLoadNumber(0);
        adapter.disableLoadMoreIfNotFullPage(rvContent);

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
            ActivityUtils.finishActivity(mActivity);
        }
        return true;

    }

    @Override
    public void doBusiness() {
        holder.showLoading();
        Utils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getData(currentPage, key);
            }
        },1000);

    }

    @Override
    public void onFailed(String failed) {
        smartRefreshLayout.finishRefresh(false);
        smartRefreshLayout.finishLoadMore(false);
        if (currentPage == 1) {
            holder.withData(failed).showLoadFailed();
        } else {
            ToastUtils.showShort(failed);
        }
    }

    @Override
    public void getDataSuccess(List<SearchResultBean.DataBean> succeed) {
        holder.showLoadSuccess();
        adapter.addData(succeed);
    }

    @Override
    public void getDataEmpty() {
        holder.withData("换个关键词试试？").showEmpty();
    }

    @Override
    public void hasMoreData(boolean hasMoreData) {
        smartRefreshLayout.finishRefresh(true);
        smartRefreshLayout.finishLoadMore(true);
        smartRefreshLayout.setNoMoreData(!hasMoreData);
    }
}
