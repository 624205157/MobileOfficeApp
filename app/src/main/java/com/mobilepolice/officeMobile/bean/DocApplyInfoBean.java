package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class DocApplyInfoBean extends BaseBean {

    /**
     * applyPerson : 17600194545
     * titel : 标题1
     * requestFlag : 通知
     * requestNum : 1
     * secretLevel : 3
     * urgentLevel : 1
     * schema : 2
     * departmentId : 1101
     * approvePerson : [{"id":"15678779990","name":"冯金龙1"},{"id":"16643416141","name":"冯金龙2"},{"id":"15546327009","name":"张馨1"}]
     * img : imgbase64码
     */

    //申请人id
    private String applyPerson;
    //标题
    private String titel;
    //发文类型
    private String requestFlag;
    //发文份数
    private String requestNum;
    //秘密等级
    private String secretLevel;
    //紧急程度
    private String urgentLevel;
    //公文模式（1=审批，2=会签）
    private String schema;
    //部门id
    private String departmentId;
    //公文照片的base64码
    private String img;
    //审批人，格式一定要是list套map
    private List<ApprovePersonBean> approvePerson;

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getRequestFlag() {
        return requestFlag;
    }

    public void setRequestFlag(String requestFlag) {
        this.requestFlag = requestFlag;
    }

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(String urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<ApprovePersonBean> getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(List<ApprovePersonBean> approvePerson) {
        this.approvePerson = approvePerson;
    }
}
