package com.mobilepolice.officeMobile.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;

import butterknife.BindView;

/**
 * 邮箱首页
 */
public class MailBoxMainActivity extends MyActivity implements View.OnClickListener {


    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.ll_send)
    LinearLayout ll_send;
    @BindView(R.id.ll_drafts)
    LinearLayout ll_drafts;
    @BindView(R.id.class_id_01)
    LinearLayout class_id_01;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("邮箱");
        ll_collect.setOnClickListener(this);
        ll_send.setOnClickListener(this);
        ll_drafts.setOnClickListener(this);
        class_id_01.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view == ll_collect) {
            //收件箱
            Intent intent = new Intent(MailBoxMainActivity.this, MailBoxCollectMainActivity.class);
            intent.putExtra("TYPE", "INBOX");
            intent.putExtra("status", 0);//全部
            startActivity(intent);
        } else if (view == ll_send) {
            //发件箱
            startActivity(MailBoxSendMainActivity.class);
        } else if (view == ll_drafts) {
            //草稿箱
            startActivity(MailBoxDraftsMainActivity.class);
        }else if (view == class_id_01) {
            //新增邮件
            startActivity(MailBoxSendEditActivity.class);
        }
    }
}
