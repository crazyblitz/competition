package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * <b>功能：</b>EventEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {

    private String eventId;
    private String eventTitle;
    private String eventContentPath;
    @org.springframework.format.annotation.DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date eventReleaseTime;
    private Integer eventReadCount;
    private Integer eventEnable;
    private String carouselId;

}
