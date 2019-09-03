package com.fish.timeline;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class TimeLineController {

    private static HashMap<String, TimeLineController> instanceCache = new HashMap<>();
    private final int startTimeHour;
    private final int endTimeHour;
    private final ViewGroup root;
    private int height;
    private int width;

    private int textWidth;
    private int textHeight;
    private int startLocation;
    private int endLocation;

    private float weight;

    private int hourCount;

    public static HashMap<String, TimeLineController> getInstanceCache() {
        return instanceCache;
    }

    private TimeLineAdapter  adapter = new TimeLineAdapter();;
    AbsoluteLayout mLayout;

    public TimeLineController(String key, int startTimeHour, int endTimeHour, final ViewGroup root) {
        this.startTimeHour = startTimeHour;
        this.endTimeHour = endTimeHour;
        this.root = root;

        hourCount = endTimeHour - startTimeHour + 1;
        instanceCache.put(key, this);

        mLayout = new AbsoluteLayout(root.getContext());
        root.addView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView textView =new TextView(root.getContext());
        textView.setText(" ");
        root.addView(textView);
        textView.post(()->{
            textHeight = textView.getHeight();
            mLayout.post(() -> {
                width = mLayout.getWidth();
                height = mLayout.getHeight();
                Log.e("TimeLineController:HourCount ", hourCount + "");
                weight = (height * 1.0f / hourCount);
                Log.e("TimeLineController: weight", weight + "");
                Log.e("TimeLineController: ", "" + width);
                Log.e("TimeLineController: ", "" + height);
                initTimeLine(mLayout.getContext());
                RecyclerView recyclerView = new RecyclerView(root.getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                root.addView(recyclerView, params);

                recyclerView.setAdapter(adapter);
                root.removeView(textView);
            });
        });


//        startService(new Intent(this,RunningService.class));
    }

    private void initTimeLine(Context context) {
        for (int i = startTimeHour; i < endTimeHour + 1; i++) {
            TextView textView = new TextView(context);
            if (i == startTimeHour)
                textView.setTag("startFlag");
            else if (i == endTimeHour) {
                textView.setTag("endFlag");
            }
            textView.setText("" + (0 + i) + ":00");
            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2, 10, (int) (height * 1.0 / hourCount * (i - startTimeHour)));
            mLayout.addView(textView, params);
        }

        mLayout.findViewWithTag("startFlag").post(() -> {
            View f = root.findViewWithTag("startFlag");
            startLocation = (int) f.getY();
            textWidth = f.getWidth();
            if (textHeight==0)
            textHeight = f.getHeight();
        });
        mLayout.findViewWithTag("endFlag").post(() -> {
            View f = root.findViewWithTag("endFlag");
            endLocation = (int) f.getY();
//            createData();
        });


    }


    class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.VH> {

        private HashMap<Integer, ArrayList<TaskInfo.ObjBean>> detailsCache = new HashMap<>();

        private Context context;

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_line, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            createItem(holder, position);
        }

        private void createItem(VH holder, int position) {
            holder.root.removeAllViews();
            ArrayList<TaskInfo.ObjBean> taskInfos = detailsCache.get(position);
            for (int i = 0; i < taskInfos.size(); i++) {
                dispatchItemTask(taskInfos.get(i), holder.root);
            }
        }

        private void dispatchItemTask(TaskInfo.ObjBean taskInfo, AbsoluteLayout root) {
            TextView textView = new TextView(context);
            counter++;
            textView.setBackgroundColor(colors[counter % colors.length]);
            textView.setText(taskInfo.getContent());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(20,0,0,0);
            textView.setTextColor(context.getResources().getColor(android.R.color.white));
            String startTime = DateUtil.format("HH:mm", DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", taskInfo.getFromDate()));
            String endTime = DateUtil.format("HH:mm", DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", taskInfo.getToDate()));
            int startHour = Integer.parseInt(DateUtil.format("HH", DateUtil.parseDate("HH:mm", startTime)));
            int startMin = Integer.parseInt(DateUtil.format("mm", DateUtil.parseDate("HH:mm", startTime)));
            int endHour = Integer.parseInt(DateUtil.format("HH", DateUtil.parseDate("HH:mm", endTime)));
            int endMin = Integer.parseInt(DateUtil.format("mm", DateUtil.parseDate("HH:mm", endTime)));


            Log.e("textHeight: ", textHeight + "");
            float heightSize = ((startHour - startTimeHour) * 60 + startMin) * 1.0f / 60;
            float size = ((endHour * 60 + endMin) - (startHour * 60 + startMin)) * 1.0f / 60;
            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (weight * size), 0, (int) ((heightSize * weight) + textHeight / 2));
            Log.e( "dispatchItemTask:>> ",""+((heightSize * weight) + textHeight / 2));
            Log.e( "dispatchItemTask:>> ",""+((heightSize * weight)));

            root.addView(textView, params);
            root.invalidate();

        }

        public void clear(){
            detailsCache.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return detailsCache.size();
        }

        class VH extends RecyclerView.ViewHolder {

            AbsoluteLayout root;

            public VH(@NonNull View itemView) {
                super(itemView);
                root = itemView.findViewById(R.id.mRoot);
            }
        }

        void setData(ArrayList<TaskInfo.ObjBean> data) {
            Collections.sort(data);
            dispatchItemTask(data);
            notifyDataSetChanged();
        }

        private void dispatchItemTask(ArrayList<TaskInfo.ObjBean> data) {
            int idx = 0;
            for (int i = 0; i < data.size(); i++) {
                ArrayList<TaskInfo.ObjBean> cacheList = detailsCache.get(idx);
                if (cacheList == null) {
                    cacheList = new ArrayList<>();
                    detailsCache.put(idx,cacheList);
                }

                if (compareTaskItemTimeArea(cacheList, data.get(i))) {
                    cacheList.add(data.get(i));
                    idx = 0;
                } else {
                    idx++;
                    i--;
                }
            }
        }

        private boolean compareTaskItemTimeArea(ArrayList<TaskInfo.ObjBean> cacheList, TaskInfo.ObjBean objBean) {
            if (cacheList.size() == 0) return true;
            for (int i = 0; i < cacheList.size(); i++) {

                if ( DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", cacheList.get(i).getToDate()) >  DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", objBean.getFromDate())){
                    return false;
                }
            }
            return true;
        }
    }

    public void setData(List<TaskInfo.ObjBean> data) {
        mLayout.post(() -> {
            adapter.clear();
            adapter.setData(new ArrayList<>(data));
        });
    }

    private int counter;


    private int[] colors = {
            Color.argb(255,255,0,0),
            Color.argb(255,255,165,0),
            Color.argb(255,255,255,0),
            Color.argb(255,0,255,0),
            Color.argb(255,0,127,255),
            Color.argb(255,0,0,255),
            Color.argb(255,139,0,255)

    };

}


