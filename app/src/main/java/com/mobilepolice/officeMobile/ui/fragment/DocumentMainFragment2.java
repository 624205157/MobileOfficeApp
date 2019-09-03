package com.mobilepolice.officeMobile.ui.fragment;

import android.opengl.Visibility;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.widget.NoScrollViewPager;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.ui.activity.DocDetails;
import com.mobilepolice.officeMobile.ui.adapter.ImageViewAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class DocumentMainFragment2 extends MyLazyFragment    //, TbsReaderView.ReaderCallback
{

    @BindView(R.id.vp)
    NoScrollViewPager mList;
    String id = "";
    String approveNodeId = "2";
    String approvePerson = "";
    String applyPerson = "";
    String approveType = "";
    //审批结果（1=打字意见，2=图片签写，3=语音签写）
    String approveFlag = "";
    //打字文字，图片、语音base64码
    String approveOpinion = "";
    DocPendingBean docPendingBean;
    @BindView(R.id.previous)
    TextView previous;
    @BindView(R.id.current)
    TextView current;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.mBottomBar)
    LinearLayout mBottomBar;

    private ImageViewAdapter adapter = new ImageViewAdapter();

    public static DocumentMainFragment newInstance() {
        return new DocumentMainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_document_main2;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        DocDetails activity = (DocDetails) getFragmentActivity();
        id = activity.getId();
        docPendingBean = activity.getDocPendingBean();
        mList.setAdapter(adapter);
        mList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                current.setText(String.valueOf(i+1));
                currentIndex=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void showImage(String... path) {
        adapter.setData(Arrays.asList(path));
    }


    private int currentIndex = 0;
    private int maxLength;

    @Override
    protected void initData() {
        //  showtest();// showPdf();
        if (docPendingBean != null && docPendingBean.getObj() != null) {
            String img = docPendingBean.getObj().getImg();
            img = img.replaceAll("10.106.12.104:8789", "192.168.20.228:7121");

            String[] imgs = img.split(",");

            if (imgs != null && imgs.length > 0) {
                showImage(imgs);
                maxLength = imgs.length;

            } else {
                showImage(docPendingBean.getObj().getImg());
                maxLength = 1;
            }

            mBottomBar.setVisibility(maxLength == 1 ? View.GONE : View.VISIBLE);

            total.setText(String.valueOf(maxLength));
            approveNodeId = docPendingBean.getAttributes().getApproveNodeId();
            applyPerson = docPendingBean.getObj().getApplyPerson();
            approveType = docPendingBean.getObj().getSchema();
        }
    }


    private static final String TAG = "OfficeFileReader";
    //TbsReaderView tbsReaderView;
    String filePath, cachePath;

    public static final String VIEW_DETAIL = "view:detail";
    public static final String FILE_ID = "file:office";


    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);
        return str;
    }


    @OnClick({R.id.previous, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.previous:
                if (currentIndex > 0) {
                    currentIndex--;
                }
                break;
            case R.id.next:
                if (currentIndex < maxLength - 1) {
                    currentIndex++;
                }
                break;
        }
        mList.setCurrentItem(currentIndex);
        current.setText(String.valueOf(currentIndex + 1));
    }
}