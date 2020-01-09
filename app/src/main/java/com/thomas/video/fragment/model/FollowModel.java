package com.thomas.video.fragment.model;

import com.thomas.video.entity.FollowEntity;
import com.thomas.video.fragment.contract.FollowContract;

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
public class FollowModel implements FollowContract.Model {
    @Override
    public void getData(FindMultiCallback<FollowEntity> callback) {
        LitePal.order("createTime desc").findAsync(FollowEntity.class).listen(callback);
    }

    @Override
    public void deleteFollow(String videoId, UpdateOrDeleteCallback callback) {
        LitePal.deleteAllAsync(FollowEntity.class, "videoId =?",videoId).listen(callback);
    }
}
