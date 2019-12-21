package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.base.BaseFragment;
import com.thomas.video.entity.FollowEntity;
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
public class FollowFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private BaseQuickAdapter<FollowEntity, BaseViewHolder> adapter;
    private List<FollowEntity> datas = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_follow;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        adapter = new BaseQuickAdapter<FollowEntity, BaseViewHolder>(R.layout.item_follow, datas) {
            @Override
            protected void convert(BaseViewHolder helper, FollowEntity item) {
                ClickUtils.applyPressedViewScale(helper.itemView);
                helper.setText(R.id.item_tv_name, item.getName());
                ImageHelper.displayContentImage(helper.getView(R.id.item_iv_video), item.getImgUrl());
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(0);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnItemLongClickListener((adapter, view, position) -> {
            showTips(position);
            return true;
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", datas.get(position).getName());
            bundle.putString("id", datas.get(position).getVideoId());
            bundle.putString("url", "?m=vod-detail-id-" + datas.get(position).getVideoId() + ApiConstant.END_URL);
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });
    }

    private void showTips(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("真的要取消关注“ " + datas.get(position).getName() + " ”吗?")
                .setTitle("提示")
                .setPositiveButton("取消关注", (dialog, which) -> LitePal.deleteAllAsync(FollowEntity.class, "videoId =?", datas.get(position).getVideoId()).listen(new UpdateOrDeleteCallback() {
                    @Override
                    public void onFinish(int rowsAffected) {
                        ToastUtils.showShort("取消关注成功");
                        dialog.dismiss();
                        adapter.remove(position);
                        if (adapter.getData().size() == 0) {
                            EmptyView emptyView = new EmptyView(mActivity);
                            emptyView.setInfo("什么都没有", "快去关注你感兴趣的影片吧！");
                            adapter.setEmptyView(emptyView);
                        }
                    }
                }))
                .setNegativeButton("再想想", (dialog, which) -> dialog.dismiss()).create().show();
    }

    @Override
    public void doBusiness() {
        LitePal.order("createTime desc").findAsync(FollowEntity.class).listen(list -> {
            datas.clear();
            datas.addAll(list);
            if (datas.size() == 0) {
                EmptyView emptyView = new EmptyView(mActivity);
                emptyView.setInfo("什么都没有", "快去关注你感兴趣的影片吧！");
                adapter.setEmptyView(emptyView);
            } else {
                adapter.setNewData(datas);
            }

        });
    }

    @Override
    public void onDebouncingClick(View view) {

    }



    @Override
    public void onResume() {
        super.onResume();
        LitePal.order("createTime desc").findAsync(FollowEntity.class).listen(list -> {
            datas.clear();
            datas.addAll(list);
            if (datas.size() == 0) {
                EmptyView emptyView = new EmptyView(mActivity);
                emptyView.setInfo("什么都没有", "快去关注你感兴趣的影片吧！");
                adapter.setEmptyView(emptyView);
            } else {
                adapter.setNewData(datas);
            }

        });
    }

}
