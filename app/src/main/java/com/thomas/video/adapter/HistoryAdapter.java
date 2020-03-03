package com.thomas.video.adapter;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.core.utils.ClickUtils;
import com.thomas.video.R;
import com.thomas.video.entity.HistoryEntity;
import com.thomas.video.helper.ImageHelper;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class HistoryAdapter extends BaseQuickAdapter<HistoryEntity, BaseViewHolder> {

    private OnDeleteListener onDeleteListener;

    public OnDeleteListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public HistoryAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryEntity item) {
        SuperTextView superTextView = (SuperTextView) helper.itemView;
        superTextView.getLeftTopTextView().setGravity(Gravity.LEFT);
        superTextView.getLeftTextView().setGravity(Gravity.LEFT);
        superTextView.getLeftBottomTextView().setGravity(Gravity.LEFT);
        superTextView.setLeftTopString(item.getName());
        superTextView.setLeftTopTextIsBold(true);
        superTextView.setLeftString("当前观看到：" + item.getCurrentName());
        superTextView.setLeftBottomString(item.getCreateTime());
        ImageHelper.displayExtraImage(superTextView.getLeftIconIV(), item.getImgUrl());
        superTextView.getRightIconIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.delete(helper.getAdapterPosition());
                }
            }
        });
    }

    public interface OnDeleteListener {
        void delete(int position);
    }
}
