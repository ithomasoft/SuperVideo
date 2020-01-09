package com.thomas.video.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thomas.core.utils.ClickUtils;
import com.thomas.video.R;
import com.thomas.video.entity.HistoryEntity;
import com.thomas.video.helper.ImageHelper;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class HistoryAdapter extends BaseQuickAdapter<HistoryEntity, BaseViewHolder> {
    public HistoryAdapter(@Nullable List<HistoryEntity> data) {
        super(R.layout.item_history, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryEntity item) {
        helper.setText(R.id.item_tv_name, item.getName());
        helper.setText(R.id.item_tv_current_name, "当前观看到：" + item.getCurrentName());
        helper.setText(R.id.item_tv_time, item.getCreateTime());
        ImageHelper.displayExtraImage(helper.getView(R.id.item_iv_video), item.getImgUrl());
        ClickUtils.applyPressedViewScale(helper.getView(R.id.item_iv_delete));
        helper.addOnClickListener(R.id.item_iv_delete);
    }
}
