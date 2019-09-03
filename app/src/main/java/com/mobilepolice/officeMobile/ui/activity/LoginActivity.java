package com.mobilepolice.officeMobile.ui.activity;

import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.hjq.widget.ClearEditText;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.PendingWorkBean;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.utils.EditTextInputHelper;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.utils.MyCookie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public class LoginActivity extends MyActivity
        implements View.OnClickListener {

    @BindView(R.id.et_login_phone)
    ClearEditText mPhoneView;
//    @BindView(R.id.et_login_password)
//    EditText mPasswordView;
    @BindView(R.id.btn_login_commit)
    Button mCommitView;

    private EditTextInputHelper mEditTextInputHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        mCommitView.setOnClickListener(this);
       // mEditTextInputHelper = new EditTextInputHelper(mCommitView, false);
//        mEditTextInputHelper.addViews(mPhoneView, mPasswordView);
    }

    @Override
    protected void initData() {
        findApplyInfo("781D623188C04A0989CEF4BA1EDFB3DE");
    }

//    @Override
//    public void onRightClick(View v) {
//        // 跳转到注册界面
//        startActivity(RegisterActivity.class);
//    }

    @Override
    protected void onDestroy() {
       // mEditTextInputHelper.removeViews();
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        //不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    private void login(String phone) {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        WebServiceUtils.getPersonDeptName("findApplyPersonDeptName", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    String departmentId = JSON.parseObject(obj).getString(
                            "departmentId");
                    String departmentName = JSON.parseObject(obj).getString(
                            "departmentName");
                    MyApplication.getInstance().personDeptid = departmentId;
                    MyApplication.getInstance().personDeptName = departmentName;
                    MyCookie.putString("mobile", phone);
                    MyApplication.getInstance().personPhone = phone;
                    startActivity(HomeActivity.class);
                    finish();
                }
            }
        });
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (v == mCommitView) {
            if (mPhoneView.getText().toString().length() != 11) {
                ToastUtils.show(getResources().getString(R.string.phone_input_error));
                return;
            }
            login(mPhoneView.getText().toString());
        }
    }

    private void findApplyInfo(String id) {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApplyInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    List<PendingWorkBean> list = FastJsonHelper.deserializeList(json,
                            PendingWorkBean.class);
                    if (list != null && list.size() > 0) {

                    }
                } else {
                    toast("读取数据失败，请重新尝试");

                }
            }
        });
    }
}