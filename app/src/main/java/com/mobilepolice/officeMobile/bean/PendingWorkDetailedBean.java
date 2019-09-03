package com.mobilepolice.officeMobile.bean;

public class PendingWorkDetailedBean extends BaseBean {

    /**
     * schema : 2
     * pdfFile :
     * id : 781D623188C04A0989CEF4BA1EDFB3DE
     * requestNum : 1
     * requestFlag : 通知
     * titel : 标题1
     * department : 1101
     * departmentName : 秘书科
     * urgentLevel : 1
     * img : http://www.freetk.cn:8789/mobileworkws-image/document/F561DE67555044DD8B4454579854DDDD-17600194545.jpg
     * secretLevel : 3
     * createDate : 2018-11-29 10:38:35
     * applyPersonName : 冯金龙3
     * applyPerson : 17600194545
     */

    private String schema;
   // private String pdfFile;
    private String id;
    private String requestNum;
    private String requestFlag;
    private String titel;
    private String department;
    private String departmentName;
    private String urgentLevel;
    private String img;
    private String secretLevel;
    private String createDate;
    private String applyPersonName;
    private String applyPerson;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
//
//    public String getPdfFile() {
//        return pdfFile;
//    }
//
//    public void setPdfFile(String pdfFile) {
//        this.pdfFile = pdfFile;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    public String getRequestFlag() {
        return requestFlag;
    }

    public void setRequestFlag(String requestFlag) {
        this.requestFlag = requestFlag;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(String urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getApplyPersonName() {
        return applyPersonName;
    }

    public void setApplyPersonName(String applyPersonName) {
        this.applyPersonName = applyPersonName;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }
}
