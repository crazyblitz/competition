package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * <b>功能：</b>InformationEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information extends BaseEntity {

    private String infoId;
    private String infoTitle;
    @org.springframework.format.annotation.DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date infoReleaseTime;
    private String infoContentPath;
    private Integer infoReadCount;
    private String infoImgBig;
    private String infoImgSmall;
    private String infoResourcePath;
    private String infoDetail;
    private String eventId;
    private Integer infoType;

}
