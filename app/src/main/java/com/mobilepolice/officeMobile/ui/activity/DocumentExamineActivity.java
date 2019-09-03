package com.mobilepolice.officeMobile.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.AttributesBean;
import com.mobilepolice.officeMobile.bean.Record;
import com.mobilepolice.officeMobile.bean.ShowDocInfoBean;
import com.mobilepolice.officeMobile.manager.AudioRecordButton;
import com.mobilepolice.officeMobile.manager.MediaManager;
import com.mobilepolice.officeMobile.pdf.VideoModel;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.FileUtil;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.utils.PopupWindowFactory;
import com.mobilepolice.officeMobile.utils.TakePictureManager;
import com.mobilepolice.officeMobile.utils.TimeUtil;
import com.yorhp.audiolibrary.AudioRecordUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 公文审批SignatureView
 */
public class DocumentExamineActivity extends MyActivity implements View.OnClickListener {

    @BindView(R.id.class_id_01)
    LinearLayout class_id_01;
    //    @BindView(R.id.class_id_02)
//    LinearLayout class_id_02;
    @BindView(R.id.class_id_03)
    LinearLayout class_id_03;
    @BindView(R.id.class_id_04)
    LinearLayout class_id_04;
    @BindView(R.id.class_id_05)
    LinearLayout class_id_05;
    @BindView(R.id.et_edit)
    EditText et_edit;
    @BindView(R.id.img_photo)
    ImageView img_photo;
    @BindView(R.id.ll_img)
    LinearLayout ll_img;
    @BindView(R.id.ll_record)
    LinearLayout ll_record;
    @BindView(R.id.iea_iv_voiceLine)
    ImageView iea_iv_voiceLine;
    @BindView(R.id.iea_ll_singer)
    LinearLayout iea_ll_singer;
    @BindView(R.id.iea_tv_voicetime1)
    TextView iea_tv_voicetime1;

    @BindView(R.id.tv_agree)
    TextView tv_agree;
    @BindView(R.id.tv_upagree)
    TextView tv_upagree;
    @BindView(R.id.em_tv_btn)
    AudioRecordButton mEmTvBtn;
    @BindView(R.id.img_record)
    ImageView img_record;
    float y;
    long time;
    //    int type = 0;//0输入1手写2录音
    String id = "";
    String approveNodeId = "2";
    String approvePerson = "";
    String applyPerson = "";
    String approveType = "";
    //审批结果（1=打字意见，2=图片签写，3=语音签写）
    String approveFlag = "";
    //打字文字，图片、语音base64码
    String approveOpinion = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_examine;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        setTitle("公文审批");
        id = getIntent().getStringExtra("id");
        approveNodeId = getIntent().getStringExtra("approveNodeId");
        approvePerson = getIntent().getStringExtra("approvePerson");
        applyPerson = getIntent().getStringExtra("applyPerson");
        approveType = getIntent().getStringExtra("approveType");
        AudioRecordUtil.init(FileUtil.RECORD_PATH);
        recordUtil = AudioRecordUtil.getInstance();
        record();
        setListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setImage();
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void initData() {

    }

    private void setImage() {
        String path = MyApplication.getInstance().path_image;
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        Bitmap photoBmp = null;
        try {
            photoBmp = TakePictureManager.getBitmapFormUri(DocumentExamineActivity.this, Uri.fromFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int degree = TakePictureManager.getBitmapDegree(file.getAbsolutePath());
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = TakePictureManager.rotateBitmapByDegree(photoBmp, degree);
        if (newbitmap != null) {
            ll_img.setVisibility(View.VISIBLE);
            MyApplication.getInstance().path_type = 2;
            img_photo.setImageBitmap(newbitmap);

        }
    }


    private void setListener() {
        class_id_01.setOnClickListener(this);
        // class_id_02.setOnClickListener(this);
        class_id_03.setOnClickListener(this);
        class_id_04.setOnClickListener(this);
        class_id_05.setOnClickListener(this);
        ll_record.setOnClickListener(this);
        iea_iv_voiceLine.setOnClickListener(this);
        tv_upagree.setOnClickListener(this);
        tv_agree.setOnClickListener(this);
        mEmTvBtn.setHasRecordPromission(false);
        mEmTvBtn.setHasRecordPromission(true);
        mEmTvBtn.setAudioFinishRecorderListener((seconds, filePath) -> {
            Record recordModel = new Record();
            recordModel.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
            recordModel.setPath(filePath);
            recordModel.setPlayed(false);
            MyApplication.getInstance().path_record = filePath;
            ll_record.setVisibility(View.VISIBLE);
        });
        img_record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        y = event.getRawY();
                        time = System.currentTimeMillis();
                        mPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                        try {
                            recordUtil.startRecord(DocumentExamineActivity.this);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            Snackbar.make(img_record, "先允许调用系统录音权限", Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - time < 1000) {
                            Snackbar.make(img_record, "录音时间过短，请重试", Snackbar.LENGTH_SHORT).show();
                            recordUtil.cancelRecord(DocumentExamineActivity.this);
                            mPop.dismiss();
                            break;
                        } else if (y - event.getRawY() > 300) {
                            Snackbar.make(img_record, "已取消录制语音", Snackbar.LENGTH_SHORT).show();
                            recordUtil.cancelRecord(DocumentExamineActivity.this);
                            mPop.dismiss();
                            break;
                        } else {
                            try {
                                recordUtil.stopRecord(DocumentExamineActivity.this);        //结束录音（保存录音文件）

                            } catch (Exception e) {
                                e.printStackTrace();
                                Snackbar.make(img_record, "先允许调用系统录音权限", Snackbar.LENGTH_SHORT);
                            }
                            mPop.dismiss();
                            break;
                        }
                    case MotionEvent.ACTION_CANCEL:
                        recordUtil.cancelRecord(DocumentExamineActivity.this); //取消录音（不保存录音文件）
                        mPop.dismiss();
                        break;
                }
                return true;
            }
        });
    }

    List<AnimationDrawable> mAnimationDrawables = new ArrayList<>();

    private void setLl_record() {
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

    @Override
    public void onClick(View v) {
        if (v == class_id_01) {
            startActivity(DocumentSignatureActivity.class);
        } else if (v == class_id_03) {
            MyApplication.getInstance().path_type = 1;
            et_edit.setEnabled(true);
            et_edit.setSelection(et_edit.length());
            et_edit.requestFocus();
            //SoftKeyboardUtils.showSoftKeyboard(getActivity());
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        et_edit.requestFocus();
                        imm.showSoftInput(et_edit, 0);
                        et_edit.setSelection(et_edit.length());

                    }
                }
            }, 100);
        } else if (v == iea_iv_voiceLine) {
            setLl_record();
        } else if (v == tv_agree) {
            MyApplication.getInstance().path_type = 1;
            et_edit.setText(tv_agree.getText().toString());
        } else if (v == tv_upagree) {
            MyApplication.getInstance().path_type = 1;
            et_edit.setText(tv_upagree.getText().toString());
        } else if (v == class_id_04) {
            if (MyApplication.getInstance().path_type == 1) {
                MyApplication.getInstance().path_content = et_edit.getText().toString();
            } else if (MyApplication.getInstance().path_type == 2) {

            } else if (MyApplication.getInstance().path_type == 3) {

            }
            approveWorkFlowInfo();


//            showProgressDialog(true, "正在生成文件");
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    PdfGenerator.generate(mContext, getList(), "test.pdf");
//                    Message message = new Message();
//                    message.obj = 1;
//                    handler.dispatchMessage(message);
//                }
//            }.start();

        } else if (v == class_id_05) {
            finish();
//            ExportToPdf pdf = new ExportToPdf("", "");
//            try {
//                pdf.write(new ArrayList<String>());
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
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
            jsonObject.put("applyPerson", applyPerson);
            jsonObject.put("approveType", approveType);
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
                    finish();
                    //  save();
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                }
            }
        });
    }

    AudioRecordUtil recordUtil;
    PopupWindowFactory mPop;

    /**
     * 语音录制
     */
    private void record() {
        if (mPop != null) {
            return;
        }

        final View view = View.inflate(this, R.layout.dialog_record, null);
        final ImageView mImageView = (ImageView) view.findViewById(R.id.zeffect_recordbutton_dialog_imageview);
        final TextView mTextView = (TextView) view.findViewById(R.id.zeffect_recordbutton_dialog_time_tv);
        mPop = new PopupWindowFactory(this, view);

        AudioRecordUtil.getInstance().setOnAudioStatusUpdateListener(new AudioRecordUtil.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {
                //根据分贝值来设置录音时话筒图标的上下波动，下面有讲解
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                mTextView.setText(TimeUtil.getTimeE(time).substring(14, 19));
            }

            @Override
            public void onStop(final String filePath) {
                MyApplication.getInstance().path_record = filePath;
                MyApplication.getInstance().path_type = 3;
                ll_record.setVisibility(View.VISIBLE);
//                Snackbar.make(img_record, "语音保存路径为：" + filePath, Snackbar.LENGTH_SHORT).setAction("查看", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        toast(filePath);
//
//                    }
//                }).show();
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {  //这个是发送过来的消息
            showProgressDialog(false);
            finish();
            // 处理从子线程发送过来的消息
            int arg1 = msg.arg1;  //获取消息携带的属性值
            int arg2 = msg.arg2;
            int what = msg.what;
            Object result = msg.obj;
            System.out.println("-arg1--->>" + arg1);
            System.out.println("-arg2--->>" + arg2);
            System.out.println("-what--->>" + what);
            System.out.println("-result--->>" + result);
            Bundle bundle = msg.getData(); // 用来获取消息里面的bundle数据
            System.out.println("-getData--->>"
                    + bundle.getStringArray("strs").length);
        }

        ;
    };

    private void resetAnim(AnimationDrawable animationDrawable) {
        if (!mAnimationDrawables.contains(animationDrawable)) {
            mAnimationDrawables.add(animationDrawable);
        }
        for (AnimationDrawable ad : mAnimationDrawables) {
            ad.selectDrawable(0);
            ad.stop();
        }
    }


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
        model = new VideoModel("所属部门", "秘书科 张三", false);
        list.add(model);
        model = new VideoModel("发文类型", "通知", false);
        list.add(model);
        model = new VideoModel("发文份数", "3", false);
        list.add(model);
        model = new VideoModel("秘密等级", "秘密三级", false);
        list.add(model);
        model = new VideoModel("紧急程度", "重要不紧急", false);
        list.add(model);
        model = new VideoModel("公文模式", "审批", false);
        list.add(model);
        model = new VideoModel("审批人", "张三", false);
        model.setPath(MyApplication.getInstance().path_image);
        model = new VideoModel("会签人", "李四", false);
        list.add(model);
        return list;
    }

    //    findApplyInfo("781D623188C04A0989CEF4BA1EDFB3DE");
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
                    result = result.replace("\\", "");
                    result = result.replace("D:", "");
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    String attributes = JSON.parseObject(result).getString(
                            "attributes");

                    ShowDocInfoBean showDocInfoBean = FastJsonHelper.deserialize(obj,
                            ShowDocInfoBean.class);
                    AttributesBean attributesBean = FastJsonHelper.deserialize(attributes,
                            AttributesBean.class);
                    if (showDocInfoBean != null) {

                    }
                } else {
                    toast("读取数据失败，请重新尝试");

                }
            }
        });
    }
}
