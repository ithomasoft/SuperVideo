package com.thomas.video.fragment.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.fragment.contract.HistoryContract;
import com.thomas.video.fragment.model.HistoryModel;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class HistoryPresenter extends BaseMvpPresenter<HistoryContract.Model, HistoryContract.View> implements HistoryContract.Presenter {
    @Override
    protected HistoryContract.Model createModel() {
        return new HistoryModel();
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
    public void deleteHistory(int position, String videoId) {
        getModel().deleteHistory(videoId, rowsAffected -> {
            if (isViewAttached()) {
                getView().deleteSuccess(position);
            }
        });
    }
}
