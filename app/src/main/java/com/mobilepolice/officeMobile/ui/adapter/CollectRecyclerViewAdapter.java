//package com.mobilepolice.officeMobile.ui.adapter;
//
//import android.support.v7.widget.RecyclerView;
//
//
//import com.mobilepolice.officeMobile.R;
//import com.mobilepolice.officeMobile.bean.NormalModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
//import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
//import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;
//
//
///**
// * 收件箱
// */
//public class CollectRecyclerViewAdapter extends BGARecyclerViewAdapter<NormalModel> {
//    /**
//     * 当前处于打开状态的item
//     */
//    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();
//
//    public CollectRecyclerViewAdapter(RecyclerView recyclerView) {
//        super(recyclerView, R.layout.item_collect_bgaswipe);
//    }
//
//    @Override
//    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
//        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
//        swipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
//            @Override
//            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
//                closeOpenedSwipeItemLayoutWithAnim();
//                mOpenedSil.add(swipeItemLayout);
//            }
//
//            @Override
//            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
//                mOpenedSil.remove(swipeItemLayout);
//            }
//
//            @Override
//            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
//                closeOpenedSwipeItemLayoutWithAnim();
//            }
//        });
//        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_delete);
//        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_delete);
//        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_more);
//        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_more);
//    }
//
//    @Override
//    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, NormalModel model) {
//        viewHolderHelper.setText(R.id.tv_item_collect_name, model.mEmail).setText(R.id.tv_item_collect_datetime, model.mDate).setText(R.id.tv_item_collect_title, model.mTitle).setText(R.id.tv_item_collect_detail, model.mDetail);
//        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
////        if (position % 3 == 0) {
////            swipeItemLayout.setSwipeAble(false);
////        } else {
//        swipeItemLayout.setSwipeAble(true);
//        //       }
//    }
//
//    public void closeOpenedSwipeItemLayoutWithAnim() {
//        for (BGASwipeItemLayout sil : mOpenedSil) {
//            sil.closeWithAnim();
//        }
//        mOpenedSil.clear();
//    }
//}