package com.mobilepolice.officeMobile.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.ApprovePersonBean;
import com.mobilepolice.officeMobile.bean.DocApplyInfoBean;
import com.mobilepolice.officeMobile.bean.SpinnerItem;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.adapter.SelectPopAdapter;
import com.mobilepolice.officeMobile.utils.App;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.utils.TakePictureManager;

import org.json.JSONArray;
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
import butterknife.OnClick;


/**
 * 公文申请
 */
public class DocumentApplyActivity extends MyActivity implements View.OnClickListener {


    @BindView(R.id.ll_photo)
    LinearLayout ll_photo;
    @BindView(R.id.img_photo)
    ImageView img_photo;
    @BindView(R.id.et_doc_title)
    EditText et_doc_title;

    @BindView(R.id.tv_doc_department)
    TextView tv_doc_department;

    @BindView(R.id.ll_doc_type)
    LinearLayout ll_doc_type;
    @BindView(R.id.tv_doc_type)
    TextView tv_doc_type;

    @BindView(R.id.et_doc_count)
    EditText et_doc_count;

    @BindView(R.id.ll_doc_secretrank)
    LinearLayout ll_doc_secretrank;
    @BindView(R.id.tv_doc_secretrank)
    TextView tv_doc_secretrank;

    @BindView(R.id.ll_doc_EmergencyLevel)
    LinearLayout ll_doc_EmergencyLevel;
    @BindView(R.id.tv_doc_EmergencyLevel)
    TextView tv_doc_EmergencyLevel;
    @BindView(R.id.ll_doc_module)
    LinearLayout ll_doc_module;
    @BindView(R.id.tv_doc_module)
    TextView tv_doc_module;

    @BindView(R.id.tv_doc_approver_title)
    TextView tv_doc_approver_title;
    @BindView(R.id.tv_doc_approver)
    TextView tv_doc_approver;

    @BindView(R.id.view_signer)
    View view_signer;
    @BindView(R.id.ll_signer)
    LinearLayout ll_signer;
    @BindView(R.id.tv_doc_signer)
    TextView tv_doc_signer;

    @BindView(R.id.view_signer2)
    View view_signer2;
    @BindView(R.id.ll_signer2)
    LinearLayout ll_signer2;
    @BindView(R.id.tv_doc_signer2)
    TextView tv_doc_signer2;


    @BindView(R.id.view_signer3)
    View view_signer3;
    @BindView(R.id.ll_signer3)
    LinearLayout ll_signer3;
    @BindView(R.id.tv_doc_signer3)
    TextView tv_doc_signer3;

    @BindView(R.id.view_signer4)
    View view_signer4;
    @BindView(R.id.ll_signer4)
    LinearLayout ll_signer4;
    @BindView(R.id.tv_doc_signer4)
    TextView tv_doc_signer4;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.img_photo2)
    ImageView photo2;
    @BindView(R.id.more)
    TextView more;

    String path;
    DocApplyInfoBean bean;

    List<ApprovePersonBean> listpb;
    private PopupWindow pop;
    private ListView listView = null;
    private SelectPopAdapter mSelectAdapter;
    private List<SpinnerItem> listOrderStatus = new ArrayList<>();
    List<ApprovePersonBean> approvePerson = new ArrayList<>();
    String tv_doc_type_id;
    String tv_doc_secretrank_id;
    String tv_doc_EmergencyLevel_id;
    String tv_doc_module_id;

    ArrayList<String> filePath = new ArrayList<>();

    private static final int FILE_EDITOR_REQUEST = 0x7f;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_apply;
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
        selectPics();
        more.setVisibility(View.GONE);
//        menu = new PopupMenu(this, ll_photo);
//        menu.inflate(R.menu.pic_options);
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.edit:
//
//                        break;
//                    case R.id.add:

//                        break;
//                }
//                return true;
//            }
//        });

        setTitle("公文发起");
        ll_doc_type.setOnClickListener(this);
        ll_doc_secretrank.setOnClickListener(this);
        ll_doc_EmergencyLevel.setOnClickListener(this);
        ll_doc_module.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        path = getIntent().getStringExtra("path");
        if (!TextUtils.isEmpty(path)) {
            filePath.add(path);
            showImage();
        } else {
            toast("拍摄照片异常");
            finish();
        }
        tv_doc_department.setText(MyApplication.getInstance().personDeptName);
        tv_doc_module.setText("审批");
        tv_doc_module_id = "1";
        getFindApprovePerson(tv_doc_module_id);
    }


    @Override
    public void onClick(View v) {
        if (v == ll_doc_type) {
            hintKbTwo();
            DialogDocType(v);
        } else if (v == ll_doc_secretrank) {
            hintKbTwo();
            DialogDocSecretrank(v);
        } else if (v == ll_doc_EmergencyLevel) {
            hintKbTwo();
            DialogDocEmergencyLevel(v);
        } else if (v == ll_doc_module) {
            hintKbTwo();
            DialogDocMode(v);
        } else if (v == tv_save) {
            bean = new DocApplyInfoBean();
            save();
        }
    }

    private void showImage() {
        File outFile = new File(path);
        int degree = TakePictureManager.getBitmapDegree(outFile.getAbsolutePath());
        Bitmap photoBmp = null;
        try {
            photoBmp = TakePictureManager.getBitmapFormUri(DocumentApplyActivity.this, Uri.fromFile(outFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = TakePictureManager.rotateBitmapByDegree(photoBmp, degree);
        //Bitmap map = takePictureManager.decodeUriAsBitmap(filePath);
        if (newbitmap != null) {
            img_photo.setImageBitmap(newbitmap);
        }
    }

    private void getFindApprovePerson(String approveType) {
        SoapParams params = new SoapParams();
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //公文模式（1=审批，2=会签）
            jsonObject.put("approveType", approveType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApprovePerson", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    listpb = FastJsonHelper.deserializeList(obj,
                            ApprovePersonBean.class);
//                    Collections.reverse(listpb);
                    if (listpb != null && listpb.size() > 0) {
                        approvePerson = new ArrayList<>();
                        view_signer.setVisibility(View.GONE);
                        ll_signer.setVisibility(View.GONE);
                        view_signer2.setVisibility(View.GONE);
                        ll_signer2.setVisibility(View.GONE);
                        view_signer3.setVisibility(View.GONE);
                        ll_signer3.setVisibility(View.GONE);
                        if (listpb.size() > 0) {
                            if (approveType.equals(2)) {
                                tv_doc_approver_title.setText("会签人");
                            }
                            tv_doc_approver.setText(listpb.get(0).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(0).getId(), listpb.get(0).getName()));
                        }
                        if (listpb.size() > 1) {
                            view_signer.setVisibility(View.VISIBLE);
                            ll_signer.setVisibility(View.VISIBLE);
                            tv_doc_signer.setText(listpb.get(1).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(1).getId(), listpb.get(1).getName()));
                        }
                        if (listpb.size() > 2) {
                            view_signer2.setVisibility(View.VISIBLE);
                            ll_signer2.setVisibility(View.VISIBLE);
                            tv_doc_signer2.setText(listpb.get(2).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(2).getId(), listpb.get(2).getName()));
                        }
                        if (listpb.size() > 3) {
                            view_signer3.setVisibility(View.VISIBLE);
                            ll_signer3.setVisibility(View.VISIBLE);
                            tv_doc_signer3.setText(listpb.get(3).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(3).getId(), listpb.get(3).getName()));
                        }
                        if (listpb.size() > 4) {
                            view_signer4.setVisibility(View.VISIBLE);
                            ll_signer4.setVisibility(View.VISIBLE);
                            tv_doc_signer4.setText(listpb.get(4).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(4).getId(), listpb.get(4).getName()));
                        }
                    }
                }

            }
        });
    }

    //发文类型
    private void DialogDocType(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "通知"));
        listOrderStatus.add(new SpinnerItem("2", "下发"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_type_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_type.setText(name);
                dismissPopWindow();
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    //秘密等级
    private void DialogDocSecretrank(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "一级"));
        listOrderStatus.add(new SpinnerItem("2", "二级"));
        listOrderStatus.add(new SpinnerItem("3", "三级"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_secretrank_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_secretrank.setText(name);
                dismissPopWindow();

            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    //紧急程度
    private void DialogDocEmergencyLevel(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "一级"));
        listOrderStatus.add(new SpinnerItem("2", "二级"));
        listOrderStatus.add(new SpinnerItem("3", "三级"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_EmergencyLevel_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_EmergencyLevel.setText(name);
                dismissPopWindow();

            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    //公文模式
    private void DialogDocMode(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "审批"));
        listOrderStatus.add(new SpinnerItem("2", "会签"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_module_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_module.setText(name);
                dismissPopWindow();
                getFindApprovePerson(tv_doc_module_id);
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void dismissPopWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) this).getWindow().setAttributes(lp);
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

    private void save() {
        if (TextUtils.isEmpty(et_doc_title.getText().toString())) {
            toast("标题不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tv_doc_type.getText().toString())) {
            toast("发文类型不能为空！");
            return;
        }
        if (TextUtils.isEmpty(et_doc_count.getText().toString())) {
            toast("发文份数不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tv_doc_secretrank_id)) {
            toast("秘密等级不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tv_doc_EmergencyLevel_id)) {
            toast("紧急程度不能为空！");
            return;
        }

        showProgressDialog(true);
//        bean.setApplyPerson("17600194545");
//        bean.setTitel(et_doc_title.getText().toString());
//        bean.setRequestFlag(tv_doc_type.getText().toString());
//        bean.setRequestNum(et_doc_count.getText().toString());
//        bean.setSecretLevel(tv_doc_secretrank_id);
//        bean.setUrgentLevel(tv_doc_EmergencyLevel_id);
//        bean.setSchema(tv_doc_module_id);
//        bean.setDepartmentId(MyApplication.getInstance().personDeptid);
//        bean.setApprovePerson(approvePerson);
        //bean.setImg(setBase64Photo(path));
//        String json = JSON.toJSONString(bean);
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", App.userCode);
            jsonObject.put("titel", et_doc_title.getText().toString());
            jsonObject.put("requestFlag", tv_doc_type.getText().toString());
            jsonObject.put("requestNum", et_doc_count.getText().toString());
            jsonObject.put("secretLevel", tv_doc_secretrank_id);
            jsonObject.put("urgentLevel", tv_doc_EmergencyLevel_id);
            jsonObject.put("schema", tv_doc_module_id);
            jsonObject.put("departmentId", MyApplication.getInstance().personDeptid);
            // String approvePersons = JSON.toJSONString(approvePerson);
            JSONArray array = new JSONArray();
            JSONObject tmpObj = null;
//            Collections.reverse(approvePerson);
            int count = approvePerson.size();
            for (int i = 0; i < count; i++) {
                tmpObj = new JSONObject();
                tmpObj.put("id", approvePerson.get(i).getId());
                tmpObj.put("name", approvePerson.get(i).getName());
                array.put(tmpObj);
            }
            //String personInfos = .toString(); // 将JSONArray转换得到String
//            jsonObject.put("personInfos" , personInfos);   // 获得JSONObject的String
//            String personInfos = jsonArray.toString(); // 将JSONArray转换得到String
            jsonObject.put("approvePerson", array);
            StringBuilder builder = new StringBuilder();
            JSONArray imgs = new JSONArray();

            for (int i = 0; i < filePath.size(); i++) {
                imgs.put(setBase64Photo(filePath.get(i)));
            }
            jsonObject.put("img", imgs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String jsons = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", jsons);

        WebServiceUtils.getPersonDeptName("saveWorkFlowApplyInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                    finish();
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                }

            }
        });
    }


    @OnClick(R.id.more)
    void pictureOptions() {
        startActivityForResult(new Intent(DocumentApplyActivity.this, ImageDocDetailsActivity.class).putExtra("fileList", filePath), 1002);
    }

    @OnClick(R.id.add_ext)
    void addImage() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }


    private void selectPics() {
        dialog = new AlertDialog.Builder(mContext).
                setTitle("请选择").
                setMessage("请选择相机").
                setPositiveButton("相 机", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        //getCamera();
                        takePhoto();

                    }
                })
                .setNegativeButton("相 册", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        selectPhoto();

                    }
                }).create();
    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
        } else {
            takePhoto0();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            filePath.add(targetPath);
            targetPath = null;
            if (filePath.size() >= 2) {
                photo2.setVisibility(View.VISIBLE);
                Glide.with(photo2).load(filePath.get(1)).into(photo2);
            } else {
                photo2.setVisibility(View.GONE);
            }

            if (filePath.size() > 2) {
                more.setVisibility(View.VISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }
        } else if (requestCode == 1001 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            Log.e("imageUri:", imageUri + "");
            String selectPhoto = getRealPathFromUri(this, imageUri);
            Log.e("onActivityResult: ", selectPhoto);
            filePath.add(selectPhoto);
            if (filePath.size() >= 2) {
                photo2.setVisibility(View.VISIBLE);
                Glide.with(photo2).load(filePath.get(1)).into(photo2);
            } else {
                photo2.setVisibility(View.GONE);
            }
            if (filePath.size() > 2) {
                more.setVisibility(View.VISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }
        } else if (requestCode == 1002 && resultCode == RESULT_OK) {
            filePath = data.getStringArrayListExtra("fileList");
            if (filePath.size() >= 2) {
                photo2.setVisibility(View.VISIBLE);
                Glide.with(img_photo).load(filePath.get(0)).into(img_photo);
                Glide.with(photo2).load(filePath.get(1)).into(photo2);
            } else {
                photo2.setVisibility(View.GONE);
            }
            if (filePath.size() > 2) {
                more.setVisibility(View.VISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        if (context == null || uri == null) {
            return null;
        }
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Byfile(context, uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Api11To18(context, uri);
        }
//        int sdkVersion = Build.VERSION.SDK_INT;
//        if (sdkVersion < 11) {
//            // SDK < Api11
//            return getRealPathFromUri_BelowApi11(context, uri);
//        }
////        if (sdkVersion < 19) {
////             SDK > 11 && SDK < 19
////            return getRealPathFromUri_Api11To18(context, uri);
//            return getRealPathFromUri_ByXiaomi(context, uri);
////        }
//        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);//没用到
    }

    //针对图片URI格式为Uri:: file:///storage/emulated/0/DCIM/Camera/IMG_20170613_132837.jpg
    private static String getRealPathFromUri_Byfile(Context context, Uri uri) {
        String uri2Str = uri.toString();
        String filePath = uri2Str.substring(uri2Str.indexOf(":") + 3);
        return filePath;
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = null;

        wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = {id};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * //适配api11-api18,根据uri获取图片的绝对路径。
     * 针对图片URI格式为Uri:: content://media/external/images/media/1028
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Log.e("handleImgeOnKitKat: ", imagePath);
    }

    /**
     * 4.4及以上系统处理图片的方法
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

//        if (DocumentsContract.isDocumentUri(this, uri)) {
        //如果是document类型的uri，则通过document id处理
        String docId = DocumentsContract.getDocumentId(uri);
        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
            //解析出数字格式的id
            String id = docId.split(":")[1];
            String selection = MediaStore.Images.Media._ID + "=" + id;
            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
            imagePath = getImagePath(contentUri, null);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        Log.e("handleImgeOnKitKat: ", imagePath);
        //根据图片路径显示图片
//        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePhoto0();
        } else {
            Log.e("PermissionsResult: ", "permission denied");
        }
    }

    private void takePhoto0() {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir = new File(root, "imgCache");
        dir.mkdirs();
        File imgFile = new File(dir, System.currentTimeMillis() + ".png");
        targetPath = imgFile.getAbsolutePath();
        Uri imgUri = null;

        //判断当前手机版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imgUri = FileProvider.getUriForFile(mContext, "com.mobilepolice.officeMobile.fileprovider", imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        this.startActivityForResult(intent, 1000);
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, 1001);
    }

    private PopupMenu menu;
    private AlertDialog dialog;

    private String targetPath;
}
