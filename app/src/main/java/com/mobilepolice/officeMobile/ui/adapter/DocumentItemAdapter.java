package com.mobilepolice.officeMobile.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.DocumentInfo;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;

public class DocumentItemAdapter extends BaseAdapter<DocumentInfo.ObjBean, DocumentItemAdapter.DocumentItemViewHolder> {


    public DocumentItemAdapter(List<DocumentInfo.ObjBean> data) {
        super(data);
    }

    @Override
    protected DocumentItemViewHolder create(View view) {
        return new DocumentItemViewHolder(view);
    }

    @Override
    protected void bindView(DocumentItemViewHolder vh, int position, DocumentInfo.ObjBean item) {
        String[] imgs = item.getImg().split(",");
        ImageOptions imageOptions = new ImageOptions.Builder()
                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.mipmap.not_photo)
                .setLoadingDrawableId(R.mipmap.not_photo)
                .build();
        if (imgs != null && imgs.length > 0)
            x.image().bind(vh.imageView, imgs[0], imageOptions);
//            Glide.with(vh.icon).load(imgs[0]).into(vh.icon);
        else x.image().bind(vh.imageView, item.getImg(), imageOptions);


        vh.tv_title.setText(item.getTitel());
        String Level = "";
        if (item.getUrgentLevel().equals("1")) {

            vh.simpleRatingBar.setRating(1f);

            Level = "一级";
        } else if (item.getUrgentLevel().equals("2")) {
            vh.simpleRatingBar.setRating(3f);
            Level = "二级";
        } else if (item.getUrgentLevel().equals("3")) {
            Level = "三级";
            vh.simpleRatingBar.setRating(5f);
        }
        vh.tv_store_addcomment.setText("紧急程度:" + Level);
        vh.tv_person.setText("发起人：" + item.getApplyPerson());
        vh.tv_datetime.setText("时间：" + item.getCreateDate());
    }

    @Override
    protected int getLayout() {
        return R.layout.item_grid_pending_work;
    }

    class DocumentItemViewHolder extends BaseAdapter.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tv_store_addcomment)
        TextView tv_store_addcomment;
        @BindView(R.id.tv_person)
        TextView tv_person;
        @BindView(R.id.tv_datetime)
        TextView tv_datetime;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.ratingbar)
        SimpleRatingBar simpleRatingBar;

        public DocumentItemViewHolder(View view) {
            super(view);
        }
    }
}
