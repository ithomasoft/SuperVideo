package com.thomas.video.widget;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.material.snackbar.Snackbar;
import com.thomas.video.ui.SettingActivity;

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
        bottomProgressBar.setVisibility(GONE);
    }

    @Override
    public void gotoScreenNormal() {
        super.gotoScreenNormal();
        titleTextView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(VISIBLE);
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
    public void onStateError() {
        super.onStateError();
        Snackbar.make(this, "如果播放不了，请到设置中心切换播放引擎", Snackbar.LENGTH_LONG).
                setAction("跳转", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityUtils.startActivity(SettingActivity.class);
                    }
                }).show();
    }


}
