package com.mobilepolice.officeMobile.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.PendingWorkBean;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.adapter.PendingWorkAdapter;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 待办工作列表
 */
public class DocumentPendingWorkActivity extends MyActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    PendingWorkAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_pending_work;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        Bundle bundle = this.getIntent().getExtras();
        String title = bundle.getString("title");
        setTitle(title);
        int type = bundle.getInt("type", -1);
        initAdapter();
        findApproveTaskInfo();
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.setNewData(null);
        findApproveTaskInfo();
    }

    @Override
    protected void initData() {


    }

    private void initAdapter() {

        mAdapter = new PendingWorkAdapter();
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PendingWorkBean bean = mAdapter.getData().get(position);
                MyApplication.getInstance().path_type = 0;
                MyApplication.getInstance().path_record = "";
                MyApplication.getInstance().path_image = "";
                MyApplication.getInstance().path_pdf = "";
                Intent intent = new Intent(getActivity(), DocumentPendingWorkDetailedActivity.class);
                String ids = bean.getRequestid();
                intent.putExtra("id", ids);
                startActivity(intent);
            }
        });
    }

    public void startActivity() {
        startActivity(DocumentPendingWorkDetailedActivity.class);
        finish();
    }

    //待办工作
    private void findApproveTaskInfo() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("approvePerson", MyApplication.getInstance().personPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);

        /*"findApproveTaskInfo"*/
        WebServiceUtils.getPersonDeptName("findApproveTaskInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    List<PendingWorkBean> list = FastJsonHelper.deserializeList(obj,
                            PendingWorkBean.class);
                    if (list != null && list.size() > 0) {
                        mAdapter.setNewData(list);
                    }
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
//                    finish();
                }
            }
        });
    }

}
