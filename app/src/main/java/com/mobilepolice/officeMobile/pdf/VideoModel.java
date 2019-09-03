package com.mobilepolice.officeMobile.pdf;

public class VideoModel {
    private boolean flag;
    private String name;
    private String desc;
    private String path;
    public int buy_Price;
    public int sell_Price;
    private long length;

    public VideoModel() {
        super();
    }

    public VideoModel(String name, String desc,boolean flag) {
        super();
        this.name = name;
        this.desc = desc;
        this.flag=flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
