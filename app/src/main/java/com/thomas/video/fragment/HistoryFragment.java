package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.base.BaseFragment;
import com.thomas.video.entity.HistoryEntity;
import com.thomas.video.helper.ImageHelper;
import com.thomas.video.ui.DetailActivity;
import com.thomas.video.widget.EmptyView;

import org.litepal.LitePal;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class HistoryFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter<HistoryEntity, BaseViewHolder> adapter;
    private List<HistoryEntity> datas = new ArrayList<>();


    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        adapter = new BaseQuickAdapter<HistoryEntity, BaseViewHolder>(R.layout.item_history, datas) {
            @Override
            protected void convert(BaseViewHolder helper, HistoryEntity item) {
                helper.setText(R.id.item_tv_name, item.getName());
                helper.setText(R.id.item_tv_current_name, "当前观看到：" + item.getCurrentName());
                helper.setText(R.id.item_tv_time, item.getCreateTime());
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
            Bundle bundle = new Bundle();
            bundle.putString("title", datas.get(position).getName());
            bundle.putString("id", datas.get(position).getVideoId());
            bundle.putString("url", "?m=vod-detail-id-" + datas.get(position).getVideoId() + ApiConstant.END_URL);
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setMessage("真的要删除“ " + datas.get(position).getName() + " ”的记录吗?")
                    .setTitle("提示")
                    .setPositiveButton("删除记录", (dialog, which) -> deleteHistory(position))
                    .setNegativeButton("再想想", (dialog, which) -> dialog.dismiss()).create().show();

        });
    }

    @Override
    public void doBusiness() {
        LitePal.order("createTime desc").findAsync(HistoryEntity.class).listen(list -> {
            datas.clear();
            datas.addAll(list);
            if (datas.size() == 0) {
                EmptyView emptyView = new EmptyView(mActivity);
                emptyView.setInfo("什么都没有", "快去观看你感兴趣的影片吧！");
                adapter.setEmptyView(emptyView);
            } else {
                adapter.setNewData(datas);
            }

        });
    }

    private void deleteHistory(int position) {
        LitePal.deleteAllAsync(HistoryEntity.class, "videoId =?", datas.get(position).getVideoId()).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                adapter.remove(position);
                if (adapter.getData().size() == 0) {
                    EmptyView emptyView = new EmptyView(mActivity);
                    emptyView.setInfo("什么都没有", "快去关注你感兴趣的影片吧！");
                    adapter.setEmptyView(emptyView);
                }
            }
        });
    }

    @Override
    public void onDebouncingClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        LitePal.order("createTime desc").findAsync(HistoryEntity.class).listen(list -> {
            datas.clear();
            datas.addAll(list);
            if (datas.size() == 0) {
                EmptyView emptyView = new EmptyView(mActivity);
                emptyView.setInfo("什么都没有", "快去观看你感兴趣的影片吧！");
                adapter.setEmptyView(emptyView);
            } else {
                adapter.setNewData(datas);
            }

        });
    }
}
