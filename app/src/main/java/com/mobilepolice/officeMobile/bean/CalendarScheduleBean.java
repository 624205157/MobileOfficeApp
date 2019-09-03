package com.mobilepolice.officeMobile.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class CalendarScheduleBean extends DataSupport implements Cloneable, Serializable {
    private String content;
    private String starttime;
    private String endtime;
    //当前年
    int year = 0;
    //当前月
    int month = 0;
    //当前月的第几天：即当前日
    int day_of_month = 0;
    int day_start_hour = 0;
    int day_end_hour = 0;

    public CalendarScheduleBean() {

    }

    public int getDay_start_hour() {
        return day_start_hour;
    }

    public void setDay_start_hour(int day_start_hour) {
        this.day_start_hour = day_start_hour;
    }

    public int getDay_end_hour() {
        return day_end_hour;
    }

    public void setDay_end_hour(int day_end_hour) {
        this.day_end_hour = day_end_hour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(int day_of_month) {
        this.day_of_month = day_of_month;
    }
}
