package com.thomas.video.helper;

import com.arialyy.aria.core.download.DownloadEntity;

import java.util.List;

/**
 * @author Thomas
 * @date 2019/12/21
 * @updatelog
 * @since
 */
public interface DownLoadCallback {
    void onResult(List<DownloadEntity> datas);
}
