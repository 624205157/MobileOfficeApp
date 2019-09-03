package com.fish.timeline;

import java.util.List;

public class TaskInfo {
    /**
     * msg : 查询成功！
     * obj : [{"fromDate":"2019-03-12 10:10:10","policeNumber":"456","toDate":"2019-03-12 21:10:10","id":"E476B47AFC2449469F76431426D0FE27","content":"测试内容"},{"fromDate":"2019-03-12 10:10:10","policeNumber":"456","toDate":"2019-03-12 21:10:10","id":"DE25CF072B564327A57C1B8CF7B974F8","content":"测试内容"},{"fromDate":"2019-03-12 10:10:10","policeNumber":"456","toDate":"2019-03-12 21:10:10","id":"3D24D74F1A224CD099703EF6190DBB17","content":"测试内容"}]
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

    public static class ObjBean implements Comparable<ObjBean> {
        /**
         * fromDate : 2019-03-12 10:10:10
         * policeNumber : 456
         * toDate : 2019-03-12 21:10:10
         * id : E476B47AFC2449469F76431426D0FE27
         * content : 测试内容
         */

        private String fromDate;
        private String policeNumber;
        private String toDate;
        private String id;
        private String content;

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getPoliceNumber() {
            return policeNumber;
        }

        public void setPoliceNumber(String policeNumber) {
            this.policeNumber = policeNumber;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int compareTo(ObjBean o) {
            if (DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", toDate) < DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", o.toDate))
                return -1;
            else if (DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", toDate) > DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", o.toDate))
                return 1;
            else
                return 0;
        }
    }
}
