package com.thomas.video.fragment.contract;

import com.arialyy.aria.core.download.DownloadEntity;
import com.thomas.core.mvp.IBaseMvpModel;
import com.thomas.core.mvp.IBaseMvpView;
import com.thomas.video.helper.DownLoadCallback;

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
        void getData(int type, DownLoadCallback callback);
    }

    interface View extends IBaseMvpView {
        void getDataSuccess(List<DownloadEntity> succeed);
        void getDataEmpty();
    }

    interface Presenter {
        void getData(int type);
    }
}
