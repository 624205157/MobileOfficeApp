package com.mobilepolice.officeMobile.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.email.HttpUtil;
import com.mobilepolice.officeMobile.utils.EmailFormatUtil;
import com.mobilepolice.officeMobile.utils.MyCookie;

import java.util.List;

import butterknife.BindView;

public class EmailLoginActivity extends MyActivity implements TextWatcher, OnClickListener, OnPermission {

    @BindView(R.id.tb_title)
    TitleBar tb_title;

    private EditText emailAddress;
    private EditText password;
    private Button clearAddress;
    private Button emailLogin;
    private ProgressDialog dialog;
    private SharedPreferences sp;
    private CheckBox cb_remenber;
    private CheckBox cb_autologin;
    private boolean isRbPwd;
    private boolean isAutoLogin;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (MyApplication.session == null) {
                dialog.dismiss();
                toast("账号或密码错误");
            } else {
                dialog.dismiss();
                Intent intent = new Intent(EmailLoginActivity.this, MailBoxMainActivity.class);
                startActivity(intent);
                finish();
                // toast("登入成功");
            }
            super.handleMessage(msg);
        }

    };

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_email_login;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    /**
     * 初始化数据
     */
    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        setTitle("邮箱登录");
        tb_title.setRightTitle("设置服务器");
        tb_title.setRightColor(getResources().getColor(R.color.white));
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        clearAddress = (Button) findViewById(R.id.clear_address);
        emailLogin = (Button) findViewById(R.id.login_btn);
        cb_remenber = (CheckBox) findViewById(R.id.remenberPassword);
        cb_autologin = (CheckBox) findViewById(R.id.autoLogin);

        clearAddress.setOnClickListener(this);
        emailAddress.addTextChangedListener(this);
        emailLogin.setOnClickListener(this);

        cb_remenber.setOnClickListener(this);
        cb_autologin.setOnClickListener(this);
        sp = getSharedPreferences("config", Context.MODE_APPEND);
        isRemenberPwd();
//        emailAddress.setText("wanggonglei68@163.com");
//        password.setText("wgl545416022");
//        isAutoLogin();
//        requestFilePermission();
        setMailHost();
    }

    private void setMailHost()
    {

        MyApplication.info.setMailServerHost(MyCookie.getString("send_host",""));
        MyApplication.info.setMailServerPort(MyCookie.getString("send_port",""));
        MyApplication.info.setImapServerHost( MyCookie.getString("imap_host",""));
        MyApplication.info.setImapServerPort( MyCookie.getString("imap_post",""));
    }
    private void isAutoLogin() {
        boolean isAutoLogin = sp.getBoolean("IsAutoLogin", false);
        if (isAutoLogin) {
            loginEmail();
        }
    }

    @Override
    protected void initData() {
        tb_title.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
               finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                startActivity(MailSettingActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_address:
                emailAddress.setText("");
                break;
            case R.id.login_btn:
                loginEmail();
                break;
            case R.id.remenberPassword:
                remenberPwd();
                break;
            case R.id.autoLogin:
                autoLogin();

                break;
        }
    }

    /**
     * 是否记住密码
     */
    private void isRemenberPwd() {
        isRbPwd = sp.getBoolean("isRbPwd", false);
        if (isRbPwd) {
            String addr = sp.getString("address", "");
            String pwd = sp.getString("password", "");
            emailAddress.setText(addr);
            password.setText(pwd);
            cb_remenber.setChecked(true);
        }
    }

    /**
     * 记住密码
     */
    private void remenberPwd() {
        isRbPwd = sp.getBoolean("isRbPwd", false);
        if (isRbPwd) {
            sp.edit().putBoolean("isRbPwd", false).commit();
            cb_remenber.setChecked(false);
        } else {
            sp.edit().putBoolean("isRbPwd", true).commit();
            cb_remenber.setChecked(true);

        }
    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        isAutoLogin = sp.getBoolean("IsAutoLogin", false);
        if (isAutoLogin) {
            sp.edit().putBoolean("IsAutoLogin", false).commit();
            cb_autologin.setChecked(false);
        } else {
            sp.edit().putBoolean("IsAutoLogin", true).commit();
            cb_autologin.setChecked(true);

        }
    }

    /**
     * 登入邮箱
     */
    private void loginEmail() {
        String address = emailAddress.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(EmailLoginActivity.this, "邮箱地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(EmailLoginActivity.this, "邮箱密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        /**
         * 校验邮箱格式
         */
        if (!EmailFormatUtil.emailFormat(address)) {
            Toast.makeText(EmailLoginActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
        } else {
            if (isRbPwd) {
                sp.edit().putString("address", emailAddress.getText().toString().trim()).commit();
                sp.edit().putString("password", password.getText().toString().trim()).commit();
            }
            isAutoLogin=false;
            if (isAutoLogin) {
                sp.edit().putBoolean("IsAutoLogin", true);
            }
            String host = "smtp." + address.substring(address.lastIndexOf("@") + 1);//25
//            String host="imap."+address.substring(address.lastIndexOf("@")+1);//143
//            MyApplication.info.setMailServerHost(host);
//            MyApplication.info.setMailServerPort("25");
            MyApplication.info.setUserName(address);
            MyApplication.info.setPassword(pwd);
            MyApplication.info.setValidate(true);

            /**
             * 进度条
             */
            dialog = new ProgressDialog(EmailLoginActivity.this);
            dialog.setMessage("正在登入，请稍后");
            dialog.show();

            /**
             * 访问网络
             */
            new Thread() {
                @Override
                public void run() {
                    //登入操作
                    HttpUtil util = new HttpUtil();
                    MyApplication.session = util.login();
                    Message message = handler.obtainMessage();
                    message.sendToTarget();
                }

            }.start();
        }
    }


    /**
     * 文本监听事件
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            clearAddress.setVisibility(View.VISIBLE);
        } else {
            clearAddress.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (XXPermissions.isHasPermission(EmailLoginActivity.this, Permission.Group.STORAGE)) {
            hasPermission(null, true);
        } else {
            requestFilePermission();
        }
    }

    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
//                .permission(Permission.CAMERA)
//                .permission(Permission.RECORD_AUDIO)
                .request(this);
    }

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            //toast("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(EmailLoginActivity.this, true);
        } else {
            //toast("请先授予文件读写权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
}
