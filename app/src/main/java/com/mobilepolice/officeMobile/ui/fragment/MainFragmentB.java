package com.mobilepolice.officeMobile.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.HomeGoodBean;
import com.mobilepolice.officeMobile.ui.activity.DocumentMainActivity;
import com.mobilepolice.officeMobile.ui.activity.EmailLoginActivity;
import com.mobilepolice.officeMobile.ui.activity.NewsListActivity;
import com.mobilepolice.officeMobile.ui.activity.SearchDocumentActivity;
import com.mobilepolice.officeMobile.ui.activity.TimeTaskActivity;
import com.mobilepolice.officeMobile.ui.adapter.WorkMainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public class MainFragmentB extends MyLazyFragment {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tb_test_bar)
    TitleBar tb_test_bar;


    public static MainFragmentB newInstance() {
        return new MainFragmentB();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_b;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {

    }


    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected void initData() {
        initDiscountShop(getListdata());
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    private List<HomeGoodBean> getListdata() {
        List<HomeGoodBean> listshop = new ArrayList<>();
        HomeGoodBean bean = new HomeGoodBean();
        bean.setId("1");
        bean.setName("发起公文");
        bean.setSrc(R.mipmap.doc1);
        listshop.add(bean);

        bean = new HomeGoodBean();
        bean.setId("2");
        bean.setName("接受公文");
        bean.setSrc(R.mipmap.doc2);
        listshop.add(bean);


        bean = new HomeGoodBean();
        bean.setId("3");
        bean.setName("公文查询");
        bean.setSrc(R.mipmap.doc3);
        listshop.add(bean);


        bean = new HomeGoodBean();
        bean.setId("4");
        bean.setName("邮箱");
        bean.setSrc(R.mipmap.doc4);
        listshop.add(bean);

        bean = new HomeGoodBean();
        bean.setId("5");
        bean.setName("日程管理");
        bean.setSrc(R.mipmap.doc5);
        listshop.add(bean);

//        bean = new HomeGoodBean();
//        bean.setId("5");
//        bean.setName("考勤管理");
//        bean.setSrc(R.mipmap.doc6);
//        listshop.add(bean);
//
//        bean = new HomeGoodBean();
//        bean.setId("6");
//        bean.setName("通知通告");
//        bean.setSrc(R.mipmap.doc7);
//        listshop.add(bean);

        return listshop;
    }


    private void initDiscountShop(final List<HomeGoodBean> listshop) {

        WorkMainAdapter shopAdapter = new WorkMainAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(shopAdapter);
        shopAdapter.setNewData(listshop);
        shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                HomeGoodBean bean = listshop.get(position);
                if (bean.getId().equals("1")) {
                    Intent intent = new Intent(getActivity(), DocumentMainActivity.class);
                    String ids = bean.getId();
                    intent.putExtra("id", ids);
                    startActivity(intent);
                } else if (bean.getId().equals("3")) {

                    startActivity(SearchDocumentActivity.class);
                    // startActivity(MailBoxMainActivity.class);
//                } else if (bean.getId().equals("4")) {
//
//                    startActivity(EmailLoginActivity.class);
//                    // startActivity(MailBoxMainActivity.class);
                } else if (bean.getId().equals("5")) {
                    Intent timeTask = new Intent(getContext(), TimeTaskActivity.class);
                    startActivity(timeTask);
//                } else if (bean.getId().equals("6")) {
//                    Intent intent = new Intent(getActivity(), NewsListActivity.class);
//                    String ids = bean.getId();
//                    intent.putExtra("title", "通知公告");
//                    intent.putExtra("url", "http://www.freetk.cn:8789/mobileworkws/information.html");
//                    startActivity(intent);
                } else {
                    toast("此功能正在维护中");
                }
            }
        });
    }
}