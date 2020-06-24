package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.ThreadUtils;
import com.thomas.core.utils.Utils;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.adapter.HistoryAdapter;
import com.thomas.video.base.LazyThomasMvpFragment;
import com.thomas.video.entity.HistoryEntity;
import com.thomas.video.fragment.contract.HistoryContract;
import com.thomas.video.fragment.presenter.HistoryPresenter;
import com.thomas.video.helper.DialogHelper;
import com.thomas.video.helper.StatusHelper;
import com.thomas.video.ui.DetailActivity;
import com.thomas.video.widget.NormalDialog;

import java.util.List;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class HistoryFragment extends LazyThomasMvpFragment<HistoryPresenter> implements HistoryContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private HistoryAdapter mAdapter;

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
        if (holder == null) {
            holder = StatusHelper.getDefault().wrap(smartRefreshLayout).withRetry(() -> {
                holder.showLoading();
                ThreadUtils.runOnUiThreadDelayed(() -> presenter.getData(), 1000);
            });
        }

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> presenter.getData());
        mAdapter = new HistoryAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", mAdapter.getData().get(position).getName());
            bundle.putString("id", mAdapter.getData().get(position).getVideoId());
            bundle.putString("url", "?m=vod-detail-id-" + mAdapter.getData().get(position).getVideoId() + ApiConstant.END_URL);
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });

        mAdapter.setOnDeleteListener(new HistoryAdapter.OnDeleteListener() {
            @Override
            public void delete(int position) {
                showTips(position);
            }
        });
    }

    private void showTips(int position) {
        DialogHelper.showDialogCenter("提示", "真的要删除“ " + mAdapter.getData().get(position).getName() + " ”的记录吗?",
                "再想想", "删除记录", new NormalDialog.OnDialogListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onSure() {
                        deleteHistory(position);
                    }
                });
    }


    @Override
    protected void onFirstUserVisible() {
        holder.showLoading();
        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getData();
            }
        }, 1000);

    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        presenter.getData();
    }

    private void deleteHistory(int position) {
        presenter.deleteHistory(position, mAdapter.getData().get(position).getVideoId());
    }


    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    public void onFailed(Object tag,String failed) {
        smartRefreshLayout.finishRefresh(false);
        holder.withData(failed).showLoadFailed();
    }

    @Override
    public void getDataSuccess(List<HistoryEntity> succeed) {
        smartRefreshLayout.finishRefresh(true);
        holder.showLoadSuccess();
        mAdapter.setNewData(succeed);
    }

    @Override
    public void getDataEmpty() {
        smartRefreshLayout.finishRefresh(true);
        holder.withData("快去观看你感兴趣的影片吧！").showEmpty();
    }

    @Override
    public void deleteSuccess(int position) {
        mAdapter.remove(position);
    }
}
