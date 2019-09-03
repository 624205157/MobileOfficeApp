package com.mobilepolice.officeMobile.ui.fragment;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

//import com.tencent.smtt.sdk.TbsReaderView;

/**
 * 通知公告
 */
public class NoticeListFragment extends MyLazyFragment {
    @BindView(R.id.webView)
    WebView webView;

    String id = "";

    public static NoticeListFragment newInstance() {
        return new NoticeListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice_list;
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
        webView.loadUrl("http://www.freetk.cn:8789/mobileworkws/information.html");
    }


    @Override
    protected void initData() {

    }
}