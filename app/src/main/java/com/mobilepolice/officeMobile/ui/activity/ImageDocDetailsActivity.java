package com.mobilepolice.officeMobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.ui.adapter.ImageDetailsAdapter;
import com.mobilepolice.officeMobile.ui.adapter.ItemTouchProcessor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDocDetailsActivity extends AppCompatActivity {

    private ImageDetailsAdapter adapter;

    private ItemTouchProcessor processor;
    @BindView(R.id.mList)
    RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_doc_details);
        ButterKnife.bind(this);
        ArrayList<String> fp = getIntent().getStringArrayListExtra("fileList");
        processor = new ItemTouchProcessor();
        adapter = new ImageDetailsAdapter();
        processor.setItemTouchListener(adapter);
        mList.setLayoutManager(new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL, false));
        mList.setAdapter(adapter);
        adapter.setFilePaths(fp);
        new ItemTouchHelper(processor).attachToRecyclerView(mList);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("fileList", adapter.getData()));
        super.onBackPressed();
    }

}
