package com.mobilepolice.officeMobile.ui.activity;

import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.Email;
import com.mobilepolice.officeMobile.email.IOUtil;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 收件箱详情
 */
public class MailBoxCollectDetailActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_collect_detail;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("收件箱列表");
        email = (Email) getIntent().getSerializableExtra("EMAIL");
        attachmentsInputStreams = ((MyApplication) getApplication()).getAttachmentsInputStreams();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }


    private TextView tv_addr, tv_mailsubject, tv_mailcontent;
    private ListView lv_mailattachment;
    private WebView wv_mailcontent;
    private Button btn_cancel, btn_relay;
    private ArrayList<InputStream> attachmentsInputStreams;
    private Email email;
    private Handler handler;


    public void initViews() {
        handler = new MyHandler(this);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        tv_mailsubject = (TextView) findViewById(R.id.tv_mailsubject);
        tv_mailcontent = (TextView) findViewById(R.id.tv_mailcontent);
        if (email.getAttachments().size() > 0) {
            lv_mailattachment = (ListView) findViewById(R.id.lv_mailattachment);
            lv_mailattachment.setVisibility(View.VISIBLE);
            lv_mailattachment.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, email.getAttachments()));
            lv_mailattachment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            handler.obtainMessage(0, "开始下载\"" + email.getAttachments().get(position) + "\"").sendToTarget();
                            InputStream is = attachmentsInputStreams.get(position);
                            String path = new IOUtil().stream2file(is, Environment.getExternalStorageDirectory().toString() + "/temp/" + email.getAttachments().get(position));
                            if (path == null) {
                                handler.obtainMessage(0, "下载失败！").sendToTarget();
                            } else {
                                handler.obtainMessage(0, "文件保存在：" + path).sendToTarget();
                            }
                        }
                    }).start();
                }
            });
        }

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_relay = (Button) findViewById(R.id.btn_relay);

        tv_addr.setText(email.getFrom());
        tv_mailsubject.setText(email.getSubject());
        if (email.isHtml()) {
            wv_mailcontent = (WebView) findViewById(R.id.wv_mailcontent);
            wv_mailcontent.setVisibility(View.VISIBLE);
            wv_mailcontent.loadDataWithBaseURL(null, email.getContent(), "text/html", "utf-8", null);
            // wv_mailcontent.getSettings().setLoadWithOverviewMode(true);
            // wv_mailcontent.getSettings().setUseWideViewPort(true);
            //设置缩放
            wv_mailcontent.getSettings().setBuiltInZoomControls(true);

            //网页适配
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int scale = dm.densityDpi;
            if (scale == 240) {
                wv_mailcontent.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (scale == 160) {
                wv_mailcontent.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else {
                wv_mailcontent.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            }
            wv_mailcontent.setWebChromeClient(new WebChromeClient());
            tv_mailcontent.setVisibility(View.GONE);
        } else {
            tv_mailcontent.setText(email.getContent());
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MailBoxCollectDetailActivity.this.finish();
            }
        });

        btn_relay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // startActivity(new Intent(MailBoxCollectDetailActivity.this, MailEditActivity.class).putExtra("EMAIL", email).putExtra("TYPE", 1));
            }
        });
        /*btn_relay.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MailContentActivity.this, MailEditActivity.class).putExtra("EMAIL", email).putExtra("type", 2));
                return true;
            }
        });*/
    }

    private static class MyHandler extends Handler {

        private WeakReference<MailBoxCollectDetailActivity> wrActivity;

        public MyHandler(MailBoxCollectDetailActivity activity) {
            this.wrActivity = new WeakReference<MailBoxCollectDetailActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            final MailBoxCollectDetailActivity activity = wrActivity.get();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    }

    ;

}
