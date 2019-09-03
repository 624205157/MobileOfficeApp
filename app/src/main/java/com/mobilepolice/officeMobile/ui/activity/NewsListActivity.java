package com.mobilepolice.officeMobile.ui.activity;

import android.view.View;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * 新闻列表
 */
public class NewsListActivity extends MyActivity {
    @BindView(R.id.tb_title)
    TitleBar tb_title;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }


    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        setTitle(title);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return false;
            }
        });
        webView.loadUrl(url);
        //"http://www.freetk.cn:8789/mobileworkws/news-list.html"
        tb_title.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

                if (webView.canGoBack()) {
                    webView.goBack();
                }else {
                    finish();
                }
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
}