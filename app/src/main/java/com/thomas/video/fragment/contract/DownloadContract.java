package com.thomas.video.fragment.contract;

import com.thomas.core.mvp.IBaseMvpModel;
import com.thomas.core.mvp.IBaseMvpView;
import com.thomas.video.entity.DownloadEntity;

import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public interface DownloadContract {
    interface Model extends IBaseMvpModel {
        void getData(int type, FindMultiCallback<DownloadEntity> callback);
    }

    interface View extends IBaseMvpView {
        void getDataSuccess(List<DownloadEntity> succeed);
        void getDataEmpty();
    }

    interface Presenter {
        void getData(int type);
    }
}
