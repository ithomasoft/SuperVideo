package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.Utils;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.adapter.FollowAdapter;
import com.thomas.video.base.LazyThomasMvpFragment;
import com.thomas.video.entity.FollowEntity;
import com.thomas.video.fragment.contract.FollowContract;
import com.thomas.video.fragment.presenter.FollowPresenter;
import com.thomas.video.helper.DialogHelper;
import com.thomas.video.helper.StatusHelper;
import com.thomas.video.ui.DetailActivity;
import com.thomas.video.widget.NormalDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class FollowFragment extends LazyThomasMvpFragment<FollowPresenter> implements FollowContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;


    private FollowAdapter mAdapter;

    @Override
    protected FollowPresenter createPresenter() {
        return new FollowPresenter();
    }

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
        if (holder == null) {
            holder = StatusHelper.getDefault().wrap(smartRefreshLayout).withRetry(() -> {
                holder.showLoading();
                Utils.runOnUiThreadDelayed(() -> presenter.getData(), 1000);
            });
        }

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> presenter.getData());
        mAdapter = new FollowAdapter();
        rvContent.setLayoutManager(new GridLayoutManager(mActivity, 2));
        rvContent.setAdapter(mAdapter);

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showTips(position);
            return true;
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", mAdapter.getData().get(position).getName());
            bundle.putString("id", mAdapter.getData().get(position).getVideoId());
            bundle.putString("url", "?m=vod-detail-id-" + mAdapter.getData().get(position).getVideoId() + ApiConstant.END_URL);
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });
    }

    private void showTips(int position) {
        DialogHelper.showDialogCenter("提示", "真的要取消关注“ " + mAdapter.getData().get(position).getName() + " ”吗?",
                "再想想", "取消关注", new NormalDialog.OnDialogListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onSure() {
                        deleteFollow(position);
                    }
                });
    }

    private void deleteFollow(int position) {
        presenter.deleteFollow(position, mAdapter.getData().get(position).getVideoId());
    }

    @Override
    protected void onFirstUserVisible() {
        holder.showLoading();
        Utils.runOnUiThreadDelayed(() -> presenter.getData(), 1000);
    }

    @Override
    protected void onUserVisible() {
        presenter.getData();
    }

    @Override
    public void getDataSuccess(List<FollowEntity> succeed) {
        smartRefreshLayout.finishRefresh(true);
        holder.showLoadSuccess();
        mAdapter.setNewData(succeed);
    }

    @Override
    public void getDataEmpty() {
        smartRefreshLayout.finishRefresh(true);
        holder.withData("快去关注你感兴趣的影片吧！").showEmpty();
    }

    @Override
    public void deleteSuccess(int position) {
        mAdapter.remove(position);
    }

    @Override
    public void onFailed(Object tag,String failed) {
        smartRefreshLayout.finishRefresh(false);
        holder.withData(failed).showLoadFailed();
    }

}
