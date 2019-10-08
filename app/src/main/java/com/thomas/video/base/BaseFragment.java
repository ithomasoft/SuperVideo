package com.thomas.video.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Thomas
 * @date 2019/6/26
 * @updatelog
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private Unbinder unbinder;
    protected View mContentView;
    protected LayoutInflater mInflater;
    protected Activity mActivity;

    @Override
    public boolean isTransparent() {
        return true;
    }

    private View.OnClickListener mClickListener = v -> onDebouncingClick(v);

    @Override
    public void onStart() {
        super.onStart();
        if (isNeedRegister()&& (!EventBus.getDefault().isRegistered(this))){
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        setRootLayout(bindLayout());
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(@LayoutRes int layoutId) {
        if (layoutId <= 0) {return;}
        mContentView = mInflater.inflate(layoutId, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onDestroyView() {
        if (mContentView != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onStop() {
        KeyboardUtils.hideSoftInput(mActivity);
        super.onStop();
        if (isNeedRegister()&&EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void applyDebouncingClickListener(View... views) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (mContentView == null) {throw new NullPointerException("ContentView is null.");}
        return mContentView.findViewById(id);
    }
}
