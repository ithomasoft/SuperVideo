package com.thomas.video.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.thomas.video.R;

public class EmptyView extends ConstraintLayout {

    private TextView tv_empty_title, tv_empty_content;

    public EmptyView(Context context) {
        this(context,null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_empty, this);
        tv_empty_title =findViewById(R.id.view_tv_title);
        tv_empty_content =findViewById(R.id.view_tv_content);
    }


    public void setInfo(String title, String content) {
        tv_empty_title.setText(title);
        tv_empty_content.setText(content);
    }
}
