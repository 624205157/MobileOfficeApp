package com.mobilepolice.officeMobile.http;

import android.util.Base64;
import android.util.Log;

import com.fish.timeline.TaskInfo;
import com.google.gson.Gson;
import com.mobilepolice.officeMobile.bean.ApplyInfoBean;
import com.mobilepolice.officeMobile.bean.ApplyTaskDetails;
import com.mobilepolice.officeMobile.bean.ApproveDataInfo;
import com.mobilepolice.officeMobile.bean.ContactsData;
import com.mobilepolice.officeMobile.bean.DocumentInfo;
import com.mobilepolice.officeMobile.bean.FindDepartmentAll;
import com.mobilepolice.officeMobile.bean.InsertTaskInfo;
import com.mobilepolice.officeMobile.bean.LoginBean;
import com.mobilepolice.officeMobile.bean.LoopImageNewsBean;
import com.mobilepolice.officeMobile.bean.NewsDetailBean;
import com.mobilepolice.officeMobile.bean.NewsListBean;
import com.mobilepolice.officeMobile.bean.NoticeListBean;
import com.mobilepolice.officeMobile.bean.QueryTaskInfo;
import com.mobilepolice.officeMobile.bean.SimpleBean;
import com.mobilepolice.officeMobile.config.Config;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HttpConnectInterface {


    static Observable<ApproveDataInfo> findApproveData(String applyPerson) {
        return Observable.create(new ObservableOnSubscribe<ApproveDataInfo>() {
            @Override
            public void subscribe(ObservableEmitter<ApproveDataInfo> emitter) throws Exception {
                MediaType mediaType = MediaType.parse("application/xml");
                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:findApplyTaskInfo>\r\n         <json>{\"applyPerson\":\"" + applyPerson + "\"}</json>\r\n      </ser:findApplyTaskInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
                Request request = new Request.Builder()
                        .url(Config.SERVICE_URL)
                        .post(body)
                        .addHeader("soapaction", "\"\"")
                        .addHeader("content-type", "application/xml")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "ec147cf5-f7a8-3baa-6386-172e5a9f5339")
                        .build();
                try {
                    Response response = HttpTools.okHttpClient().newCall(request).execute();
                    String content = response.body().string();
                    content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
                    content=content.replaceAll("10.106.12.104:8789","192.168.20.228:7121");
                    emitter.onNext(new Gson().fromJson(content, ApproveDataInfo.class));
                } catch (IOException e) {
                    emitter.onError(e);
                }
            }
        });

    }

    static Observable<byte[]> getImage(String url) {
        return Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("soapaction", "\"\"")
                        .addHeader("content-type", "text/xml")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "59f3676c-e45f-ae74-4117-58bdabbf26d1")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    emitter.onNext(response.body().bytes());
                    emitter.onComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }


    static Observable<String> loadFileBase64(String path) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                File file = new File(path);
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len = 0;
                byte[] buff = new byte[512];
                while ((len = inputStream.read(buff)) > 0) {
                    byteArrayOutputStream.write(buff, 0, len);
                }
                inputStream.close();
                byte[] encode = Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                emitter.onNext(new String(encode).replaceAll("\n", "").replaceAll("\r", ""));
                emitter.onComplete();
            }
        });
    }

            String base = "http://192.168.20.228:7121/mobileworkws/";
//            String base = "http://ccsyc.cn:8789/mobileworkws/";
//    String base = "http://www.freetk.cn:8789/mobileworkws/";

    static Observable<ApplyInfoBean> findApplyInfo(String requestid) {
        return Observable.create(new ObservableOnSubscribe<ApplyInfoBean>() {
            @Override
            public void subscribe(ObservableEmitter<ApplyInfoBean> emitter) throws Exception {

                MediaType mediaType = MediaType.parse("application/xml");
                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:findApplyInfo>\r\n         <json>{\"id\":\"" + requestid + "\"}</json>\r\n      </ser:findApplyInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
                Request request = new Request.Builder()
                        .url(Config.SERVICE_URL)
                        .post(body)
                        .addHeader("soapaction", "\"\"")
                        .addHeader("content-type", "application/xml")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "cd5f2b38-3898-4846-daf5-d5903cae2d61")
                        .build();

                Response response = HttpTools.okHttpClient().newCall(request).execute();
                String content = response.body().string();
                content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);

                emitter.onNext(new Gson().fromJson(content, ApplyInfoBean.class));
            }
        });

    }

    static Observable<DocumentInfo> findDocumentInfo(String content) {
        Log.e("findApplyInfo: ", content);

        return Observable.create(emitter -> {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
                    .url(base + "oa/findDocumentInfo")
                    .post(body)
                    .addHeader("cache-control", "no-cache")
                    .build();

            try {
                Response response = HttpTools.okHttpClient().newCall(request).execute();
                emitter.onNext(new Gson().fromJson(response.body().string().replaceAll("10.106.12.104:8789","192.168.20.228:7121"), DocumentInfo.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static Observable<ApplyTaskDetails> findApplyTaskInfo(String userCode) {
        return Observable.create(new ObservableOnSubscribe<ApplyTaskDetails>() {
            @Override
            public void subscribe(ObservableEmitter<ApplyTaskDetails> emitter) throws Exception {
                MediaType mediaType = MediaType.parse("application/xml");
                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:findApplyTaskInfo>\r\n         <json>{\"applyPerson\":\""+userCode+"\"}</json>\r\n      </ser:findApplyTaskInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
                Request request = new Request.Builder()
                        .url(Config.SERVICE_URL)
                        .post(body)
                        .addHeader("soapaction", "\"\"")
                        .addHeader("content-type", "application/xml")
                        .addHeader("cache-control", "no-cache")
                        .build();

                Response response = HttpTools.okHttpClient().newCall(request).execute();
                String content = response.body().string();
                content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
                content=content.replaceAll("10.106.12.104:8789","192.168.20.228:7121");
                emitter.onNext(new Gson().fromJson(content, ApplyTaskDetails.class));

            }
        });
    }

//    static Observable<SimpleBean> uploadBase64Pdp(String base64, String reqId) {
//        Log.e("uploadBase64Pdp: ", reqId);
//        Log.e("uploadBase64Pdp: ", base64);
//        return Observable.create(new ObservableOnSubscribe<SimpleBean>() {
//            @Override
//            public void subscribe(ObservableEmitter<SimpleBean> emitter) throws Exception {
//                MediaType mediaType = MediaType.parse("text/xml");
//                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:upWorkFlowApplyInfo>\r\n         <json>{\"requestid\":\"" + reqId + "\",\"pdfImgBase64\":\"" + base64 + "\"}</json>\r\n      </ser:upWorkFlowApplyInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
//                Request request = new Request.Builder()
//                        .url(ws_base)
//                        .post(body)
//                        .addHeader("soapaction", "\"\"")
//                        .addHeader("content-type", "text/xml")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "a8cabfd7-5aea-9f90-3b36-c1629dcb5e41")
//                        .build();
//
//                try {
//                    Response response = HttpTools.okHttpClient().newCall(request).execute();
//                    String content = response.body().string();
//                    content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
//                    emitter.onNext(new Gson().fromJson(content, SimpleBean.class));
//                } catch (IOException e) {
//                    emitter.onError(e);
//                }
//            }
//        });
//
//    }

    @FormUrlEncoded
    @POST("http://192.168.20.230:8081/uas/sso/singlesignoncontrol/checkbill.do")
    Observable<LoginBean> login(@Field("strBill") String token);

    @POST(base + "data/findDepartmentAll")
    Observable<ArrayList<FindDepartmentAll>> findDepartmentAll();

    @FormUrlEncoded
    @POST(base + "data/findTxlByDepartment")
    Observable<ArrayList<ContactsData>> findTxlByDepartment(@Field("department") String department, @Field("lineStart") String lineStart, @Field("lineEnd") String lineEnd);

    @FormUrlEncoded
    @POST(base + "data/findTxlInfoById")
    Observable<Object> findTxlInfoById(@Field("id") String id);

    @FormUrlEncoded
    @POST(base + "data/findTzggInfo")
    Observable<ArrayList<NoticeListBean>> findTzggInfo(@Field("lineStart") String lineStart, @Field("lineEnd") String lineEnd, @Field("userid") String userid);

    @FormUrlEncoded
    @POST(base + "data/findTzggInfoDetails")
    Observable<NewsDetailBean> findTzggInfoDetails(@Field("contentId") String contentId);

    @FormUrlEncoded
    @POST(base + "data/findTpxwInfo")
    Observable<ArrayList<NewsListBean>> findTpxwInfo(@Field("lineStart") String lineStart, @Field("lineEnd") String lineEnd);

    @FormUrlEncoded
    @POST(base + "data/findTpxwInfoDetails")
    Observable<NewsDetailBean> findTpxwInfoDetails(@Field("contentId") String contentId);

    @POST(base + "data/findTpxwImageInfo")
    Observable<List<LoopImageNewsBean>> findTpxwImageInfo();

    @FormUrlEncoded
    @POST(base + "data/saveInformationFlagInfo")
    Observable<Object> saveInformationFlagInfo(@Field("contentId") String contentId, @Field("userid") String userid);


//    static Observable<PendingApprove> requirePendingApproveTask(String approveId) {
//
//        return Observable.create(new ObservableOnSubscribe<PendingApprove>() {
//            @Override
//            public void subscribe(ObservableEmitter<PendingApprove> emitter) throws Exception {
//                MediaType mediaType = MediaType.parse("text/xml");
//                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:findApproveTaskInfo>\r\n         <json>{\"approvePerson\":\"" + approveId + "\"}</json>\r\n      </ser:findApproveTaskInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
//                Request request = new Request.Builder()
//                        .url(ws_base)
//                        .post(body)
//                        .addHeader("soapaction", "\"\"")
//                        .addHeader("content-type", "text/xml")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "f5512a33-10ab-d278-02e3-4c7594d13ee9")
//                        .build();
//
//                try {
//                    Response response = HttpTools.okHttpClient().newCall(request).execute();
//                    String content = response.body().string();
//                    content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
//                    emitter.onNext(new Gson().fromJson(content, PendingApprove.class));
//                    emitter.onComplete();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    emitter.onError(e);
//                }
//            }
//        });
//    }


//    static Observable<SimpleBean> approve(String requestid, String approveNodeId, String approvePerson, String applyPerson, String approveType, String approveFlag, String approveOpinion) {
//        return Observable.create(new ObservableOnSubscribe<SimpleBean>() {
//            @Override
//            public void subscribe(ObservableEmitter<SimpleBean> emitter) throws Exception {
//                MediaType mediaType = MediaType.parse("text/xml");
//                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n" +
//                        "   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:approveWorkFlowInfo>\r\n         <json>" +
//                        "{\r\n" +
//                        "                \"requestid\": \"" + requestid + "\",\r\n" +
//                        "                \"approveNodeId\": \"" + approveNodeId + "\",\r\n" +
//                        "                \"approvePerson\": \"" + approvePerson + "\",\r\n" +
//                        "                \"applyPerson\": \"" + applyPerson + "\",\r\n" +
//                        "                \"approveType\": \"" + approveType + "\",\r\n" +
//                        "                \"approveFlag\": \"" + approveFlag + "\",\r\n" +
//                        "                \"approveOpinion\": \"" + approveOpinion + "\"\r\n" +
//                        "        }</json>\r\n      </ser:approveWorkFlowInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
//                Request request = new Request.Builder()
//                        .url(ws_base)
//                        .post(body)
//                        .addHeader("soapaction", "\"\"")
//                        .addHeader("content-type", "text/xml")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "7f9552d7-0395-c024-bd9b-d9c49ee226ad")
//                        .build();
//
//                try {
//                    Response response = HttpTools.okHttpClient().newCall(request).execute();
//                    String content = response.body().string();
//                    content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
//                    emitter.onNext(new Gson().fromJson(content, SimpleBean.class));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//    }


    //    static Observable<SimpleBean> rejectedWorkFlow(String requestid, String approvePerson, String applyPerson, String approveOpinion, String approveOpinionBase64) {
//        return Observable.create(new ObservableOnSubscribe<SimpleBean>() {
//            @Override
//            public void subscribe(ObservableEmitter<SimpleBean> emitter) throws Exception {
//                MediaType mediaType = MediaType.parse("application/xml");
//                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
//                        "xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   " +
//                        "<soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:rejectedWorkFlow>\r\n   " +
//                        "      <json>{\"requestid\":\"" + requestid + "\",\"approvePerson\":\"" + approvePerson + "\"," +
//                        "\"applyPerson\":\"" + applyPerson + "\",\"approveOpinion\": \"" + approveOpinion + "\",   " +
//                        " \"approveOpinionBase64\": \"" + approveOpinionBase64 + "\"}</json>\r\n    " +
//                        "  </ser:rejectedWorkFlow>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
//                Request request = new Request.Builder()
//                        .url(ws_base)
//                        .post(body)
//                        .addHeader("soapaction", "\"\"")
//                        .addHeader("content-type", "application/xml")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "396509eb-5b88-b961-b685-7c37372b2116")
//                        .build();
//
//                try {
//                    Response response = HttpTools.okHttpClient().newCall(request).execute();
//                    String content = response.body().string();
//                    content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
//                    emitter.onNext(new Gson().fromJson(content, SimpleBean.class));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    emitter.onError(e);
//                }
//            }
//        });
//    }
//
    @POST(base + "schedule/findRcxxInfo")
    @Headers("Content-Type:application/json")
    Observable<TaskInfo> readTimeLineTask(@Body QueryTaskInfo string);


    @Headers("Content-Type:application/json")
    @POST(base + "schedule/save")
    Observable<SimpleBean> saveTimeLineTask(@Body InsertTaskInfo taskInfo);

//    static Observable<ApproveList> approveList(String approvePerson) {
//        return Observable.create(new ObservableOnSubscribe<ApproveList>() {
//            @Override
//            public void subscribe(ObservableEmitter<ApproveList> emitter) throws Exception {
//
//                MediaType mediaType = MediaType.parse("text/xml");
//                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.mobilework.com/\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <ser:findAlreadyApproveInfo>\r\n         <json>{\"approvePerson\":\"" + approvePerson + "\"}</json>\r\n      </ser:findAlreadyApproveInfo>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
//                Request request = new Request.Builder()
//                        .url(ws_base)
//                        .post(body)
//                        .addHeader("soapaction", "\"\"")
//                        .addHeader("content-type", "text/xml")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "acfa9934-a8d5-c608-3e85-f4f7b750feed")
//                        .build();
//
//                Response response = HttpTools.okHttpClient().newCall(request).execute();
//                String content = response.body().string();
//                content = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);
//                emitter.onNext(new Gson().fromJson(content, ApproveList.class));
//            }
//        });
//    }

    ///E5F2DDAEA0444C048B7FAE592A3BEACF
//    String ws_base = "http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl=";
//    String ws_base = "http://192.168.20.228:7121/mobileworkws/services/MobileWorkws?wsdl=";

//    String base = "http://192.168.20.228:7121/mobileworkws/";
//    String base = "http://www.freetk.cn:8789/mobileworkws/";
}
