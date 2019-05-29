package com.ley.innovation.contest.business.page;

import com.ley.springboot.base.page.BasePage;


/**
 * <b>功能：</b>UserPage<br>
 */
public class UserPage extends BasePage {

    private String userId;
    private String userIdOperator = "=";
    private String userTelphone;
    private String userTelphoneOperator = "=";
    private String userAge;
    private String userAgeOperator = "=";
    private String userEmail;
    private String userEmailOperator = "=";
    private String userName;
    private String userNameOperator = "=";
    private String eventId;
    private String eventIdOperator = "=";

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdOperator() {
        return this.userIdOperator;
    }

    public void setUserIdOperator(String userIdOperator) {
        this.userIdOperator = userIdOperator;
    }

    public String getUserTelphone() {
        return this.userTelphone;
    }

    public void setUserTelphone(String userTelphone) {
        this.userTelphone = userTelphone;
    }

    public String getUserTelphoneOperator() {
        return this.userTelphoneOperator;
    }

    public void setUserTelphoneOperator(String userTelphoneOperator) {
        this.userTelphoneOperator = userTelphoneOperator;
    }

    public String getUserAge() {
        return this.userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserAgeOperator() {
        return this.userAgeOperator;
    }

    public void setUserAgeOperator(String userAgeOperator) {
        this.userAgeOperator = userAgeOperator;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmailOperator() {
        return this.userEmailOperator;
    }

    public void setUserEmailOperator(String userEmailOperator) {
        this.userEmailOperator = userEmailOperator;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameOperator() {
        return this.userNameOperator;
    }

    public void setUserNameOperator(String userNameOperator) {
        this.userNameOperator = userNameOperator;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventIdOperator() {
        return this.eventIdOperator;
    }

    public void setEventIdOperator(String eventIdOperator) {
        this.eventIdOperator = eventIdOperator;
    }

}
