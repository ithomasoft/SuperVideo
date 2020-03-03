package com.thomas.video.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.bean.OpenSourceBean;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class OpenSourceAdapter extends BaseQuickAdapter<OpenSourceBean, BaseViewHolder> {
    public OpenSourceAdapter(@Nullable List<OpenSourceBean> data) {
        super(R.layout.item_open_source,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OpenSourceBean item) {
        helper.setText(R.id.item_tv_name, item.getName());
        helper.setText(R.id.item_tv_url, item.getUrl());
    }
}
