package com.mobilepolice.officeMobile.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.ScheduleBean;
import com.mobilepolice.officeMobile.utils.TimeUtil;

import java.util.Date;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class ScheduleWorkStatusAdapter extends BaseQuickAdapter<ScheduleBean, BaseViewHolder> {
    public ScheduleWorkStatusAdapter() {
        super(R.layout.item_grid_work_status, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScheduleBean item) {

        ImageView imageView = helper.getView(R.id.imageView);

//        ImageOptions imageOptions = new ImageOptions.Builder()
//                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
//                .setImageScaleType(ImageView.ScaleType.FIT_XY)
//                .setFailureDrawableId(R.mipmap.not_photo)
//                .setLoadingDrawableId(R.mipmap.not_photo)
//                .build();
//        if (!TextUtils.isEmpty(item.getImage())) {
//            x.image().bind(imageView, Config.UPDATE_SERVER_PHOTO + item.getImage(), imageOptions);
//        }
        TextView tv_datetime = helper.getView(R.id.tv_datetime);
        TextView tv_person = helper.getView(R.id.tv_person);
        String dateime = "";
        if (!TextUtils.isEmpty(item.getCreateDate())) {
            Date temp = TimeUtil.strToDateLong(item.getCreateDate());
            dateime = TimeUtil.dateToStrLong(temp);
        }
        helper.setText(R.id.tv_flowto, dateime);
        helper.setText(R.id.tv_datetime, item.getCreateDate());
        //0未审批人1发起人2当前会签人3已会签人
        if (item.getType() == 0) {
            if (item.isFlag()) {
                helper.setText(R.id.tv_title, "审批人：" + item.getApprovePersonName());
            } else {
                helper.setText(R.id.tv_title, "会签人：" + item.getApprovePersonName());
            }
            imageView.setImageResource(R.mipmap.work_dsp);
            tv_datetime.setVisibility(View.GONE);
        } else if (item.getType() == 1) {
            helper.setText(R.id.tv_title, "发起人：" + item.getApprovePersonName());
            tv_person.setVisibility(View.GONE);
            imageView.setImageResource(R.mipmap.work_ysp2);
        } else if (item.getType() == 2) {
            if (item.isFlag()) {
                helper.setText(R.id.tv_title, "审批人：" + item.getApprovePersonName());
                helper.setText(R.id.tv_person, "正在审批");
            } else {
                helper.setText(R.id.tv_title, "会签人：" + item.getApprovePersonName());
                helper.setText(R.id.tv_person, "正在会签");
            }

            helper.setTextColor(R.id.tv_person, mContext.getResources().getColor(R.color.shortcut));
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.shortcut));
            tv_datetime.setVisibility(View.GONE);
            imageView.setImageResource(R.mipmap.work_ysp);
        } else if (item.getType() == 3) {
            if (item.isFlag()) {
                helper.setText(R.id.tv_title, "审批人：" + item.getApprovePersonName());
            } else {
                helper.setText(R.id.tv_title, "会签人：" + item.getApprovePersonName());
            }
            imageView.setImageResource(R.mipmap.work_ysp2);
        }
//        helper.setText(R.id.tv_flowto, item.getCreateDate());
//        helper.setText(R.id.tv_datetime, item.getCreateDate());


//  ImageView img_back = helper.getView(R.id.img_back);
//        img_back.setVisibility(View.VISIBLE);
//        helper.setText(R.id.item_content, item.getName());
    }
}
