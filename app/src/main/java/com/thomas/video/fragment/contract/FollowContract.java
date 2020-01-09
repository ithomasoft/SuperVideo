package com.thomas.video.fragment.contract;

import com.thomas.core.mvp.IBaseMvpModel;
import com.thomas.core.mvp.IBaseMvpView;
import com.thomas.video.entity.FollowEntity;

import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public interface FollowContract {
    interface Model extends IBaseMvpModel{
        void getData(FindMultiCallback<FollowEntity> callback);
        void deleteFollow(String videoId, UpdateOrDeleteCallback callback);

    }

    interface View extends IBaseMvpView{
        void getDataSuccess(List<FollowEntity> succeed);
        void getDataEmpty();
        void deleteSuccess(int position);
    }

    interface Presenter{
        void getData();

        void deleteFollow(int position, String videoId);
    }
}
