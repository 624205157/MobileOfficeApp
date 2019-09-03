package com.mobilepolice.officeMobile.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobilepolice.officeMobile.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miao on 2019/4/6.
 */
public class ImageDetailsAdapter extends RecyclerView.Adapter<ImageDetailsAdapter.ImageViewHolder> implements ItemTouchListener {

    private ArrayList<String> filePaths = new ArrayList<>();

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_details, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder vh, int i) {
        Glide.with(vh.img).load(filePaths.get(i)).into(vh.img);
    }

    public void setFilePaths(ArrayList<String> filePaths) {
        this.filePaths = filePaths;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filePaths.size();
    }

    public ArrayList<String> getData() {
        return filePaths;
    }

    @Override
    public void drag(int src, int dest) {
        String ssrc = filePaths.get(src);
        String sdest = filePaths.get(dest);
        filePaths.set(src, sdest);
        filePaths.set(dest, ssrc);
        notifyItemMoved(src, dest);
    }


    @Override
    public void swipe(int src) {
        filePaths.remove(src);
        notifyItemRemoved(src);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.im)
        ImageView img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
