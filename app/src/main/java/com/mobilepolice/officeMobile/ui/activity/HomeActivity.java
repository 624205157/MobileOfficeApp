package com.mobilepolice.officeMobile.ui.activity;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.HomeGoodBean;
import com.mobilepolice.officeMobile.config.Config;
import com.mobilepolice.officeMobile.soap.SoapClient;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.SoapParseUtils;
import com.mobilepolice.officeMobile.soap.SoapUtil;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.utils.ActivityStackManager;
import com.mobilepolice.officeMobile.utils.App;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.utils.MyCookie;
import com.mobilepolice.officeMobile.utils.OnClickUtils;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.ui.adapter.HomeFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 主页界面
 */
public class HomeActivity extends MyActivity implements
        ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;

    private HomeFragmentAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mViewPager.addOnPageChangeListener(this);

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        // test();
//        Player player = new Player();
//        player.playUrl("http://sc1.111ttt.cn:8282/2018/1/03m/13/396131229550.m4a?tflag=1519095601&pin=6cd414115fdb9a950d827487b16b5f97#.mp3");

    }

    @Override
    protected void initData() {
        updateSystem();
        login();
        showdata();

//        //创建JSONObject
//        JSONObject jsonObject = new JSONObject();
//        //键值对赋值
//        try {
//            jsonObject.put("applyPerson", "17600194545");
//            //        jsonObject.put("name", student.getName());
////        jsonObject.put("clazz", student.getClazz());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //转化成json字符串
//        String json = jsonObject.toString();
//        SoapParams params = new SoapParams();
//        params.put("json", json);
//        WebServiceUtils.getPersonDeptName("findApplyPersonDeptName", params, new WebServiceUtils.CallBack() {
//            @Override
//            public void result(String result) {
//                if (JsonParseUtils.jsonToBoolean(result)) {
//                    String obj = JSON.parseObject(result).getString(
//                            "obj");
//                    String departmentId = JSON.parseObject(obj).getString(
//                            "departmentId");
//                    String departmentName = JSON.parseObject(obj).getString(
//                            "departmentName");
//                    MyApplication.getInstance().personDeptid = departmentId;
//                    MyApplication.getInstance().personDeptName = departmentName;
//                }
//            }
//        });
//        WebServiceUtils.getPersonDeptName("findApprovePerson", params, new WebServiceUtils.CallBack() {
//            @Override
//            public void result(String result) {
//                String temp = result;
//                if(JsonParseUtils.jsonToBoolean(result))
//                {
//                    toast(temp);
//                }
//            }
//        });
    }

    private void showdata() {
        mPagerAdapter = new HomeFragmentAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());

    }

    private void login() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", App.userCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApplyPersonDeptName", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    String departmentId = JSON.parseObject(obj).getString(
                            "departmentId");
                    String departmentName = JSON.parseObject(obj).getString(
                            "departmentName");
                    MyApplication.getInstance().personDeptid = departmentId;
                    MyApplication.getInstance().personDeptName = departmentName;
                    MyApplication.getInstance().personPhone =App.userCode;
                    showdata();
                } else {
                    MyApplication.getInstance().personDeptid = "3";
                    MyApplication.getInstance().personDeptName = "科技通信处";
                    toast("读取部门失败,请重新登录");
                    showdata();
//                    startActivity(LoginNewActivity.class);
//                    finish();
                }
            }
        });
    }


    private void test() {

        JSONArray array = new JSONArray();
        JSONObject object = null;
        JSONObject obj = new JSONObject();
        try {
            for (int i = 0; i < 3; i++) {
                object = new JSONObject();
                object.put("item1", "value1");
                object.put("age", 12);
                object.put("name", "tom");
                array.put(object);
            }
            obj.put("name", array);
            obj.put("id", "历史");
            obj.put("wgl", "是否");
            Log.i("数据", "----->  " + obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        List<ApprovePersonBean> list = new ArrayList<>();
//        list.add(new ApprovePersonBean("12","张三"));
//        list.add(new ApprovePersonBean("14","张四"));
//        list.add(new ApprovePersonBean("14","张五"));
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        JSONObject tmpObj = null;
//        int count = list.size();
//        for (int i = 0; i < count; i++) {
//            try {
//                tmpObj = new JSONObject();
//                tmpObj.put("name", list.get(i).getName());
//                tmpObj.put("id", list.get(i).getId());
//                jsonArray.put(tmpObj);
//                tmpObj = null;
//                String personInfos = jsonArray.toString(); // 将JSONArray转换得到String
//                jsonObject.put("personInfos", personInfos);   // 获得JSONObject的String
//                String str = gson.toJson(jsonObject);
//                System.out.println(str);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
//                case 0:
//                    mBottomNavigationView.setSelectedItemId(R.id.home_message);
//                    break;
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.home_workbench);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_index);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_contacts);
                break;
            case 3:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//                case R.id.home_message:
//                    mViewPager.setCurrentItem(0);
//                    return true;
            case R.id.home_workbench:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.home_index:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.home_contacts:
                mViewPager.setCurrentItem(2);
                return true;
            case R.id.home_me:
                mViewPager.setCurrentItem(3);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (OnClickUtils.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            getWindow().getDecorView().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    private String checkRegister() {

        // String wsdl = "http://222.168.10.43:885/YBMessageWeb/LoginWebServicePort?wsdl";
        String wsdl = "http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";//"http://192.168.10.175:8080/YBMessageWeb/LoginWebServicePort?wsdl";
        String namespace = "http://services.webservice.mobilework.com/";
        // 调用方法的名称
        String webmethod = "findApplyPersonDeptName";
        // 创建soapObject对象
        SoapObject soapObject = new SoapObject(namespace, webmethod);
        // 设置参数
        soapObject.addProperty("json", "{\"applyPerson\":\"17600194545\"}");
        // 创建SoapSerializationEnvelope对象，并设置WebService版本号
        SoapSerializationEnvelope serializationEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

// 设置是否调用的是dotNet开发的WebService
        serializationEnvelope.dotNet = false;
        // 设置serializationEnvelope对象的badyOut属性
        serializationEnvelope.bodyOut = soapObject;
        // 创建HttpTransportSE对象,并且确定wsdl网络地址
        HttpTransportSE httpTransportSE = new HttpTransportSE(wsdl);
        try {
            // httpTransportSE调用Call方法
            httpTransportSE.call(null, serializationEnvelope);
            // 获取返回的结果对象
            if (serializationEnvelope.getResponse() != null) {
                Object obj = serializationEnvelope.getResponse();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(obj.toString());
                    if (jsonObject.get("state").equals("success")) {
//                        telecode = jsonObject.getString("msg");
//                        response = setPushDevice();
                        return "success";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return obj.toString();

            } else {
                return "result-null";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 获取子列表的数据
     */
    private void getSupSpinerData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(Config.TIMEOUT);
        SoapParams params = new SoapParams();
        params.put("applyPerson", "17600194545");//区域ID
        // params.put("arg01",545416022);//区域ID
        // params.put("userId",3);//用户id

        soapUtil.call(Config.SERVICE_URL, Config.SERVICE_NAME_SPACE, "", params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {

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
                /**
                 ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override public void run() {
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
                 ***/
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
            SoapObject so = new SoapObject("http://services.webservice.mobilework.com/", "findApplyPersonDeptName");

            //设置调用Service需要传入的两个参数，闻说参数名可以不正确，但顺序必需要正确
            so.addProperty("applyPerson", "17600194545");
            // so.addProperty("password", psw);

            // 设置调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = so;

// 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = false;


// 设置Service所使用的URL
            String url = "http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";//this.getString(R.string.webservice_domain) + this.getString(R.string.webservice_url_logic);
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