package com.thomas.video.ui.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.bean.SearchResultBean;
import com.thomas.video.helper.JsoupHelper;
import com.thomas.video.ui.contract.ResultContract;
import com.thomas.video.ui.model.ResultModel;

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
public class ResultPresenter extends BaseMvpPresenter<ResultContract.Model, ResultContract.View> implements ResultContract.Presenter {
    @Override
    protected ResultContract.Model createModel() {
        return new ResultModel();
    }

    @Override
    public void getData(int currentPage, String key) {

        getModel().getData(currentPage, key, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (isViewAttached()) {
                    SearchResultBean resultBean = null;
                    try {
                        resultBean = JsoupHelper.parseSearchResult(response.body());
                    } catch (Exception e) {
                        getView().onFailed(0,"出现未知异常");
                    }

                    getView().hasMoreData(resultBean.getPage().getPageindex() < resultBean.getPage().getPagecount());

                    if (resultBean.getData() != null && resultBean.getData().size() > 0) {
                        getView().getDataSuccess(resultBean.getData());
                    } else {
                        getView().getDataEmpty();
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
