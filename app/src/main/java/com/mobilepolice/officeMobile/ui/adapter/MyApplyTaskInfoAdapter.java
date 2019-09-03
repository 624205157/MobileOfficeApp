package com.mobilepolice.officeMobile.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.ApplyTaskDetails;
import com.mobilepolice.officeMobile.bean.PendingWorkBean;
import com.mobilepolice.officeMobile.ui.adapter.BaseAdapter;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyApplyTaskInfoAdapter extends BaseQuickAdapter<ApplyTaskDetails.ObjBean, BaseViewHolder> {
    public MyApplyTaskInfoAdapter() {
        super(R.layout.item_grid_pending_work, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyTaskDetails.ObjBean item) {

        ImageView imageView = helper.getView(R.id.imageView);
//        imageView.setImageResource(item.getSrc());
        ImageOptions imageOptions = new ImageOptions.Builder()
                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.mipmap.not_photo)
                .setLoadingDrawableId(R.mipmap.not_photo)
                .build();
        if (!TextUtils.isEmpty(item.getApplyOffWordFile()) && !item.getApplyOffWordFile().contains(",")) {
            x.image().bind(imageView, item.getApplyOffWordFile(), imageOptions);
        } else if (!TextUtils.isEmpty(item.getApplyOffWordFile()) && item.getApplyOffWordFile().contains(",")) {
            String[] imgs = item.getApplyOffWordFile().split(",");
            x.image().bind(imageView, imgs[0], imageOptions);
        }
        helper.setText(R.id.tv_title, item.getTitle());
        SimpleRatingBar simpleRatingBar = helper.getView(R.id.ratingbar);
        String Level = "";
        if (item.getUrgentLevel().equals("1")) {

            simpleRatingBar.setRating(1f);

            Level = "一级";
        } else if (item.getUrgentLevel().equals("2")) {
            simpleRatingBar.setRating(3f);
            Level = "二级";
        } else if (item.getUrgentLevel().equals("3")) {
            Level = "三级";
            simpleRatingBar.setRating(5f);
        }
        helper.setText(R.id.tv_store_addcomment, "紧急程度:" + Level);
        helper.setText(R.id.tv_person, "发起人：" + item.getApplyPersonName());
        helper.setText(R.id.tv_datetime, "时间：" + item.getCreateDate());
        //  helper.setText(R.id.tv_dsp, item.getOverFlag());

//        TextView item_content = helper.getView(R.id.item_content);
//        item_content.setVisibility(View.VISIBLE);
//        helper.setText(R.id.item_content, item.getName());
    }
}
