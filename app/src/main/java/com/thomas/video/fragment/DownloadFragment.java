package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ClickUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.thomas.video.R;
import com.thomas.video.base.BaseFragment;
import com.thomas.video.entity.DownloadEntity;
import com.thomas.video.helper.ImageHelper;
import com.thomas.video.widget.EmptyView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class DownloadFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter<DownloadEntity, BaseViewHolder> adapter;
    private List<DownloadEntity> datas = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_download;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        adapter = new BaseQuickAdapter<DownloadEntity, BaseViewHolder>(R.layout.item_history, datas) {
            @Override
            protected void convert(BaseViewHolder helper, DownloadEntity item) {
                helper.setText(R.id.item_tv_name, item.getTitle());
                helper.setText(R.id.item_tv_time, item.getCreateDate());
                ImageHelper.displayExtraImage(helper.getView(R.id.item_iv_video), item.getImgUrl());
                ClickUtils.applyPressedViewScale(helper.getView(R.id.item_iv_delete));
                helper.addOnClickListener(R.id.item_iv_delete);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(0);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnItemClickListener((adapter, view, position) -> {
        });

        tabLayout.addTab(tabLayout.newTab().setText("正在下载"), 0, true);
        tabLayout.addTab(tabLayout.newTab().setText("下载完成"), 1, false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getData("0");
                } else {
                    getData("1");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void doBusiness() {
getData("0");
    }

    @Override
    public void onDebouncingClick(View view) {

    }

    private void getData(String type) {
        LitePal.order("createDate desc").where("state like ?", type).findAsync(DownloadEntity.class).listen(list -> {
            datas.clear();
            datas.addAll(list);
            if (datas.size() == 0) {
                EmptyView emptyView = new EmptyView(mActivity);
                emptyView.setInfo("什么都没有", "快去下载你感兴趣的影片吧！");
                adapter.setEmptyView(emptyView);
            } else {
                adapter.setNewData(datas);
            }

        });
    }
}
