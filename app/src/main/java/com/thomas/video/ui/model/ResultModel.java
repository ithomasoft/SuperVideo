package com.thomas.video.ui.model;

import com.thomas.core.utils.EncodeUtils;
import com.thomas.video.ApiConstant;
import com.thomas.video.ui.contract.ResultContract;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class ResultModel implements ResultContract.Model {
    @Override
    public void getData(int currentPage, String key, SimpleCallback<String> callback) {
        String realKey = EncodeUtils.urlEncode(key);
        Kalle.get(ApiConstant.Search.SEARCH_URL + currentPage + ApiConstant.SEARCH_KEY + realKey + ApiConstant.END_URL)
                .perform(callback);

    }
}
