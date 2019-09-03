package com.mobilepolice.officeMobile.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.ApplyInfoBean;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.ui.adapter.FinishWorkAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DocumentWorkDetailedActivity extends MyActivity {

    @BindView(R.id.approveList)
    RecyclerView approveList;
    private List<String> titles = new ArrayList<>();
    String id = "";
    DocPendingBean docPendingBean;
    private FinishWorkAdapter adapter ;
    private ArrayList<ApplyInfoBean.AttributesBean.ApproveListInfoBean> attrs;
    private ApplyInfoBean.ObjBean data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doc_work_finish_details;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("公文审批");

        adapter=new FinishWorkAdapter();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        id = getIntent().getStringExtra("id");
        getIntentData();
        restoreData();
        approveList.setLayoutManager(new LinearLayoutManager(this));
        approveList.setAdapter(adapter);
        Log.e("initView: ", id);
//        findApplyInfo(id);
    }

    private void restoreData() {
        adapter.setNewData(attrs);
    }


    private void getIntentData() {
        attrs = getIntent().getParcelableArrayListExtra("approveInfo");
        data = getIntent().getParcelableExtra("data");
    }

    public String getId() {
        return id;
    }

    public DocPendingBean getDocPendingBean() {
        return docPendingBean;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void initData() {

    }

    private void findApplyInfo(String requestid) {
        HttpConnectInterface.findApplyInfo(requestid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::findApplyInfo, this::err, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void findApplyInfo(Object o) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //公文审批单查看
//    private void findApplyInfo(String id) {
//        //创建JSONObject
//        JSONObject jsonObject = new JSONObject();
//        //键值对赋值
//        try {
//            //17600194545
//            jsonObject.put("id", id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //转化成json字符串
//        String json = jsonObject.toString();
//        SoapParams params = new SoapParams();
//        params.put("json", json);
//        showProgressDialog(true);
//        WebServiceUtils.getPersonDeptName("findApplyInfo", params, new WebServiceUtils.CallBack() {
//            @Override
//            public void result(String result) {
//                try{
//                if (JsonParseUtils.jsonToBoolean(result)) {
//                    docPendingBean = FastJsonHelper.deserialize(result,
//                            DocPendingBean.class);
//                    if (docPendingBean != null) {
//                        initDatas();
//                    } else {
//                        toast("读取订单异常");
//                        finish();
//                    }
//                } else {
//                    String msg = JsonParseUtils.jsonToMsg(result);
//                    toast(msg);
//                    finish();
//                }
//                showProgressDialog(false);
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                    toast(ex.getLocalizedMessage());
//                }
//            }
//        });
//    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<MyLazyFragment> fragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<MyLazyFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
