package com.mobilepolice.officeMobile.ui.adapter;

/**
 * Created by miao on 2019/4/8.
 */
public interface ItemTouchListener {
    void drag(int src, int dest);

    void swipe(int src);
}
