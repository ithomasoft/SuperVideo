package com.thomas.video.widget;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.arialyy.aria.core.task.DownloadTask;
import com.thomas.video.R;

public class DownloadNotification {

    private NotificationManager mManager;
    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private static final int mNotifiyId = 0;

    public DownloadNotification(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > 26) {
            NotificationChannel channel = new NotificationChannel("aaa",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            mManager.createNotificationChannel(channel);
        }
        mBuilder = new NotificationCompat.Builder(mContext, "aaa");
        mBuilder.setContentTitle("够看影视")
                .setContentText("下载进度")
                .setProgress(100, 0, false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setTicker("开始下载")
                .setLocalOnly(true)
                .setVibrate(new long[]{0})
                .setSound(null)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setOngoing(true);

        mManager.notify(mNotifiyId, mBuilder.build());
    }

    public void upload(DownloadTask task) {
        if (mBuilder != null) {
            mBuilder.setContentTitle(task.getTaskName());
            if (task.isRunning()) {
                long len = task.getFileSize();
                int p = (int) (task.getCurrentProgress() * 100 / len);
                mBuilder.setContentText(task.getConvertSpeed());
                mBuilder.setProgress(100, p, false);
            }
            if (task.isComplete()) {
                mBuilder.setContentText("下载完成").setAutoCancel(true);
            }
            if (task.isStop()){
                mBuilder.setContentText("下载停止").setAutoCancel(true);
            }
            if (task.isCancel()){
                mBuilder.setContentText("下载暂停").setAutoCancel(true);
            }
            mManager.notify(mNotifiyId, mBuilder.build());
        }
    }
}
