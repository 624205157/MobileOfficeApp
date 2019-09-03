package com.mobilepolice.officeMobile.email;

import android.support.annotation.NonNull;


import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.EmailInfo;

import java.io.File;

public class SendMailUtil {
//    //qq
//    private static final String HOST = "smtp.qq.com";
//    private static final String PORT = "587";
//    private static final String FROM_ADD = "teprinciple@foxmail.com"; //发送方邮箱
//    private static final String FROM_PSW = "lfrlpganzjrwbeci";//发送方邮箱授权码

//    //163
//    private static final String HOST = "smtp.163.com";
//    private static final String PORT = "465"; //或者465  994
//    private static final String FROM_ADD = "wanggonglei68@163.com";
//    private static final String FROM_PSW = "wgl45416022";
//    private static final String TO_ADD = "2584770373@qq.com";

    public static void send(final File file, String toAdd) {
        final EmailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo, file);
            }
        }).start();
    }

    public static void send(String toAdd) {
        final EmailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    @NonNull
    private static EmailInfo creatMail(String toAdd) {
        final EmailInfo mailInfo = new EmailInfo();
        mailInfo.setMailServerHost(MyApplication.info.getMailServerHost());
        mailInfo.setMailServerPort(MyApplication.info.getMailServerPort());
        mailInfo.setValidate(true);
        mailInfo.setUserName(MyApplication.info.getUserName()); // 你的邮箱地址
        mailInfo.setPassword(MyApplication.info.getPassword());// 您的邮箱密码
        mailInfo.setFromAddress(MyApplication.info.getFromAddress()); // 发送的邮箱
        mailInfo.setToAddress(MyApplication.info.getToAddress()); // 发到哪个邮件去
        mailInfo.setSubject("Hello"); // 邮件主题
        mailInfo.setContent("Android 测试"); // 邮件文本
        return mailInfo;
    }

}
