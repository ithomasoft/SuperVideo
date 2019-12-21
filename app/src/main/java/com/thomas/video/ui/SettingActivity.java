package com.thomas.video.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.thomas.video.R;
import com.thomas.video.base.BaseActivity;

import butterknife.BindView;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_change_player)
    TextView tvChangePlayer;
    @BindView(R.id.tv_change_home)
    TextView tvChangeHome;
    @BindView(R.id.tv_change_theme)
    TextView tvChangeTheme;
    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        toolbar.setTitle(getString(R.string.menu_setting));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        applyDebouncingClickListener(tvChangeHome, tvChangePlayer, tvChangeTheme, tvClean, tvSave);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ActivityUtils.finishActivity(mActivity, true);
        }
        return true;

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onDebouncingClick(View view) {
        int clickId = view.getId();
        if (clickId == R.id.tv_change_player) {
            showEngineDialog();
        } else if (clickId == R.id.tv_change_home) {
            showHomeDialog();
        } else if (clickId == R.id.tv_clean) {
          String size =  FileUtils.getSize(PathUtils.getInternalAppCachePath());

            showLoading("清理中。。。");
            Utils.runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    CleanUtils.cleanInternalCache();
                    ToastUtils.showShort("清理了"+size+"的缓存");
                    hideLoading();
                }
            }, 3000);

        } else {
            ToastUtils.showShort("即将上线");
        }

    }

    private void showHomeDialog() {
        CharSequence[] items = new CharSequence[]{"将【够看搜索】设为主页", "将【我的关注】设为主页"};
        int selectItem = SPUtils.getInstance("setting").getInt("home", 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setSingleChoiceItems(items, selectItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SPUtils.getInstance("setting").put("home", i);
                ToastUtils.showShort(items[i]);
                dialogInterface.dismiss();
            }
        }).setTitle("设置主页").create().show();
    }

    private void showEngineDialog() {
        CharSequence[] items = new CharSequence[]{"系统播放器", "哔哩哔哩播放器", "谷歌播放器"};
        int selectItem = SPUtils.getInstance("setting").getInt("engine", 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setSingleChoiceItems(items, selectItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SPUtils.getInstance("setting").put("engine", i);
                dialogInterface.dismiss();
            }
        }).setTitle("选择播放器").create().show();
    }
}
