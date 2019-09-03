package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class ApproveDataInfo {
    /**
     * msg : 操作成功
     * obj : [{"overFlag":"0","applyPersonName":"钱科员","approveNode":"流程结束","title":"标题1","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/7599EB673CF94AF1AD313FDAE8DBEAE5-110754.jpg","applyPerson":"110754","approvePersonName":"","urgentLevel":"1","requestid":"7599EB673CF94AF1AD313FDAE8DBEAE5","approvePerson":"","approveNodeId":"","id":"C39ADCAF108B40798A9D08759554788A","createDate":"2019-03-11 14:04:28"},{"overFlag":"0","applyPersonName":"钱科员","approveNode":"厅长","title":"标题1","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/365175DE5C014100B567E32FCA4095BB-110754.jpg","applyPerson":"110754","approvePersonName":"","urgentLevel":"1","requestid":"365175DE5C014100B567E32FCA4095BB","approvePerson":"100938","approveNodeId":"3","id":"2475892510EA47399B365E50EE790A81","createDate":"2019-03-11 13:50:58"}]
     * success : true
     * attributes : null
     */

    private String msg;
    private boolean success;
    private Object attributes;
    private List<ObjBean> obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * overFlag : 0
         * applyPersonName : 钱科员
         * approveNode : 流程结束
         * title : 标题1
         * applyOffWordFile : http://www.freetk.cn:8789/mobileworkws-image/document/7599EB673CF94AF1AD313FDAE8DBEAE5-110754.jpg
         * applyPerson : 110754
         * approvePersonName :
         * urgentLevel : 1
         * requestid : 7599EB673CF94AF1AD313FDAE8DBEAE5
         * approvePerson :
         * approveNodeId :
         * id : C39ADCAF108B40798A9D08759554788A
         * createDate : 2019-03-11 14:04:28
         */

        private String overFlag;
        private String applyPersonName;
        private String approveNode;
        private String title;
        private String applyOffWordFile;
        private String applyPerson;
        private String approvePersonName;
        private String urgentLevel;
        private String requestid;
        private String approvePerson;
        private String approveNodeId;
        private String id;
        private String createDate;

        public String getOverFlag() {
            return overFlag;
        }

        public void setOverFlag(String overFlag) {
            this.overFlag = overFlag;
        }

        public String getApplyPersonName() {
            return applyPersonName;
        }

        public void setApplyPersonName(String applyPersonName) {
            this.applyPersonName = applyPersonName;
        }

        public String getApproveNode() {
            return approveNode;
        }

        public void setApproveNode(String approveNode) {
            this.approveNode = approveNode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getApplyOffWordFile() {
            return applyOffWordFile;
        }

        public void setApplyOffWordFile(String applyOffWordFile) {
            this.applyOffWordFile = applyOffWordFile;
        }

        public String getApplyPerson() {
            return applyPerson;
        }

        public void setApplyPerson(String applyPerson) {
            this.applyPerson = applyPerson;
        }

        public String getApprovePersonName() {
            return approvePersonName;
        }

        public void setApprovePersonName(String approvePersonName) {
            this.approvePersonName = approvePersonName;
        }

        public String getUrgentLevel() {
            return urgentLevel;
        }

        public void setUrgentLevel(String urgentLevel) {
            this.urgentLevel = urgentLevel;
        }

        public String getRequestid() {
            return requestid;
        }

        public void setRequestid(String requestid) {
            this.requestid = requestid;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
