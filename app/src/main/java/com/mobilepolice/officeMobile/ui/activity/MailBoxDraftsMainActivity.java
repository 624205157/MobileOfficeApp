package com.mobilepolice.officeMobile.ui.activity;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;

/**
 * 草稿箱首页
 */
public class MailBoxDraftsMainActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_drafts_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }


    @Override
    protected void initView() {
        setTitle("草稿箱");
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
