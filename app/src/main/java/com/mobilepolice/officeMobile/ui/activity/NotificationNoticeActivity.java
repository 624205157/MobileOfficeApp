package com.mobilepolice.officeMobile.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.ui.fragment.NewsListFragment;
import com.mobilepolice.officeMobile.ui.fragment.NoticeListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 通知公告和新闻列表
 */
public class NotificationNoticeActivity extends MyActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;
    private List<String> titles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification_notice;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }


    @Override
    protected void initView() {
        setTitle("消息列表");
    }


    @Override
    protected void initData() {

        titles.add("通知公告");
        titles.add("新闻列表");
        final ArrayList<MyLazyFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NoticeListFragment());
        fragmentList.add(new NewsListFragment());
        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        view_pager.setAdapter(adapter);
        view_pager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        view_pager.setOffscreenPageLimit(titles.size());


        mSlidingTabLayout.setViewPager(view_pager);
        view_pager.setCurrentItem(0);
        //  mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                fragmentList.get(arg0);
                // mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(arg0));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<MyLazyFragment> fragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<MyLazyFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
}