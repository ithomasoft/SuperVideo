package com.thomas.video.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.thomas.video.R;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class StatusView extends LinearLayoutCompat implements View.OnClickListener {
    public static final int STATUS_LOAD_SUCCESS = 2;
    public static final int STATUS_LOAD_FAILED = 3;
    public static final int STATUS_EMPTY_DATA = 4;


    private final AppCompatTextView tvStatusMsg;
    private final Runnable mRetryTask;
    private final LottieAnimationView ivStatusIcon;
    private Object data;
    private Context context;

    public StatusView(Context context, Object data, Runnable retryTask) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_status, this, true);
        ivStatusIcon = findViewById(R.id.iv_status_icon);
        tvStatusMsg = findViewById(R.id.tv_status_msg);
        this.mRetryTask = retryTask;
        this.data = data;
    }

    public void setMsgViewVisibility(boolean visible) {
        tvStatusMsg.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setStatus(int status) {
        boolean show = true;
        View.OnClickListener onClickListener = null;
        int image = R.raw.thomas_loading;
        String str = "加载中。。。";
        switch (status) {
            case STATUS_LOAD_SUCCESS:
                show = false;
                break;
            case STATUS_LOAD_FAILED:
                str = (String) data;
                image = R.raw.thomas_error;
                onClickListener = this;
                break;
            case STATUS_EMPTY_DATA:
                str = (String) data;
                image = R.raw.thomas_empty;
                break;
            default:
                break;
        }
        ivStatusIcon.setAnimation(image);
        ivStatusIcon.playAnimation();
        setOnClickListener(onClickListener);
        tvStatusMsg.setText(str);
        setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mRetryTask != null) {
            mRetryTask.run();
        }
    }
}