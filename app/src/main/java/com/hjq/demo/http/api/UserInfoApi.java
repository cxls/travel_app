package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;

/**
 *    author : Android 轮子哥
 *    time   : 2019/12/07
 *    desc   : 获取用户信息
 */
public final class UserInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "getInfo";
    }

    public final class Bean {
        /** 用户ID */
        private Long userId;

        /** 部门ID */
        private Long deptId;

        /** 用户账号 */
        private String userName;

        /** 用户昵称 */
        private String nickName;

        /** 用户邮箱 */
        private String email;

        /** 手机号码 */
        private String phonenumber;

        /** 用户性别 */
        private String sex;

        /** 用户类型（00系统用户） */
        private String userType;

        /** 年龄 */
        private Long age;

        /** 职业，详见字典 */
        private String profession;

        /** 用户头像 */
        private String avatar;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getDeptId() {
            return deptId;
        }

        public void setDeptId(Long deptId) {
            this.deptId = deptId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Long getAge() {
            return age;
        }

        public void setAge(Long age) {
            this.age = age;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}