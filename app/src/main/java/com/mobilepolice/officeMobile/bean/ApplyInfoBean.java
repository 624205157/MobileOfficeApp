package com.mobilepolice.officeMobile.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ApplyInfoBean {
    /**
     * msg : 操作成功
     * obj : {"departmentName":"秘书科","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/365175DE5C014100B567E32FCA4095BB-110754.jpg","applyPersonName":"钱科员","approveImage":"","secretLevel":"3","applyPerson":"110754","titel":"标题1","pdfFile":"E:/zhhl/apache-tomcat-secretPhone/webapps/mobileworkws-image/pdf/365175DE5C014100B567E32FCA4095BB.pdf","urgentLevel":"1","id":"365175DE5C014100B567E32FCA4095BB","department":"1101","requestFlag":"通知","createDate":"2019-03-11 13:50:58"}
     * success : true
     * attributes : {"notApprovePerson":[{"approvePersonName":"100935","approvePerson":"100935"}],"approveListInfo":[{"approveType":"1","approvePersonName":"","approveResultFile":"http://www.freetk.cn:8789/mobileworkws-image/approve/365175DE5C014100B567E32FCA4095BB-100938.jpg","approvePerson":"100938","approveFlag":"2","approveOpinion":null,"createDate":"2019-03-12 14:14:46"},{"approveType":"1","approvePersonName":"","approveResultFile":"http://www.freetk.cn:8789/mobileworkws-image/approve/365175DE5C014100B567E32FCA4095BB-100935.jpg","approvePerson":"100935","approveFlag":"2","approveOpinion":null,"createDate":"2019-03-12 09:38:08"},{"approveType":"1","approvePersonName":"","approveResultFile":"http://www.freetk.cn:8789/mobileworkws-image/approve/365175DE5C014100B567E32FCA4095BB-100936.jpg","approvePerson":"100936","approveFlag":"2","approveOpinion":null,"createDate":"2019-03-11 16:14:30"}]}
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

    public static class ObjBean implements Parcelable {
        /**
         * departmentName : 秘书科
         * schema : 1
         * requestNum : 1
         * img : http://www.freetk.cn:8789/mobileworkws-image/document/365175DE5C014100B567E32FCA4095BB-110754.jpg
         * applyPersonName : 钱科员
         * approveImage :
         * secretLevel : 3
         * applyPerson : 110754
         * titel : 标题1
         * pdfFile : E:/zhhl/apache-tomcat-secretPhone/webapps/mobileworkws-image/pdf/365175DE5C014100B567E32FCA4095BB.pdf
         * urgentLevel : 1
         * id : 365175DE5C014100B567E32FCA4095BB
         * department : 1101
         * requestFlag : 通知
         * createDate : 2019-03-11 13:50:58
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
    }

    public static class AttributesBean {
        private List<NotApprovePersonBean> notApprovePerson;
        private List<ApproveListInfoBean> approveListInfo;

        public List<NotApprovePersonBean> getNotApprovePerson() {
            return notApprovePerson;
        }

        public void setNotApprovePerson(List<NotApprovePersonBean> notApprovePerson) {
            this.notApprovePerson = notApprovePerson;
        }

        public ArrayList<ApproveListInfoBean> getApproveListInfo() {
            return new ArrayList<>(approveListInfo);
        }

        public void setApproveListInfo(List<ApproveListInfoBean> approveListInfo) {
            this.approveListInfo = approveListInfo;
        }

        public static class NotApprovePersonBean {
            /**
             * approvePersonName : 100935
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

        public static class ApproveListInfoBean implements Parcelable {
            /**
             * approveType : 1
             * approvePersonName :
             * approveResultFile : http://www.freetk.cn:8789/mobileworkws-image/approve/365175DE5C014100B567E32FCA4095BB-100938.jpg
             * approvePerson : 100938
             * approveFlag : 2
             * approveOpinion : null
             * createDate : 2019-03-12 14:14:46
             */

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.approveType);
                dest.writeString(this.approvePersonName);
                dest.writeString(this.approveResultFile);
                dest.writeString(this.approvePerson);
                dest.writeString(this.approveFlag);
                dest.writeString(this.approveOpinion);
                dest.writeString(this.createDate);
            }

            public ApproveListInfoBean() {
            }

            protected ApproveListInfoBean(Parcel in) {
                this.approveType = in.readString();
                this.approvePersonName = in.readString();
                this.approveResultFile = in.readString();
                this.approvePerson = in.readString();
                this.approveFlag = in.readString();
                this.approveOpinion = in.readString();
                this.createDate = in.readString();
            }

            public static final Parcelable.Creator<ApproveListInfoBean> CREATOR = new Parcelable.Creator<ApproveListInfoBean>() {
                @Override
                public ApproveListInfoBean createFromParcel(Parcel source) {
                    return new ApproveListInfoBean(source);
                }

                @Override
                public ApproveListInfoBean[] newArray(int size) {
                    return new ApproveListInfoBean[size];
                }
            };
        }
    }
}
