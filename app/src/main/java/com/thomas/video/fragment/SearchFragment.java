package com.thomas.video.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.KeyboardUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.video.R;
import com.thomas.video.base.LazyThomasFragment;
import com.thomas.video.ui.ResultActivity;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class SearchFragment extends LazyThomasFragment {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toResult();
                return true;
            } else {
                return false;
            }

        });

        applyThomasClickListener(ivSearch);
    }


    @Override
    public void onThomasClick(@NonNull View view) {
        int clickId = view.getId();
        if (clickId == R.id.iv_search) {
            toResult();
        }
    }

    private void toResult() {
        String searchText = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            ToastUtils.showLong("请先输入要搜索内容");
        } else {
            KeyboardUtils.hideSoftInput(mActivity);
            etSearch.getText().clear();
            //保存搜索记录
            Bundle bundle = new Bundle();
            bundle.putString("key", searchText);
            ActivityUtils.startActivity(bundle, ResultActivity.class);
        }
    }


}
