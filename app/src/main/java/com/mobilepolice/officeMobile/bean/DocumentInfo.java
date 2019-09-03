package com.mobilepolice.officeMobile.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fish.timeline.DateUtil;

import java.util.List;

public class DocumentInfo {
    /**
     * msg : 查询成功
     * obj : [{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/F3E0D18D91E84DE788744898CD11F8B7-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"3","applyPerson":"110754","titel":"买买买~","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/F3E0D18D91E84DE788744898CD11F8B7.pdf","urgentLevel":"3","id":"F3E0D18D91E84DE788744898CD11F8B7","department":"3","requestFlag":"通知","createDate":"2019-03-12 16:51:41"},{"departmentName":"","schema":"1","requestNum":"3","img":"http://www.freetk.cn:8789/mobileworkws-image/document/81D7609834E34B898614FD6532861161-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"1","applyPerson":"110754","titel":"吃吃吃~","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/81D7609834E34B898614FD6532861161.pdf","urgentLevel":"1","id":"81D7609834E34B898614FD6532861161","department":"3","requestFlag":"通知","createDate":"2019-03-12 16:52:48"},{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/D7192012119944709A5B50CEC62817E8-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"1","applyPerson":"110754","titel":"吃吃吃吃","pdfFile":"","urgentLevel":"1","id":"D7192012119944709A5B50CEC62817E8","department":"3","requestFlag":"通知","createDate":"2019-03-13 16:13:03"},{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/1554694722519-100936.jpg,http://www.freetk.cn:8789/mobileworkws-image/document/1554694722582-100936.jpg","applyPersonName":"","approveImage":"","secretLevel":"3","applyPerson":"100936","titel":"1","pdfFile":"","urgentLevel":"3","id":"BCC2EA2612834F2D9A5B05AFDC66DBB2","department":"2","requestFlag":"通知","createDate":"2019-04-08 11:38:42"},{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/DC3A3B68C93944358F65E91F8CA9FFD6-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"3","applyPerson":"110754","titel":"测试","pdfFile":"","urgentLevel":"3","id":"DC3A3B68C93944358F65E91F8CA9FFD6","department":"3","requestFlag":"通知","createDate":"2019-03-13 15:46:00"},{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/925CE3ED71504065B1B8199CCA7CA80E-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"1","applyPerson":"110754","titel":"测试1","pdfFile":"","urgentLevel":"1","id":"925CE3ED71504065B1B8199CCA7CA80E","department":"3","requestFlag":"通知","createDate":"2019-03-13 15:48:19"},{"departmentName":"","schema":"1","requestNum":"12","img":"http://www.freetk.cn:8789/mobileworkws-image/document/0E2D49223DE2482EABF41EAE3FA99A4E-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"1","applyPerson":"110754","titel":"测试36","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/0E2D49223DE2482EABF41EAE3FA99A4E.pdf","urgentLevel":"2","id":"0E2D49223DE2482EABF41EAE3FA99A4E","department":"3","requestFlag":"通知","createDate":"2019-03-13 15:48:58"},{"departmentName":"","schema":"1","requestNum":"1","img":"D:/jeecgProjecg/mobileworkws-image/document/8C492AD7BDB441EABA0069C51F035DBE-110755.jpg","applyPersonName":"","approveImage":"","secretLevel":"3","applyPerson":"110755","titel":"冯金龙测试标题1","pdfFile":"","urgentLevel":"1","id":"8C492AD7BDB441EABA0069C51F035DBE","department":"1101","requestFlag":"下发","createDate":"2019-03-11 16:39:31"},{"departmentName":"","schema":"1","requestNum":"1","img":"D:/jeecgProjecg/mobileworkws-image/document/1554360947953-1111867.jpg,D:/jeecgProjecg/mobileworkws-image/document/1554360948155-1111867.jpg","applyPersonName":"","approveImage":"","secretLevel":"1","applyPerson":"1111867","titel":"测试标题1","pdfFile":"","urgentLevel":"1","id":"DC326586B208435A80621304ED841EE3","department":"1101","requestFlag":"测试通知","createDate":"2019-04-04 14:55:48"},{"departmentName":"","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/7599EB673CF94AF1AD313FDAE8DBEAE5-110754.jpg","applyPersonName":"","approveImage":"","secretLevel":"3","applyPerson":"110754","titel":"标题1","pdfFile":"http://www.freetk.cn:8789/mobileworkws-image/pdf/7599EB673CF94AF1AD313FDAE8DBEAE5.pdf","urgentLevel":"1","id":"7599EB673CF94AF1AD313FDAE8DBEAE5","department":"1101","requestFlag":"通知","createDate":"2019-03-11 14:04:28"}]
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

    public static class ObjBean implements Parcelable, Comparable<ObjBean> {
        /**
         * departmentName :
         * schema : 1
         * requestNum : 1
         * img : http://www.freetk.cn:8789/mobileworkws-image/document/F3E0D18D91E84DE788744898CD11F8B7-110754.jpg
         * applyPersonName :
         * approveImage :
         * secretLevel : 3
         * applyPerson : 110754
         * titel : 买买买~
         * pdfFile : http://www.freetk.cn:8789/mobileworkws-image/pdf/F3E0D18D91E84DE788744898CD11F8B7.pdf
         * urgentLevel : 3
         * id : F3E0D18D91E84DE788744898CD11F8B7
         * department : 3
         * requestFlag : 通知
         * createDate : 2019-03-12 16:51:41
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.departmentName);
            dest.writeString(this.schema);
            dest.writeString(this.requestNum);
            dest.writeString(this.img);
            dest.writeString(this.applyPersonName);
            dest.writeString(this.approveImage);
            dest.writeString(this.secretLevel);
            dest.writeString(this.applyPerson);
            dest.writeString(this.titel);
            dest.writeString(this.pdfFile);
            dest.writeString(this.urgentLevel);
            dest.writeString(this.id);
            dest.writeString(this.department);
            dest.writeString(this.requestFlag);
            dest.writeString(this.createDate);
        }

        public ObjBean() {
        }

        protected ObjBean(Parcel in) {
            this.departmentName = in.readString();
            this.schema = in.readString();
            this.requestNum = in.readString();
            this.img = in.readString();
            this.applyPersonName = in.readString();
            this.approveImage = in.readString();
            this.secretLevel = in.readString();
            this.applyPerson = in.readString();
            this.titel = in.readString();
            this.pdfFile = in.readString();
            this.urgentLevel = in.readString();
            this.id = in.readString();
            this.department = in.readString();
            this.requestFlag = in.readString();
            this.createDate = in.readString();
        }

        public static final Parcelable.Creator<ObjBean> CREATOR = new Parcelable.Creator<ObjBean>() {
            @Override
            public ObjBean createFromParcel(Parcel source) {
                return new ObjBean(source);
            }

            @Override
            public ObjBean[] newArray(int size) {
                return new ObjBean[size];
            }
        };

        @Override
        public int compareTo(@NonNull ObjBean o) {
            long current = DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", createDate);
            long other = DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", o.createDate);
            if (current - other > 0)return -1;
            else if (current - other< 0) return  1;
            else
                return 0;
        }
    }
}
