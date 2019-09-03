package com.mobilepolice.officeMobile.bean;

public class NoticeListBean extends BaseBean {

    /**
     * id : 1
     * title : 景俊海到长白县调研时强调:扎景俊海到长白县调研时强调
     * con : 11月15日，省委副书记、省长景俊海来到长白县调研。他强调，要11月15日，省委副书记、省长景俊海来到长白县调研。他强调，要
     * time : 1545118758965
     * count : 28
     * isSeen : false
     * isNew : true
     */

    private String id;
    private String title;
    private String con;
    private String time;
    private int count;
    private boolean isSeen;
    private boolean isNew;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isIsSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
}
