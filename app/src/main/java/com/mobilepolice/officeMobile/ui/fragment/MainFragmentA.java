package com.mobilepolice.officeMobile.ui.fragment;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.widget.XCollapsingToolbarLayout;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目炫酷效果示例
 */
public class MainFragmentA extends MyLazyFragment
        implements XCollapsingToolbarLayout.OnScrimsListener {

//    @BindView(R.id.abl_test_bar)
//    AppBarLayout mAppBarLayout;
//    @BindView(R.id.ctl_test_bar)
//    XCollapsingToolbarLayout mCollapsingToolbarLayout;
//    @BindView(R.id.t_test_title)
//    Toolbar mToolbar;
//    @BindView(R.id.tb_test_bar)
//    TitleBar mTitleBar;

//    @BindView(R.id.tv_test_address)
//    TextView mAddressView;
//    @BindView(R.id.tv_test_search)
//    TextView mSearchView;

    public static MainFragmentA newInstance() {
        return new MainFragmentA();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_a;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }



    @Override
    protected void initView() {

        // 给这个ToolBar设置顶部内边距，才能和TitleBar进行对齐
//        ImmersionBar.setTitleBar(getSupportActivity(), mToolbar);
//        getStatusBarConfig().statusBarColor(R.color.white);
        //设置渐变监听
        //mCollapsingToolbarLayout.setOnScrimsListener(this);

    }



    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    /**
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
//        if (shown) {
//            mAddressView.setTextColor(getResources().getColor(R.color.black));
//            mSearchView.setBackgroundResource(R.drawable.bg_home_search_bar_gray);
//            getStatusBarConfig().statusBarDarkFont(true).init();
//        }else {
//            mAddressView.setTextColor(getResources().getColor(R.color.white));
//            mSearchView.setBackgroundResource(R.drawable.bg_home_search_bar_transparent);
//            getStatusBarConfig().statusBarDarkFont(false).init();
//        }
    }
}