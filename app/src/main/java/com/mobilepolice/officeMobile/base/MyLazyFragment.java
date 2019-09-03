package com.mobilepolice.officeMobile.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjq.toast.ToastUtils;
//import com.hjq.umeng.UmengHelper;
import com.mobilepolice.officeMobile.widget.DialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中Fragment懒加载基类
 */
public abstract class MyLazyFragment extends UILazyFragment {

    private Unbinder mButterKnife;// View注解
    private DialogUtils mProgressDialogUtils;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        mProgressDialogUtils = new DialogUtils(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 友盟统计
//        UmengHelper.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 友盟统计
//        UmengHelper.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
    }

    public void showProgressDialog(boolean show, String message) {
        mProgressDialogUtils.showProgressDialog(show, message);
    }

    public void showProgressDialog(boolean show) {
        mProgressDialogUtils.showProgressDialog(show);
    }
    /**
     * 显示一个吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }
}