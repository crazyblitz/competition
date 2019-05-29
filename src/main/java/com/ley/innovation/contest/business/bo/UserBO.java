package com.ley.innovation.contest.business.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.ToString;

/**
 * excel export bo
 **/
@ToString
public class UserBO extends BaseRowModel {


    @ExcelProperty(index = 0, value = "用户名")
    private String userName;
    @ExcelProperty(index = 1, value = "年龄")
    private Integer userAge;
    @ExcelProperty(index = 2, value = "手机号")
    private String userTelphone;
    @ExcelProperty(index = 3, value = "电子邮箱")
    private String userEmail;
    @ExcelProperty(index = 4, value = "赛事名")
    private String eventName;


    public String getUserTelphone() {
        return userTelphone;
    }

    public void setUserTelphone(String userTelphone) {
        this.userTelphone = userTelphone;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
