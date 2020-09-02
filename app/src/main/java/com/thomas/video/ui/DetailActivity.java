package com.thomas.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arialyy.aria.core.Aria;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.core.constant.PermissionConstants;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.PermissionUtils;
import com.thomas.core.utils.SPUtils;
import com.thomas.core.utils.ThreadUtils;
import com.thomas.core.utils.TimeUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.base.ThomasMvpActivity;
import com.thomas.video.bean.VideoDetailBean;
import com.thomas.video.engine.ExoEngine;
import com.thomas.video.entity.EpisodeEntity;
import com.thomas.video.entity.FollowEntity;
import com.thomas.video.entity.HistoryEntity;
import com.thomas.video.helper.DialogHelper;
import com.thomas.video.helper.DownloadService;
import com.thomas.video.helper.ImageHelper;
import com.thomas.video.helper.StatusHelper;
import com.thomas.video.ui.contract.DetailContract;
import com.thomas.video.ui.presenter.DetailPresenter;
import com.thomas.video.widget.SuperVideo;

import org.litepal.LitePal;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZMediaSystem;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class DetailActivity extends ThomasMvpActivity<DetailPresenter> implements DetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_detail_alias)
    AppCompatTextView tvDetailAlias;
    @BindView(R.id.tv_detail_area)
    AppCompatTextView tvDetailArea;
    @BindView(R.id.tv_detail_language)
    AppCompatTextView tvDetailLanguage;
    @BindView(R.id.tv_detail_type)
    AppCompatTextView tvDetailType;
    @BindView(R.id.tv_detail_date)
    AppCompatTextView tvDetailDate;
    @BindView(R.id.tv_detail_time)
    AppCompatTextView tvDetailTime;
    @BindView(R.id.tv_detail_score)
    AppCompatTextView tvDetailScore;
    @BindView(R.id.tv_detail_director)
    AppCompatTextView tvDetailDirector;
    @BindView(R.id.tv_detail_stars)
    AppCompatTextView tvDetailStars;
    @BindView(R.id.tv_detail_introduction)
    AppCompatTextView tvDetailIntroduction;
    @BindView(R.id.rv_detail_episode)
    RecyclerView rvDetailEpisode;
    @BindView(R.id.video_player)
    SuperVideo videoPlayer;
    String title, url, id;
    @BindView(R.id.root_detail)
    ConstraintLayout rootDetail;

    private boolean isFollow = false;

    private BaseQuickAdapter<EpisodeEntity, BaseViewHolder> adapter;
    private List<EpisodeEntity> datas = new ArrayList<>();
    private VideoDetailBean resultBean;
    private int currentEpisode = 0;

    List<FollowEntity> db_datas;
    List<HistoryEntity> db_history;
    private int engine;

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter();
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        title = bundle.getString("title");
        url = bundle.getString("url");
        id = bundle.getString("id");
        Aria.download(this).register();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        if (holder == null) {
            holder = StatusHelper.getDefault().wrap(rootDetail).withRetry(new Runnable() {
                @Override
                public void run() {
                    holder.showLoading();
                    presenter.getData(id);
                }
            });
        }

        engine = SPUtils.getInstance("setting").getInt("engine", 0);

        adapter = new BaseQuickAdapter<EpisodeEntity, BaseViewHolder>(R.layout.item_detail_episode, datas) {
            @Override
            protected void convert(BaseViewHolder helper, EpisodeEntity item) {
                helper.setText(R.id.item_tv_name, item.getName());
                if (currentEpisode == helper.getAdapterPosition()) {
                    helper.getView(R.id.item_iv_play).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.item_iv_play).setVisibility(View.GONE);
                }
            }
        };
        rvDetailEpisode.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvDetailEpisode.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            currentEpisode = position;
            if (isFollow) {
                if (db_datas == null || db_datas.size() == 0) {
                    db_datas = LitePal.where("videoId = ?", id).find(FollowEntity.class);
                }
                db_datas.get(0).setCurrentEpisode(currentEpisode);
                db_datas.get(0).save();
            }
            adapter.notifyDataSetChanged();
            rvDetailEpisode.scrollToPosition(currentEpisode);
            setVideo(datas.get(position).getOnlineUrl(), title + " " + datas.get(position).getName());
            videoPlayer.startVideo();
        });

        videoPlayer.setOnStartListener(() -> updateHistory());

        applyThomasClickListener(tvDetailIntroduction);
    }

    private void updateHistory() {
        HistoryEntity entity = LitePal.where("videoId = ?", id).findFirst(HistoryEntity.class);
        if (entity == null) {
            entity = new HistoryEntity();
            entity.setVideoId(id);
            entity.setImgUrl(resultBean.getImgUrl());
            entity.setName(title);
        }
        entity.setCurrentName(datas.get(currentEpisode).getName());
        entity.setCreateTime(TimeUtils.getNowString());
        entity.setCurrentEpisode(currentEpisode);
        entity.save();
    }


    private void setVideo(String onlineUrl, String title) {
        if (engine == 0) {
            videoPlayer.setUp(onlineUrl, title, JzvdStd.SCREEN_NORMAL, JZMediaSystem.class);
        } else if (engine == 1) {
            videoPlayer.setUp(onlineUrl, title, JzvdStd.SCREEN_NORMAL, ExoEngine.class);
        } else {
            videoPlayer.setUp(onlineUrl, title, JzvdStd.SCREEN_NORMAL, JZMediaSystem.class);
        }
        if (SPUtils.getInstance("setting").getBoolean("auto", true)) {
            videoPlayer.startVideoAfterPreloading();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    /**
     * 更新菜单
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isFollow) {
            menu.findItem(R.id.menu_follow).setTitle("取消关注");
        } else {
            menu.findItem(R.id.menu_follow).setTitle("添加关注");
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtils.finishActivity(mActivity, true);
                break;
            case R.id.menu_follow:
                if (isFollow) {
                    deleteFollow();
                } else {
                    addFollow();
                }
                break;
            case R.id.menu_download:
                showDownloadDialog();
                break;
            default:
                break;
        }
        return true;

    }

    private void showDownloadDialog() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(mActivity, DownloadService.class);
                        intent.putExtra("imageUrl", resultBean.getImgUrl());
                        intent.putExtra("downloadUrl", datas.get(currentEpisode).getDownloadUrl());
                        intent.putExtra("fileName", resultBean.getName() + datas.get(currentEpisode).getName() + ".mp4");
                        startService(intent);
                    }

                    @Override
                    public void onDenied() {

                    }
                }).request();


    }

    /**
     * 添加关注
     */
    private void addFollow() {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setVideoId(id);
        followEntity.setCreateTime(TimeUtils.getNowString());
        followEntity.setName(title);
        followEntity.setImgUrl(resultBean.getImgUrl());
        followEntity.setCurrentEpisode(currentEpisode);
        followEntity.saveAsync().listen(success -> {
            if (success) {
                isFollow = true;
                updateState();
            }
        });
    }

    private void updateState() {
        if (isFollow) {
            ToastUtils.showShort("添加关注成功");
        } else {
            ToastUtils.showShort("取消关注成功");
        }
        invalidateOptionsMenu();
    }

    /**
     * 取消关注
     */
    private void deleteFollow() {
        LitePal.deleteAllAsync(FollowEntity.class, "videoId =?", id).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                isFollow = false;
                updateState();
            }
        });
    }

    @Override
    public void doBusiness() {
        holder.showLoading();
        ThreadUtils.runOnUiThreadDelayed(() -> presenter.getData(url), 1000);
    }

    /**
     * 设置当前观看的集数
     */
    private void initCurrentEpisode() {
        rvDetailEpisode.scrollToPosition(currentEpisode);
        setVideo(datas.get(currentEpisode).getOnlineUrl(), title + " " + datas.get(currentEpisode).getName());
    }


    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onFailed(Object tag, String failed) {
        holder.withData(failed).showLoadFailed();
    }


    @Override
    public void onThomasClick(@NonNull View view) {
        int clickId = view.getId();
        if (clickId == R.id.tv_detail_introduction) {
            DialogHelper.showTipsCenter(resultBean.getIntroduction());
        }
    }

    @Override
    public void getDataSuccess(VideoDetailBean succeed) {
        holder.showLoadSuccess();
        resultBean = succeed;
        ImageHelper.displayImage(videoPlayer.posterImageView, resultBean.getImgUrl());
        tvDetailAlias.setText(resultBean.getAlias());
        tvDetailArea.setText(resultBean.getArea());
        tvDetailLanguage.setText(resultBean.getLanguage());
        tvDetailType.setText(resultBean.getType());
        tvDetailDate.setText(resultBean.getDate());
        tvDetailTime.setText(resultBean.getTime());
        tvDetailScore.setText(resultBean.getScore());
        tvDetailDirector.setText(resultBean.getDirector());
        tvDetailStars.setText(resultBean.getStars());
        tvDetailIntroduction.setText(resultBean.getIntroduction());
        datas.addAll(resultBean.getEpisodeList());
        adapter.setNewData(datas);

        db_datas = LitePal.where("videoId = ?", id).find(FollowEntity.class);
        db_history = LitePal.where("videoId = ?", id).find(HistoryEntity.class);
        isFollow = (db_datas != null && db_datas.size() > 0);
        invalidateOptionsMenu();

        if (db_history != null && db_history.size() > 0) {
            currentEpisode = db_history.get(0).getCurrentEpisode();
            adapter.notifyItemChanged(currentEpisode);
        }
        initCurrentEpisode();
    }
}
