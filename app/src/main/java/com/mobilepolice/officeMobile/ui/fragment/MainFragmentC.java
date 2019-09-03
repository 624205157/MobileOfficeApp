package com.mobilepolice.officeMobile.ui.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.NewsListBean;
import com.mobilepolice.officeMobile.bean.NoticeListBean;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.http.HttpTools;
import com.mobilepolice.officeMobile.ui.activity.NewsDetailedActivity;
import com.mobilepolice.officeMobile.ui.adapter.NewsAdapter;
import com.mobilepolice.officeMobile.ui.adapter.NoticeAdapter;
import com.mobilepolice.officeMobile.utils.App;
import com.mobilepolice.officeMobile.widget.RecycleViewDivider;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目框架使用示例
 */
public class MainFragmentC extends MyLazyFragment {

    @BindView(R.id.tb_test_bar)
    TitleBar tb_test_bar;


    @BindView(R.id.class_id_01)
    LinearLayout class_id_01;
    @BindView(R.id.class_id_02)
    LinearLayout class_id_02;
    @BindView(R.id.line_wating01)
    View line_wating01;
    @BindView(R.id.line_wating02)
    View line_wating02;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;
    private List<String> titles = new ArrayList<>();

    private int toggleFlag;

    public static MainFragmentC newInstance() {
        return new MainFragmentC();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_c;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
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

    int newsPage, noticePage;
    //////////////////////////////////////////////////////////////////////
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private NoticeAdapter adapter;
    ArrayList<NewsListBean> list = new ArrayList<>();
    ArrayList<NoticeListBean> listnotice = new ArrayList<NoticeListBean>();

    @Override
    protected void initView() {

        class_id_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_wating01.setVisibility(View.VISIBLE);
                line_wating02.setVisibility(View.INVISIBLE);
                loadNewsByToggleFlag(0);
            }
        });
        class_id_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_wating02.setVisibility(View.VISIBLE);
                line_wating01.setVisibility(View.INVISIBLE);
                loadNewsByToggleFlag(1);
            }
        });
        tb_test_bar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        toggleFlag = 0;
        initAdapters();
        initRecyclerViewSettings();
        initNewsAdapterOnItemClick();
        initNoticeAdapterItemClick();


        /*2019-03-01-end*/
        loadNewsByToggleFlag(toggleFlag);
    }

    private void initAdapters() {
        mAdapter = new NewsAdapter(0);
        adapter = new NoticeAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    private void initRecyclerViewSettings() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRecyclerView.setFocusable(false);

    }

    private void loadNewsByToggleFlag(int toggleFlag) {
        switch (toggleFlag) {
            case 0:
                loadNews();
                break;
            case 1:
                loadNotice();
                break;
            case 2:
                break;
        }

    }

    private void loadNews() {
        HttpTools.build(HttpConnectInterface.class)
                .findTpxwInfo(String.valueOf(1), String.valueOf(20))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::newsResult, this::err, this::onComplete)
                .isDisposed();
        newsPage += 5;
    }

    private void loadNotice() {
        HttpTools.build(HttpConnectInterface.class)
                .findTzggInfo(String.valueOf( 1), String.valueOf(20), App.userCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::noticeResult, this::err, this::onComplete)
                .isDisposed();
        noticePage += 5;
    }

    private void noticeResult(ArrayList<NoticeListBean> o) {
        if (o != null && o.size() > 0) {
            listnotice.addAll(o);
            adapter.setData(listnotice);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void newsResult(ArrayList<NewsListBean> o) {
        if (o != null && o.size() > 0) {
            list.addAll(o);
            mAdapter.setData(list);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initData() {
    }

    private void initNewsAdapterOnItemClick() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailedActivity.class);
                intent.putExtra("title", "新闻中心");
                intent.putExtra("flag", "NEWS");
                intent.putExtra("contentId", mAdapter.getData().get(position).getId());
                intent.putExtra("titleIn", mAdapter.getData().get(position).getTitle());
                intent.putExtra("img", mAdapter.getData().get(position).getImg());
                intent.putExtra("time", mAdapter.getData().get(position).getTime());
                startActivity(intent);
            }
        });

    }

    private void initNoticeAdapterItemClick() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailedActivity.class);
                intent.putExtra("title", "通知公告");
                intent.putExtra("flag", "NOTICE");
                intent.putExtra("url", "file:///android_asset/news1.html");
                intent.putExtra("contentId", MainFragmentC.this.adapter.getData().get(position).getId());
                intent.putExtra("titleIn", MainFragmentC.this.adapter.getData().get(position).getTitle());
                intent.putExtra("time", MainFragmentC.this.adapter.getData().get(position).getTime());
                startActivity(intent);
            }
        });
    }

    /*设置商品列表listView的高度*/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}