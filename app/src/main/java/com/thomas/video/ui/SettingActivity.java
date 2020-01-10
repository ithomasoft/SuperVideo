package com.thomas.video.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

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
            String size = FileUtils.getSize(PathUtils.getInternalAppCachePath());
            Utils.runOnUiThreadDelayed(() -> {
                CleanUtils.cleanInternalCache();
                ToastUtils.showShort("清理了" + size + "的缓存");
            }, 3000);

        } else if (clickId == R.id.tv_save) {
            SPUtils.getInstance("setting").put("auto", !SPUtils.getInstance("setting").getBoolean("auto",true));
            if (SPUtils.getInstance("setting").getBoolean("auto")) {
                ToastUtils.showShort("关闭省流量，将自动播放");
            } else {
                ToastUtils.showShort("开启省流量，不自动播放");
            }

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
        DialogItemBean ijkBean = new DialogItemBean("哔哩哔哩播放器", "1");
        DialogItemBean exoBean = new DialogItemBean("谷歌播放器", "2");
        datas.add(systemBean);
        datas.add(ijkBean);
        datas.add(exoBean);
        DialogHelper.showBottom(datas, new BottomDialog.OnItemClickListener() {
            @Override
            public void onItemClick(DialogItemBean itemBean, int position) {
                SPUtils.getInstance("setting").put("engine", position);
                ToastUtils.showShort("切换成功！");
            }
        });
    }
}
