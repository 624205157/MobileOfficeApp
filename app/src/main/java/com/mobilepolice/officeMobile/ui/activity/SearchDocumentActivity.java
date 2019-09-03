package com.mobilepolice.officeMobile.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.timeline.DateUtil;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.bean.DocumentInfo;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.ui.adapter.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchDocumentActivity extends MyActivity {

    @BindView(R.id.approve)
    Spinner approve;
    @BindView(R.id.name_selected)
    TextView nameSelected;
    @BindView(R.id.date_start_selected)
    TextView dateSelected;
    @BindView(R.id.state_selected)
    TextView stateSelected;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.createDate)
    Button createDate;
    @BindView(R.id.query)
    CardView query;
    @BindView(R.id.texts)
    TextView text;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toDate)
    Button toDate;

    private DatePickerDialog dialog;
    private DatePickerDialog dialog2;

    String currentDate;
    String selected = "1";
    String toDateString;


    private StringAdapter adapter;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_document;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        adapter = new StringAdapter();
        approve.setAdapter(adapter);
        approve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = adapter.getData().get(position).equals("未审批") ? "0" : "1";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setTitle("公文查询");
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @OnClick(R.id.query)
    void onQuery() {
        StringBuilder buff = new StringBuilder();

        buff.append("{");
        if (!TextUtils.isEmpty(name.getText().toString())) {
            buff.append("\"title\":\"").append(name.getText().toString()).append("\",");
        }
        if (!TextUtils.isEmpty(currentDate)) {
            buff.append("\"fromDate\":\"").append(currentDate).append("\",");
        }
        if (!TextUtils.isEmpty(currentDate)) {
            buff.append("\"toDate\":\"").append(toDateString).append("\",");
        }
        buff.append("\"overFlag\":\"").append(selected).append("\",");

        if (buff.lastIndexOf(",") == buff.length() - 1) {
            buff.deleteCharAt(buff.length() - 1);
        }
        buff.append("}");

        if (!TextUtils.isEmpty(currentDate)&&!TextUtils.isEmpty(toDateString)&&DateUtil.parseDate("yyyy-MM-dd", currentDate) > DateUtil.parseDate("yyyy-MM-dd", toDateString)) {
            Toast.makeText(this, "公文查询时间范围：结束时间不可以早于开始时间", Toast.LENGTH_SHORT).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        text.setVisibility(View.GONE);
        query.setCardBackgroundColor(getResources().getColor(R.color.white));
        HttpConnectInterface.findDocumentInfo(buff.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::result, this::err, this::onComplete)
                .isDisposed();

    }

    @OnClick(R.id.createDate)
    void setCreateDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        if (dialog == null)
            dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    GregorianCalendar c = new GregorianCalendar();
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DATE, dayOfMonth);
                    currentDate = DateUtil.format("yyyy-MM-dd", c.getTimeInMillis());
                    createDate.setText(currentDate);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        dialog.show();
    }

    @OnClick(R.id.toDate)
    void setToDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        if (dialog2 == null)
            dialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    GregorianCalendar c = new GregorianCalendar();
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DATE, dayOfMonth);
                    toDateString = DateUtil.format("yyyy-MM-dd", c.getTimeInMillis());
                    toDate.setText(toDateString);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        dialog2.show();
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        query.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void result(DocumentInfo documentInfo) {
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        query.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
        if (documentInfo.isSuccess()) {
            List<DocumentInfo.ObjBean> obj = documentInfo.getObj();
            if (obj != null) {
                ArrayList<DocumentInfo.ObjBean> data = new ArrayList<>(obj);
                startActivity(new Intent(this, DocumentDetailsActivity.class).putParcelableArrayListExtra("data", data));
            } else {
                Toast.makeText(this, documentInfo.getMsg(), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }


}

class StringAdapter extends BaseAdapter<String, StringAdapter.TextViewHolder> {
    public StringAdapter() {
        super(new ArrayList<>());
        data.add("已审批");
        data.add("未审批");
        notifyDataSetChanged();
    }

    @Override
    protected TextViewHolder create(View view) {
        return new TextViewHolder(view);
    }

    @Override
    protected void bindView(TextViewHolder vh, int position, String item) {
        vh.text.setText(item);
    }

    @Override
    protected int getLayout() {
        return R.layout._item_text_;
    }

    class TextViewHolder extends BaseAdapter.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        public TextViewHolder(View view) {
            super(view);
        }
    }
}
