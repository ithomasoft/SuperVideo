package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.thomas.core.utils.ActivityUtils;
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

import java.util.ArrayList;
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

    private HistoryAdapter adapter;
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
        if (holder == null) {
            holder = StatusHelper.getDefault().wrap(smartRefreshLayout).withRetry(() -> {
                holder.showLoading();
                Utils.runOnUiThreadDelayed(() -> presenter.getData(), 1500);
            });
        }

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> presenter.getData());
        adapter = new HistoryAdapter(datas);
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        rvContent.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", datas.get(position).getName());
            bundle.putString("id", datas.get(position).getVideoId());
            bundle.putString("url", "?m=vod-detail-id-" + datas.get(position).getVideoId() + ApiConstant.END_URL);
            ActivityUtils.startActivity(bundle, DetailActivity.class);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            showTips(position);
        });
    }

    private void showTips(int position) {
        DialogHelper.showDialogCenter("提示", "真的要删除“ " + datas.get(position).getName() + " ”的记录吗?",
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
        Utils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getData();
            }
        }, 1500);

    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        presenter.getData();
    }

    private void deleteHistory(int position) {
        presenter.deleteHistory(position, datas.get(position).getVideoId());
    }


    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    public void onFailed(String failed) {
        smartRefreshLayout.finishRefresh(false);
        holder.withData(failed).showLoadFailed();
    }

    @Override
    public void getDataSuccess(List<HistoryEntity> succeed) {
        smartRefreshLayout.finishRefresh(true);
        holder.showLoadSuccess();
        datas.addAll(succeed);
        adapter.setNewData(datas);
    }

    @Override
    public void getDataEmpty() {
        smartRefreshLayout.finishRefresh(true);
        holder.withData("快去观看你感兴趣的影片吧！").showEmpty();
    }

    @Override
    public void deleteSuccess(int position) {
        adapter.remove(position);
    }
}
