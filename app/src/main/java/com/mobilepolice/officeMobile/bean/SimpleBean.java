package com.mobilepolice.officeMobile.bean;

public class SimpleBean {
    /**
     * msg : 数据操作成功
     * obj : null
     * success : true
     * attributes : null
     */

    private String msg;
    private Object obj;
    private boolean success;
    private Object attributes;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }
}
