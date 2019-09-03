package com.mobilepolice.officeMobile.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.ApplyInfoBean;

import java.util.ArrayList;

public class FinishWorkAdapter extends BaseQuickAdapter<ApplyInfoBean.AttributesBean.ApproveListInfoBean, BaseViewHolder> {
    public FinishWorkAdapter() {
        super(R.layout.item_finish_work_details, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyInfoBean.AttributesBean.ApproveListInfoBean item) {
        helper.setText(R.id.name, item.getApprovePersonName());
        if (TextUtils.isEmpty(item.getApproveOpinion())) {
            helper.getView(R.id.info).setVisibility(View.GONE);
            helper.getView(R.id.infoTitle).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.info).setVisibility(View.VISIBLE);
            helper.getView(R.id.infoTitle).setVisibility(View.VISIBLE);
            helper.setText(R.id.info, item.getApproveOpinion());
        }
        ImageView view = helper.getView(R.id.img);
        Glide.with(view).load(item.getApproveResultFile()).into(view);
    }
}
