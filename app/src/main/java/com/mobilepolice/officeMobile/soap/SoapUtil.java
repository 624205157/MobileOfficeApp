package com.mobilepolice.officeMobile.soap;

import android.content.Context;

/**
 * 描述：Soap请求工具类
 * 
 * @author Louis
 * 
 */
public class SoapUtil {

	/** 实例话对象. */
	private SoapClient mClient = null;

	/** AbSoapUtil实例. */
	private static SoapUtil mAbSoapUtil;

	/**
	 * 单例 获取AbSoapUtil实例.
	 *
	 * @param context
	 *            the context
	 * @return single instance of AbSoapUtil
	 */
	public static SoapUtil getInstance(Context context) {
		if (null == mAbSoapUtil) {
			mAbSoapUtil = new SoapUtil(context);
		}
		return mAbSoapUtil;
	}

	/**
	 * AbSoapUtil构造方法.
	 *
	 * @param context
	 *            the context
	 */
	private SoapUtil(Context context) {
		super();
		this.mClient = new SoapClient(context);
	}

	/**
	 * Call.
	 *
	 * @param url
	 *            the url
	 * @param nameSpace
	 *            the name space
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 * @param iSoapUtilCallback
	 *            the listener
	 */
	public void call(String url, String nameSpace, String methodName, SoapParams params, SoapClient.ISoapUtilCallback iSoapUtilCallback) {
		mClient.call(url, nameSpace, methodName, params, iSoapUtilCallback);
	}
	public void callSynchro(String url, String nameSpace, String methodName, SoapParams params, SoapClient.ISoapUtilCallback iSoapUtilCallback) {
		mClient.callSynchro(url, nameSpace, methodName, params, iSoapUtilCallback);
	}


	/**
	 * 描述：设置连接超时时间(第一次请求前设置).
	 * 
	 * @param timeout
	 *            毫秒
	 */
	public void setTimeout(int timeout) {
		mClient.setTimeout(timeout);
	}

	/**
	 * Sets the dot net.
	 * 
	 * @param dotNet
	 *            the new dot net
	 */
	public void setDotNet(boolean dotNet) {
		mClient.setDotNet(dotNet);
	}


}
