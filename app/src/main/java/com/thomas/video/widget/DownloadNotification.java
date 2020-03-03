package com.thomas.video.widget;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

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
            mManager.createNotificationChannel(channel);
        }
        mBuilder = new NotificationCompat.Builder(mContext, "aaa");
        mBuilder.setContentTitle("Aria Download Test")
                .setContentText("进度条")
                .setProgress(100, 0, false)
                .setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setOngoing(true);

        mManager.notify(mNotifiyId, mBuilder.build());
    }

    public void upload(int progress) {
        if (mBuilder != null) {
            mBuilder.setProgress(100, progress, false);
            mManager.notify(mNotifiyId, mBuilder.build());
        }
    }
}
