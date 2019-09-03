package com.mobilepolice.officeMobile.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.hjq.base.BaseFragmentPagerAdapter;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.ui.fragment.MainFragmentA;
import com.mobilepolice.officeMobile.ui.fragment.MainFragmentB;
import com.mobilepolice.officeMobile.ui.fragment.MainFragmentC;
import com.mobilepolice.officeMobile.ui.fragment.MainFragmentD;
import com.mobilepolice.officeMobile.ui.fragment.MainFragmentE;


import java.util.List;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 主页界面 ViewPager + Fragment 适配器
 */
public final class HomeViewPagerAdapter extends BaseFragmentPagerAdapter<MyLazyFragment> {

    public HomeViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager fm, List<MyLazyFragment> list) {
        list.add(MainFragmentA.newInstance());
        list.add(MainFragmentB.newInstance());
        list.add(MainFragmentC.newInstance());
        list.add(MainFragmentD.newInstance());
        list.add(MainFragmentE.newInstance());
    }
}