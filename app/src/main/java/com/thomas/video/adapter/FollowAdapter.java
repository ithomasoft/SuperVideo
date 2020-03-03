package com.thomas.video.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.core.utils.ClickUtils;
import com.thomas.video.R;
import com.thomas.video.entity.FollowEntity;
import com.thomas.video.helper.ImageHelper;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class FollowAdapter extends BaseQuickAdapter<FollowEntity, BaseViewHolder> {
    public FollowAdapter() {
        super(R.layout.item_follow);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FollowEntity item) {
        ClickUtils.applyPressedViewScale(helper.itemView);
        helper.setText(R.id.item_tv_name, item.getName());
        ImageHelper.displayContentImage(helper.getView(R.id.item_iv_video), item.getImgUrl());
    }
}
