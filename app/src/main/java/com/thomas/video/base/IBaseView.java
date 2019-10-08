package com.thomas.video.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * 视图操作接口
 *
 * @author Thomas
 * @date 2019/4/17
 * @updatelog
 */
public interface IBaseView {

    /**
     * 是否需要注册EventBus
     *
     * @return
     */
    boolean isNeedRegister();

    /**
     * 是否是全屏透明，包含顶部状态栏和底部导航栏
     *
     * @return
     */
    boolean isTransparent();

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(@NonNull final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int bindLayout();

    void setRootLayout(@LayoutRes int layoutId);

    /**
     * 初始化 view
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onDebouncingClick(final View view);
}
