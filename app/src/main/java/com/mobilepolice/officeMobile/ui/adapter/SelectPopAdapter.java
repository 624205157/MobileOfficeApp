package com.mobilepolice.officeMobile.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.SpinnerItem;

import java.util.List;

/**
 * Created by Administrator on 2017-04-05.
 */

public class SelectPopAdapter extends BaseAdapter {

    List<SpinnerItem> list;
    Context context;
    private LayoutInflater inflater;

    public SelectPopAdapter(Context context, List<SpinnerItem> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_pop, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(list.get(position).GetValue());
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
    }


}


