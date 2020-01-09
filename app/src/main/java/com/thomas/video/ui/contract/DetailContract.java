package com.thomas.video.ui.contract;

import com.thomas.core.mvp.IBaseMvpModel;
import com.thomas.core.mvp.IBaseMvpView;
import com.thomas.video.bean.VideoDetailBean;
import com.yanzhenjie.kalle.simple.SimpleCallback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public interface DetailContract {
    interface Model extends IBaseMvpModel {
        void getData(String url, SimpleCallback<String> callback);
    }

    interface View extends IBaseMvpView {
        void getDataSuccess(VideoDetailBean succeed);
    }

    interface Presenter {
        void getData(String url);
    }
}
