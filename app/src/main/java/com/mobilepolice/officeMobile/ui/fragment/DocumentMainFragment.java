package com.mobilepolice.officeMobile.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.bean.DocPendingBean;
import com.mobilepolice.officeMobile.bean.PendingWorkDetailedBean;
import com.mobilepolice.officeMobile.soap.SoapParams;
import com.mobilepolice.officeMobile.soap.WebServiceUtils;
import com.mobilepolice.officeMobile.ui.activity.DocumentPendingWorkDetailedActivity;
import com.mobilepolice.officeMobile.ui.activity.DocumentSignatureActivity;
import com.mobilepolice.officeMobile.utils.FastJsonHelper;
import com.mobilepolice.officeMobile.utils.JsonParseUtils;
import com.mobilepolice.officeMobile.widget.DrawingView;
import com.mobilepolice.officeMobile.widget.scrollable.ScrollableHelper;
//import com.tencent.smtt.sdk.TbsReaderView;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

/**
 * 公文正文
 */
public class DocumentMainFragment extends MyLazyFragment implements ScrollableHelper.ScrollableContainer//, TbsReaderView.ReaderCallback
{

    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    @BindView(R.id.img_screenshot)
    DrawingView mDrawingView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_save)
    LinearLayout ll_save;
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

    public static DocumentMainFragment newInstance() {
        return new DocumentMainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_document_main;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        DocumentPendingWorkDetailedActivity activity = (DocumentPendingWorkDetailedActivity) getFragmentActivity();
        id = activity.getId();
        docPendingBean = activity.getDocPendingBean();
        initPaintMode();
        // initViews();
        // findApplyInfo(id);
    }

    private void showImage(String path) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.mipmap.not_photo)
                .setLoadingDrawableId(R.mipmap.not_photo)
                .build();
        if (!TextUtils.isEmpty(path)) {
            x.image().bind(imageView, path, imageOptions);
        }
    }

    Bitmap bitmap;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadImage(bitmap);
            }
            showProgressDialog(false);
        }
    };

    public Bitmap returnBitMap(final String url) {
        showProgressDialog(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }

    @Override
    protected void initData() {
        //  showtest();// showPdf();
        if (docPendingBean != null && docPendingBean.getObj() != null) {
            String img = docPendingBean.getObj().getImg();
            img = img.replaceAll("10.106.12.104:8789", "192.168.20.228:7121");
            String[] imgs = img.split(",");

            if (imgs != null && imgs.length > 0) {
                showImage(imgs[0]);
                returnBitMap(imgs[0]);
            } else {
                showImage(docPendingBean.getObj().getImg());
                returnBitMap(docPendingBean.getObj().getImg());
            }
            approveNodeId = docPendingBean.getAttributes().getApproveNodeId();
            applyPerson = docPendingBean.getObj().getApplyPerson();
            approveType = docPendingBean.getObj().getSchema();
        }
        ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   startActivity(DocumentSignatureActivity.class);


//                Intent intent = new Intent(getActivity(), DocumentExamineActivity.class);
                Intent intent = new Intent(getActivity(), DocumentSignatureActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("approveNodeId", approveNodeId);
                intent.putExtra("approvePerson", MyApplication.getInstance().personPhone);
                intent.putExtra("applyPerson", applyPerson);
                intent.putExtra("approveType", approveType);
                startActivity(intent);
            }
        });
    }

    private void initPaintMode() {
        mDrawingView.initializePen();
        mDrawingView.setPenSize(10);
        mDrawingView.setPenColor(getActivity().getResources().getColor(R.color.red));
    }

    public void loadImage(Bitmap bitmap) {
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.image);

        mDrawingView.loadImage(bitmap);
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
                    String approveNodeIds = JSON.parseObject(attributes).getString(
                            "approveNodeId");
                    MyApplication.getInstance().approveNodeId = approveNodeIds;
                    PendingWorkDetailedBean objbean = FastJsonHelper.deserialize(obj,
                            PendingWorkDetailedBean.class);
                    if (objbean != null) {
                        approveNodeId = approveNodeIds;
                        applyPerson = objbean.getApplyPerson();
                        //公文模式
                        approveType = objbean.getSchema();
                        MyApplication.getInstance().path_main_image = objbean.getImg();
                        MyApplication.getInstance().DocSchema = objbean.getSchema();
                        showImage("");

                    }
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                    getActivity().finish();
                }
            }
        });
    }

    private static final String TAG = "OfficeFileReader";
    //TbsReaderView tbsReaderView;
    String filePath, cachePath;

    public static final String VIEW_DETAIL = "view:detail";
    public static final String FILE_ID = "file:office";

    protected void initViews() {
        //StatusBarUtil.darkMode(this);
//        tbsReaderView = new TbsReaderView(getActivity(), this);
//        rlMain.addView(tbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//        filePath = MyApplication.getInstance().path_pdf;// getIntent().getStringExtra(FILE_ID);FileUtil.PDF_PATH + "1544976581192.pdf";//
//        Log.e(TAG, "initView: " + filePath);
//        cachePath = getActivity().getExternalCacheDir().getPath();
////        ViewCompat.setTransitionName(mBinding.rlMain,VIEW_DETAIL);
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                boolean result = tbsReaderView.preOpen(getFileType(filePath), false);
//                if (result) {
//                    handler.sendEmptyMessage(1);
//                } else {
//
//                }
//            }
//        }.start();
//        openFile(filePath, cachePath);
    }


//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                openFile(filePath, cachePath);
//            } else {
//                toast("打开失败");
//            }
//        }
//    };

//    private void openFile(String filePath, String tempPath) {
//        Bundle bundle = new Bundle();
//        bundle.putString("filePath", filePath);
//        bundle.putString("tempPath", tempPath);
//        //  tbsReaderView.openFile(bundle);
//    }

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

//    @Override
//    public void onCallBackAction(Integer integer, Object o, Object o1) {
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(tbsReaderView!=null) {
//            tbsReaderView.onStop();
//        }
    }


    private void upWorkFlowApplyInfo() {
        showProgressDialog(true, "正在上传PDF文件");
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
                @Override
                public void result(String result) {
                    showProgressDialog(false);
                    if (JsonParseUtils.jsonToBoolean(result)) {
                        toast("上传PDF成功");
                    } else {
                        String msg = JsonParseUtils.jsonToMsg(result);
                        toast(msg);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            showProgressDialog(false);
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


    @Override
    public View getScrollableView() {
        return null;
    }
    /***
     *   private void showtest() {
     * //        String path =  Environment
     * //                .getExternalStorageDirectory().toString() + "/haha.pdf";//Environment.getExternalStorageDirectory().getAbsolutePath() + "/wgl123456.pdf";
     *         String path = MyApplication.getInstance().path_pdf;// FileUtil.PDF_PATH + "test.pdf";
     *         if (!TextUtils.isEmpty(path)) {
     *             File file = new File(path);
     * //           ll_save.setVisibility(View.VISIBLE);
     *             pdfView.setVisibility(View.VISIBLE);
     *             pdfView //.fromUri(uri)
     *                     .fromFile(file)
     *                     .defaultPage(0)
     *                     .onPageChange(new OnPageChangeListener() {
     *                         @Override
     *                         public void onPageChanged(int page, int pageCount) {
     *
     *                         }
     *                     })
     *                     .enableAnnotationRendering(true)
     *                     .onLoad(new OnLoadCompleteListener() {
     *                         @Override
     *                         public void loadComplete(int nbPages) {
     *
     *                         }
     *                     })
     *                     .scrollHandle(new DefaultScrollHandle(getContext()))
     *                     .spacing(10) // in dp
     *                     .onPageError(new OnPageErrorListener() {
     *                         @Override
     *                         public void onPageError(int page, Throwable t) {
     *
     *                         }
     *                     })
     *                     .load();
     *         } else {
     *             //ll_save.setVisibility(View.GONE);
     *             // pdfView.setVisibility(View.GONE);
     *         }
     *     }
     *
     *     private void showPdf() {
     *         try {
     *             String path = FileUtil.PDF_PATH + "haha.pdf";//Environment.getExternalStorageDirectory().getAbsolutePath() + "/wgl123456.pdf";
     *             File file = new File(path);
     *             if (file.exists()) {
     *                 pdfView.setVisibility(View.VISIBLE);
     *                 PDFView.Configurator configurator = pdfView.fromFile(file);
     *                 if (configurator != null) {
     *                     configurator.load();
     *                 }
     *                 pdfView.fromFile(file).load();
     *             } else {
     *                 pdfView.setVisibility(View.GONE);
     *             }
     *         } catch (Exception ex) {
     *
     *         }
     *     }
     */
}