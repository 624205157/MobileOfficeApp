package com.mobilepolice.officeMobile.webservice;

import com.mobilepolice.officeMobile.utils.MyCookie;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017-03-27.
 */

public class HttpWebServer {
    String token;

    public HttpWebServer() {
        super();
        token = MyCookie.getString("token", "");
    }

    // 检测更新
    public void checkUpdate(MyCallBack<String> requestCallBack) {

        String url = "http://www.freetk.cn:8789/download/ydbg.json";
        Map<String, Object> map = new HashMap<>();
//        map.put("appkey", "10003");
//        map.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
//        map.put("format", "json");
//        map.put("idcard", "110101199001011114");
        XUtil.Get(url, map, requestCallBack);
    }

}