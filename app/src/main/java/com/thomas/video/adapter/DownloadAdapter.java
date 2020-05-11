package com.thomas.video.adapter;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.arialyy.aria.core.download.DownloadEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.helper.ImageHelper;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class DownloadAdapter extends BaseQuickAdapter<DownloadEntity, BaseViewHolder> {
    public DownloadAdapter() {
        super(R.layout.item_download);
        addChildClickViewIds(R.id.iv_action);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DownloadEntity item) {
        helper.setText(R.id.tv_name, item.getFileName());
        helper.setText(R.id.tv_speed, item.getConvertSpeed());
        ImageHelper.displayExtraImage(helper.findView(R.id.iv_pic), item.getStr());
        long len = item.getFileSize();
        if (len != 0) {
            int p = (int) (item.getCurrentProgress() * 100 / len);
            ProgressBar progressBar = helper.findView(R.id.progressBar);
            progressBar.setMax(100);
            progressBar.setProgress(p);
        }
        if (item.getState() == 4) {
            helper.setImageResource(R.id.iv_action, R.drawable.ic_pause);
        } else if (item.getState() == 2) {
            helper.setImageResource(R.id.iv_action, R.drawable.ic_play);
        } else if (item.getState() == 0) {
            helper.setImageResource(R.id.iv_action, R.drawable.ic_error);
        } else {
            helper.setImageResource(R.id.iv_action, R.drawable.ic_unknown);
        }
    }


}
