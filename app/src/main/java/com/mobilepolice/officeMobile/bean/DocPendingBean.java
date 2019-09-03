package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class DocPendingBean extends BaseBean {

    /**
     * msg : 操作成功
     * obj : {"departmentName":"秘书科","schema":"2","requestNum":"5","img":"http://www.freetk.cn:8789/mobileworkws-image/document/1870A88BCB4D41AEB7CA65D35CDB36EF-110754.jpg","applyPersonName":"钱科员","secretLevel":"3","applyPerson":"110754","titel":"条件","pdfFile":"","urgentLevel":"3","id":"1870A88BCB4D41AEB7CA65D35CDB36EF","department":"3","requestFlag":"通知","createDate":"2019-01-02 12:25:33"}
     * success : true
     * attributes : {"approveNodeId":"1","notApprovePerson":[{"approvePersonName":"赵科长","approvePerson":"100935"},{"approvePersonName":"李厅长","approvePerson":"100938"}],"approveListInfo":[{"approveType":"2","approvePersonName":"","approveResultFile":"http://www.freetk.cn:8789/mobileworkws-image/approve/1870A88BCB4D41AEB7CA65D35CDB36EF-100936.jpg","approvePerson":"100936","approveFlag":"2","approveOpinion":null,"createDate":"2019-01-02 16:13:58"}]}
     */

    private String msg;
    private ObjBean obj;
    private boolean success;
    private AttributesBean attributes;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public static class ObjBean extends BaseBean {
        /**
         * departmentName : 秘书科
         * schema : 2
         * requestNum : 5
         * img : http://www.freetk.cn:8789/mobileworkws-image/document/1870A88BCB4D41AEB7CA65D35CDB36EF-110754.jpg
         * applyPersonName : 钱科员
         * secretLevel : 3
         * applyPerson : 110754
         * titel : 条件
         * pdfFile :
         * urgentLevel : 3
         * id : 1870A88BCB4D41AEB7CA65D35CDB36EF
         * department : 3
         * requestFlag : 通知
         * createDate : 2019-01-02 12:25:33
         */

        private String departmentName;
        private String schema;
        private String requestNum;
        private String img;
        private String applyPersonName;
        private String secretLevel;
        private String applyPerson;
        private String titel;
        private String pdfFile;
        private String urgentLevel;
        private String id;
        private String department;
        private String requestFlag;
        private String createDate;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getRequestNum() {
            return requestNum;
        }

        public void setRequestNum(String requestNum) {
            this.requestNum = requestNum;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getApplyPersonName() {
            return applyPersonName;
        }

        public void setApplyPersonName(String applyPersonName) {
            this.applyPersonName = applyPersonName;
        }

        public String getSecretLevel() {
            return secretLevel;
        }

        public void setSecretLevel(String secretLevel) {
            this.secretLevel = secretLevel;
        }

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

        public String getPdfFile() {
            return pdfFile;
        }

        public void setPdfFile(String pdfFile) {
            this.pdfFile = pdfFile;
        }

        public String getUrgentLevel() {
            return urgentLevel;
        }

        public void setUrgentLevel(String urgentLevel) {
            this.urgentLevel = urgentLevel;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getRequestFlag() {
            return requestFlag;
        }

        public void setRequestFlag(String requestFlag) {
            this.requestFlag = requestFlag;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }

    public static class AttributesBean extends BaseBean {
        /**
         * approveNodeId : 1
         * notApprovePerson : [{"approvePersonName":"赵科长","approvePerson":"100935"},{"approvePersonName":"李厅长","approvePerson":"100938"}]
         * approveListInfo : [{"approveType":"2","approvePersonName":"","approveResultFile":"http://www.freetk.cn:8789/mobileworkws-image/approve/1870A88BCB4D41AEB7CA65D35CDB36EF-100936.jpg","approvePerson":"100936","approveFlag":"2","approveOpinion":null,"createDate":"2019-01-02 16:13:58"}]
         */

        private String approveNodeId;
        private List<NotApprovePersonBean> notApprovePerson;
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
             * approvePersonName : 赵科长
             * approvePerson : 100935
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
            /**
             * approveType : 2
             * approvePersonName :
             * approveResultFile : http://www.freetk.cn:8789/mobileworkws-image/approve/1870A88BCB4D41AEB7CA65D35CDB36EF-100936.jpg
             * approvePerson : 100936
             * approveFlag : 2
             * approveOpinion : null
             * createDate : 2019-01-02 16:13:58
             */
            public ApproveListInfoBean() {
            }

            public ApproveListInfoBean(String approvePerson, String approvePersonName) {
                this.approvePersonName = approvePersonName;
                this.approvePerson = approvePerson;
            }

            private String approveType;
            private String approvePersonName;
            private String approveResultFile;
            private String approvePerson;
            private String approveFlag;
            private String approveOpinion;
            private String createDate;

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

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }
    }
}
