package com.mobilepolice.officeMobile.bean;

/**
 * Created by HLL on 2017/3/30.
 */

public class SpinnerItem {
    private String Key = "";
    private String Value = "";

    public SpinnerItem() {
        Key = "";
        Value = "";
    }

    public SpinnerItem(String _Key, String _Value) {
        Key = _Key;
        Value = _Value;
    }

    @Override
    public String toString() {
// 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
// TODO Auto-generated method stub
        return Value;
    }

    public String GetKey() {
        return Key;
    }

    public String GetValue() {
        return Value;
    }
}
