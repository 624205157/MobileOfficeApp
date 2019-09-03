package com.mobilepolice.officeMobile.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.HomeGoodBean;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class WorkMainAdapter extends BaseQuickAdapter<HomeGoodBean, BaseViewHolder> {
    public WorkMainAdapter() {
        super(R.layout.item_grid_work_main, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeGoodBean item) {

        ImageView imageView = helper.getView(R.id.imageView);
        imageView.setImageResource(item.getSrc());
//        ImageOptions imageOptions = new ImageOptions.Builder()
//                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
//                .setImageScaleType(ImageView.ScaleType.FIT_XY)
//                .setFailureDrawableId(R.mipmap.not_photo)
//                .setLoadingDrawableId(R.mipmap.not_photo)
//                .build();
//        if (!TextUtils.isEmpty(item.getImage())) {
//            x.image().bind(imageView, Config.UPDATE_SERVER_PHOTO + item.getImage(), imageOptions);
//        }
        helper.setText(R.id.tv_name, item.getName());
//        TextView item_content = helper.getView(R.id.item_content);
//        item_content.setVisibility(View.VISIBLE);
//        helper.setText(R.id.item_content, item.getName());
    }
}
