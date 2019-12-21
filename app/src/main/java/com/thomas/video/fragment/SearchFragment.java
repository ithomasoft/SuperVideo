package com.thomas.video.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.thomas.video.ApiConstant;
import com.thomas.video.R;
import com.thomas.video.base.BaseFragment;
import com.thomas.video.ui.ResultActivity;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import butterknife.BindView;

/**
 * @author Thomas
 * @date 2019/6/27
 * @updatelog
 */
public class SearchFragment extends BaseFragment {
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
        applyDebouncingClickListener(ivSearch);
        ClickUtils.applyPressedViewScale(ivSearch);
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                toResult();
                return true;
            } else {
                return false;
            }

        });
    }

    @Override
    public void doBusiness() {
        Kalle.get(ApiConstant.Source.HOME_URL).perform(new SimpleCallback<String>() {
            @Override
            public void onResponse(SimpleResponse<String, String> response) {
            }
        });
    }

    @Override
    public void onDebouncingClick(View view) {
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
