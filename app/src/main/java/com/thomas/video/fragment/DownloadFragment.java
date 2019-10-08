package com.thomas.video.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.thomas.video.R;
import com.thomas.video.base.BaseFragment;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class DownloadFragment extends BaseFragment {
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

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onDebouncingClick(View view) {

    }
}
