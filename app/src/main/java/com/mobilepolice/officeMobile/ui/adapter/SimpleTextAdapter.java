package com.mobilepolice.officeMobile.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.FindDepartmentAll;

import java.util.ArrayList;

public class SimpleTextAdapter extends BaseAdapter {
    private ArrayList<FindDepartmentAll> departmentAlls = new ArrayList<>();

    @Override
    public int getCount() {
        return departmentAlls.size();
    }

    @Override
    public FindDepartmentAll getItem(int position) {
        return departmentAlls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vh vh;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_contacts_type, null);
            vh = new Vh();
            vh.tv_department = convertView.findViewById(R.id.tv_department);
            convertView.setTag(vh);
        }else {
            vh = (Vh) convertView.getTag();
        }
        vh.tv_department.setText(getItem(position).getName());
        return convertView;
    }

    private class Vh {
        private TextView tv_department;
    }

    public void setDepartmentAlls(ArrayList<FindDepartmentAll> departmentAlls) {
        this.departmentAlls = departmentAlls;
        notifyDataSetChanged();
    }
}
