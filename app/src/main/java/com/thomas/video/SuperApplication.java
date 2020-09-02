package com.thomas.video;

import androidx.appcompat.app.AppCompatDelegate;

import com.arialyy.aria.core.Aria;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.bugly.Bugly;
import com.thomas.core.BaseApplication;
import com.thomas.core.utils.AppUtils;
import com.thomas.video.adapter.StatusAdapter;
import com.thomas.video.helper.StatusHelper;

import org.litepal.LitePal;

/**
 * @author Thomas
 * @date 2019/6/26
 * @updatelog
 */
public class SuperApplication extends BaseApplication {

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(false);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableLoadMoreWhenContentNotFull(false);
            layout.setDisableContentWhenLoading(true);
            layout.setDisableContentWhenRefresh(true);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            return new ClassicsHeader(context);
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context);
        });
    }

    @Override
    public void initCrashReport() {
        Bugly.init(this, "917aee00ab", AppUtils.isAppDebug());
    }

    @Override
    public void initNetWork() {

    }

    @Override
    protected void initExpands() {
        //全局统一状态组件
        StatusHelper.debug(AppUtils.isAppDebug());
        StatusHelper.initDefault(new StatusAdapter());
        //初始数据库框架
        LitePal.initialize(this);
        //初始化下载器框架
        Aria.init(this);
    }
}
