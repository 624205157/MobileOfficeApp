package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class AttributesBean extends BaseBean {
    private String approveNodeId;
    //未审批列表
    private List<NotApprovePersonBean> notApprovePerson;
    //已审批列表
    private List<ApproveListInfoBean> approveListInfo;

    public String getApproveNodeId() {
        return approveNodeId;
    }

    public void setApproveNodeId(String approveNodeId) {
        this.approveNodeId = approveNodeId;
    }

    public List<NotApprovePersonBean> getNotApprovePerson() {
        return notApprovePerson;
    }

    public void setNotApprovePerson(List<NotApprovePersonBean> notApprovePerson) {
        this.notApprovePerson = notApprovePerson;
    }

    public List<ApproveListInfoBean> getApproveListInfo() {
        return approveListInfo;
    }

    public void setApproveListInfo(List<ApproveListInfoBean> approveListInfo) {
        this.approveListInfo = approveListInfo;
    }

    public static class NotApprovePersonBean extends BaseBean {
        /**
         * approvePersonName : 冯金龙1
         * approvePerson : 15678779990
         */

        private String approvePersonName;
        private String approvePerson;

        public String getApprovePersonName() {
            return approvePersonName;
        }

        public void setApprovePersonName(String approvePersonName) {
            this.approvePersonName = approvePersonName;
        }

        public String getApprovePerson() {
            return approvePerson;
        }

        public void setApprovePerson(String approvePerson) {
            this.approvePerson = approvePerson;
        }
    }

    public static class ApproveListInfoBean extends BaseBean {

        public ApproveListInfoBean()
        {}
        public ApproveListInfoBean(String approvePerson, String approvePersonName) {
            this.approvePersonName = approvePersonName;
            this.approvePerson = approvePerson;
        }

        /**
         * approvePersonName : 张馨1
         * approveResultFile :
         * approvePerson : 15546327009
         * createDate : 2018-11-29 11:40:22
         */

        private String approvePersonName;
        private String approveResultFile;
        private String approvePerson;
        private String createDate;
        //公文模式（1=审批，2=会签）
        private String approveType;
        //审批结果（1=打字意见，2=图片签写，3=语音签写）
        private String approveFlag;
        //打字文字，图片、语音base64码
        private String approveOpinion;

        public String getApprovePersonName() {
            return approvePersonName;
        }

        public void setApprovePersonName(String approvePersonName) {
            this.approvePersonName = approvePersonName;
        }

        public String getApproveResultFile() {
            return approveResultFile;
        }

        public void setApproveResultFile(String approveResultFile) {
            this.approveResultFile = approveResultFile;
        }

        public String getApprovePerson() {
            return approvePerson;
        }

        public void setApprovePerson(String approvePerson) {
            this.approvePerson = approvePerson;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getApproveType() {
            return approveType;
        }

        public void setApproveType(String approveType) {
            this.approveType = approveType;
        }

        public String getApproveFlag() {
            return approveFlag;
        }

        public void setApproveFlag(String approveFlag) {
            this.approveFlag = approveFlag;
        }

        public String getApproveOpinion() {
            return approveOpinion;
        }

        public void setApproveOpinion(String approveOpinion) {
            this.approveOpinion = approveOpinion;
        }
    }
}
