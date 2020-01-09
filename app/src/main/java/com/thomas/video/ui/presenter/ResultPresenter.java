package com.thomas.video.ui.presenter;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.video.bean.SearchResultBean;
import com.thomas.video.helper.JsoupHelper;
import com.thomas.video.ui.contract.ResultContract;
import com.thomas.video.ui.model.ResultModel;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

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
        getModel().getData(currentPage, key, new SimpleCallback<String>() {
            @Override
            public void onResponse(SimpleResponse<String, String> response) {
                if (isViewAttached()) {
                    SearchResultBean resultBean = JsoupHelper.parseSearchResult(response.succeed());

                    getView().hasMoreData(resultBean.getPage().getPageindex() < resultBean.getPage().getPagecount());

                    if (resultBean.getData() != null && resultBean.getData().size() > 0) {
                        getView().getDataSuccess(resultBean.getData());
                    } else {
                        getView().getDataEmpty();
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
