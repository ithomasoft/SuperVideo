package com.thomas.video.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.entity.DownloadEntity;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class DownloadAdapter extends BaseQuickAdapter<DownloadEntity, BaseViewHolder> {
    public DownloadAdapter(@Nullable List<DownloadEntity> data) {
        super(R.layout.item_download,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DownloadEntity item) {

    }
}
