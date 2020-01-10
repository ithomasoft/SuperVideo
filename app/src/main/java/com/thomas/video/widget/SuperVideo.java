package com.thomas.video.widget;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.thomas.video.R;
import com.thomas.video.helper.DialogHelper;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

/**
 * @author Thomas
 * @describe 功能超全的播放器
 * @date 2019/8/16
 * @updatelog
 * @since
 */
public class SuperVideo extends JzvdStd {
    public interface onStartListener {
        void onStart();
    }

    private onStartListener onStartListener;

    public void setOnStartListener(SuperVideo.onStartListener onStartListener) {
        this.onStartListener = onStartListener;
    }

    public SuperVideo(Context context) {
        super(context);
    }

    public SuperVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        SAVE_PROGRESS = true;
        bottomProgressBar.setVisibility(GONE);
    }

    @Override
    public void startVideo() {
        if (screen == SCREEN_FULLSCREEN) {
            JZMediaInterface.SAVED_SURFACE = null;
            addTextureView();
            AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            onStatePreparing();
        } else {
            super.startVideo();
        }
        if (onStartListener != null) {
            onStartListener.onStart();
        }
    }


    @Override
    public void setUp(JZDataSource jzDataSource, int screen, Class mediaInterfaceClass) {
        super.setUp(jzDataSource, screen, mediaInterfaceClass);
        //设置全屏状态下才显示标题
        if (this.screen == SCREEN_FULLSCREEN) {
            titleTextView.setVisibility(View.VISIBLE);
        } else {
            titleTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void gotoScreenFullscreen() {
        super.gotoScreenFullscreen();
        titleTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void gotoScreenNormal() {
        super.gotoScreenNormal();
        titleTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAutoCompletion() {
        if (screen == SCREEN_FULLSCREEN) {
            thumbImageView.setVisibility(View.GONE);
            onStateAutoComplete();
        } else {
            super.onAutoCompletion();
            thumbImageView.setVisibility(View.GONE);
        }
    }


    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(topCon);
        bottomContainer.setVisibility(bottomCon);
        startButton.setVisibility(startBtn);
        loadingProgressBar.setVisibility(loadingPro);
        thumbImageView.setVisibility(thumbImg);
        bottomProgressBar.setVisibility(GONE);
        mRetryLayout.setVisibility(retryLayout);
    }

    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                if (clarityPopWindow != null) {
                    clarityPopWindow.dismiss();
                }

                if (screen != SCREEN_TINY) {
                    bottomProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showWifiDialog() {
        DialogHelper.showDialogCenter("提示", getResources().getString(R.string.tips_not_wifi),
                getResources().getString(R.string.tips_not_wifi_cancel),
                getResources().getString(R.string.tips_not_wifi_confirm),
                new NormalDialog.OnDialogListener() {
                    @Override
                    public void onCancel() {
                        clearFloatScreen();
                    }

                    @Override
                    public void onSure() {
                        startVideo();
                        WIFI_TIP_DIALOG_SHOWED = true;
                    }
                });
    }
}
