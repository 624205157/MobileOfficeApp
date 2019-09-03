package com.mobilepolice.officeMobile.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.utils.FileUtil;
import com.mobilepolice.officeMobile.utils.TakePictureManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

/**
 * 公文发起
 */
public class DocumentMainActivity extends MyActivity {


    @BindView(R.id.imageView)
    ImageView imageView;
    private TakePictureManager takePictureManager;
    private Dialog dialog;
    /**
     * 判断的标识
     */
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private Bitmap photo = null;
    private String path;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAvatar_view();
            }
        });
        setTitle("公文发起");
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    private void startActivity() {
        Intent intent = new Intent(getApplicationContext(), DocumentApplyActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);
//        finish();
    }

    private void setAvatar_view() {

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
        dialog.show();
    }

    private void takePhoto() {
        takePictureManager = new TakePictureManager(this);
        //开启裁剪 比例 1:3 宽高 350 350  (默认不裁剪)
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        Log.i("view" , "height" +displayMetrics.heightPixels);
//        Log.i("view" , "width" +displayMetrics.widthPixels);
        // takePictureManager.setTailor(1, 1, displayMetrics.widthPixels, displayMetrics.heightPixels);
        //拍照方式
        takePictureManager.startTakeWayByCarema();
        //回调
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            //成功拿到图片,isTailor 是否裁剪？ ,outFile 拿到的文件 ,filePath拿到的URl
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                getTakesPhoto(outFile);
            }

            //失败回调
            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {
                Log.e("==w", deniedPermissions.toString());
            }
        });
    }

    private void getTakesPhoto(File outFile) {
        Bitmap photoBmp = null;
        try {
            photoBmp = TakePictureManager.getBitmapFormUri(DocumentMainActivity.this, Uri.fromFile(outFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int degree = TakePictureManager.getBitmapDegree(outFile.getAbsolutePath());
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = TakePictureManager.rotateBitmapByDegree(photoBmp, degree);
        //Bitmap map = takePictureManager.decodeUriAsBitmap(filePath);
        if (newbitmap != null) {
            //uploadPhoto(newbitmap);
          //  path = outFile.getAbsolutePath();
            saveBitmap(newbitmap);
          //  startActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    private void selectPhoto() {
        takePictureManager = new TakePictureManager(this);
        takePictureManager.startTakeWayByAlbum();
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                Bitmap photoBmp = null;
                try {
                    photoBmp = TakePictureManager.getBitmapFormUri(DocumentMainActivity.this, Uri.fromFile(outFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int degree = TakePictureManager.getBitmapDegree(outFile.getAbsolutePath());
                /**
                 * 把图片旋转为正的方向
                 */
                Bitmap newbitmap = TakePictureManager.rotateBitmapByDegree(photoBmp, degree);
                if (newbitmap != null) {
                    //uploadPhoto(newbitmap);
                    saveBitmap(newbitmap);
                   // startActivity();
                }
            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {

            }

        });
    }
    /** 保存方法 */
    public void saveBitmap(Bitmap newbitmap ) {
        //Log.e(TAG, "保存图片");
        File f = new File(FileUtil.HEADIMAGE_PATH, String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            newbitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
          //  Log.i(TAG, "已经保存");
            path = f.getAbsolutePath();
            startActivity();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 从相册选择图片来源
     */
    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }


    /**
     * 把Bitmap转Byte
     */
    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
