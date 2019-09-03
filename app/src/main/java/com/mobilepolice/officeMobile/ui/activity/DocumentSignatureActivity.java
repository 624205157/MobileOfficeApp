package com.mobilepolice.officeMobile.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hjq.bar.TitleBar;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.utils.FileUtil;
import com.mobilepolice.officeMobile.utils.IsPad;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.widget.SignatureView;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 公文审批SignatureView
 */
public class DocumentSignatureActivity extends MyActivity implements View.OnClickListener {
    //    @BindView(R.id.tv)
//    TextView tv;
    @BindView(R.id.img_container)
    ImageView img_container;
    //    @BindView(R.id.imageView)
//    ImageView imageView;
    @BindView(R.id.id_sign)
    SignatureView mSignView;
    @BindView(R.id.img_save)
    ImageView img_save;
    @BindView(R.id.tb_title)
    TitleBar tb_title;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private List<File> uploadFiles = new ArrayList<>();
    String id = "";
    String approveNodeId = "2";
    String approvePerson = "";
    String applyPerson = "";
    String approveType = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_signature;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void initView() {
        setTitle("手写签名");
        tb_title.setLeftTitle("返回");
        img_save.setOnClickListener(this);
        if (IsPad.isPad(mContext)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }
        id = getIntent().getStringExtra("id");
        approveNodeId = getIntent().getStringExtra("approveNodeId");
        approvePerson = getIntent().getStringExtra("approvePerson");
        applyPerson = getIntent().getStringExtra("applyPerson");
        approveType = getIntent().getStringExtra("approveType");
//        imageView.setOnClickListener(this);
        //  disableShowSoftInput(mContainer);
        mSignView.setSignatureCallBack(new SignatureView.ISignatureCallBack() {
            @Override
            public void onSignCompeleted(View view, Bitmap bitmap) {
                //String fileDir = getExternalCacheDir() + "signature/";
                String fileDir = FileUtil.PDF_PATH;
                String path = fileDir + SystemClock.elapsedRealtime() + ".png";
                File file = new File(fileDir);
                if (!file.exists()) {
                    file.mkdir();
                }
                bitmaps.add(bitmap);
                try {
                    mSignView.save(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadFiles.add(new File(path));
//                drawBitmaps(bitmap);
//                showFiles();
                String uriString;
                img_container.setImageURI(Uri.parse(path));
                MyApplication.getInstance().path_image = path;
                int mCurrentOrientation = getResources().getConfiguration().orientation;

                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {

                    // If current screen is portrait

                    Log.i("info", "portrait"); // 竖屏
                    approveWorkFlowInfo();


                } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {

                    // If current screen is landscape

                    Log.i("info", "landscape"); // 横屏
                }

            }
        });
    }

    @Override
    protected void initData() {

    }


    private void showFiles() {
        // tv.setText(Arrays.toString(uploadFiles.toArray()));
    }

    private void drawBitmaps(Bitmap b) {
        ImageSpan imgSpan = new ImageSpan(this, b);
        SpannableString spanString = new SpannableString("i");
        spanString.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //  mContainer.append(spanString);
    }

    public void delete() {
        if (bitmaps.size() > 0) {
            bitmaps.remove(bitmaps.size() - 1);
            File file = uploadFiles.get(uploadFiles.size() - 1);
            uploadFiles.remove(file);
            file.delete();
            // deleteText(mContainer);
            showFiles();
        }
    }

    /**
     * 获取EditText光标所在的位置
     */
    private int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }

    /**
     * 向EditText指定光标位置删除字符串
     */
    private void deleteText(EditText mEditText) {
        Log.e("TAG", mEditText.getText().toString());
        if (!TextUtils.isEmpty(mEditText.getText().toString())) {
            mEditText.getText().delete(getEditTextCursorIndex(mEditText) - 1, getEditTextCursorIndex(mEditText));
        }
    }

    public static void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private void approveWorkFlowInfo(String id, String approveNodeId, String approvePerson, String applyPerson, String approveType, String approveFlag, String approveOpinion) {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //公文流转id
            jsonObject.put("requestid", id);
            //节点id（公文模式=2会签模式，传空字符串，否则得有值）
            jsonObject.put("approveNodeId", "");
            //审批人id
            jsonObject.put("approvePerson", id);
            //申请人id
            jsonObject.put("applyPerson", id);
            //公文模式（1=审批，2=会签）
            jsonObject.put("approveType", id);
            //审批结果（1=打字意见，2=图片签写，3=语音签写）
            jsonObject.put("approveFlag", id);
            //"意见，图片base64，音频base64"
            jsonObject.put("approveOpinion", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("approveWorkFlowInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    toast("上传成功");
                } else {
                    toast("读取数据失败，请重新尝试");

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == img_save) {
            approveWorkFlowInfo();
//            finish();
        }
//        if (v == imageView) {
//            delete();
//        } else
    }

    //审批公文
    private void approveWorkFlowInfo() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            MyApplication.getInstance().path_type=2;
            jsonObject.put("requestid", id);
            jsonObject.put("approveNodeId", approveNodeId);
            jsonObject.put("approvePerson", MyApplication.getInstance().personPhone);
            jsonObject.put("applyPerson", applyPerson);
            jsonObject.put("approveType", approveType);
            jsonObject.put("approveFlag", MyApplication.getInstance().path_type);
            String approveOpinion = "";
//            if (MyApplication.getInstance().path_type == 1) {
//                approveOpinion = MyApplication.getInstance().path_content;
//            } else if (MyApplication.getInstance().path_type == 2) {
            approveOpinion = setBase64Photo(MyApplication.getInstance().path_image);
//            } else if (MyApplication.getInstance().path_type == 3) {
//                approveOpinion = setBase64Photo(MyApplication.getInstance().path_record);
//            }
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
                    MyApplication.getInstance().path_type = 2;
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
}
