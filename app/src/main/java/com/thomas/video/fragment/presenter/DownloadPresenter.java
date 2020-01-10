package com.thomas.video.fragment.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.entity.DownloadEntity;
import com.thomas.video.fragment.contract.DownloadContract;
import com.thomas.video.fragment.model.DownloadModel;

import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class DownloadPresenter extends BaseMvpPresenter<DownloadContract.Model, DownloadContract.View> implements DownloadContract.Presenter {
    @Override
    protected DownloadContract.Model createModel() {
        return new DownloadModel();
    }

    @Override
    public void getData(int type) {
        getModel().getData(type, list -> {
            if (isViewAttached()) {
                if (list != null && list.size() > 0) {
                    getView().getDataSuccess(list);
                } else {
                    getView().getDataEmpty();
                }
            }
        });
    }
}
