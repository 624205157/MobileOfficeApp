package com.mobilepolice.officeMobile.utils;

import android.widget.Toast;

import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.LoginBean;


/**
 * Created by miao on 2018/9/21.
 */
public class App extends MyApplication {

    private String Token;
    private LoginBean userInfo;


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    private static App app;


    public static App app() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        try {
//            Class<?> aClass = Class.forName(SevenData.class.getName());
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public void setUserInfo(LoginBean userInfo) {
        this.userInfo = userInfo;
    }

    public LoginBean getUserInfo() {
        return userInfo;
    }


    private void result() {
        if (getUserInfo().getFlag() == 0) {
        } else {
            Toast.makeText(App.this, "UserInfo ::" + getUserInfo().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
