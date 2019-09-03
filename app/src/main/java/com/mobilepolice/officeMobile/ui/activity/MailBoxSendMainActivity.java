package com.mobilepolice.officeMobile.ui.activity;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;

/**
 * 发件箱首页
 */
public class MailBoxSendMainActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_send_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }


    @Override
    protected void initView() {
        setTitle("发件箱");
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
}
