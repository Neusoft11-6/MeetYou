package com.meetyou.cn.mvp.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.meetyou.cn.base.BaseActivity;
import com.meetyou.library.di.component.AppComponent;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import cn.meetyou.com.meetyou.R;

/**
 * Created by admin on 2018/5/4.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.version)
    TextView version;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.layout_about_us;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTopBar(topbar, "关于我们");
        version.setText(QMUIPackageHelper.getAppVersion(this));
    }

}
