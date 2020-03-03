package com.thomas.video.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.thomas.core.ui.BaseActivity;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.BarUtils;
import com.thomas.core.utils.ClickUtils;
import com.thomas.video.R;
import com.thomas.video.helper.StatusHelper;
import com.yanzhenjie.kalle.Kalle;

import butterknife.ButterKnife;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public abstract class ThomasActivity extends BaseActivity {

    protected StatusHelper.Holder holder;

    @Override
    protected boolean isNeedAdapt() {
        return false;
    }

    @Override
    protected int setAdaptScreen() {
        return 0;
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void onThomasClick(@NonNull View view) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public void initStatusBar() {
        super.initStatusBar();
        if (!TextUtils.equals(mActivity.getClass().getSimpleName(),"MainActivity")){
            BarUtils.setStatusBarColor(mActivity, ContextCompat.getColor(mActivity, R.color.thomas_color_app_title_background),true);
        }
    }

    @Override
    public void setContentView() {
        super.setContentView();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        if (ActivityUtils.getTopActivity() != null) {
            //取消当前页面的所有网络请求。
            Kalle.cancel(ActivityUtils.getTopActivity().getClass());
        }
        super.onDestroy();

    }

}
