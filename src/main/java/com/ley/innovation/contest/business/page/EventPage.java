package com.ley.innovation.contest.business.page;

import com.ley.springboot.base.page.BasePage;

import java.util.Date;

/**
 * <b>功能：</b>EventPage<br>
 */
public class EventPage extends BasePage {

    private String eventId;
    private String eventIdOperator = "=";
    private String eventTitle;
    private String eventTitleOperator = "=";
    private String eventContentPath;
    private String eventContentPathOperator = "=";
    private String eventReleaseTime;
    private String eventReleaseTime1;
    private String eventReleaseTime2;
    private String eventReleaseTimeOperator = "=";
    private String eventReadCount;
    private String eventReadCountOperator = "=";
    private String eventEnable;
    private String eventEnableOperator = "=";
    private String carouselId;
    private String carouselIdOperator = "=";

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

    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventTitleOperator() {
        return this.eventTitleOperator;
    }

    public void setEventTitleOperator(String eventTitleOperator) {
        this.eventTitleOperator = eventTitleOperator;
    }

    public String getEventContentPath() {
        return this.eventContentPath;
    }

    public void setEventContentPath(String eventContentPath) {
        this.eventContentPath = eventContentPath;
    }

    public String getEventContentPathOperator() {
        return this.eventContentPathOperator;
    }

    public void setEventContentPathOperator(String eventContentPathOperator) {
        this.eventContentPathOperator = eventContentPathOperator;
    }

    public String getEventReleaseTime() {
        return this.eventReleaseTime;
    }

    public void setEventReleaseTime(String eventReleaseTime) {
        this.eventReleaseTime = eventReleaseTime;
    }

    public String getEventReleaseTime1() {
        return this.eventReleaseTime1;
    }

    public void setEventReleaseTime1(String eventReleaseTime1) {
        this.eventReleaseTime1 = eventReleaseTime1;
    }

    public String getEventReleaseTime2() {
        return this.eventReleaseTime2;
    }

    public void setEventReleaseTime2(String eventReleaseTime2) {
        this.eventReleaseTime2 = eventReleaseTime2;
    }

    public String getEventReleaseTimeOperator() {
        return this.eventReleaseTimeOperator;
    }

    public void setEventReleaseTimeOperator(String eventReleaseTimeOperator) {
        this.eventReleaseTimeOperator = eventReleaseTimeOperator;
    }

    public String getEventReadCount() {
        return this.eventReadCount;
    }

    public void setEventReadCount(String eventReadCount) {
        this.eventReadCount = eventReadCount;
    }

    public String getEventReadCountOperator() {
        return this.eventReadCountOperator;
    }

    public void setEventReadCountOperator(String eventReadCountOperator) {
        this.eventReadCountOperator = eventReadCountOperator;
    }

    public String getEventEnable() {
        return this.eventEnable;
    }

    public void setEventEnable(String eventEnable) {
        this.eventEnable = eventEnable;
    }

    public String getEventEnableOperator() {
        return this.eventEnableOperator;
    }

    public void setEventEnableOperator(String eventEnableOperator) {
        this.eventEnableOperator = eventEnableOperator;
    }

    public String getCarouselId() {
        return this.carouselId;
    }

    public void setCarouselId(String carouselId) {
        this.carouselId = carouselId;
    }

    public String getCarouselIdOperator() {
        return this.carouselIdOperator;
    }

    public void setCarouselIdOperator(String carouselIdOperator) {
        this.carouselIdOperator = carouselIdOperator;
    }

}
