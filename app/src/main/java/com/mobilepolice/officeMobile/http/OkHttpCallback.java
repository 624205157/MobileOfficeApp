package com.mobilepolice.officeMobile.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 封装了OkCallback的处理   2016 - 10 - 14
 *
 * @param <ResponseResult> 请求成功的返回值类型
 * @param <FailResult>     请求失败的返回值类型
 */
public abstract class OkHttpCallback<ResponseResult, FailResult>
        implements Callback {

    /**
     * 请求失败标志
     */
    public static final int FAIL = -1;

    /**
     * 请求取消标志
     */
    public static final int CANCEL = 0;

    /**
     * 请求成功标志
     */
    public static final int SUCCESS = 1;


    private static final int MESSAGE_POST_SUCCESS = 0x1;

    private static final int MESSAGE_POST_FAILE = 0x2;

    /**
     * 请求失败
     *
     * @param call Call
     * @param e    IOException
     * @return FailResult请求失败的结果
     */
    public abstract FailResult onThreadFailure(Call call, IOException e);

    /**
     * 请求成功，此处对请求结果进行处理
     *
     * @param call     Call
     * @param response 处理的结果
     * @return ResponseResult 对Response处理后的结果
     * @throws IOException
     */
    public abstract ResponseResult onThreadResponse(Call call, Response response)
            throws IOException;

    /**
     * 请求失败处理的结果，Run main thread
     *
     * @param isCancel 请求是否被取消
     * @param result   请求失败处理的结果
     */
    protected void onFailResult(boolean isCancel, FailResult result) {

    }

    /**
     * 请求成功处理的结果，Run main thread
     *
     * @param result 请求成功处理后的结果
     */
    protected void onResponseResult(ResponseResult result) {

    }

    /**
     * 在onFailResult 或 onResponseResult 方法之后执行
     */
    protected void onFinally () {

    }

    @Override
    public void onFailure(Call call, final IOException e) {
        FailResult result = onThreadFailure(call, e);
        Message message = getHandler().obtainMessage(MESSAGE_POST_FAILE,
                new MyOkHttpExecuteTaskResult<FailResult>(call, this, result));
        message.sendToTarget();
    }

    @Override
    public void onResponse(Call call, final Response response)
            throws IOException {
        ResponseResult result = onThreadResponse(call, response);
        Message message = getHandler().obtainMessage(MESSAGE_POST_SUCCESS,                new MyOkHttpExecuteTaskResult<ResponseResult>(call, this, result));        message.sendToTarget();    }
    private static InternalHandler sHandler;
    private static Handler getHandler() {
        synchronized (OkHttpCallback.class) {
            if (sHandler == null) {
                sHandler = new InternalHandler();
            }
            return sHandler;
        }
    }
    private static class MyOkHttpExecuteTaskResult<Result> {
        @SuppressWarnings("rawtypes")
        final OkHttpCallback mTask;
        final Result mData;
        final Call mCall;
        public MyOkHttpExecuteTaskResult(Call call, OkHttpCallback task,
                                         Result data) {
            mCall = call;
            mTask = task;
            mData = data;
        }
    }
    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            MyOkHttpExecuteTaskResult<?> result = (MyOkHttpExecuteTaskResult<?>) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_SUCCESS:
                    result.mTask.onResponseResult(result.mData);
                    break;
                case MESSAGE_POST_FAILE:
                    result.mTask.onFailResult(result.mCall.isCanceled(), result.mData);
                    break;
            }
            result.mTask.onFinally();
        }
    }
}
