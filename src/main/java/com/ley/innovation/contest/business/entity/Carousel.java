package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <b>功能：</b>CarouselEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carousel extends BaseEntity {

    private String carouselId;
    private String carouselPath;
    private String carouselLink;
    private Integer carouselOrder;
    private String eventId;

}
