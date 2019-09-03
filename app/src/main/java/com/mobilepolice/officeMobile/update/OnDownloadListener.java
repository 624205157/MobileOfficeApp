package com.mobilepolice.officeMobile.update;

/**
 * Created by  Marlon on 2018/1/22.
 * Describe
 */

public interface OnDownloadListener {

    void onStart();

    void onProgress(int progress);

    void onFinish();


}
