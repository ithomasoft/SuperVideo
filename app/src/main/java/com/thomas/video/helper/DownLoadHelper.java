package com.thomas.video.helper;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.thomas.video.entity.DownloadEntity;
import com.yanzhenjie.kalle.Canceller;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.download.Callback;
import com.yanzhenjie.kalle.download.Download;

import org.litepal.LitePal;

import java.util.Date;

/**
 * @author Thomas
 * @describe 下载管理器帮助类
 * @date 2019/12/21
 * @updatelog
 * @since
 */
public class DownLoadHelper {

    public static int startDownload(String url, String fileName) {
        DownloadEntity downloadEntity;
        downloadEntity = LitePal.where("downloadUrl like ?", url).findFirst(DownloadEntity.class);


        Canceller canceller = Kalle.Download.get(url).directory(PathUtils.getExternalAppFilesPath())
                .fileName(fileName + ".mp4")
                .onProgress(new Download.ProgressBar() {
                    @Override
                    public void onProgress(int progress, long byteCount, long speed) {

                    }
                })
                .perform(new Callback() {
                    @Override
                    public void onStart() {

                        downloadEntity.setCreateDate(TimeUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    }

                    @Override
                    public void onFinish(String path) {
                        downloadEntity.setFinishDate(TimeUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        downloadEntity.saveOrUpdate();
                    }

                    @Override
                    public void onException(Exception e) {

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onEnd() {

                    }
                });

        return canceller.hashCode();
    }

}
