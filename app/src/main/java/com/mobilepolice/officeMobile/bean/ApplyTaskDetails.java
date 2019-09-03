package com.mobilepolice.officeMobile.bean;

import java.util.List;

public class ApplyTaskDetails {
    /**
     * msg : 操作成功
     * obj : [{"overFlag":"0","applyPersonName":"赵科长","approveNode":"","title":"11","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/1554715243298-100935.jpg,http://www.freetk.cn:8789/mobileworkws-image/document/1554715243391-100935.jpg,http://www.freetk.cn:8789/mobileworkws-image/document/1554715243500-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"3","requestid":"D23209A36F7B4254AB38B0E85DF32434","approvePerson":"100936","approveNodeId":"1","id":"FFBF63CED48F48419A488C53419581D6","createDate":"2019-04-08 17:20:43"},{"overFlag":"1","applyPersonName":"赵科长","approveNode":"流程结束","title":"那就给我整两个","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/C95F85C60BB94E569D912653399F66C6-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"3","requestid":"C95F85C60BB94E569D912653399F66C6","approvePerson":"","approveNodeId":"","id":"265402084C89401B938867D846630E0E","createDate":"2019-03-12 14:13:00"},{"overFlag":"2","applyPersonName":"赵科长","approveNode":"被驳回结束","title":"驳回2","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/8D8774E8877B4EDD8F41181BF63D7578-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"3","requestid":"8D8774E8877B4EDD8F41181BF63D7578","approvePerson":"100935","approveNodeId":"","id":"747079981B5A4216B8C865BCD575B3AD","createDate":"2019-03-11 18:27:45"},{"overFlag":"2","applyPersonName":"赵科长","approveNode":"被驳回结束","title":"请驳回","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/130CAB41B63E40959651A2EE322AD691-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"1","requestid":"130CAB41B63E40959651A2EE322AD691","approvePerson":"100935","approveNodeId":"","id":"78245E6ADF3C47FB9716BD0B3CF8754F","createDate":"2019-03-11 18:23:29"},{"overFlag":"2","applyPersonName":"赵科长","approveNode":"被驳回结束","title":"炸厕所","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/E5F2DDAEA0444C048B7FAE592A3BEACF-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"1","requestid":"E5F2DDAEA0444C048B7FAE592A3BEACF","approvePerson":"100935","approveNodeId":"","id":"EBAA1E58E8F7493BB559AB609597782A","createDate":"2019-03-11 17:49:31"},{"overFlag":"1","applyPersonName":"赵科长","approveNode":"流程结束","title":"测试可见⭕","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/5E7A3759FC2747BB937FF1EEF0CB6EB2-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"2","requestid":"5E7A3759FC2747BB937FF1EEF0CB6EB2","approvePerson":"","approveNodeId":"","id":"DF42656618DA4EDCB0499C97135315B1","createDate":"2019-03-11 16:20:19"},{"overFlag":"1","applyPersonName":"赵科长","approveNode":"流程结束","title":"测试数据","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/FD4262C7DAD6458D885BDDFCC6F6BB72-100935.jpg","applyPerson":"100935","approvePersonName":"","urgentLevel":"3","requestid":"FD4262C7DAD6458D885BDDFCC6F6BB72","approvePerson":"","approveNodeId":"","id":"1E509F40D95F4B0EAFD65C33BCB4A236","createDate":"2019-03-11 14:54:42"}]
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
         * applyPersonName : 赵科长
         * approveNode :
         * title : 11
         * applyOffWordFile : http://www.freetk.cn:8789/mobileworkws-image/document/1554715243298-100935.jpg,http://www.freetk.cn:8789/mobileworkws-image/document/1554715243391-100935.jpg,http://www.freetk.cn:8789/mobileworkws-image/document/1554715243500-100935.jpg
         * applyPerson : 100935
         * approvePersonName :
         * urgentLevel : 3
         * requestid : D23209A36F7B4254AB38B0E85DF32434
         * approvePerson : 100936
         * approveNodeId : 1
         * id : FFBF63CED48F48419A488C53419581D6
         * createDate : 2019-04-08 17:20:43
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
