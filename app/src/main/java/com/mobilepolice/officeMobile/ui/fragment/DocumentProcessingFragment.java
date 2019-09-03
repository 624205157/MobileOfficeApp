package com.mobilepolice.officeMobile.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.config.Config;
import com.mobilepolice.officeMobile.manager.MediaManager;
import com.mobilepolice.officeMobile.pdf.PdfGenerator;
import com.mobilepolice.officeMobile.pdf.VideoModel;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.activity.DocumentExamineActivity;
import com.mobilepolice.officeMobile.ui.activity.DocumentPendingWorkDetailedActivity;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.utils.Player;
import com.mobilepolice.officeMobile.utils.TakePictureManager;
import com.mobilepolice.officeMobile.widget.scrollable.ScrollableHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 公文处理
 */
public class DocumentProcessingFragment extends MyLazyFragment implements ScrollableHelper.ScrollableContainer, View.OnClickListener {


    @BindView(R.id.tv_doc_title)
    TextView tv_doc_title;
    @BindView(R.id.tv_doc_department)
    TextView tv_doc_department;
    @BindView(R.id.tv_doc_type)
    TextView tv_doc_type;
    @BindView(R.id.tv_doc_count)
    TextView tv_doc_count;
    @BindView(R.id.tv_doc_secretrank)
    TextView tv_doc_secretrank;
    @BindView(R.id.tv_doc_EmergencyLevel)
    TextView tv_doc_EmergencyLevel;
    @BindView(R.id.tv_doc_module)
    TextView tv_doc_module;
    @BindView(R.id.tv_doc_approver)
    TextView tv_doc_approver;
    @BindView(R.id.tv_doc_approver_title)
    TextView tv_doc_approver_title;

    @BindView(R.id.class_id_01)
    LinearLayout class_id_01;
    @BindView(R.id.class_id_02)
    LinearLayout class_id_02;
    @BindView(R.id.ll_record)
    LinearLayout ll_record;

    @BindView(R.id.iea_iv_voiceLine)
    ImageView iea_iv_voiceLine;
    @BindView(R.id.iea_ll_singer)
    LinearLayout iea_ll_singer;
    @BindView(R.id.iea_tv_voicetime1)
    TextView iea_tv_voicetime1;

    @BindView(R.id.img_photo)
    ImageView img_photo;

    @BindView(R.id.view_signer)
    View view_signer;
    @BindView(R.id.ll_signer)
    LinearLayout ll_signer;
    @BindView(R.id.tv_doc_signer)
    TextView tv_doc_signer;

    @BindView(R.id.img_photo_signer)
    ImageView img_photo_signer;
    @BindView(R.id.ll_record_signer)
    LinearLayout ll_record_signer;
    @BindView(R.id.iea_iv_voiceLine_signer)
    ImageView iea_iv_voiceLine_signer;
    @BindView(R.id.iea_ll_singer_signer)
    LinearLayout iea_ll_singer_signer;
    @BindView(R.id.iea_tv_voicetime1_signer)
    TextView iea_tv_voicetime1_signer;


    @BindView(R.id.view_signer2)
    View view_signer2;
    @BindView(R.id.ll_signer2)
    LinearLayout ll_signer2;
    @BindView(R.id.tv_doc_signer2)
    TextView tv_doc_signer2;
    @BindView(R.id.img_photo_signer2)
    ImageView img_photo_signer2;
    @BindView(R.id.ll_record_signer2)
    LinearLayout ll_record_signer2;
    @BindView(R.id.iea_iv_voiceLine_signer2)
    ImageView iea_iv_voiceLine_signer2;
    @BindView(R.id.iea_ll_singer_signer2)
    LinearLayout iea_ll_singer_signer2;
    @BindView(R.id.iea_tv_voicetime1_signer2)
    TextView iea_tv_voicetime1_signer2;

    @BindView(R.id.view_signer3)
    View view_signer3;
    @BindView(R.id.ll_signer3)
    LinearLayout ll_signer3;
    @BindView(R.id.tv_doc_signer3)
    TextView tv_doc_signer3;
    @BindView(R.id.img_photo_signer3)
    ImageView img_photo_signer3;
    @BindView(R.id.ll_record_signer3)
    LinearLayout ll_record_signer3;
    @BindView(R.id.iea_iv_voiceLine_signer3)
    ImageView iea_iv_voiceLine_signer3;
    @BindView(R.id.iea_ll_singer_signer3)
    LinearLayout iea_ll_singer_signer3;
    @BindView(R.id.iea_tv_voicetime1_signer3)
    TextView iea_tv_voicetime1_signer3;
    @BindView(R.id.view_signer4)
    View view_signer4;
    @BindView(R.id.ll_signer4)
    LinearLayout ll_signer4;
    @BindView(R.id.tv_doc_signer4)
    TextView tv_doc_signer4;

    @BindView(R.id.img_photo_signer4)
    ImageView img_photo_signer4;
    @BindView(R.id.ll_record_signer4)
    LinearLayout ll_record_signer4;
    @BindView(R.id.iea_iv_voiceLine_signer4)
    ImageView iea_iv_voiceLine_signer4;
    @BindView(R.id.iea_ll_singer_signer4)
    LinearLayout iea_ll_singer_signer4;
    @BindView(R.id.iea_tv_voicetime1_signer4)
    TextView iea_tv_voicetime1_signer4;
    DocPendingBean.ObjBean objbean;
    DocPendingBean.AttributesBean arrtbean;
    String id = "";
    String approveNodeId = "2";
    //审批结果（1=打字意见，2=图片签写，3=语音签写）
    String approveFlag = "";
    //打字文字，图片、语音base64码
    String approveOpinion = "";
    List<DocPendingBean.AttributesBean.ApproveListInfoBean> list = new ArrayList<>();
    DocPendingBean docPendingBean;

    public static DocumentProcessingFragment newInstance() {
        return new DocumentProcessingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_document_processing;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }


    @Override
    protected void initView() {
        class_id_01.setOnClickListener(this);
        class_id_02.setOnClickListener(this);
        iea_iv_voiceLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(MyApplication.getInstance().path_record)) {
                    Player player = new Player();
                    player.playUrl(list.get(0).getApproveResultFile());
                } else {
                    setLl_record(iea_ll_singer);
                }
            }
        });
        iea_iv_voiceLine_signer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(MyApplication.getInstance().path_record)) {
                    Player player = new Player();
                    player.playUrl(list.get(1).getApproveResultFile());
                } else {
                    setLl_record(iea_ll_singer_signer);
                }
            }
        });
        iea_iv_voiceLine_signer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(MyApplication.getInstance().path_record)) {
                    Player player = new Player();
                    player.playUrl(list.get(2).getApproveResultFile());
                } else {
                    setLl_record(iea_ll_singer_signer2);
                }
            }
        });
        iea_iv_voiceLine_signer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(MyApplication.getInstance().path_record)) {
                    Player player = new Player();
                    player.playUrl(list.get(3).getApproveResultFile());
                } else {
                    setLl_record(iea_ll_singer_signer3);
                }
            }
        });
        iea_iv_voiceLine_signer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(MyApplication.getInstance().path_record)) {
                    Player player = new Player();
                    player.playUrl(list.get(4).getApproveResultFile());
                } else {
                    setLl_record(iea_ll_singer_signer4);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (!TextUtils.isEmpty(id)) {
//            list = new ArrayList<>();
//            findApplyInfoto(id);
//        }
        if (list.size() > 0 && list.get(0).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
            if (MyApplication.getInstance().path_type == 1) {
                tv_doc_approver.setTextColor(getResources().getColor(R.color.red));
                tv_doc_approver.setText(MyApplication.getInstance().path_content);
                tv_doc_approver.setVisibility(View.VISIBLE);
                img_photo.setVisibility(View.GONE);
                ll_record.setVisibility(View.GONE);

            } else if (MyApplication.getInstance().path_type == 2) {
                tv_doc_approver.setVisibility(View.INVISIBLE);
                img_photo.setVisibility(View.VISIBLE);
                setImg_photo(img_photo);
                ll_record.setVisibility(View.GONE);
            } else if (MyApplication.getInstance().path_type == 3) {
                tv_doc_approver.setVisibility(View.INVISIBLE);
                img_photo.setVisibility(View.GONE);
                ll_record.setVisibility(View.VISIBLE);
//            setLl_record();
            }
        } else if (list.size() > 1 && list.get(1).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
            if (MyApplication.getInstance().path_type == 1) {
                tv_doc_signer.setTextColor(getResources().getColor(R.color.red));
                tv_doc_signer.setText(MyApplication.getInstance().path_content);
                tv_doc_signer.setVisibility(View.VISIBLE);
                img_photo_signer.setVisibility(View.GONE);
                ll_record_signer.setVisibility(View.GONE);

            } else if (MyApplication.getInstance().path_type == 2) {
                tv_doc_signer.setVisibility(View.INVISIBLE);
                img_photo_signer.setVisibility(View.VISIBLE);
                setImg_photo(img_photo_signer);
                ll_record_signer.setVisibility(View.GONE);
            } else if (MyApplication.getInstance().path_type == 3) {
                tv_doc_signer.setVisibility(View.INVISIBLE);
                img_photo_signer.setVisibility(View.GONE);
                ll_record_signer.setVisibility(View.VISIBLE);
//            setLl_record();
            }
        } else if (list.size() > 2 && list.get(2).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
            if (MyApplication.getInstance().path_type == 1) {
                tv_doc_signer2.setTextColor(getResources().getColor(R.color.red));
                tv_doc_signer2.setText(MyApplication.getInstance().path_content);
                tv_doc_signer2.setVisibility(View.VISIBLE);
                img_photo_signer2.setVisibility(View.GONE);
                ll_record_signer2.setVisibility(View.GONE);

            } else if (MyApplication.getInstance().path_type == 2) {
                tv_doc_signer2.setVisibility(View.INVISIBLE);
                img_photo_signer2.setVisibility(View.VISIBLE);
                setImg_photo(img_photo_signer2);
                ll_record_signer2.setVisibility(View.GONE);
            } else if (MyApplication.getInstance().path_type == 3) {
                tv_doc_signer2.setVisibility(View.INVISIBLE);
                img_photo_signer2.setVisibility(View.GONE);
                ll_record_signer2.setVisibility(View.VISIBLE);
//            setLl_record();
            }
        } else if (list.size() > 3 && list.get(3).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
            if (MyApplication.getInstance().path_type == 1) {
                tv_doc_signer3.setTextColor(getResources().getColor(R.color.red));
                tv_doc_signer3.setText(MyApplication.getInstance().path_content);
                tv_doc_signer3.setVisibility(View.VISIBLE);
                img_photo.setVisibility(View.GONE);
                ll_record.setVisibility(View.GONE);

            } else if (MyApplication.getInstance().path_type == 2) {
                tv_doc_signer3.setVisibility(View.INVISIBLE);
                img_photo_signer3.setVisibility(View.VISIBLE);
                setImg_photo(img_photo_signer3);
                ll_record_signer3.setVisibility(View.GONE);
            } else if (MyApplication.getInstance().path_type == 3) {
                tv_doc_signer3.setVisibility(View.INVISIBLE);
                img_photo_signer3.setVisibility(View.GONE);
                ll_record_signer3.setVisibility(View.VISIBLE);
//            setLl_record();
            }
        } else if (list.size() > 4 && list.get(4).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
            if (MyApplication.getInstance().path_type == 1) {
                tv_doc_signer4.setTextColor(getResources().getColor(R.color.red));
                tv_doc_signer4.setText(MyApplication.getInstance().path_content);
                tv_doc_signer4.setVisibility(View.VISIBLE);
                img_photo_signer4.setVisibility(View.GONE);
                ll_record_signer4.setVisibility(View.GONE);

            } else if (MyApplication.getInstance().path_type == 2) {
                tv_doc_signer4.setVisibility(View.INVISIBLE);
                img_photo_signer4.setVisibility(View.VISIBLE);
                setImg_photo(img_photo_signer4);
                ll_record_signer4.setVisibility(View.GONE);
            } else if (MyApplication.getInstance().path_type == 3) {
                tv_doc_signer4.setVisibility(View.INVISIBLE);
                img_photo_signer4.setVisibility(View.GONE);
                ll_record_signer4.setVisibility(View.VISIBLE);
//            setLl_record();
            }
        }


    }

    private void setImg_photo(ImageView img_photo) {
        try {
            File file = new File(MyApplication.getInstance().path_image);
            if (!file.exists()) {
                return;
            }

            Bitmap photoBmp = null;
            photoBmp = TakePictureManager.getBitmapFormUri(getActivity(), Uri.fromFile(file));
            int degree = TakePictureManager.getBitmapDegree(file.getAbsolutePath());
            /**
             * 把图片旋转为正的方向
             */
            Bitmap newbitmap = TakePictureManager.rotateBitmapByDegree(photoBmp, degree);
            if (newbitmap != null) {
                img_photo.setImageBitmap(newbitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setLl_record(LinearLayout iea_ll_singer) {
        final AnimationDrawable animationDrawable = (AnimationDrawable) iea_ll_singer.getBackground();
        //重置动画
        resetAnim(animationDrawable);
        animationDrawable.start();
        //播放前重置。
        MediaManager.release();
        //开始实质播放
        MediaManager.playSound(MyApplication.getInstance().path_record, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationDrawable.selectDrawable(0);//显示动画第一帧
                animationDrawable.stop();
                //播放完毕，当前播放索引置为-1。

            }
        });
    }

    List<AnimationDrawable> mAnimationDrawables = new ArrayList<>();

    private void resetAnim(AnimationDrawable animationDrawable) {
        if (!mAnimationDrawables.contains(animationDrawable)) {
            mAnimationDrawables.add(animationDrawable);
        }
        for (AnimationDrawable ad : mAnimationDrawables) {
            ad.selectDrawable(0);
            ad.stop();
        }
    }

    @Override
    protected void initData() {
        DocumentPendingWorkDetailedActivity activity = (DocumentPendingWorkDetailedActivity) getFragmentActivity();
        id = activity.getId();
        docPendingBean = activity.getDocPendingBean();
        if (!TextUtils.isEmpty(id)) {
            list = new ArrayList<>();
            findApplyInfoto(id);
        }
//        if (docPendingBean != null) {
//            showInit();
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (!TextUtils.isEmpty(id)) {
//            list = new ArrayList<>();
//            findApplyInfoto(id);
//        }
    }

    //公文审批单查看
    private void findApplyInfoto(String id) {
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
                try {
                    if (JsonParseUtils.jsonToBoolean(result)) {
                        docPendingBean = FastJsonHelper.deserialize(result,
                                DocPendingBean.class);
                        if (docPendingBean != null) {
                            showInit();
                        } else {
                            toast("读取订单异常");

                        }
                    } else {
                        String msg = JsonParseUtils.jsonToMsg(result);
                        toast(msg);
                    }
                    showProgressDialog(false);
                } catch (Exception ex) {
                    toast(ex.getLocalizedMessage());
                }
            }
        });
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
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    String attributes = JSON.parseObject(result).getString(
                            "attributes");
                    objbean = FastJsonHelper.deserialize(obj,
                            DocPendingBean.ObjBean.class);

                    String approveListInfo = JSON.parseObject(result).getString(
                            "approveListInfo");

                    String notApprovePerson = JSON.parseObject(result).getString(
                            "notApprovePerson");
                    String approveNodeId = JSON.parseObject(attributes).getString(
                            "approveNodeId");
                    MyApplication.getInstance().approveNodeId = approveNodeId;

                    arrtbean = FastJsonHelper.deserialize(attributes,
                            DocPendingBean.AttributesBean.class);
                    if (objbean != null) {
                        MyApplication.getInstance().path_main_image = objbean.getImg();
                        MyApplication.getInstance().DocSchema = objbean.getSchema();
                        tv_doc_title.setText(objbean.getTitel());
                        tv_doc_department.setText(objbean.getDepartmentName());
                        tv_doc_type.setText(objbean.getRequestFlag());
                        tv_doc_count.setText(objbean.getRequestNum());
                        if (objbean.getSecretLevel().equals("1")) {
                            tv_doc_secretrank.setText("一级");
                        } else if (objbean.getSecretLevel().equals("2")) {
                            tv_doc_secretrank.setText("二级");
                        } else if (objbean.getSecretLevel().equals("3")) {
                            tv_doc_secretrank.setText("三级");
                        }
                        if (objbean.getUrgentLevel().equals("1")) {
                            tv_doc_EmergencyLevel.setText("一级");
                        } else if (objbean.getUrgentLevel().equals("2")) {
                            tv_doc_EmergencyLevel.setText("二级");
                        } else if (objbean.getUrgentLevel().equals("3")) {
                            tv_doc_EmergencyLevel.setText("三级");
                        }
                        if (objbean.getSchema().equals("1")) {
                            tv_doc_module.setText("审批");
                        } else {
                            tv_doc_module.setText("会签");
                        }
                    }
                    if (arrtbean != null) {

                        if (arrtbean.getApproveListInfo() != null && arrtbean.getApproveListInfo().size() > 0) {
//                            Collections.reverse(arrtbean.getApproveListInfo());
                            for (DocPendingBean.AttributesBean.ApproveListInfoBean bean :
                                    arrtbean.getApproveListInfo()) {
                                list.add(bean);
                            }
                        }

                        List<DocPendingBean.AttributesBean.NotApprovePersonBean> naplist = arrtbean.getNotApprovePerson();
                        if (naplist != null && naplist.size() > 0) {
                            // Collections.reverse(naplist);
                            for (DocPendingBean.AttributesBean.NotApprovePersonBean bean :
                                    naplist) {
                                list.add(new DocPendingBean.AttributesBean.ApproveListInfoBean(bean.getApprovePerson(), bean.getApprovePersonName()));
                            }
                        }

                        if (list != null && list.size() > 0) {

                            if (list.size() > 0) {
                                if (!TextUtils.isEmpty(list.get(0).getCreateDate())) {


                                    if (list.get(0).getApproveFlag().equals("1")) {
                                        tv_doc_approver.setText(list.get(0).getApproveOpinion());
                                    } else if (list.get(0).getApproveFlag().equals("2")) {
                                        tv_doc_approver.setVisibility(View.INVISIBLE);
                                        img_photo.setVisibility(View.VISIBLE);
                                        ll_record.setVisibility(View.GONE);
                                        ImageOptions imageOptions = new ImageOptions.Builder()
                                                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                                .setFailureDrawableId(R.mipmap.not_photo)
                                                .setLoadingDrawableId(R.mipmap.not_photo)
                                                .build();
                                        if (!TextUtils.isEmpty(list.get(0).getApproveResultFile())) {
                                            x.image().bind(img_photo, list.get(0).getApproveResultFile(), imageOptions);
                                        }
                                    } else if (list.get(0).getApproveFlag().equals("3")) {
                                        tv_doc_approver.setVisibility(View.INVISIBLE);
                                        img_photo.setVisibility(View.GONE);
                                        ll_record.setVisibility(View.VISIBLE);


                                    }

                                } else {
                                    tv_doc_approver.setText(list.get(0).getApprovePersonName());
                                    if (list.get(0).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                                        tv_doc_approver.setTextColor(getResources().getColor(R.color.red));
                                    }
                                }
                            }
                            if (list.size() > 1) {
                                view_signer.setVisibility(View.VISIBLE);
                                ll_signer.setVisibility(View.VISIBLE);
                                tv_doc_approver_title.setText("会签人");
                                if (!TextUtils.isEmpty(list.get(1).getCreateDate())) {

                                    if (list.get(1).getApproveFlag().equals("1")) {
                                        tv_doc_signer.setText(list.get(1).getApproveOpinion());
                                    } else if (list.get(1).getApproveFlag().equals("2")) {
                                        tv_doc_signer.setVisibility(View.INVISIBLE);
                                        img_photo_signer.setVisibility(View.VISIBLE);
                                        ll_record_signer.setVisibility(View.GONE);
                                        ImageOptions imageOptions = new ImageOptions.Builder()
                                                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                                .setFailureDrawableId(R.mipmap.not_photo)
                                                .setLoadingDrawableId(R.mipmap.not_photo)
                                                .build();
                                        if (!TextUtils.isEmpty(list.get(1).getApproveResultFile())) {
                                            x.image().bind(img_photo_signer, list.get(1).getApproveResultFile(), imageOptions);
                                        }
                                    } else if (list.get(1).getApproveFlag().equals("3")) {
                                        tv_doc_signer.setVisibility(View.INVISIBLE);
                                        img_photo_signer.setVisibility(View.GONE);
                                        ll_record_signer.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    tv_doc_signer.setText(list.get(1).getApprovePersonName());
                                    if (list.get(1).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                                        tv_doc_signer.setTextColor(getResources().getColor(R.color.red));

                                    }
                                }
                            }
                            if (list.size() > 2) {
                                view_signer2.setVisibility(View.VISIBLE);
                                ll_signer2.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(list.get(2).getCreateDate())) {

                                    if (list.get(2).getApproveFlag().equals("1")) {
                                        tv_doc_signer2.setText(list.get(2).getApproveOpinion());
                                    } else if (list.get(2).getApproveFlag().equals("2")) {
                                        tv_doc_signer2.setVisibility(View.INVISIBLE);
                                        img_photo_signer2.setVisibility(View.VISIBLE);
                                        ll_record_signer2.setVisibility(View.GONE);
                                        ImageOptions imageOptions = new ImageOptions.Builder()
                                                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                                .setFailureDrawableId(R.mipmap.not_photo)
                                                .setLoadingDrawableId(R.mipmap.not_photo)
                                                .build();
                                        if (!TextUtils.isEmpty(list.get(2).getApproveResultFile())) {
                                            x.image().bind(img_photo, list.get(2).getApproveResultFile(), imageOptions);
                                        }
                                    } else if (list.get(2).getApproveFlag().equals("3")) {
                                        tv_doc_signer2.setVisibility(View.INVISIBLE);
                                        img_photo_signer2.setVisibility(View.GONE);
                                        ll_record_signer2.setVisibility(View.VISIBLE);
                                        Player player = new Player();
                                        player.playUrl("");
                                    }

                                } else {
                                    tv_doc_signer2.setText(list.get(2).getApprovePersonName());
                                    if (list.get(2).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                                        tv_doc_signer2.setTextColor(getResources().getColor(R.color.red));

                                    }
                                }

                            }
                            if (list.size() > 3) {
                                view_signer3.setVisibility(View.VISIBLE);
                                ll_signer3.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(list.get(3).getCreateDate())) {

                                    if (list.get(3).getApproveFlag().equals("1")) {
                                        tv_doc_signer3.setText(list.get(3).getApproveOpinion());
                                    } else if (list.get(3).getApproveFlag().equals("2")) {
                                        tv_doc_signer3.setVisibility(View.INVISIBLE);
                                        img_photo_signer3.setVisibility(View.VISIBLE);
                                        ll_record_signer3.setVisibility(View.GONE);
                                        ImageOptions imageOptions = new ImageOptions.Builder()
                                                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                                .setFailureDrawableId(R.mipmap.not_photo)
                                                .setLoadingDrawableId(R.mipmap.not_photo)
                                                .build();
                                        if (!TextUtils.isEmpty(list.get(3).getApproveResultFile())) {
                                            x.image().bind(img_photo_signer3, list.get(3).getApproveResultFile(), imageOptions);
                                        }
                                    } else if (list.get(3).getApproveFlag().equals("3")) {
                                        tv_doc_signer3.setVisibility(View.INVISIBLE);
                                        img_photo_signer3.setVisibility(View.GONE);
                                        ll_record_signer3.setVisibility(View.VISIBLE);
                                        Player player = new Player();
                                        player.playUrl("");
                                    }

                                } else {
                                    tv_doc_signer3.setText(list.get(3).getApprovePersonName());
                                    if (list.get(3).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                                        tv_doc_signer3.setTextColor(getResources().getColor(R.color.red));

                                    }
                                }

                            }
                            if (list.size() > 4) {
                                view_signer4.setVisibility(View.VISIBLE);
                                ll_signer4.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(list.get(4).getCreateDate())) {

                                    if (list.get(4).getApproveFlag().equals("1")) {
                                        tv_doc_signer4.setText(list.get(4).getApproveOpinion());
                                    } else if (list.get(4).getApproveFlag().equals("2")) {
                                        tv_doc_signer4.setVisibility(View.INVISIBLE);
                                        img_photo_signer4.setVisibility(View.VISIBLE);
                                        ll_record_signer4.setVisibility(View.GONE);
                                        ImageOptions imageOptions = new ImageOptions.Builder()
                                                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                                .setFailureDrawableId(R.mipmap.not_photo)
                                                .setLoadingDrawableId(R.mipmap.not_photo)
                                                .build();
                                        if (!TextUtils.isEmpty(list.get(4).getApproveResultFile())) {
                                            x.image().bind(img_photo_signer4, list.get(4).getApproveResultFile(), imageOptions);
                                        }
                                    } else if (list.get(4).getApproveFlag().equals("3")) {
                                        tv_doc_signer4.setVisibility(View.INVISIBLE);
                                        img_photo_signer4.setVisibility(View.GONE);
                                        ll_record_signer4.setVisibility(View.VISIBLE);
                                        Player player = new Player();
                                        player.playUrl("");
                                    }

                                } else {
                                    tv_doc_signer4.setText(list.get(4).getApprovePersonName());
                                    if (list.get(4).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                                        tv_doc_signer4.setTextColor(getResources().getColor(R.color.red));

                                    }

                                }
                            }
                        }

                    }
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                    getActivity().finish();
                }
            }
        });
    }


    private void showInit() {
        if (docPendingBean.getObj() != null) {
            objbean = docPendingBean.getObj();
        }
        if (docPendingBean.getAttributes() != null) {
            arrtbean = docPendingBean.getAttributes();
        }
        if (objbean != null) {
            MyApplication.getInstance().path_main_image = objbean.getImg();
            MyApplication.getInstance().DocSchema = objbean.getSchema();
            tv_doc_title.setText(objbean.getTitel());
            tv_doc_department.setText(objbean.getDepartmentName());
            tv_doc_type.setText(objbean.getRequestFlag());
            tv_doc_count.setText(objbean.getRequestNum());
            if (objbean.getSecretLevel().equals("1")) {
                tv_doc_secretrank.setText("一级");
            } else if (objbean.getSecretLevel().equals("2")) {
                tv_doc_secretrank.setText("二级");
            } else if (objbean.getSecretLevel().equals("3")) {
                tv_doc_secretrank.setText("三级");
            }
            if (objbean.getUrgentLevel().equals("1")) {
                tv_doc_EmergencyLevel.setText("一级");
            } else if (objbean.getUrgentLevel().equals("2")) {
                tv_doc_EmergencyLevel.setText("二级");
            } else if (objbean.getUrgentLevel().equals("3")) {
                tv_doc_EmergencyLevel.setText("三级");
            }
            if (objbean.getSchema().equals("1")) {
                tv_doc_module.setText("审批");
            } else {
                tv_doc_module.setText("会签");
            }
        }
        if (arrtbean != null) {

            if (arrtbean.getApproveListInfo() != null && arrtbean.getApproveListInfo().size() > 0) {
//                            Collections.reverse(arrtbean.getApproveListInfo());
                for (DocPendingBean.AttributesBean.ApproveListInfoBean bean :
                        arrtbean.getApproveListInfo()) {
                    list.add(bean);
                }
            }

            List<DocPendingBean.AttributesBean.NotApprovePersonBean> naplist = arrtbean.getNotApprovePerson();
            if (naplist != null && naplist.size() > 0) {
                // Collections.reverse(naplist);
                for (DocPendingBean.AttributesBean.NotApprovePersonBean bean :
                        naplist) {
                    list.add(new DocPendingBean.AttributesBean.ApproveListInfoBean(bean.getApprovePerson(), bean.getApprovePersonName()));
                }
            }

            if (list != null && list.size() > 0) {

                if (list.size() > 0) {
                    if (!TextUtils.isEmpty(list.get(0).getCreateDate())) {


                        if (list.get(0).getApproveFlag().equals("1")) {
                            tv_doc_approver.setText(list.get(0).getApproveOpinion());
                        } else if (list.get(0).getApproveFlag().equals("2")) {
                            tv_doc_approver.setVisibility(View.INVISIBLE);
                            img_photo.setVisibility(View.VISIBLE);
                            ll_record.setVisibility(View.GONE);
                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                    .setFailureDrawableId(R.mipmap.not_photo)
                                    .setLoadingDrawableId(R.mipmap.not_photo)
                                    .build();
                            if (!TextUtils.isEmpty(list.get(0).getApproveResultFile())) {
                                x.image().bind(img_photo, list.get(0).getApproveResultFile(), imageOptions);
                            }
                        } else if (list.get(0).getApproveFlag().equals("3")) {
                            tv_doc_approver.setVisibility(View.INVISIBLE);
                            img_photo.setVisibility(View.GONE);
                            ll_record.setVisibility(View.VISIBLE);


                        }

                    } else {
                        tv_doc_approver.setText(list.get(0).getApprovePersonName());
                        if (list.get(0).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                            tv_doc_approver.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                }
                if (list.size() > 1) {
                    view_signer.setVisibility(View.VISIBLE);
                    ll_signer.setVisibility(View.VISIBLE);
                    tv_doc_approver_title.setText("会签人");
                    if (!TextUtils.isEmpty(list.get(1).getCreateDate())) {

                        if (list.get(1).getApproveFlag().equals("1")) {
                            tv_doc_signer.setText(list.get(1).getApproveOpinion());
                        } else if (list.get(1).getApproveFlag().equals("2")) {
                            tv_doc_signer.setVisibility(View.INVISIBLE);
                            img_photo_signer.setVisibility(View.VISIBLE);
                            ll_record_signer.setVisibility(View.GONE);
                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                    .setFailureDrawableId(R.mipmap.not_photo)
                                    .setLoadingDrawableId(R.mipmap.not_photo)
                                    .build();
                            if (!TextUtils.isEmpty(list.get(1).getApproveResultFile())) {
                                x.image().bind(img_photo_signer, list.get(1).getApproveResultFile(), imageOptions);
                            }
                        } else if (list.get(1).getApproveFlag().equals("3")) {
                            tv_doc_signer.setVisibility(View.INVISIBLE);
                            img_photo_signer.setVisibility(View.GONE);
                            ll_record_signer.setVisibility(View.VISIBLE);
                        }

                    } else {
                        tv_doc_signer.setText(list.get(1).getApprovePersonName());
                        if (list.get(1).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                            tv_doc_signer.setTextColor(getResources().getColor(R.color.red));

                        }
                    }
                }
                if (list.size() > 2) {
                    view_signer2.setVisibility(View.VISIBLE);
                    ll_signer2.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(list.get(2).getCreateDate())) {

                        if (list.get(2).getApproveFlag().equals("1")) {
                            tv_doc_signer2.setText(list.get(2).getApproveOpinion());
                        } else if (list.get(2).getApproveFlag().equals("2")) {
                            tv_doc_signer2.setVisibility(View.INVISIBLE);
                            img_photo_signer2.setVisibility(View.VISIBLE);
                            ll_record_signer2.setVisibility(View.GONE);
                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                    .setFailureDrawableId(R.mipmap.not_photo)
                                    .setLoadingDrawableId(R.mipmap.not_photo)
                                    .build();
                            if (!TextUtils.isEmpty(list.get(2).getApproveResultFile())) {
                                x.image().bind(img_photo, list.get(2).getApproveResultFile(), imageOptions);
                            }
                        } else if (list.get(2).getApproveFlag().equals("3")) {
                            tv_doc_signer2.setVisibility(View.INVISIBLE);
                            img_photo_signer2.setVisibility(View.GONE);
                            ll_record_signer2.setVisibility(View.VISIBLE);
                            Player player = new Player();
                            player.playUrl("");
                        }

                    } else {
                        tv_doc_signer2.setText(list.get(2).getApprovePersonName());
                        if (list.get(2).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                            tv_doc_signer2.setTextColor(getResources().getColor(R.color.red));

                        }
                    }

                }
                if (list.size() > 3) {
                    view_signer3.setVisibility(View.VISIBLE);
                    ll_signer3.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(list.get(3).getCreateDate())) {

                        if (list.get(3).getApproveFlag().equals("1")) {
                            tv_doc_signer3.setText(list.get(3).getApproveOpinion());
                        } else if (list.get(3).getApproveFlag().equals("2")) {
                            tv_doc_signer3.setVisibility(View.INVISIBLE);
                            img_photo_signer3.setVisibility(View.VISIBLE);
                            ll_record_signer3.setVisibility(View.GONE);
                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                    .setFailureDrawableId(R.mipmap.not_photo)
                                    .setLoadingDrawableId(R.mipmap.not_photo)
                                    .build();
                            if (!TextUtils.isEmpty(list.get(3).getApproveResultFile())) {
                                x.image().bind(img_photo_signer3, list.get(3).getApproveResultFile(), imageOptions);
                            }
                        } else if (list.get(3).getApproveFlag().equals("3")) {
                            tv_doc_signer3.setVisibility(View.INVISIBLE);
                            img_photo_signer3.setVisibility(View.GONE);
                            ll_record_signer3.setVisibility(View.VISIBLE);
                            Player player = new Player();
                            player.playUrl("");
                        }

                    } else {
                        tv_doc_signer3.setText(list.get(3).getApprovePersonName());
                        if (list.get(3).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                            tv_doc_signer3.setTextColor(getResources().getColor(R.color.red));

                        }
                    }

                }
                if (list.size() > 4) {
                    view_signer4.setVisibility(View.VISIBLE);
                    ll_signer4.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(list.get(4).getCreateDate())) {

                        if (list.get(4).getApproveFlag().equals("1")) {
                            tv_doc_signer4.setText(list.get(4).getApproveOpinion());
                        } else if (list.get(4).getApproveFlag().equals("2")) {
                            tv_doc_signer4.setVisibility(View.INVISIBLE);
                            img_photo_signer4.setVisibility(View.VISIBLE);
                            ll_record_signer4.setVisibility(View.GONE);
                            ImageOptions imageOptions = new ImageOptions.Builder()
                                    // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                                    .setFailureDrawableId(R.mipmap.not_photo)
                                    .setLoadingDrawableId(R.mipmap.not_photo)
                                    .build();
                            if (!TextUtils.isEmpty(list.get(4).getApproveResultFile())) {
                                x.image().bind(img_photo_signer4, list.get(4).getApproveResultFile(), imageOptions);
                            }
                        } else if (list.get(4).getApproveFlag().equals("3")) {
                            tv_doc_signer4.setVisibility(View.INVISIBLE);
                            img_photo_signer4.setVisibility(View.GONE);
                            ll_record_signer4.setVisibility(View.VISIBLE);
                            Player player = new Player();
                            player.playUrl("");
                        }

                    } else {
                        tv_doc_signer4.setText(list.get(4).getApprovePersonName());
                        if (list.get(4).getApprovePerson().equals(MyApplication.getInstance().personPhone)) {
                            tv_doc_signer4.setTextColor(getResources().getColor(R.color.red));

                        }

                    }
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == class_id_01) {
            startActivity(DocumentExamineActivity.class);
        } else if (v == class_id_02) {
            //  startActivity(DocumentExamineActivity.class);
            approveWorkFlowInfo();

        }
    }

    //审批公文
    private void approveWorkFlowInfo() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            jsonObject.put("requestid", id);
            jsonObject.put("approveNodeId", approveNodeId);
            jsonObject.put("approvePerson", MyApplication.getInstance().personPhone);
            jsonObject.put("applyPerson", objbean.getApplyPerson());
            jsonObject.put("approveType", objbean.getSchema());
            jsonObject.put("approveFlag", MyApplication.getInstance().path_type);
            String approveOpinion = "";
            if (MyApplication.getInstance().path_type == 1) {
                approveOpinion = MyApplication.getInstance().path_content;
            } else if (MyApplication.getInstance().path_type == 2) {
                approveOpinion = setBase64Photo(MyApplication.getInstance().path_image);
            } else if (MyApplication.getInstance().path_type == 3) {
                approveOpinion = setBase64Photo(MyApplication.getInstance().path_record);
            }
            jsonObject.put("approveOpinion", approveOpinion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true, "正在审批公文");
        WebServiceUtils.getPersonDeptName("approveWorkFlowInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    toast("审批成功");
                    getActivity().finish();
                    //  save();
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                }
            }
        });
    }

    //上传pdf
    private void upWorkFlowApplyInfo() {

        try {
            //创建JSONObject
            JSONObject jsonObject = new JSONObject();
            //键值对赋值

            jsonObject.put("requestid", id);
            jsonObject.put("pdfImgBase64", setBase64Photo(MyApplication.getInstance().path_pdf));
            //转化成json字符串
            String json = jsonObject.toString();
            SoapParams params = new SoapParams();
            params.put("json", json);

            String nameSpace = Config.SERVICE_NAME_SPACE;// "http://services.webservice.mobilework.com/";
            // 调用的方法名称
            //  String methodName = methodNames;
            // EndPoint
            String endPoint = Config.SERVICE_URL;//"http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";
            // SOAP Action
            // final String soapAction = "http://services.webservice.mobilework.com/findApplyPersonDeptName";
            // 建立webservice连接对象
            final HttpTransportSE transport = new HttpTransportSE(endPoint);
            //transport.debug = true;// 是否是调试模式
            // 设置连接参数
            SoapObject soapObject = new SoapObject(nameSpace, "upWorkFlowApplyInfo");
            // 传递参数
            LinkedHashMap<String, Object> paramsList = params.getParamsList();
            Iterator<Map.Entry<String, Object>> iter = paramsList.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
            // soapObject.addProperty(key, value);
            //soapObject.addProperty("json", "{\"applyPerson\":\"17600194545\"}");
            // 设置返回参数
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// soap协议版本必须用SoapEnvelope.VER11（Soap V1.1）
            envelope.dotNet = false;// 注意：这个属性是对dotnetwebservice协议的支持,如果dotnet的webservice需要设置成true
            envelope.bodyOut = soapObject;//千万注意！！
            //  envelope.setOutputSoapObject(soapObject);// 设置请求参数

            try {
                transport.call(null, envelope);// 调用WebService
            } catch (Exception e) {
                // mHandler.sendMessage(mHandler.obtainMessage(-1, e.getMessage()));
                Message message = new Message();
                message.what = 2;
                handler.dispatchMessage(message);
            }
            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault error = (SoapFault) envelope.bodyIn;
                // 将异常的消息利用Handler发送到主线程
                //  mHandler.sendMessage(mHandler.obtainMessage(0, error.toString()));
                Message message = new Message();
                message.what = 2;
                handler.dispatchMessage(message);
            } else {
                SoapObject object = (SoapObject) envelope.bodyIn;// 获取返回的数据
                // 将结果利用Handler发送到主线程
                //  mHandler.sendMessage(mHandler.obtainMessage(1, object.getProperty(0).toString()));
                if (JsonParseUtils.jsonToBoolean(object.getProperty(0).toString())) {
                    Message message = new Message();
                    message.what = 1;
                    handler.dispatchMessage(message);
                }
            }

        } catch (Exception ex) {
        }


        /***
         //  showProgressDialog(true, "正在上传PDF文件");
         try {
         //创建JSONObject
         JSONObject jsonObject = new JSONObject();
         //键值对赋值

         jsonObject.put("requestid", id);
         jsonObject.put("pdfImgBase64", setBase64Photo(MyApplication.getInstance().path_pdf));
         //转化成json字符串
         String json = jsonObject.toString();
         SoapParams params = new SoapParams();
         params.put("json", json);

         WebServiceUtils.getPersonDeptName("upWorkFlowApplyInfo", params, new WebServiceUtils.CallBack() {
        @Override public void result(String result) {
        //  showProgressDialog(false);
        if (JsonParseUtils.jsonToBoolean(result)) {
        // toast("上传PDF成功");
        Message message = new Message();
        message.obj = 1;
        handler.dispatchMessage(message);
        } else {
        String msg = JsonParseUtils.jsonToMsg(result);
        //  toast(msg);
        Message message = new Message();
        message.obj = 2;
        handler.dispatchMessage(message);
        }
        }
        });
         } catch (JSONException e) {
         e.printStackTrace();
         // showProgressDialog(false);
         }
         ***/
    }

    //生成pdf
    private void save() {
        showProgressDialog(true, "正在生成文件并上传");
        new Thread() {
            @Override
            public void run() {
                super.run();
                String fileName = System.currentTimeMillis() + ".pdf";
                PdfGenerator.test(fileName, objbean, list);
                // PdfGenerator.generate(getActivity(), getList(), "test.pdf");
                upWorkFlowApplyInfo();
//                Message message = new Message();
//                message.obj = 1;
//                handler.dispatchMessage(message);
            }
        }.start();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {  //这个是发送过来的消息
            showProgressDialog(false);

            Object result = msg.obj;
            if (msg.what == 1) {
                MyApplication.getInstance().path_record = "";
                MyApplication.getInstance().path_content = "";
                MyApplication.getInstance().path_image = "";
                toast("上传成功");
            } else if (msg.what == 2) {
                toast("上传失败");
            }
            //      upWorkFlowApplyInfo();
            // 处理从子线程发送过来的消息
//            int arg1 = msg.arg1;  //获取消息携带的属性值
//            int arg2 = msg.arg2;
//            int what = msg.what;
//            Object result = msg.obj;
//            System.out.println("-arg1--->>" + arg1);
//            System.out.println("-arg2--->>" + arg2);
//            System.out.println("-what--->>" + what);
//            System.out.println("-result--->>" + result);
//            Bundle bundle = msg.getData(); // 用来获取消息里面的bundle数据
//            System.out.println("-getData--->>"
//                    + bundle.getStringArray("strs").length);
        }

        ;
    };

    private String setBase64Photo(String pathName) {
        String uploadBuffer = "";
        try {
            FileInputStream fis = new FileInputStream(pathName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }
            //进行Base64编码
            uploadBuffer = new String(Base64.encode(baos.toByteArray()));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            uploadBuffer = "";
        }
        return uploadBuffer;
    }

    private List<VideoModel> getList() {
        ArrayList<VideoModel> list = new ArrayList<VideoModel>();
        VideoModel model = new VideoModel("标题", "关于人事经理任命的发文", false);
        list.add(model);
//        model = new VideoModel("1111", "dempt", false);
//        list.add(model);
        model = new VideoModel("所属部门", tv_doc_department.getText().toString(), false);
        list.add(model);
        model = new VideoModel("发文类型", tv_doc_type.getText().toString(), false);
        list.add(model);
        model = new VideoModel("发文份数", tv_doc_count.getText().toString(), false);
        list.add(model);
        model = new VideoModel("秘密等级", tv_doc_secretrank.getText().toString(), false);
        list.add(model);
        model = new VideoModel("紧急程度", tv_doc_EmergencyLevel.getText().toString(), false);
        list.add(model);
        model = new VideoModel("公文模式", tv_doc_module.getText().toString(), false);
        list.add(model);
        model = new VideoModel("审批人", tv_doc_EmergencyLevel.getText().toString(), false);
        model.setPath(MyApplication.getInstance().path_image);
        model = new VideoModel("会签人", tv_doc_signer.getText().toString(), false);
        list.add(model);
        return list;
    }

    @Override
    public View getScrollableView() {
        return null;
    }
}