package com.thomas.video.ui.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.bean.VideoDetailBean;
import com.thomas.video.helper.JsoupHelper;
import com.thomas.video.ui.contract.DetailContract;
import com.thomas.video.ui.model.DetailModel;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class DetailPresenter extends BaseMvpPresenter<DetailContract.Model, DetailContract.View> implements DetailContract.Presenter {
    @Override
    protected DetailContract.Model createModel() {
        return new DetailModel();
    }

    @Override
    public void getData(String url) {
        getModel().getData(url, new SimpleCallback<String>() {
            @Override
            public void onResponse(SimpleResponse<String, String> response) {
                if (isViewAttached()) {
                    VideoDetailBean detailBean = JsoupHelper.parseVideoDetail(response.succeed());
                    if (detailBean != null) {
                        getView().getDataSuccess(detailBean);
                    } else {
                        getView().onFailed("出现未知异常异常");
                    }

                }
            }

            @Override
            public void onException(Exception e) {
                if (isViewAttached()) {
                    getView().onFailed(e.toString());
                }
            }
        });
    }
}
