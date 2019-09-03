package com.mobilepolice.officeMobile.ui.activity;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;

/**
 * 发件箱详情
 */
public class MailBoxSendDetailActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_send_detail;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {
        setTitle("发件箱列表");
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
