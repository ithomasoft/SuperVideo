package com.thomas.video.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.core.utils.ClickUtils;
import com.thomas.video.R;
import com.thomas.video.bean.SearchResultBean;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class ResultAdapter extends BaseQuickAdapter<SearchResultBean.DataBean, BaseViewHolder> {
    public ResultAdapter(@Nullable List<SearchResultBean.DataBean> data) {
        super(R.layout.item_search_result,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchResultBean.DataBean item) {
        ClickUtils.applyPressedViewScale(helper.itemView);
        helper.setText(R.id.item_tv_name, item.getName());
        helper.setText(R.id.item_tv_type, item.getType());
        helper.setText(R.id.item_tv_time, "更新时间：" + item.getUpdateTime());
        helper.setText(R.id.item_tv_sharp, item.getSharp());
        helper.setText(R.id.item_tv_last, item.getLast().equals("完结") ? item.getLast() : ("更新至：" + item.getLast()));

    }
}
