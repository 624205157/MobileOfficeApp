package com.mobilepolice.officeMobile.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.bean.DocProcessBean;
import com.mobilepolice.officeMobile.bean.ScheduleBean;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.activity.DocumentPendingWorkDetailedActivity;
import com.mobilepolice.officeMobile.ui.adapter.ScheduleWorkStatusAdapter;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.widget.scrollable.ScrollableHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 公文流程
 */
public class DocumentScheduleFragment extends MyLazyFragment implements ScrollableHelper.ScrollableContainer {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    String requestid = "";
    String approveType = "";
    DocPendingBean docPendingBean;
    ScheduleWorkStatusAdapter mAdapter;

    public static DocumentScheduleFragment newInstance() {
        return new DocumentScheduleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_document_schedule;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        initAdapter();
        DocumentPendingWorkDetailedActivity activity = (DocumentPendingWorkDetailedActivity) getFragmentActivity();
        String id = activity.getId();
        docPendingBean = activity.getDocPendingBean();
        if (!TextUtils.isEmpty(id)) {
            findApproveFlowInfo(id);
        }
    }

    private void findApproveFlowInfo(String id) {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("requestid", id);
            //公文模式（1=审批，2=会签）
            jsonObject.put("approveType", docPendingBean.getObj().getSchema());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApproveFlowInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
//                    String obj = JSON.parseObject(result).getString(
//                            "obj");
//                    String attributes = JSON.parseObject(result).getString(
//                            "attributes");
                    DocProcessBean docProcessBean = FastJsonHelper.deserialize(result,
                            DocProcessBean.class);
                    boolean flag_temp = false;
                    if (docPendingBean.getObj().getSchema().equals("1")) {
                        flag_temp = true;
                    }

                    List<ScheduleBean> scheduleBeanList = new ArrayList<ScheduleBean>();
                    if (docProcessBean.getObj() != null && docProcessBean.getObj().size() > 0) {
                        Collections.reverse(docProcessBean.getObj());
                        for (int i = 0; i < docProcessBean.getObj().size(); i++) {
                            if (i == 0) {
                                //0未审批人1发起人2当前会签人3已会签人
                                scheduleBeanList.add(new ScheduleBean(1, docProcessBean.getObj().get(i).getApprovePerson(), docProcessBean.getObj().get(i).getApprovePersonName(), docProcessBean.getObj().get(i).getCreateDate(), flag_temp));
                            } else {
                                scheduleBeanList.add(new ScheduleBean(3, docProcessBean.getObj().get(i).getApprovePerson(), docProcessBean.getObj().get(i).getApprovePersonName(), docProcessBean.getObj().get(i).getCreateDate(), flag_temp));
                            }
                        }
                    }

                    if (docProcessBean.getAttributes() != null) {
                        if (docProcessBean.getAttributes().getNowApproveInfo() != null) {
                            scheduleBeanList.add(new ScheduleBean(2, docProcessBean.getAttributes().getNowApproveInfo().getApprovePerson(), docProcessBean.getAttributes().getNowApproveInfo().getApprovePersonName(), "", flag_temp));
                        }

                        if (docPendingBean.getObj().getSchema().equals("2")) {
                            if (docProcessBean.getAttributes().getAllApproveInfo() != null && docProcessBean.getAttributes().getAllApproveInfo().size() > 0) {
                                for (int i = 0; i < docProcessBean.getAttributes().getAllApproveInfo().size(); i++) {
                                    boolean flag = false;
                                    for (int j = 0; j < scheduleBeanList.size(); j++) {
                                        if (docProcessBean.getAttributes().getAllApproveInfo().get(i).getApprovePerson().equals(scheduleBeanList.get(j).getApprovePerson())) {
                                            flag = true;
                                        }
                                    }
                                    if (!flag) {
                                        if (MyApplication.getInstance().personPhone.equals(docProcessBean.getAttributes().getAllApproveInfo().get(i).getApprovePerson())) {
                                            scheduleBeanList.add(new ScheduleBean(2, docProcessBean.getAttributes().getAllApproveInfo().get(i).getApprovePerson(), docProcessBean.getAttributes().getAllApproveInfo().get(i).getApprovePersonName(), "", flag_temp));

                                        } else {
                                            scheduleBeanList.add(new ScheduleBean(0, docProcessBean.getAttributes().getAllApproveInfo().get(i).getApprovePerson(), docProcessBean.getAttributes().getAllApproveInfo().get(i).getApprovePersonName(), "", flag_temp));
                                        }
                                    }

                                }
                            }
                        }
                    }
                    mAdapter.setNewData(scheduleBeanList);

                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                }
            }
        });
    }

    @Override
    public View getScrollableView() {
        return null;
    }

    private void initAdapter() {

        mAdapter = new ScheduleWorkStatusAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

}