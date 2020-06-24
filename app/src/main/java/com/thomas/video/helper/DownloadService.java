package com.thomas.video.helper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.thomas.core.utils.PathUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.video.widget.DownloadNotification;

public class DownloadService extends Service {
    private DownloadNotification mNotify;
    private String downloadUrl;
    private String fileName;
    private String imgUrl;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotify = new DownloadNotification(getApplicationContext());
        Aria.download(this).register();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downloadUrl = intent.getStringExtra("downloadUrl");
        fileName = intent.getStringExtra("fileName");
        imgUrl = intent.getStringExtra("imgUrl");

        long taskId = Aria.download(this)
                .load(downloadUrl)
                .setFilePath(PathUtils.getInternalAppFilesPath() + "/" + fileName)
                .setExtendField(imgUrl)
                .create();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Aria.download(this).unRegister();
    }


    @Download.onTaskStart
    public void onTaskStart(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，开始下载");
        mNotify.upload(task);
    }

    @Download.onTaskStop
    public void onTaskStop(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，停止下载");
        mNotify.upload(task);
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，取消下载");
        mNotify.upload(task);
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，下载失败");
        mNotify.upload(task);
    }

    @Download.onTaskComplete
    public void onTaskComplete(DownloadTask task) {
        ToastUtils.showShort(task.getDownloadEntity().getFileName() + "，下载完成");
        mNotify.upload(task);
    }

    @Download.onTaskRunning
    public void onTaskRunning(DownloadTask task) {
        mNotify.upload(task);
    }

    private class DownloadBinder extends Binder {
        DownloadService getService() {
            return DownloadService.this;
        }

        public void setData(String downloadUrl, String fileName) {
            DownloadService.this.downloadUrl = downloadUrl;
            DownloadService.this.fileName = fileName;
        }
    }
}
