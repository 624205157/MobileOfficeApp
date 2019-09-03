package com.mobilepolice.officeMobile.ui.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by miao on 2018/9/6.
 */
public abstract class BaseAdapter<T, VH extends BaseAdapter.ViewHolder> extends android.widget.BaseAdapter {

    protected Context context;
    protected List<T> data;

    public BaseAdapter(List<T> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (context == null) context = parent.getContext();
        VH vh;
        if (convertView == null) {
            convertView = getLayout(parent);
            vh = create(convertView);
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }
        bindView(vh, position, getItem(position));
        return convertView;
    }

    protected abstract VH create(View view);

    protected abstract void bindView(VH vh, int position, T item);

    protected View getLayout(ViewGroup parent) {
        return View.inflate(parent.getContext(), getLayout(), null);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    protected abstract int getLayout();

    public abstract static class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
