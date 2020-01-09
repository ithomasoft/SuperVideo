package com.thomas.video;

import androidx.appcompat.app.AppCompatDelegate;

import com.liulishuo.filedownloader.FileDownloader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.thomas.core.BaseApplication;
import com.thomas.core.utils.AppUtils;
import com.thomas.core.utils.PathUtils;
import com.thomas.video.adapter.StatusAdapter;
import com.thomas.video.helper.StatusHelper;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.connect.BroadcastNetwork;
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor;
import com.yanzhenjie.kalle.connect.http.RedirectInterceptor;
import com.yanzhenjie.kalle.cookie.DBCookieStore;
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore;

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
        Kalle.setConfig(KalleConfig.newBuilder()
                .cookieStore(DBCookieStore.newBuilder(this).build())
                .cacheStore(DiskCacheStore.newBuilder(PathUtils.getExternalAppCachePath()).build())
                .network(new BroadcastNetwork(this))
                .addInterceptor(new LoggerInterceptor("Thomas", AppUtils.isAppDebug()))
                .addInterceptor(new RedirectInterceptor())
                .build());
    }

    @Override
    protected void initExpands() {
        //全局统一状态组件
        StatusHelper.debug(AppUtils.isAppDebug());
        StatusHelper.initDefault(new StatusAdapter());
        //初始数据库框架
        LitePal.initialize(this);
        //初始化下载器框架
        FileDownloader.setup(this);
    }
}
