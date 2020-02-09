package com.thomas.video.helper;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.thomas.core.utils.LogUtils;
import com.thomas.core.utils.PathUtils;
import com.thomas.video.entity.DownloadEntity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas
 * @describe 下载管理器帮助类
 * @date 2019/12/21
 * @updatelog
 * @since
 */
public class DownLoadHelper {

    private ArrayList<DownloadStatusUpdater> updaterList = new ArrayList<>();


    private final static class HolderClass {
        private final static DownLoadHelper INSTANCE = new DownLoadHelper();
    }

    public static DownLoadHelper getImpl() {
        return HolderClass.INSTANCE;
    }

    public int startDownload(String url) {
        int id = FileDownloader.getImpl().create(url)
                .setPath(PathUtils.getExternalAppFilesPath())
                .setListener(fileDownloadListener)
                .setAutoRetryTimes(3)
                .asInQueueTask().enqueue();
        FileDownloader.getImpl().start(fileDownloadListener, false);
        return id;
    }

    private FileDownloadListener fileDownloadListener = new FileDownloadLargeFileListener() {
        @Override
        protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            LogUtils.e(soFarBytes+"--pending--"+totalBytes+"---"+task.getFilename());
//            saveDownloadInfo(task);
        }

        @Override
        protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            LogUtils.e(soFarBytes+"--progress--"+totalBytes);
//            saveDownloadInfo(task);
        }

        @Override
        protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            LogUtils.e(soFarBytes+"--paused--"+totalBytes);
//            saveDownloadInfo(task);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            LogUtils.e("completed----"+task.getFilename());
//            saveDownloadInfo(task);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            LogUtils.e("error----"+task.getPath());
//            saveDownloadInfo(task);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            LogUtils.e("warn----"+task.getPath());
//            saveDownloadInfo(task);
        }
    };

    private void saveDownloadInfo(BaseDownloadTask task) {
        DownloadEntity downloadEntity = LitePal.where("taskId = ?", task.getId() + "").findFirst(DownloadEntity.class);
        if (downloadEntity == null) {
            //不存在的任务清除掉
            FileDownloader.getImpl().clear(task.getId(), task.getPath());
            return;
        }


        if (task.getStatus() == FileDownloadStatus.completed) {
            complete(task);
        } else {
            update(task);
        }
    }

    public void addUpdater(final DownloadStatusUpdater updater) {
        if (!updaterList.contains(updater)) {
            updaterList.add(updater);
        }
    }

    public boolean removeUpdater(final DownloadStatusUpdater updater) {
        return updaterList.remove(updater);
    }


    private void complete(final BaseDownloadTask task) {
        final List<DownloadStatusUpdater> updaterListCopy = (List<DownloadStatusUpdater>) updaterList.clone();
        for (DownloadStatusUpdater downloadStatusUpdater : updaterListCopy) {
            downloadStatusUpdater.complete(task);
        }
    }

    private void update(final BaseDownloadTask task) {
        final List<DownloadStatusUpdater> updaterListCopy = (List<DownloadStatusUpdater>) updaterList.clone();
        for (DownloadStatusUpdater downloadStatusUpdater : updaterListCopy) {
            downloadStatusUpdater.update(task);
        }
    }

    public interface DownloadStatusUpdater {
        void complete(BaseDownloadTask task);

        void update(BaseDownloadTask task);
    }
}
