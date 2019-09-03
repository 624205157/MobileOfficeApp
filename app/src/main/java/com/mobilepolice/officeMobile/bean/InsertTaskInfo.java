package com.mobilepolice.officeMobile.bean;

public class InsertTaskInfo {
    /**
     * policeNumber : 456
     * content : 测试内容
     * fromDate : 2019-03-12 10:10:10
     * toDate : 2019-03-12 21:10:10
     */

    private String policeNumber;
    private String content;
    private String fromDate;
    private String toDate;

    public String getPoliceNumber() {
        return policeNumber;
    }

    public void setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
