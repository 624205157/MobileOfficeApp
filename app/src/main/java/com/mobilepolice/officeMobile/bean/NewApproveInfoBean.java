package com.mobilepolice.officeMobile.bean;

public class NewApproveInfoBean extends BaseBean{

    /**
     * approvePersonName : 赵科长
     * approveNode : 审批节点1
     * approvePerson : 15678779990
     * approveNodeId : 1
     */

    private String approvePersonName;
    private String approveNode;
    private String approvePerson;
    private String approveNodeId;

    public String getApprovePersonName() {
        return approvePersonName;
    }

    public void setApprovePersonName(String approvePersonName) {
        this.approvePersonName = approvePersonName;
    }

    public String getApproveNode() {
        return approveNode;
    }

    public void setApproveNode(String approveNode) {
        this.approveNode = approveNode;
    }

    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getApproveNodeId() {
        return approveNodeId;
    }

    public void setApproveNodeId(String approveNodeId) {
        this.approveNodeId = approveNodeId;
    }
}
