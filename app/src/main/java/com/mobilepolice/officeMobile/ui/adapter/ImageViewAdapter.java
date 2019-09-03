package com.mobilepolice.officeMobile.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobilepolice.officeMobile.R;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends PagerAdapter {

    private ArrayList<String> imgs = new ArrayList<>();

    public void setData(List<String> asList) {
        if (asList != null) {
            imgs.clear();
            imgs.addAll(asList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView view = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.item_images, container, false);
        Glide.with(view).load(imgs.get(position)).into(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


}
