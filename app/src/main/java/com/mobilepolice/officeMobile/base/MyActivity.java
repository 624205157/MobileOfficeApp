package com.mobilepolice.officeMobile.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
//import com.hjq.umeng.UmengHelper;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.config.Config;
import com.mobilepolice.officeMobile.update.NotificationInfo;
import com.mobilepolice.officeMobile.update.UpdateInfo;
import com.mobilepolice.officeMobile.update.UpdateManager;
import com.mobilepolice.officeMobile.utils.IsPad;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.webservice.HttpWebServer;
import com.mobilepolice.officeMobile.webservice.MyCallBack;

import org.xutils.common.Callback;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中的Activity基类
 */
public abstract class MyActivity extends UIActivity
        implements OnTitleBarListener {
    protected Context mContext;
    private Unbinder mButterKnife;//View注解


    @Override
    public void init() {
        mContext = this;
        //初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }
        initOrientation();
        mButterKnife = ButterKnife.bind(this);

        super.init();
    }

    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        //如果没有指定屏幕方向，则默认为竖屏
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }

        if (IsPad.isPad(mContext)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }
    }

    public void showProgressDialog(boolean show, String message) {
        mProgressDialogUtils.showProgressDialog(show, message);
    }

    public void showProgressDialog(boolean show) {
        mProgressDialogUtils.showProgressDialog(show);
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    protected TitleBar getTitleBar() {
        if (getTitleBarId() > 0 && findViewById(getTitleBarId()) instanceof TitleBar) {
            return findViewById(getTitleBarId());
        }
        return null;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    //此方法只是关闭软键盘
    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * {@link OnTitleBarListener}
     */

    // 标题栏左边的View被点击了
    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    // 标题栏中间的View被点击了
    @Override
    public void onTitleClick(View v) {
    }

    // 标题栏右边的View被点击了
    @Override
    public void onRightClick(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计
//        UmengHelper.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计
//        UmengHelper.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) mButterKnife.unbind();
    }

    /**
     * 显示一个吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }


    public void updateSystem() {
        HttpWebServer webServer = new HttpWebServer();
        webServer.checkUpdate(new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {

                int vercode = Config.getVerCode(MyActivity.this);
                try {
                    if (!TextUtils.isEmpty(result)) {
                        int newVerCode = Integer.parseInt(JsonParseUtils.jsonToResult(result, "VersionCode"));
                        String newVerName = JsonParseUtils.jsonToResult(result, "VersionName");
                        String apkname = JsonParseUtils.jsonToResult(result, "apkname");
                        String versionInfo = JsonParseUtils.jsonToResult(result, "VersionInfo");
                        String isMustDownload = JsonParseUtils.jsonToResult(result, "isMustDownload");

                        boolean isForce = false;
                        if (!TextUtils.isEmpty(isMustDownload)) {
                            if (isMustDownload.equals("0")) {
                                isForce = false;
                            } else if (isMustDownload.equals("1")) {
                                isForce = true;
                            }
                        }
                        String versonURL = "http://www.freetk.cn:8789/download/" + apkname;
                        if (newVerCode > vercode) {
                            updateApk(MyActivity.this, versionInfo, newVerName, isForce, true, 10000000, versonURL, MyActivity.this.getResources().getString(R.string.app_name));
                        }
                    }
                } catch (Exception e) {

                    //Toast.makeText(getActivity(), "检查更新信息失败", Toast.LENGTH_SHORT).show();

                }

//                JSONArray array = null;
//                    array = new JSONArray(result);
//                    if (array.length() > 0) {
//                        JSONObject obj = array.getJSONObject(0);
//                        try {
//                            int newVerCode = Integer.parseInt(obj.getString("VersionCode"));
//                            String newVerName = obj.getString("VersionName");
//                            String isMustDownload = "1";
//                            String apkname = obj.getString("apkname");
//                            //String versonURL = obj.getString("VersonURL");
//                            String verson_size = obj.getString("volume");
//                            String versionInfo = obj.getString("VersionInfo");
//                            //ss = URLDecoder.decode(obj.getString("VersionInfo"), "UTF-8").split("&");
//                            if (newVerCode > vercode) {
//                                boolean isForce = false;
//                                if (isMustDownload.equals("0")) {
//                                    isForce = false;
//                                } else if (isMustDownload.equals("1")) {
//                                    isForce = true;
//                                }
//                                String versonURL = "http://www.freetk.cn:8789/download/" + apkname;
//                                updateApk(mContext, versionInfo, newVerName, isForce, true, 10000000, versonURL, mContext.getResources().getString(R.string.app_name));
//                                // setIsMustDownload();
//                            } else {
//                                // Toast.makeText(getActivity(), "已是最新版本。版本号：" + newVerName, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//
//                            //Toast.makeText(getActivity(), "检查更新信息失败", Toast.LENGTH_SHORT).show();
//
//                        }

//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Message message = new Message();
//                message.arg1 = 1;
//                mhandler.sendMessage(message);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                super.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });

    }

    /**
     * @param hitContent  提示更新内容
     * @param versionName 更新版本名
     * @param isForce     是否强制升级
     * @param isSlient    是否静默安装
     * @param fileSize    Apk文件大小
     * @param apkURL      Apk下载链接
     * @param apkName     Apk名称
     */
    public void updateApk(Context mContext, String hitContent, String versionName, boolean isForce, boolean isSlient, long fileSize, String apkURL, String apkName) {
        //不用害怕 根据英文名称直译就可以
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.versionName = versionName;
        updateInfo.versionCode = 10;
        updateInfo.isForce = isForce;
        updateInfo.size = fileSize;
        updateInfo.updateContent = hitContent;
        if (isForce) {
            updateInfo.isIgnorable = false;
        }
        NotificationInfo notificationInfo = new NotificationInfo(R.mipmap.ic_launcher, R.mipmap.ic_launcher, apkName, "正在下载中", hitContent);
        new UpdateManager(mContext, apkURL, apkName, isSlient, updateInfo, notificationInfo).init();
    }

}