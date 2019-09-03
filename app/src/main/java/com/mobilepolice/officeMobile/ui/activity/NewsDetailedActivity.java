package com.mobilepolice.officeMobile.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.fish.timeline.DateUtil;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.bean.NewsDetailBean;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.http.HttpTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 新闻列表
 */
public class NewsDetailedActivity extends MyActivity {


    @BindView(R.id.leftButton)
    ImageView leftButton;
    @BindView(R.id.mWebConetent)
    WebView webView;
    private int type;

    @BindView(R.id.title_top)
    TextView title_top;
    @BindView(R.id.news_time)
    TextView news_time;

    private String titleIn;
    private String time= DateUtil.format("yyyy-MM-dd", System.currentTimeMillis());
    private String img = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detailed;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }


    @Override
    protected void initView() {

        initWebView();
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        leftButton.setOnClickListener(v -> finish());
    }

    private void initWebView() {

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 隐藏滚动条webView.requestFocus();
        webView.requestFocusFromTouch();

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);// 支持JS
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView.getSettings().setLoadWithOverviewMode(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染等级
        mWebSettings.setBuiltInZoomControls(false);// 设置支持缩放
        mWebSettings.setDomStorageEnabled(true);//使用localStorage则必须打开
        mWebSettings.setBlockNetworkImage(true);// 首先阻塞图片，让图片不显示
        mWebSettings.setBlockNetworkImage(false);//  页面加载好以后，在放开图片：
        mWebSettings.setSupportMultipleWindows(false);// 设置同一个界面
        mWebSettings.setBlockNetworkImage(false);
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebSettings.setNeedInitialFocus(false);// 禁止webview上面控件获取焦点(黄色边框)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载时
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载结束时
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("tenvideo2:")) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    return true;
//                }
                view.loadUrl(url);
                /**
                 * 网页跳转：
                 * 1.在当前的webview跳转到新连接
                 * view.loadUrl(url);
                 * 2.调用系统浏览器跳转到新网页
                 * Intent i = new Intent(Intent.ACTION_VIEW);
                 * i.setData(Uri.parse(url));
                 * startActivity(i);
                 */
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void initData() {
        getIntentData();
        HttpConnectInterface httpConnectInterface = HttpTools.build(HttpConnectInterface.class);
        if (flag != null)
            switch (flag) {
                case "NEWS":
                    type = 0;
                    httpConnectInterface.findTpxwInfoDetails(contentId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::newsResult, this::err, this::onComplete)
                            .isDisposed();
                    break;
                case "NOTICE":
                    type = 1;
                    httpConnectInterface.findTzggInfoDetails(contentId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::newsResult, this::err, this::onComplete)
                            .isDisposed();
                    break;

            }
        else {
            webView.loadUrl("file:///android_asset/news1.html");
        }
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void newsResult(NewsDetailBean o) {
        title_top.setText(o.getTitle().replaceAll("<br>", "\n"));
        news_time.setText(o.getTime());
//        cached(o.getCon())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::cacheHtml, this::err, this::onComplete)
//                .isDisposed();

        webView.loadDataWithBaseURL(null, marginData(o.getCon()), "text/html", "utf-8", null);
    }



    private String marginData(String content) {

        String c = ("<!DOCTYPE2 html><html><head><meta charset=\"utf-8\">" +
                "<meta content=\"width=device-width, initial-scale=1.0," +
                "maximum-scale=1.0, user-scalable=0;\" name=\"viewport\" />" +
                "</head><?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<body>" + content + "</body></html>")
//                .replaceAll("10.106.18.75:8081", "192.168.20.228:7124")
                .replaceAll("/u/cms/www", "http://192.168.20.228:7124/u/cms/www");
        Log.e("marginData: ", c);
        return c;

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    private void getIntentData() {
        contentId = getIntent().getStringExtra("contentId");
        titleIn = getIntent().getStringExtra("titleIn");
        img = getIntent().getStringExtra("img");
        flag = getIntent().getStringExtra("flag");
        time = getIntent().getStringExtra("time");
    }

    private String contentId;
    private String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}