package com.ley.innovation.contest.business.page;

import com.ley.springboot.base.page.BasePage;


/**
 * <b>功能：</b>AdminPage<br>
 */
public class AdminPage extends BasePage {

    private String adminId;
    private String adminIdOperator = "=";
    private String adminName;
    private String adminNameOperator = "=";
    private String adminPassword;
    private String adminPasswordOperator = "=";
    private String encryptKey;
    private String encryptKeyOperator = "=";
    private String isAdminSuper;
    private String isAdminSuperOperator = "=";

    public String getAdminId() {
        return this.adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminIdOperator() {
        return this.adminIdOperator;
    }

    public void setAdminIdOperator(String adminIdOperator) {
        this.adminIdOperator = adminIdOperator;
    }

    public String getAdminName() {
        return this.adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminNameOperator() {
        return this.adminNameOperator;
    }

    public void setAdminNameOperator(String adminNameOperator) {
        this.adminNameOperator = adminNameOperator;
    }

    public String getAdminPassword() {
        return this.adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPasswordOperator() {
        return this.adminPasswordOperator;
    }

    public void setAdminPasswordOperator(String adminPasswordOperator) {
        this.adminPasswordOperator = adminPasswordOperator;
    }

    public String getEncryptKey() {
        return this.encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getEncryptKeyOperator() {
        return this.encryptKeyOperator;
    }

    public void setEncryptKeyOperator(String encryptKeyOperator) {
        this.encryptKeyOperator = encryptKeyOperator;
    }

    public String getIsAdminSuper() {
        return this.isAdminSuper;
    }

    public void setIsAdminSuper(String isAdminSuper) {
        this.isAdminSuper = isAdminSuper;
    }

    public String getIsAdminSuperOperator() {
        return this.isAdminSuperOperator;
    }

    public void setIsAdminSuperOperator(String isAdminSuperOperator) {
        this.isAdminSuperOperator = isAdminSuperOperator;
    }

}
