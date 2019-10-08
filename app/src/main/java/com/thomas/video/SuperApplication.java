package com.thomas.video;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.tencent.bugly.Bugly;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.connect.BroadcastNetwork;
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor;
import com.yanzhenjie.kalle.connect.http.RedirectInterceptor;
import com.yanzhenjie.kalle.cookie.DBCookieStore;
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore;

import org.litepal.LitePal;

import java.util.ArrayList;

/**
 * @author Thomas
 * @date 2019/6/26
 * @updatelog
 */
public class SuperApplication extends MultiDexApplication {
//    private HttpProxyCacheServer proxy;
//
//    public static HttpProxyCacheServer getProxy(Context context) {
//        SuperApplication app = (SuperApplication) context.getApplicationContext();
//        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtils.isMainProcess()) {
            initLog();
            initCrash();
            initNetWork();
            initExpands();
        }
    }


    private void initLog() {
        DoraemonKit.install(this);
        final LogUtils.Config config = LogUtils.getConfig()
                // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(BuildConfig.DEBUG)
                // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)
                // 设置 log 全局标签，默认为空
                .setGlobalTag("Thomas")
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true)
                // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(false)
                // 当自定义路径为空时，写入应用的/cache/log/目录中
                .setDir("")
                // 当文件前缀为空时，默认为"logMsg"，即写入文件为"util-yyyy-MM-dd.txt"
                .setFilePrefix("")
                // 输出日志是否带边框开关，默认开
                .setBorderSwitch(true)
                // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setSingleTagSwitch(true)
                // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setConsoleFilter(LogUtils.V)
                // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)
                // log 栈深度，默认为 1
                .setStackDeep(1)
                // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setStackOffset(0)
                // 设置日志可保留天数，默认为 -1 表示无限时长
                .setSaveDays(3)
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new LogUtils.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "LogUtils Formatter ArrayList { " + list.toString() + " }";
                    }
                });
        LogUtils.d(config.toString());
    }

    private void initNetWork() {
        Kalle.setConfig(KalleConfig.newBuilder()
                .cookieStore(DBCookieStore.newBuilder(this).build())
                .cacheStore(DiskCacheStore.newBuilder(PathUtils.getExternalAppCachePath()).build())
                .network(new BroadcastNetwork(this))
                .addInterceptor(new LoggerInterceptor("KalleSample", BuildConfig.DEBUG))
                .addInterceptor(new RedirectInterceptor())
                .build());
    }

    private void initCrash() {
        if (BuildConfig.DEBUG) {
            CrashUtils.init((crashInfo, e) -> LogUtils.e(crashInfo));
        } else {
            initCrashReport();
        }
    }

    private void initCrashReport() {
        Bugly.init(this, "917aee00ab", false);
    }

    private void initExpands() {
        //初始数据库框架
        LitePal.initialize(this);
        //初始化下载器框架
        FileDownloader.setupOnApplicationOnCreate(this)
        .connectionCreator(new FileDownloadUrlConnection
                .Creator(new FileDownloadUrlConnection
                .Configuration()
                .connectTimeout(15000)
                .readTimeout(15000)
        )).commit();
    }


}
