package com.thomas.video.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thomas.core.utils.ClickUtils;
import com.thomas.video.R;
import com.thomas.video.entity.FollowEntity;
import com.thomas.video.helper.ImageHelper;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class FollowAdapter extends BaseQuickAdapter<FollowEntity, BaseViewHolder> {
    public FollowAdapter(@Nullable List<FollowEntity> data) {
        super(R.layout.item_follow,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FollowEntity item) {
        ClickUtils.applyPressedViewScale(helper.itemView);
        helper.setText(R.id.item_tv_name, item.getName());
        ImageHelper.displayContentImage(helper.getView(R.id.item_iv_video), item.getImgUrl());
    }
}
