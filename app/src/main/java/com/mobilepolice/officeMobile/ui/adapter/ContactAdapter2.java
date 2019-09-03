package com.mobilepolice.officeMobile.ui.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.ContactsData;
import com.mobilepolice.officeMobile.utils.BitmapUtils;

import org.xutils.image.ImageOptions;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class ContactAdapter2 extends BaseQuickAdapter<ContactsData, BaseViewHolder> {
    public ContactAdapter2() {
        super(R.layout.item_grid_contacts, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsData item) {

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
        Bitmap bitmap = BitmapFactory.decodeResource(imageView.getResources(), R.mipmap.icon_user_faqi);

        imageView.setImageBitmap(BitmapUtils.ovalRectangleBitmap(bitmap, bitmap.getWidth() / 2));
//        bitmap.recycle();
        helper.setText(R.id.tv_name, item.getName());
    }
}
