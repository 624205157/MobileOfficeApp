package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class DocProcessBean extends BaseBean {

    /**
     * msg : 操作成功
     * obj : [{"approvePersonName":"钱科员","overFlag":"0","approveOrSign":"2","approveResultFile":"","requestid":"1A04C19DFB4A4417A21E9ADCB0068CC8","approvePerson":"17600194545","approveFlag":"1","approveOpinion":"申请发起","id":"6620B60FCB5145F5901F0C0E8E636B4E","createDate":"2018-12-14 08:53:10"}]
     * success : true
     * attributes : {"allApproveInfo":[{"approveType":"3","approvePersonName":"李厅长","approveOrSign":"2","approvePerson":"15546327009","approveName":"厅长","id":"6","approveDept":"2"},{"approveType":"1","approvePersonName":"赵科长","approveOrSign":"2","approvePerson":"15678779990","approveName":"主任","id":"4","approveDept":"1"},{"approveType":"2","approvePersonName":"孙主任","approveOrSign":"2","approvePerson":"16643416141","approveName":"科长","id":"5","approveDept":"1"}],"nowApproveInfo":null}
     */

    private AttributesBean attributes;
    private List<ObjBean> obj;

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class AttributesBean extends BaseBean {
        /**
         * allApproveInfo : [{"approveType":"3","approvePersonName":"李厅长","approveOrSign":"2","approvePerson":"15546327009","approveName":"厅长","id":"6","approveDept":"2"},{"approveType":"1","approvePersonName":"赵科长","approveOrSign":"2","approvePerson":"15678779990","approveName":"主任","id":"4","approveDept":"1"},{"approveType":"2","approvePersonName":"孙主任","approveOrSign":"2","approvePerson":"16643416141","approveName":"科长","id":"5","approveDept":"1"}]
         * nowApproveInfo : null
         */

        private NowApproveInfoBean nowApproveInfo;
        private List<AllApproveInfoBean> allApproveInfo;

        public NowApproveInfoBean getNowApproveInfo() {
            return nowApproveInfo;
        }

        public void setNowApproveInfo(NowApproveInfoBean nowApproveInfo) {
            this.nowApproveInfo = nowApproveInfo;
        }

        public List<AllApproveInfoBean> getAllApproveInfo() {
            return allApproveInfo;
        }

        public void setAllApproveInfo(List<AllApproveInfoBean> allApproveInfo) {
            this.allApproveInfo = allApproveInfo;
        }

        public static class NowApproveInfoBean extends BaseBean {
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

        public static class AllApproveInfoBean extends BaseBean {
            /**
             * approveType : 3
             * approvePersonName : 李厅长
             * approveOrSign : 2
             * approvePerson : 15546327009
             * approveName : 厅长
             * id : 6
             * approveDept : 2
             */

            private String approveType;
            private String approvePersonName;
            private String approveOrSign;
            private String approvePerson;
            private String approveName;
            private String id;
            private String approveDept;

            public String getApproveType() {
                return approveType;
            }

            public void setApproveType(String approveType) {
                this.approveType = approveType;
            }

            public String getApprovePersonName() {
                return approvePersonName;
            }

            public void setApprovePersonName(String approvePersonName) {
                this.approvePersonName = approvePersonName;
            }

            public String getApproveOrSign() {
                return approveOrSign;
            }

            public void setApproveOrSign(String approveOrSign) {
                this.approveOrSign = approveOrSign;
            }

            public String getApprovePerson() {
                return approvePerson;
            }

            public void setApprovePerson(String approvePerson) {
                this.approvePerson = approvePerson;
            }

            public String getApproveName() {
                return approveName;
            }

            public void setApproveName(String approveName) {
                this.approveName = approveName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getApproveDept() {
                return approveDept;
            }

            public void setApproveDept(String approveDept) {
                this.approveDept = approveDept;
            }
        }
    }

    public static class ObjBean extends BaseBean {
        /**
         * approvePersonName : 钱科员
         * overFlag : 0
         * approveOrSign : 2
         * approveResultFile :
         * requestid : 1A04C19DFB4A4417A21E9ADCB0068CC8
         * approvePerson : 17600194545
         * approveFlag : 1
         * approveOpinion : 申请发起
         * id : 6620B60FCB5145F5901F0C0E8E636B4E
         * createDate : 2018-12-14 08:53:10
         */

        private String approvePersonName;
        private String overFlag;
        private String approveOrSign;
        private String approveResultFile;
        private String requestid;
        private String approvePerson;
        private String approveFlag;
        private String approveOpinion;
        private String id;
        private String createDate;

        public String getApprovePersonName() {
            return approvePersonName;
        }

        public void setApprovePersonName(String approvePersonName) {
            this.approvePersonName = approvePersonName;
        }

        public String getOverFlag() {
            return overFlag;
        }

        public void setOverFlag(String overFlag) {
            this.overFlag = overFlag;
        }

        public String getApproveOrSign() {
            return approveOrSign;
        }

        public void setApproveOrSign(String approveOrSign) {
            this.approveOrSign = approveOrSign;
        }

        public String getApproveResultFile() {
            return approveResultFile;
        }

        public void setApproveResultFile(String approveResultFile) {
            this.approveResultFile = approveResultFile;
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
