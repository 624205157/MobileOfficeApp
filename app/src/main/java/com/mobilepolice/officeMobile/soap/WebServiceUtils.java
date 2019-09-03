package com.mobilepolice.officeMobile.soap;

import android.os.Handler;
import android.os.Message;

import com.mobilepolice.officeMobile.config.Config;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebServiceUtils {
    public interface CallBack {
        void result(String result);
    }

    public static void getPersonDeptName(final String methodName, final SoapParams params, final CallBack callBack) {
        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
                callBack.result((String) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 命名空间
                String nameSpace = Config.SERVICE_NAME_SPACE;// "http://services.webservice.mobilework.com/";
                // 调用的方法名称
                //  String methodName = methodNames;
                // EndPoint
                String endPoint = Config.SERVICE_URL;//"http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";
                // SOAP Action
                // final String soapAction = "http://services.webservice.mobilework.com/findApplyPersonDeptName";
                // 建立webservice连接对象
                final HttpTransportSE transport = new HttpTransportSE(endPoint);
                //transport.debug = true;// 是否是调试模式
                // 设置连接参数
                SoapObject soapObject = new SoapObject(nameSpace, methodName);
                // 传递参数
                LinkedHashMap<String, Object> paramsList = params.getParamsList();
                Iterator<Map.Entry<String, Object>> iter = paramsList.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    soapObject.addProperty(entry.getKey(), entry.getValue());
                }
                // soapObject.addProperty(key, value);
                //soapObject.addProperty("json", "{\"applyPerson\":\"17600194545\"}");
                // 设置返回参数
                final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);// soap协议版本必须用SoapEnvelope.VER11（Soap V1.1）
                envelope.dotNet = false;// 注意：这个属性是对dotnetwebservice协议的支持,如果dotnet的webservice需要设置成true
                envelope.bodyOut = soapObject;//千万注意！！
                //  envelope.setOutputSoapObject(soapObject);// 设置请求参数

                try {
                    transport.call(null, envelope);// 调用WebService
                } catch (Exception e) {
                    mHandler.sendMessage(mHandler.obtainMessage(-1, e.getMessage()));
                }
                if (envelope.bodyIn instanceof SoapFault) {
                    SoapFault error = (SoapFault) envelope.bodyIn;
                    // 将异常的消息利用Handler发送到主线程
                    mHandler.sendMessage(mHandler.obtainMessage(0, error.toString()));
                } else {
                    SoapObject object = (SoapObject) envelope.bodyIn;// 获取返回的数据
                    // 将结果利用Handler发送到主线程
                    mHandler.sendMessage(mHandler.obtainMessage(1, object.getProperty(0).toString()));
                }
            }
        }).start();
    }
}
