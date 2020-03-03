package com.thomas.video.fragment.model;

import com.arialyy.aria.core.Aria;
import com.thomas.video.fragment.contract.DownloadContract;
import com.thomas.video.helper.DownLoadCallback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class DownloadModel implements DownloadContract.Model {


    @Override
    public void getData(int type, DownLoadCallback callback) {
        callback.onResult(type == 0 ? Aria.download(this).getAllNotCompleteTask() : Aria.download(this).getAllCompleteTask());
    }

}
