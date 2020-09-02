package com.thomas.video.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.thomas.core.ui.LazyFragment;
import com.thomas.video.helper.StatusHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public abstract class LazyThomasFragment extends LazyFragment {
    private Unbinder unbinder;
    protected StatusHelper.Holder holder;

    @Override
    public boolean isTransparent() {
        return true;
    }


    @Override
    public void setContentView() {
        super.setContentView();
        unbinder = ButterKnife.bind(this, mContentView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {
    }

    @Override
    public void onThomasClick(@NonNull View view) {

    }
}
