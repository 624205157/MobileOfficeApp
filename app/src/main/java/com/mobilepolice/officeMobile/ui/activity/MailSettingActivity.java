package com.mobilepolice.officeMobile.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.utils.MyCookie;

import butterknife.BindView;

/**
 * 设置邮件服务器
 */
public class MailSettingActivity extends MyActivity {

    @BindView(R.id.send_host_editText)
    EditText send_host_editText;
    @BindView(R.id.send_port_editText)
    EditText send_port_editText;
    @BindView(R.id.imap_host_editText)
    EditText imap_host_editText;
    @BindView(R.id.imap_port_editText)
    EditText imap_port_editText;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mail_setting;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("设置邮箱服务器");
        send_host_editText.setText(MyApplication.info.getMailServerHost());
        send_port_editText.setText(MyApplication.info.getMailServerPort());
        imap_host_editText.setText(MyApplication.info.getImapServerHost());
        imap_port_editText.setText(MyApplication.info.getImapServerPort());
    }

    @Override
    protected void initData() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(send_host_editText.getText().toString()))
                {
                    toast("发件服务器地址");
                    return;
                }
                if(TextUtils.isEmpty(send_port_editText.getText().toString()))
                {
                    toast("发件服务器端口");
                    return;
                }
                if(TextUtils.isEmpty(imap_host_editText.getText().toString()))
                {
                    toast("收件服务器地址");
                    return;
                }
                if(TextUtils.isEmpty(imap_port_editText.getText().toString()))
                {
                    toast("收件服务器端口");
                    return;
                }
                MyCookie.putString("send_host", send_host_editText.getText().toString().trim());
                MyCookie.putString("send_port", send_port_editText.getText().toString().trim());
                MyCookie.putString("imap_host", imap_host_editText.getText().toString().trim());
                MyCookie.putString("imap_post", imap_port_editText.getText().toString().trim());
                MyApplication.info.setMailServerHost(send_host_editText.getText().toString().trim());
                MyApplication.info.setMailServerPort(send_port_editText.getText().toString().trim());
                MyApplication.info.setImapServerHost(imap_host_editText.getText().toString().trim());
                MyApplication.info.setImapServerPort(imap_port_editText.getText().toString().trim());
                finish();
            }
        });

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
}
