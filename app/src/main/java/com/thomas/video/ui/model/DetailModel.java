package com.thomas.video.ui.model;

import com.thomas.video.ApiService;
import com.thomas.video.retrofit.RetrofitHelper;
import com.thomas.video.ui.contract.DetailContract;

import retrofit2.Callback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class DetailModel implements DetailContract.Model {
    @Override
    public void getData(String url, Callback<String> callback) {
        RetrofitHelper.createApi(ApiService.class)
                .getDetail(url)
                .enqueue(callback);
    }
}
