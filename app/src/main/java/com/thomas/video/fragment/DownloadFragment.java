package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.thomas.video.R;
import com.thomas.video.base.LazyThomasFragment;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class DownloadFragment extends LazyThomasFragment {


    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_download;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {

    }
}
