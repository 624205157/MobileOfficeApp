package com.mobilepolice.officeMobile.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.ApplyInfoBean;
import com.mobilepolice.officeMobile.bean.ApproveDataInfo;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.ui.adapter.WorkFinishAdapter;
import com.mobilepolice.officeMobile.utils.App;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 待办工作列表
 */
public class DocumentFinishedWorkActivity extends MyActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    WorkFinishAdapter mAdapter;

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

        findApproveTaskInfo(App.userCode);
//        findApproveTaskInfo();
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
        findApproveTaskInfo(MyApplication.getInstance().personPhone);
    }

    @Override
    protected void initData() {


    }

    private void initAdapter() {
        mAdapter = new WorkFinishAdapter();
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ApproveDataInfo.ObjBean bean = mAdapter.getData().get(position);
                MyApplication.getInstance().path_type = 0;
                MyApplication.getInstance().path_record = "";
                MyApplication.getInstance().path_image = "";
                MyApplication.getInstance().path_pdf = "";
                Intent intent = new Intent(getActivity(), DocumentWorkDetailedActivity.class);
                String ids = bean.getRequestid();
                Log.e( "onItemClick: ", ids);
                if (approveDataDetailCache.get(ids) == null) return;
                intent.putExtra("id", ids);
                intent.putExtra("data", approveDataDetailCache.get(ids).getObj());
                intent.putParcelableArrayListExtra("approveInfo", approveDataDetailCache.get(ids).getAttributes().getApproveListInfo());
                startActivity(intent);
            }
        });
    }

    public void startActivity() {
        startActivity(DocumentPendingWorkDetailedActivity.class);
        finish();
    }

    //待办工作
    private void findApproveTaskInfo(String applyPerson) {
        showProgressDialog(true);
        HttpConnectInterface.findApproveData(applyPerson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::approveTaskList, this::err, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        showProgressDialog(false);
    }


    private void approveTaskList(ApproveDataInfo approveDataInfo) {
        showProgressDialog(false);
        if (approveDataInfo.isSuccess()) {
            mAdapter.setNewData(approveDataInfo.getObj());
            for (int i = 0; i < approveDataInfo.getObj().size(); i++) {
                approveDataDetail(approveDataInfo.getObj().get(i).getRequestid());
            }
        }
    }

    private void approveDataDetail(String requestid) {
        HttpConnectInterface.findApplyInfo(requestid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((o->this.applyInfo(o,requestid)), this::err, this::onComplete)
                .isDisposed();
    }

    private void applyInfo(ApplyInfoBean o, String requestid) {
        if (o.isSuccess() && o.getAttributes() != null) {
            approveDataDetailCache.put(o.getObj().getId(), o);
            if (o.getAttributes().getApproveListInfo().size() == 0) {
                mAdapter.remove(o.getObj().getId());
            }
        }else {
            Log.e( "applyInfo: ",requestid );
        }
    }


    private HashMap<String, ApplyInfoBean> approveDataDetailCache = new HashMap<>();

}