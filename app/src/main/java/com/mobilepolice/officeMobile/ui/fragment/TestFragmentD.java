package com.mobilepolice.officeMobile.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.ui.activity.AboutActivity;
import com.mobilepolice.officeMobile.ui.activity.LoginActivity;
import com.mobilepolice.officeMobile.ui.activity.RegisterActivity;
import com.mobilepolice.officeMobile.ui.activity.WebActivity;
import com.mobilepolice.officeMobile.utils.IntentExtraUtils;

import butterknife.BindView;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目界面跳转示例
 */
public class TestFragmentD extends MyLazyFragment
        implements View.OnClickListener {

    @BindView(R.id.btn_test_login)
    Button mLoginView;
    @BindView(R.id.btn_test_register)
    Button mRegisterView;
    @BindView(R.id.btn_test_about)
    Button mAboutView;
    @BindView(R.id.btn_test_browser)
    Button mBrowserView;

    public static TestFragmentD newInstance() {
        return new TestFragmentD();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_d;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_d_title;
    }

    @Override
    protected void initView() {
        mLoginView.setOnClickListener(this);
        mRegisterView.setOnClickListener(this);
        mAboutView.setOnClickListener(this);
        mBrowserView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (v == mLoginView) {
            startActivity(LoginActivity.class);
        }else if (v == mRegisterView) {
            startActivity(RegisterActivity.class);
        }else if (v == mAboutView) {
            startActivity(AboutActivity.class);
        }else if (v == mBrowserView) {
            IntentExtraUtils.getInstance(WebActivity.class)
                    .putString("https://github.com/getActivity/")
                    .startActivity(getActivity());
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}