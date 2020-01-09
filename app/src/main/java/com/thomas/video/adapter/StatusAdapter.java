package com.thomas.video.adapter;

import android.view.View;

import com.thomas.video.helper.StatusHelper;
import com.thomas.video.widget.StatusView;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class StatusAdapter implements StatusHelper.Adapter {
    @Override
    public View getView(StatusHelper.Holder holder, View convertView, int status) {
        StatusView statusView = null;

        if (convertView != null && convertView instanceof StatusView) {
            statusView = (StatusView) convertView;
        }
        if (statusView == null) {
            statusView = new StatusView(holder.getContext(), holder.getData(), holder.getRetryTask());
        }
        statusView.setData(holder.getData());
        statusView.setStatus(status);
        return statusView;
    }
}
