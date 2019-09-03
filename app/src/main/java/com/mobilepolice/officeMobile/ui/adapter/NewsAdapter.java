package com.mobilepolice.officeMobile.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.NewsListBean;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;

import static org.litepal.LitePalApplication.getContext;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class NewsAdapter extends BaseQuickAdapter<NewsListBean, BaseViewHolder> {
    private int type;

    public NewsAdapter(int type) {
        super(R.layout.item_grid_news, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsListBean item) {

        ImageView imageView = helper.getView(R.id.imageView);
        ImageOptions imageOptions = new ImageOptions.Builder()
                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.mipmap.not_photo)
                .setLoadingDrawableId(R.mipmap.not_photo)
                .build();
//        if (!TextUtils.isEmpty(item.getImg())) {
//            x.image().bind(imageView, item.getImg(), imageOptions);
//        }

        helper.setText(R.id.tv_title, item.getTitle().replaceAll("<br>", "\n"));
        /*设置id*/
        helper.setTag(R.id.tv_title, item.getId());

        helper.setText(R.id.tv_content, item.getCon().replaceAll("<br>","\n"));
//        helper.getView(R.id.tv_content).setVisibility(INVISIBLE);
        helper.setText(R.id.tv_datetime, item.getTime());
        ImageView img_hot = helper.getView(R.id.img_hot);

        if (type == 0) {
            Log.e("type", "" + type);
            helper.setText(R.id.section_tips, "新闻动态");
        } else if (type == 1) {
            Log.e("type", "" + type);
            helper.setText(R.id.section_tips, "地市动态");
        }
        if (item.isIsNew()) {
            img_hot.setVisibility(View.VISIBLE);

        } else {
            img_hot.setVisibility(View.GONE);
//            imageView.setImageResource(R.mipmap.news_img2);
//            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.news_img2);
            if (item.getImg() != null)
                Glide.with(imageView).load(item.getImg().replaceAll("10.106.18.75:8081", "192.168.20.228:7124")).into(imageView);
//            imageView.setImageBitmap(xfermode(bitmap, 10));
//            bitmap.recycle();
        }
//        Bitmap bitmap;
//        switch (Integer.parseInt(item.getId())) {
//            case 1:
//                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banner_img);
//                imageView.setImageBitmap(xfermode(bitmap, 10));
//                bitmap.recycle();
//                break;
//            case 2:
//                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banner_img2);
//                imageView.setImageBitmap(xfermode(bitmap, 10));
//                bitmap.recycle();
//                break;
//            case 3:
//                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banner_img3);
//                imageView.setImageBitmap(xfermode(bitmap, 10));
//                bitmap.recycle();
//                break;
//            case 4:
//                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banner_img4);
//                imageView.setImageBitmap(xfermode(bitmap, 10));
//                bitmap.recycle();
//                break;
//            case 5:
//                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banner_img5);
//                imageView.setImageBitmap(xfermode(bitmap, 10));
//                bitmap.recycle();
//                break;
//            case 6:
//                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.banner_img6);
//                imageView.setImageBitmap(xfermode(bitmap, 10));
//                bitmap.recycle();
//                break;
//        }
    }

    /**
     * 图片合成 之 圆角矩形
     *
     * @param src    源图片
     * @param radius 圆角半径
     * @return 圆角图片
     */
    private Bitmap xfermode(Bitmap src, float radius) {
        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_4444, true);
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(dest);

        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.right = bitmap.getWidth();
        rectF.top = 0;
        rectF.bottom = bitmap.getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        canvas.save();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        canvas.save();
        bitmap.recycle();
        return dest;
    }

    public void setData(ArrayList<NewsListBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
