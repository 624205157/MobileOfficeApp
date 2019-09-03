package com.mobilepolice.officeMobile.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.bean.DocumentInfo;
import com.mobilepolice.officeMobile.ui.adapter.DocumentItemAdapter;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class DocumentDetailsActivity extends AppCompatActivity {

    ArrayList<DocumentInfo.ObjBean> data;
    private DocumentItemAdapter adapter;

    @BindView(R.id.mList)
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_details);
        ButterKnife.bind(this);
        data = getIntent().getParcelableArrayListExtra("data");
        Collections.sort(data);
        adapter = new DocumentItemAdapter(data);
        mList.setAdapter(adapter);

    }


    @OnItemClick(R.id.mList)
    void onItemPress(int pos){
        startActivity(new Intent(this,DocDetails.class).putExtra("id",adapter.getData().get(pos).getId()));
    }
}
