package com.mobilepolice.officeMobile.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mobilepolice.officeMobile.base.MyLazyFragment;
import com.mobilepolice.officeMobile.ui.activity.LoginActivity;
import com.mobilepolice.officeMobile.widget.DialogUtils;
import com.mobilepolice.officeMobile.widget.scrollable.ScrollableHelper;


/**
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 * Created by yuandl on 2016-11-17.
 */

public abstract class LazyLoadFragment extends MyLazyFragment implements ScrollableHelper.ScrollableContainer{
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected final String TAG = "LazyLoadFragment";
    private View view;
    private DialogUtils mProgressDialogUtils;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(setContentView(), container, false);
            mProgressDialogUtils = new DialogUtils(getActivity());
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }
//   public ImageOptions imageOptions = new ImageOptions.Builder()
//            // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
//            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//            .setFailureDrawableId(R.mipmap.not_photo)
//            .setLoadingDrawableId(R.mipmap.not_photo)
//            .build();
    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;

    }
//    public boolean isExpire(String result) {
//        if (JsonParseUtils.jsonToBooleanBind("401", result)) {
//            startActivity();
//            return true;
//        }
//        return false;
//    }

    public void startActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    //此方法只是关闭软键盘
    @Override
    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void showProgressDialog(boolean show, String message) {
        mProgressDialogUtils.showProgressDialog(show, message);
    }

    @Override
    public void showProgressDialog(boolean show) {
        mProgressDialogUtils.showProgressDialog(show);
    }
    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();
    protected abstract void initView();
    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {

        return (T) getContentView().findViewById(id);
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected void stopLoad() {
    }
}
