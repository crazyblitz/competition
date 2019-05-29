package com.ley.innovation.contest.business.controller;

import com.ley.springboot.base.page.BasePage;


/**
 * <b>功能：</b>ProcessPage<br>
 */
public class ProcessPage extends BasePage {

    private String processId;
    private String processIdOperator = "=";
    private String processTime;
    private String processTimeOperator = "=";
    private String processTime1;
    private String processTime2;
    private String processEnable;
    private String processEnableOperator = "=";
    private String processOrder;
    private String processOrderOperator = "=";
    private String processStage;
    private String processStageOperator = "=";
    private String processDesc;
    private String processDescOperator = "=";
    private String processTitle;
    private String processTitleOperator = "=";


    public String getProcessId() {
        return this.processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessIdOperator() {
        return this.processIdOperator;
    }

    public void setProcessIdOperator(String processIdOperator) {
        this.processIdOperator = processIdOperator;
    }

    public String getProcessTime() {
        return this.processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getProcessTimeOperator() {
        return this.processTimeOperator;
    }

    public void setProcessTimeOperator(String processTimeOperator) {
        this.processTimeOperator = processTimeOperator;
    }

    public String getProcessEnable() {
        return this.processEnable;
    }

    public void setProcessEnable(String processEnable) {
        this.processEnable = processEnable;
    }

    public String getProcessEnableOperator() {
        return this.processEnableOperator;
    }

    public void setProcessEnableOperator(String processEnableOperator) {
        this.processEnableOperator = processEnableOperator;
    }

    public String getProcessOrder() {
        return this.processOrder;
    }

    public void setProcessOrder(String processOrder) {
        this.processOrder = processOrder;
    }

    public String getProcessOrderOperator() {
        return this.processOrderOperator;
    }

    public void setProcessOrderOperator(String processOrderOperator) {
        this.processOrderOperator = processOrderOperator;
    }

    public String getProcessStage() {
        return this.processStage;
    }

    public void setProcessStage(String processStage) {
        this.processStage = processStage;
    }

    public String getProcessStageOperator() {
        return this.processStageOperator;
    }

    public void setProcessStageOperator(String processStageOperator) {
        this.processStageOperator = processStageOperator;
    }

    public String getProcessDesc() {
        return this.processDesc;
    }

    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

    public String getProcessDescOperator() {
        return this.processDescOperator;
    }

    public void setProcessDescOperator(String processDescOperator) {
        this.processDescOperator = processDescOperator;
    }

    public String getProcessTitle() {
        return this.processTitle;
    }

    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    public String getProcessTitleOperator() {
        return this.processTitleOperator;
    }

    public void setProcessTitleOperator(String processTitleOperator) {
        this.processTitleOperator = processTitleOperator;
    }

    public String getProcessTime1() {
        return processTime1;
    }

    public void setProcessTime1(String processTime1) {
        this.processTime1 = processTime1;
    }

    public String getProcessTime2() {
        return processTime2;
    }

    public void setProcessTime2(String processTime2) {
        this.processTime2 = processTime2;
    }
}
