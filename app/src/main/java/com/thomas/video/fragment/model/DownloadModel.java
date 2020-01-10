package com.thomas.video.fragment.model;

import com.thomas.video.entity.DownloadEntity;
import com.thomas.video.fragment.contract.DownloadContract;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class DownloadModel implements DownloadContract.Model {
    @Override
    public void getData(int type, FindMultiCallback<DownloadEntity> callback) {
        LitePal.where("state = ?", type + "").order("createDate desc").findAsync(DownloadEntity.class).listen(callback);
    }
}
