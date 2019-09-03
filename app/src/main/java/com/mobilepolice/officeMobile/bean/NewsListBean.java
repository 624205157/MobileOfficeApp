package com.mobilepolice.officeMobile.bean;

public class NewsListBean extends BaseBean {


    /**
     * id : 1
     * title : 景俊海到长白县调研时强调:扎景俊海到长白县调研时强调
     * con : 11月15日，省委副书记、省长景俊海来到长白县调研。他强调，要11月15日，省委副书记、省长景俊海来到长白县调研。他强调，要
     * time : 11-15
     * img : null
     * isNew : true
     */

    private String id;
    private String title;
    private String con;
    private String time;
    private String img;
    private boolean isNew;

    public void NewsListBean() {

    }

    public void NewsListBean(String id, String title, String con, String time, String img, boolean isNew) {
        this.id = id;
        this.title = title;
        this.con = con;
        this.img = img;
        this.time = time;
        this.isNew = isNew;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
}
