package com.ley.innovation.contest.business.page;

import com.ley.springboot.base.page.BasePage;


/**
 * <b>功能：</b>CarouselPage<br>
 */
public class CarouselPage extends BasePage {

    private String carouselId;
    private String carouselIdOperator = "=";
    private String carouselPath;
    private String carouselPathOperator = "=";
    private String carouselLink;
    private String carouselLinkOperator = "=";
    private String carouselOrder;
    private String carouselOrderOperator = "=";
    private String eventId;
    private String eventIdOperator = "=";

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

    public String getCarouselPath() {
        return this.carouselPath;
    }

    public void setCarouselPath(String carouselPath) {
        this.carouselPath = carouselPath;
    }

    public String getCarouselPathOperator() {
        return this.carouselPathOperator;
    }

    public void setCarouselPathOperator(String carouselPathOperator) {
        this.carouselPathOperator = carouselPathOperator;
    }

    public String getCarouselLink() {
        return this.carouselLink;
    }

    public void setCarouselLink(String carouselLink) {
        this.carouselLink = carouselLink;
    }

    public String getCarouselLinkOperator() {
        return this.carouselLinkOperator;
    }

    public void setCarouselLinkOperator(String carouselLinkOperator) {
        this.carouselLinkOperator = carouselLinkOperator;
    }

    public String getCarouselOrder() {
        return this.carouselOrder;
    }

    public void setCarouselOrder(String carouselOrder) {
        this.carouselOrder = carouselOrder;
    }

    public String getCarouselOrderOperator() {
        return this.carouselOrderOperator;
    }

    public void setCarouselOrderOperator(String carouselOrderOperator) {
        this.carouselOrderOperator = carouselOrderOperator;
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
