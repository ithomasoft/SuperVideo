package com.thomas.video.ui.model;

import com.thomas.core.utils.EncodeUtils;
import com.thomas.video.ApiConstant;
import com.thomas.video.ApiService;
import com.thomas.video.retrofit.RetrofitHelper;
import com.thomas.video.ui.contract.ResultContract;

import retrofit2.Callback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class ResultModel implements ResultContract.Model {
    @Override
    public void getData(int currentPage, String key, Callback<String> callback) {
        String realKey = EncodeUtils.urlEncode(key);

        RetrofitHelper.createApi(ApiService.class)
                .getResult(ApiConstant.Search.SEARCH_URL + currentPage + ApiConstant.SEARCH_KEY + realKey + ApiConstant.END_URL)
                .enqueue(callback);

    }
}
