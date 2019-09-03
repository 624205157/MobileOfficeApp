package com.mobilepolice.officeMobile.bean;

public class ApprovePersonBean extends BaseBean {
    private String id;
    private String name;

    public ApprovePersonBean() {

    }

    public ApprovePersonBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
