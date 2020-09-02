package com.thomas.video.ui.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.bean.VideoDetailBean;
import com.thomas.video.helper.JsoupHelper;
import com.thomas.video.ui.contract.DetailContract;
import com.thomas.video.ui.model.DetailModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getModel().getData(url, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (isViewAttached()) {
                    VideoDetailBean detailBean = null;
                    try {
                        detailBean = JsoupHelper.parseVideoDetail(response.body());
                    } catch (Exception e) {
                        getView().onFailed(0,"出现未知异常");
                    }
                    if (detailBean != null) {
                        getView().getDataSuccess(detailBean);
                    } else {
                        getView().onFailed(0,"出现未知异常");
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (isViewAttached()) {
                    getView().onFailed(0,t.toString());
                }
            }
        });

    }
}
