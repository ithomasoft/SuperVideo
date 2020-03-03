package com.thomas.video.helper;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.thomas.core.utils.PathUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.video.widget.DownloadNotification;

public class DownloadService extends Service {
    private static final String DOWNLOAD_URL =
            "http://rs.0.gaoshouyou.com/d/df/db/03df9eab61dbc48a5939f671f05f1cdf.apk";
    private DownloadNotification mNotify;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotify = new DownloadNotification(getApplicationContext());
        Aria.download(this).register();
        Aria.download(this)
                .load(DOWNLOAD_URL)
                .setFilePath(PathUtils.getExternalMoviesPath() + "/service_task.apk")
                .create();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Aria.download(this).unRegister();
    }


    @Download.onTaskStart
    public void onTaskStart(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，开始下载");
    }

    @Download.onTaskStop
    public void onTaskStop(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，停止下载");
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，取消下载");
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，下载失败");
    }

    @Download.onTaskComplete
    public void onTaskComplete(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，下载完成");
        mNotify.upload(100);
    }

    @Download.onTaskRunning
    public void onTaskRunning(DownloadTask task) {
        long len = task.getFileSize();
        int p = (int) (task.getCurrentProgress() * 100 / len);
        mNotify.upload(p);
    }
}
