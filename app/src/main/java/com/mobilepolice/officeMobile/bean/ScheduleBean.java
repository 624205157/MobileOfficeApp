package com.mobilepolice.officeMobile.bean;

public class ScheduleBean extends BaseBean {

    public ScheduleBean() {
    }

    public ScheduleBean(int type, String approvePerson, String approvePersonName, String createDate,boolean flag) {
        this.type = type;
        this.approvePerson = approvePerson;
        this.approvePersonName = approvePersonName;
        this.createDate = createDate;
        this.flag=flag;
    }

    private String approvePerson;
    private String approvePersonName;
    private String createDate;
    //0未审批人1发起人2当前会签人3已会签人
    private int type;
    private boolean flag;
    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getApprovePersonName() {
        return approvePersonName;
    }

    public void setApprovePersonName(String approvePersonName) {
        this.approvePersonName = approvePersonName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}



