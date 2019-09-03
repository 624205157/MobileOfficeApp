package com.mobilepolice.officeMobile.ui.activity;

import android.app.Activity;
import android.os.Message;
import android.widget.Toast;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.bean.HomeGoodBean;
import com.mobilepolice.officeMobile.config.Config;
import com.mobilepolice.officeMobile.soap.SoapClient;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.SoapParseUtils;
import com.mobilepolice.officeMobile.soap.SoapUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.LinkedList;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 关于界面
 */
public class AboutActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_about_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    /**
     * 获取子列表的数据
     */
    private void getSupSpinerData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(Config.TIMEOUT);
        SoapParams params = new SoapParams();
        params.put("arg01", 545416022);//区域ID
        // params.put("arg01",545416022);//区域ID
        // params.put("userId",3);//用户id

        soapUtil.call(Config.SERVICE_URL, Config.SERVICE_NAME_SPACE,"", params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        final LinkedList<HomeGoodBean> monitorBeens = SoapParseUtils.getMonitorBeans(bodyIn.toString());
                        if (monitorBeens == null || monitorBeens.size() == 0) {
                            Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        for (int i = 0; i < monitorBeens.size(); i++) {
                            buffer.append("  \n " + monitorBeens.get(i).getContent() + "  \n     ");
                        }
                        //tvResult.setText(buffer);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(mContext, "访问失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Login() {
        Message msg = new Message();

        try {
//声明Service的空间命名，.net默认为 http://tempuri.org/
            //第二个参数是要调用的方法
            SoapObject so = new SoapObject("http://www.w3.org/2003/05/soap-envelope", "qqCheckOnline");

            //设置调用Service需要传入的两个参数，闻说参数名可以不正确，但顺序必需要正确
            so.addProperty("qqCode", "545416022");
            // so.addProperty("password", psw);

            // 设置调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = so;

// 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = true;


// 设置Service所使用的URL
            String url = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";//this.getString(R.string.webservice_domain) + this.getString(R.string.webservice_url_logic);
            HttpTransportSE ht = new HttpTransportSE(url);
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
//接收返回的对象
                SoapObject responseSO = (SoapObject) envelope.getResponse();
                Boolean succeed = Boolean.parseBoolean(responseSO.getProperty("Succeed").toString());
                if (!succeed) {
                    msg.obj = responseSO.getProperty("Message").toString();
                    msg.what = 0;
                } else {
                    msg.obj = responseSO.getProperty("ReturnObject");
                    msg.what = 1;
                }
            }
        } catch (Exception ex) {
            msg.obj = ex.getMessage();
            msg.what = -1;
        } finally {
            // handler.sendMessage(msg);
        }
    }
}
