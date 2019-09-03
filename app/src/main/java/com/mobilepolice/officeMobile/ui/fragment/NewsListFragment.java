package com.mobilepolice.officeMobile.ui.fragment;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

//import com.tencent.smtt.sdk.TbsReaderView;

/**
 * 新闻列表
 */
public class NewsListFragment extends MyLazyFragment {

    @BindView(R.id.webView)
    WebView webView;

    String id = "";

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        webView.loadUrl("http://www.freetk.cn:8789/mobileworkws/news-list.html");
    }


    @Override
    protected void initData() {

    }
}