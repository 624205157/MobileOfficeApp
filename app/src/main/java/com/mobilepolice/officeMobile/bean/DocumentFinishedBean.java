package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class DocumentFinishedBean {

    /**
     * msg : 该警号有已审批的数据！
     * obj : [{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/C92095B226514CF2A2E6225D52226CB6-100936.jpg","applyPersonName":"","approveImage":"http://www.freetk.cn:8789/mobileworkws-image/approve/C92095B226514CF2A2E6225D52226CB6-100938.jpg","secretLevel":"1","applyPerson":"100936","titel":"hdhsbajnwjhwhsbxhje","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/C92095B226514CF2A2E6225D52226CB6.pdf","urgentLevel":"3","id":"C92095B226514CF2A2E6225D52226CB6","department":"2","requestFlag":"通知","createDate":"2019-03-12 09:18:39"},{"departmentName":"","schema":"1","requestNum":"3","img":"http://www.freetk.cn:8789/mobileworkws-image/document/5E7A3759FC2747BB937FF1EEF0CB6EB2-100935.jpg","applyPersonName":"","approveImage":"http://www.freetk.cn:8789/mobileworkws-image/approve/5E7A3759FC2747BB937FF1EEF0CB6EB2-100938.jpg","secretLevel":"2","applyPerson":"100935","titel":"测试可见⭕","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/5E7A3759FC2747BB937FF1EEF0CB6EB2.pdf","urgentLevel":"2","id":"5E7A3759FC2747BB937FF1EEF0CB6EB2","department":"1","requestFlag":"通知","createDate":"2019-03-11 16:20:19"},{"departmentName":"","schema":"1","requestNum":"3","img":"http://www.freetk.cn:8789/mobileworkws-image/document/FD4262C7DAD6458D885BDDFCC6F6BB72-100935.jpg","applyPersonName":"","approveImage":"http://www.freetk.cn:8789/mobileworkws-image/approve/FD4262C7DAD6458D885BDDFCC6F6BB72-100938.jpg","secretLevel":"2","applyPerson":"100935","titel":"测试数据","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/FD4262C7DAD6458D885BDDFCC6F6BB72.pdf","urgentLevel":"3","id":"FD4262C7DAD6458D885BDDFCC6F6BB72","department":"1","requestFlag":"下发","createDate":"2019-03-11 14:54:42"},{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/7599EB673CF94AF1AD313FDAE8DBEAE5-110754.jpg","applyPersonName":"","approveImage":"http://www.freetk.cn:8789/mobileworkws-image/approve/7599EB673CF94AF1AD313FDAE8DBEAE5-100938.jpg","secretLevel":"3","applyPerson":"110754","titel":"标题1","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/7599EB673CF94AF1AD313FDAE8DBEAE5.pdf","urgentLevel":"1","id":"7599EB673CF94AF1AD313FDAE8DBEAE5","department":"1101","requestFlag":"通知","createDate":"2019-03-11 14:04:28"}]
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
         * departmentName :
         * schema : 1
         * requestNum : 1
         * img : http://www.freetk.cn:8789/mobileworkws-image/document/C92095B226514CF2A2E6225D52226CB6-100936.jpg
         * applyPersonName :
         * approveImage : http://www.freetk.cn:8789/mobileworkws-image/approve/C92095B226514CF2A2E6225D52226CB6-100938.jpg
         * secretLevel : 1
         * applyPerson : 100936
         * titel : hdhsbajnwjhwhsbxhje
         * pdfFile : http://www.freetk.cn:8789/mobileworkws-image/pdf/C92095B226514CF2A2E6225D52226CB6.pdf
         * urgentLevel : 3
         * id : C92095B226514CF2A2E6225D52226CB6
         * department : 2
         * requestFlag : 通知
         * createDate : 2019-03-12 09:18:39
         */

        private String departmentName;
        private String schema;
        private String requestNum;
        private String img;
        private String applyPersonName;
        private String approveImage;
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

        public String getApproveImage() {
            return approveImage;
        }

        public void setApproveImage(String approveImage) {
            this.approveImage = approveImage;
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
}
