package com.thomas.video.fragment.model;

import com.thomas.video.entity.HistoryEntity;
import com.thomas.video.fragment.contract.HistoryContract;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class HistoryModel implements HistoryContract.Model {
    @Override
    public void getData(FindMultiCallback<HistoryEntity> callback) {
        LitePal.order("createTime desc").findAsync(HistoryEntity.class).listen(callback);
    }

    @Override
    public void deleteHistory(String videoId, UpdateOrDeleteCallback callback) {
        LitePal.deleteAllAsync(HistoryEntity.class, "videoId =?",videoId).listen(callback);
    }
}
