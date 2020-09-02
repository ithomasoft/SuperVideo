package com.thomas.video.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.thomas.video.R;
import com.thomas.video.bean.DialogItemBean;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.Direction;
import razerdp.util.animation.TranslationConfig;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/10
 * @updatelog
 * @since
 */
public class BottomDialog  extends BasePopupWindow {
    private RecyclerView rvDialogContent;
    private OnDialogItemClickListener onItemClickListener;

    private BottomDialog(Context context) {
        super(context);
    }

    private BottomDialog(Context context, Builder builder) {
        this(context);
        rvDialogContent = findViewById(R.id.rv_dialog_content);
        setAlignBackground(false);
        setClipChildren(false);
        setPopupGravity(Gravity.BOTTOM);
        setContent(builder);
    }

    private void setContent(final Builder builder) {
        DialogMenuAdapter adapter = new DialogMenuAdapter(builder.items);
        rvDialogContent.setAdapter(adapter);
        rvDialogContent.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(builder.items.get(position), position);
                    dismiss();
                }
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.view_bottom_dialog);
    }

    @Override
    protected Animation onCreateShowAnimation() {

        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM).toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {

        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM).toDismiss();
    }

    public void setOnItemClickListener(OnDialogItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnDialogItemClickListener {
        void onItemClick(DialogItemBean itemBean, int position);
    }

    public static class Builder {
        private Context context;
        private List<DialogItemBean> items;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setItems(List<DialogItemBean> items) {
            this.items = items;
            return this;
        }

        public BottomDialog build() {
            return new BottomDialog(context, this);
        }
    }

    private class DialogMenuAdapter extends BaseQuickAdapter<DialogItemBean, BaseViewHolder> {

        public DialogMenuAdapter(@Nullable List<DialogItemBean> data) {
            super(R.layout.item_bottom_dialog, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, DialogItemBean item) {
            helper.setText(R.id.tv_dialog_item_name, item.getName());
        }
    }
}
