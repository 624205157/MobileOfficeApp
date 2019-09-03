package com.mobilepolice.officeMobile.bean;

import java.util.List;

/**
 * Created by miao on 2018/9/27.
 */
public class LoginBean {

    /**
     * flag : 验证结果
     * message : 提示信息
     * id : 暂不使用
     * userInfo : {"id":"id","name":"姓名","sex":"性别","code":"警号","depcode":"单位编码","depid":"单位ID","identifier":"身份证号","mobile":"手机号","police":"警种","position":"职级","effectivedate":"票据有效期","limits":["终端访问权限","终端访问权限","终端访问权       xxx限"]}
     */

    private int flag;
    private String message;
    private String id;
    /**
     * id : id
     * name : 姓名
     * sex : 性别
     * code : 警号
     * depcode : 单位编码
     * depid : 单位ID
     * identifier : 身份证号
     * mobile : 手机号
     * police : 警种
     * position : 职级
     * effectivedate : 票据有效期
     * limits : ["终端访问权限","终端访问权限","终端访问权限"]
     */

    private UserInfoBean userInfo;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String id;
        private String name;
        private String sex;
        private String code;
        private String depcode;
        private String depid;
        private String identifier;
        private String mobile;
        private String police;
        private String position;
        private String effectivedate;
        private List<String> limits;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDepcode() {
            return depcode;
        }

        public void setDepcode(String depcode) {
            this.depcode = depcode;
        }

        public String getDepid() {
            return depid;
        }

        public void setDepid(String depid) {
            this.depid = depid;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPolice() {
            return police;
        }

        public void setPolice(String police) {
            this.police = police;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getEffectivedate() {
            return effectivedate;
        }

        public void setEffectivedate(String effectivedate) {
            this.effectivedate = effectivedate;
        }

        public List<String> getLimits() {
            return limits;
        }

        public void setLimits(List<String> limits) {
            this.limits = limits;
        }
    }
}
