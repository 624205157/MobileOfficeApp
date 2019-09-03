package com.mobilepolice.officeMobile.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.fragment.DocumentMainFragment;
import com.mobilepolice.officeMobile.ui.fragment.DocumentMainFragment2;
import com.mobilepolice.officeMobile.ui.fragment.DocumentProcessingFragment;
import com.mobilepolice.officeMobile.ui.fragment.DocumentProcessingFragment2;
import com.mobilepolice.officeMobile.ui.fragment.DocumentScheduleFragment;
import com.mobilepolice.officeMobile.ui.fragment.DocumentScheduleFragment2;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 待办工作
 */
public class DocDetails  extends MyActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;
    private List<String> titles = new ArrayList<>();
    String id = "";
    DocPendingBean docPendingBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_pending_work_detailed;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("公文详情");
        id = getIntent().getStringExtra("id");
        Log.e( "initView: ", id);
        findApplyInfo(id);
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

    private void initDatas() {

        titles.add("公文正文");
//        titles.add("公文处理单");

        titles.add("流程状态");
        final ArrayList<MyLazyFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DocumentMainFragment2());
//        fragmentList.add(new DocumentProcessingFragment2());
        fragmentList.add(new DocumentScheduleFragment2());
        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        view_pager.setAdapter(adapter);
        view_pager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        view_pager.setOffscreenPageLimit(titles.size());


        mSlidingTabLayout.setViewPager(view_pager);
        view_pager.setCurrentItem(0);
        //  mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                fragmentList.get(arg0);
                // mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(arg0));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


//    private void findApplyInfo(String requestid){
//        HttpConnectInterface.findApplyInfo(requestid)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::findApplyInfo, this::err, this::onComplete)
//                .isDisposed();
//    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void findApplyInfo(Object o) {

    }

    //公文审批单查看
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
                try{
                    if (JsonParseUtils.jsonToBoolean(result)) {
                        docPendingBean = FastJsonHelper.deserialize(result,
                                DocPendingBean.class);
                        if (docPendingBean != null) {
                            initDatas();
                        } else {
                            toast("读取订单异常");
                            finish();
                        }
                    } else {
                        String msg = JsonParseUtils.jsonToMsg(result);
                        toast(msg);
                        finish();
                    }
                    showProgressDialog(false);
                }catch (Exception ex){
                    ex.printStackTrace();
                    toast(ex.getLocalizedMessage());
                }
            }
        });
    }

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
