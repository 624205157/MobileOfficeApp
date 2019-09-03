package com.mobilepolice.officeMobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.ApplyTaskDetails;
import com.mobilepolice.officeMobile.bean.PendingWorkBean;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.adapter.MyApplyTaskInfoAdapter;
import com.mobilepolice.officeMobile.ui.adapter.PendingWorkAdapter;
import com.mobilepolice.officeMobile.utils.App;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyApplyDocumentActivity extends AppCompatActivity {

    //    @BindView(R.id.mList)
//    ListView mList;
//    private MyApplyTaskInfoAdapter adapter;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_pending_work);
        ButterKnife.bind(this);
        findViewById(R.id.toolbar).setVisibility(View.GONE);
        initView();
    }

    private void onCompleted() {

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void result(ApplyTaskDetails applyTaskDetails) {
        if (applyTaskDetails.isSuccess())
            mAdapter.setNewData(applyTaskDetails.getObj());
    }

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    MyApplyTaskInfoAdapter mAdapter;


    protected void initView() {
        initAdapter();
        findApplyTaskInfo();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.setNewData(null);
        findApplyTaskInfo();
    }


    private void initAdapter() {

        mAdapter = new MyApplyTaskInfoAdapter();
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ApplyTaskDetails.ObjBean bean = mAdapter.getData().get(position);
                MyApplication.getInstance().path_type = 0;
                MyApplication.getInstance().path_record = "";
                MyApplication.getInstance().path_image = "";
                MyApplication.getInstance().path_pdf = "";
                Intent intent = new Intent(MyApplyDocumentActivity.this, DocDetails.class);
                String ids = bean.getRequestid();
                intent.putExtra("id", ids);
                startActivity(intent);
            }
        });
    }

    public void startActivity() {
        startActivity(new Intent(this, DocumentPendingWorkDetailedActivity.class));
        finish();
    }

    //待办工作
    private void findApplyTaskInfo() {
        HttpConnectInterface.findApplyTaskInfo(App.userCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::result, this::onError, this::onCompleted)
                .isDisposed();
    }

}
