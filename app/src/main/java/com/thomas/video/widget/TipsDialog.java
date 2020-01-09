package com.thomas.video.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import androidx.appcompat.widget.AppCompatTextView;

import com.thomas.video.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public class TipsDialog extends BasePopupWindow {


    private AppCompatTextView tvDialogContent, tvDialogOk;
    private OnSureClickListener onSureClickListener;

    private TipsDialog(Context context) {
        super(context);
    }

    private TipsDialog(Context context, Builder builder) {
        this(context);
        setPopupGravity(Gravity.CENTER);
        setClipChildren(false);
        setOutSideTouchable(false);
        setOutSideDismiss(false);
        bindEvent();
        tvDialogContent.setText(builder.content);
        tvDialogOk.setText(TextUtils.isEmpty(builder.ok) ? getContext().getString(android.R.string.ok) : builder.ok);
    }


    private void bindEvent() {
        tvDialogContent = findViewById(R.id.tv_dialog_content);
        tvDialogOk = findViewById(R.id.btn_dialog_ok);
        tvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onSureClickListener != null) {
                    onSureClickListener.onSure();
                }
            }
        });
    }

    public static class Builder {
        private Context context;
        private String content, ok;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        public Builder setSure(String ok) {
            this.ok = ok;
            return this;
        }

        public TipsDialog build() {
            return new TipsDialog(context, this);
        }

    }

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.dialog_tips);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    public interface OnSureClickListener {
        void onSure();
    }
}
