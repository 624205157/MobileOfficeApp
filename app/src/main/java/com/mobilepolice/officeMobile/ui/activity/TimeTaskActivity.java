package com.mobilepolice.officeMobile.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fish.timeline.DateUtil;
import com.fish.timeline.TaskInfo;
import com.fish.timeline.TimeLineController;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.officeMobile.R;
import com.mobilepolice.officeMobile.base.MyActivity;
import com.mobilepolice.officeMobile.base.MyApplication;
import com.mobilepolice.officeMobile.bean.CalendarScheduleBean;
import com.mobilepolice.officeMobile.bean.InsertTaskInfo;
import com.mobilepolice.officeMobile.bean.NormalModel;
import com.mobilepolice.officeMobile.bean.QueryTaskInfo;
import com.mobilepolice.officeMobile.bean.SimpleBean;
import com.mobilepolice.officeMobile.http.HttpConnectInterface;
import com.mobilepolice.officeMobile.http.HttpTools;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.othershe.calendarview.weiget.WeekView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TimeTaskActivity extends MyActivity implements View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_time_task;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }




    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }
    @BindView(R.id.mTimeLine)
    LinearLayout mRoot;

    @BindView(R.id.ll_rc_main)
    LinearLayout ll_rc_main;
    @BindView(R.id.lastMonth)
    ImageView lastMonth;
    @BindView(R.id.nextMonth)
    ImageView nextMonth;
    @BindView(R.id.calendar)
    CalendarView calendarView;

    @BindView(R.id.rv_rc_mRecyclerView)
    RecyclerView rv_rc_mRecyclerView;
    @BindView(R.id.tv_rc_title)
    TextView tv_rc_title;


    @BindView(R.id.ll_rc_create)
    LinearLayout ll_rc_create;
    @BindView(R.id.et_reject)
    EditText et_reject;
    @BindView(R.id.ll_start_time)
    LinearLayout ll_start_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.ll_end_time)
    LinearLayout ll_end_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.class_id_11)
    LinearLayout class_id_11;
    @BindView(R.id.class_id_22)
    LinearLayout class_id_22;
    @BindView(R.id.weekView)
    WeekView weekView;

    private int[] cDate = CalendarUtil.getCurrentDate();
    private String SingleChoose = "";
    //    private TimePickerView pvTime;
    private OptionsPickerView pvTime;
    //当前年
    int year = 0;
    //当前月
    int month = 0;
    //当前月的第几天：即当前日
    int day_of_month = 0;
    int day_start_hour = 0;
    int day_end_hour = 0;


    protected void initView() {

        ll_rc_main.setOnClickListener(this);
        lastMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
//        tv_rc_add.setOnClickListener(this);
        ll_rc_create.setOnClickListener(this);
        ll_start_time.setOnClickListener(this);
        ll_end_time.setOnClickListener(this);
        class_id_11.setOnClickListener(this);
        class_id_22.setOnClickListener(this);

        controller = new TimeLineController("1", 8, 17, mRoot);
        setTitle("日程管理");
        findTimeTaskToday();
        callRc();
    }

    private void callRc() {
        // / 这一步必须要做,否则不会显示.
        ll_rc_main.setVisibility(View.VISIBLE);
        ll_rc_create.setVisibility(View.GONE);
        initCalendarView();
    }

    private TimeLineController controller;

    private void findTimeTaskToday() {
        String today = DateUtil.format("yyyy-MM-dd", System.currentTimeMillis());
        HttpConnectInterface connectInterface = HttpTools.build(HttpConnectInterface.class);
        QueryTaskInfo taskInfo = new QueryTaskInfo();
        taskInfo.setFromDate(today);
        taskInfo.setPoliceNumber(MyApplication.userCode);
        connectInterface.readTimeLineTask(taskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((o -> this.todayTask(today, o)), this::onErr, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void onErr(Throwable throwable) {
        throwable.printStackTrace();
    }


    private void todayTask(String today, TaskInfo o) {
        if (o.isSuccess()) {
            taskCache.put(today, o);
            controller.setData(o.getObj());
        }
    }

    private HashMap<String, TaskInfo> taskCache = new HashMap<>();


    protected void initData() {
        ll_rc_main.setVisibility(View.VISIBLE);
        ll_rc_create.setVisibility(View.GONE);
        calendarView
                .setStartEndDate("1949.1", "2060.12")
                .setDisableStartEndDate("1997.1.1", "2027.12.31")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();

    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (lastMonth == v) {
            calendarView.lastMonth();
        } else if (nextMonth == v) {
            calendarView.nextMonth();
        } else if (ll_start_time == v) {
            initTimePickerStart();
        } else if (ll_end_time == v) {
            initTimePickerEnd();
        } else if (class_id_11 == v) {
            if (TextUtils.isEmpty(et_reject.getText().toString())) {
                ToastUtils.show("日程内容不能为空");
                return;
            }
            if (TextUtils.isEmpty(tv_start_time.getText().toString())) {
                ToastUtils.show("开始时间不能为空");
                return;
            }
            if (TextUtils.isEmpty(tv_end_time.getText().toString())) {
                ToastUtils.show("结束时间不能为空");
                return;
            }
            if (checkTime(tv_start_time.getText().toString(), tv_end_time.getText().toString())) {
                ToastUtils.show("结束时间必须大于开始时间");
                return;
            }

            HttpConnectInterface connectInterface = HttpTools.build(HttpConnectInterface.class);
            InsertTaskInfo insertTaskInfo = new InsertTaskInfo();
            insertTaskInfo.setContent(et_reject.getText().toString());
            insertTaskInfo.setFromDate(DateUtil.format("yyyy-MM-dd HH:mm:ss", DateUtil.parseDate("yyyy年MM月dd日 HH:mm", tv_start_time.getText().toString())));
            insertTaskInfo.setToDate(DateUtil.format("yyyy-MM-dd HH:mm:ss", DateUtil.parseDate("yyyy年MM月dd日 HH:mm", tv_end_time.getText().toString())));
            insertTaskInfo.setPoliceNumber(MyApplication.userCode);
            connectInterface.saveTimeLineTask(insertTaskInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .subscribe(this::commit, this::onErr, this::onComplete)
                    .isDisposed();

            CalendarScheduleBean scheduleBean = new CalendarScheduleBean();
            scheduleBean.setContent(et_reject.getText().toString());
            scheduleBean.setStarttime(tv_start_time.getText().toString());
            scheduleBean.setStarttime(tv_end_time.getText().toString());
            scheduleBean.setYear(year);
            scheduleBean.setMonth(month);
            scheduleBean.setDay_of_month(day_of_month);
            scheduleBean.setDay_start_hour(day_start_hour);
            scheduleBean.setDay_end_hour(day_end_hour);
            scheduleBean.save();
            ll_rc_main.setVisibility(View.VISIBLE);
            ll_rc_create.setVisibility(View.GONE);
            clearRcCreate();
        } else if (class_id_22 == v) {
            ll_rc_main.setVisibility(View.VISIBLE);
            ll_rc_create.setVisibility(View.GONE);
            clearRcCreate();
        }

    }

    private void commit(SimpleBean simpleBean) {
        if (simpleBean.isSuccess()) {
            findTimeTaskToday();
        }
    }

    /**
     * 日程添加清空
     */
    private void clearRcCreate() {
        et_reject.setText("");
        tv_start_time.setText("");
        tv_end_time.setText("");
    }


    private void initCalendarView() {
        tv_rc_title.setText(cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
        // chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                tv_rc_title.setText(date[0] + "年" + date[1] + "月" + date[2] + "日");
                year = date[0];
                month = date[1];
                day_of_month = date[2];
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(date[0], date[1], date[2], 0, 0);
                //  mWeekView.goToDate(selectedDate);
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                tv_rc_title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                year = date.getSolar()[0];
                month = date.getSolar()[1];
                day_of_month = date.getSolar()[2];
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(date.getSolar()[0], date.getSolar()[1], date.getSolar()[2], 0, 0);
                // mWeekView.goToDate(selectedDate);
                SingleChoose = date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日 00:00";
                tv_start_time.setText(SingleChoose);
                tv_end_time.setText(SingleChoose);
                ll_rc_main.setVisibility(View.GONE);
                ll_rc_create.setVisibility(View.VISIBLE);
                if (date.getType() == 1) {
                    //chooseDate.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                }
            }
        });


    }


    private void initTimePickerStart() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Calendar selectedDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
//        //当前年
//        int year = selectedDate.get(Calendar.YEAR);
//        //当前月
//        int month = (selectedDate.get(Calendar.MONTH));
//        //当前月的第几天：即当前日
//        int day_of_month = selectedDate.get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year, month - 1, day_of_month, 8, 0);
        endDate.set(year, month - 1, day_of_month, 17, 0);
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 0, 23);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
//        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
//                /*btn_Time.setText(getTime(date));*/
////                Button btn = (Button) v;
////                btn.setText(getTime(date));
//                String attime = getTime(date);
//                day_start_hour = date.getHours();
//                tv_start_time.setText(attime);
//                Log.e("onTimeSelect: ", DateUtil.format("yyyy-MM-dd HH:mm:ss", date.getTime()));
//            }
//        })
//                //年月日时分秒 的显示与否，不设置则默认全部显示
//                .setType(new boolean[]{false, false, false, true, false, false})
//                .setLabel("年", "月", "日", "时", "分", "秒")
//                .setTitleText("日期时间")
//                .isCenterLabel(false)
//                .setDividerColor(Color.DKGRAY)
//                .setContentTextSize(21)
//                .setDate(selectedDate)
////                .setRangDate(selectedDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
//                .setDecorView(null)
//                .build();

        pvTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_start_time.setText(DateUtil.format("yyyy年MM月dd日", selectedDate.getTimeInMillis()) + " " + timeList.get(options1) + ":00");
            }
        }).setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                .setSubCalSize(20)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                        .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setPicker(timeTitle);
//        pvTime= new   OptionsPickerView (new PickerOptions())
//        pvTime.show();
        pvTime.show();
    }

    private ArrayList<String> timeTitle = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();

    {
        timeTitle.add("8时");
        timeTitle.add("9时");
        timeTitle.add("10时");
        timeTitle.add("11时");
        timeTitle.add("12时");
        timeTitle.add("13时");
        timeTitle.add("14时");
        timeTitle.add("15时");
        timeTitle.add("16时");
        timeTitle.add("17时");

        timeList.add("08");
        timeList.add("09");
        timeList.add("10");
        timeList.add("11");
        timeList.add("12");
        timeList.add("13");
        timeList.add("14");
        timeList.add("15");
        timeList.add("16");
        timeList.add("17");

    }

    private void initTimePickerEnd() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Calendar selectedDate = Calendar.getInstance();
//        //当前年
//        int year = selectedDate.get(Calendar.YEAR);
//        //当前月
//        int month = (selectedDate.get(Calendar.MONTH));
//        //当前月的第几天：即当前日
//        int day_of_month = selectedDate.get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year, month - 1, day_of_month, 0, 0);
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 0, 23);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
//        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
//                /*btn_Time.setText(getTime(date));*/
////                Button btn = (Button) v;
////                btn.setText(getTime(date));
//                String attime = getTime(date);
//                day_end_hour = date.getHours();
//                tv_end_time.setText(attime);
//            }
//        })
//                //年月日时分秒 的显示与否，不设置则默认全部显示
//                .setType(new boolean[]{false, false, false, true, false, false})
//                .setLabel("年", "月", "日", "时", "分", "秒")
//                .setTitleText("日期时间")
//                .isCenterLabel(false)
//                .setDividerColor(Color.DKGRAY)
//                .setContentTextSize(21)
//                .setDate(selectedDate)
////                .setRangDate(selectedDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
//                .setDecorView(null)
//                .build();
//        pvTime.show();
        pvTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_end_time.setText(DateUtil.format("yyyy年MM月dd日", selectedDate.getTimeInMillis()) + " " + timeList.get(options1) + ":00");
            }
        }).setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                .setSubCalSize(20)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                        .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setPicker(timeTitle);
        pvTime.show();
    }

    public static String getHour(Date currentTime) {
//        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00", Locale.CHINA);
        String dateString = sdf.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    public static int getHour(String str) {
        int result = 0;

        if (str.equals("01")) {

        }
        return result;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:00");
        return format.format(date);
    }

    private boolean checkTime(String start, String end) {
        Log.e("date", "checkTime: " + start + "::" + end);
        boolean flag = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(start);//开始时间
            //  String EndTime = getTime(new Date());
            Date date2 = dateFormat.parse(end);//结束时间
            if (date2.getTime() < date1.getTime()) {
                //showToastUtils.show("结束时间小于开始时间");
                flag = true;
            } else if (date2.getTime() == date1.getTime()) {
                //   showToastUtils.show("开始时间与结束时间相同");
                //flag = true;
            } else if (date2.getTime() > date1.getTime()) {
                //正常情况下的逻辑操作.
                // flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
