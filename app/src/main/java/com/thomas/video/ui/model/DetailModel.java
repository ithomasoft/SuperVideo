package com.thomas.video.ui.model;

import com.thomas.video.ApiConstant;
import com.thomas.video.ui.contract.DetailContract;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class DetailModel implements DetailContract.Model {
    @Override
    public void getData(String url, SimpleCallback<String> callback) {
        Kalle.get(ApiConstant.DETAIL_URL + url).perform(callback);
    }
}
