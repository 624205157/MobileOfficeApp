package com.mobilepolice.officeMobile.bean;

public class LoopImageNewsBean {
    /**
     * id : 297815
     * title : 高广滨在省公安厅视察全国“两会”安保工作时强调<br>扎实细致做好保稳定护安全促和谐各项工作<br>为全国“两会”顺利召开营造安全稳定政治社会环境
     * con : null
     * time : null
     * img : http://10.106.18.75:8081/u/cms/www/201903/05093451vgno.jpg
     */

    private String id;
    private String title;
    private Object con;
    private Object time;
    private String img;

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

    public Object getCon() {
        return con;
    }

    public void setCon(Object con) {
        this.con = con;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public String getImg() {
        return img.replaceAll("10.106.18.75:8081","192.168.20.228:7124");
    }

    public void setImg(String img) {
        this.img = img;
    }
}
