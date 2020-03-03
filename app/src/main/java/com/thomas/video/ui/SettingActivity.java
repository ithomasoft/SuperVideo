package com.thomas.video.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.allen.library.SuperTextView;
import com.thomas.core.utils.ActivityUtils;
import com.thomas.core.utils.CleanUtils;
import com.thomas.core.utils.FileUtils;
import com.thomas.core.utils.PathUtils;
import com.thomas.core.utils.SPUtils;
import com.thomas.core.utils.ToastUtils;
import com.thomas.core.utils.Utils;
import com.thomas.video.R;
import com.thomas.video.base.ThomasActivity;
import com.thomas.video.bean.DialogItemBean;
import com.thomas.video.helper.DialogHelper;
import com.thomas.video.widget.BottomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingActivity extends ThomasActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_change_player)
    SuperTextView tvChangePlayer;
    @BindView(R.id.tv_change_home)
    SuperTextView tvChangeHome;
    @BindView(R.id.tv_change_theme)
    SuperTextView tvChangeTheme;
    @BindView(R.id.tv_clean)
    SuperTextView tvClean;
    @BindView(R.id.tv_save)
    SuperTextView tvSave;

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

        int player = SPUtils.getInstance("setting").getInt("engine", 0);
        tvChangePlayer.setRightString(player == 0 ? "系统播放器" : "谷歌播放器");


        int home = SPUtils.getInstance("setting").getInt("home", 0);
        tvChangeHome.setRightString(home == 0 ? "够看搜索" : "我的关注");

        String size = FileUtils.getSize(PathUtils.getInternalAppCachePath());
        tvClean.setRightString(size);

        boolean save = SPUtils.getInstance("setting").getBoolean("auto");
        tvSave.setSwitchClickable(false);
        tvSave.setSwitchIsChecked(save);

        applyThomasClickListener(tvChangeHome, tvChangePlayer, tvChangeTheme, tvClean, tvSave);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ActivityUtils.finishActivity(mActivity);
        }
        return true;

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onThomasClick(@NonNull View view) {
        int clickId = view.getId();
        if (clickId == R.id.tv_change_player) {
            showEngineDialog();
        } else if (clickId == R.id.tv_change_home) {
            showHomeDialog();
        } else if (clickId == R.id.tv_clean) {
            CleanUtils.cleanInternalCache();
            String size = FileUtils.getSize(PathUtils.getInternalAppCachePath());
            tvClean.setRightString(size);
            ToastUtils.showShort("清理了完成");

        } else if (clickId == R.id.tv_save) {
            SPUtils.getInstance("setting").put("auto", !SPUtils.getInstance("setting").getBoolean("auto", true));
            boolean save = SPUtils.getInstance("setting").getBoolean("auto");
            tvSave.setSwitchIsChecked(save);
        } else {
            ToastUtils.showShort("即将上线");
        }

    }

    private void showHomeDialog() {
        List<DialogItemBean> datas = new ArrayList<>();
        DialogItemBean searchBean = new DialogItemBean("将【够看搜索】设为主页", "0");
        DialogItemBean followBean = new DialogItemBean("将【我的关注】设为主页", "1");
        datas.add(searchBean);
        datas.add(followBean);
        DialogHelper.showBottom(datas, (itemBean, position) -> {
            SPUtils.getInstance("setting").put("home", position);
            ToastUtils.showShort("切换成功！");
        });
    }

    private void showEngineDialog() {
        List<DialogItemBean> datas = new ArrayList<>();
        DialogItemBean systemBean = new DialogItemBean("系统播放器", "0");
        DialogItemBean exoBean = new DialogItemBean("谷歌播放器", "1");
        datas.add(systemBean);
        datas.add(exoBean);
        DialogHelper.showBottom(datas, new BottomDialog.OnDialogItemClickListener() {
            @Override
            public void onItemClick(DialogItemBean itemBean, int position) {
                SPUtils.getInstance("setting").put("engine", position);
                ToastUtils.showShort("切换成功！");
            }
        });
    }
}
