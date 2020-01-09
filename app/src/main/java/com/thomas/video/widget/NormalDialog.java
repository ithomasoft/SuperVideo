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
public class NormalDialog extends BasePopupWindow {


    private AppCompatTextView tvDialogTitle, tvDialogContent, tvDialogCancel, tvDialogOk;
    private OnDialogListener onDialogListener;

    private NormalDialog(Context context) {
        super(context);

    }

    private NormalDialog(Context context, Builder builder) {
        this(context);
        setPopupGravity(Gravity.CENTER);
        setClipChildren(false);
        bindEvent();
        if (TextUtils.isEmpty(builder.title)) {
            tvDialogTitle.setVisibility(View.GONE);
        } else {
            tvDialogTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText(builder.title);
        }
        tvDialogContent.setText(builder.content);
        tvDialogCancel.setText(TextUtils.isEmpty(builder.cancel) ? getContext().getString(android.R.string.cancel) : builder.cancel);
        tvDialogOk.setText(TextUtils.isEmpty(builder.ok) ? getContext().getString(android.R.string.ok) : builder.ok);
    }

    private void bindEvent() {
        tvDialogTitle = findViewById(R.id.tv_dialog_title);
        tvDialogContent = findViewById(R.id.tv_dialog_content);
        tvDialogCancel = findViewById(R.id.btn_dialog_cancel);
        tvDialogOk = findViewById(R.id.btn_dialog_ok);
        tvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogListener.onSure();
                dismiss();
            }
        });
        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogListener.onCancel();
                dismiss();
            }
        });


    }

    public static class Builder {
        private Context context;
        private String title, content, cancel, ok;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setSure(String ok) {
            this.ok = ok;
            return this;
        }

        public NormalDialog build() {
            return new NormalDialog(context, this);
        }


    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.dialog_normal);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }


    public interface OnDialogListener {
        void onCancel();

        void onSure();
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }


}
