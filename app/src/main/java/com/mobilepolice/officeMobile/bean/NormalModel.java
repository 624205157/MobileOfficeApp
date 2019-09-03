package com.mobilepolice.officeMobile.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 14:53
 * 描述:
 */
public class NormalModel extends DataSupport implements Cloneable, Serializable {
    public String mTitle;
    public String mDetail;
    public String mEmail;
    public String mDate;
    public boolean mFlag = false;
    public int mType = 0;//0收件人1发件人2草稿箱

    public NormalModel() {

    }

    public NormalModel(String title, String detail, String email, String date,boolean flag, int type) {
        mTitle = title;
        mDetail = detail;
        mEmail = email;
        mDate=date;
        mFlag = flag;
        mType = type;

    }
}