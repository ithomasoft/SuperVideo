package com.thomas.video.fragment.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.fragment.contract.FollowContract;
import com.thomas.video.fragment.model.FollowModel;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class FollowPresenter extends BaseMvpPresenter<FollowContract.Model, FollowContract.View> implements FollowContract.Presenter {
    @Override
    protected FollowContract.Model createModel() {
        return new FollowModel();
    }

    @Override
    public void getData() {
        getModel().getData(list -> {
            if (isViewAttached()) {
                if (list != null && list.size() > 0) {
                    getView().getDataSuccess(list);
                } else {
                    getView().getDataEmpty();
                }
            }

        });
    }

    @Override
    public void deleteFollow(int position, String videoId) {
        getModel().deleteFollow(videoId, rowsAffected -> {
            if (isViewAttached()) {
                getView().deleteSuccess(position);
            }
        });
    }
}
