package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <b>功能：</b>WebsiteEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Website extends BaseEntity {

    private String websiteBkImg2;
    private Integer configType;
    private String eventSynopsisDesc1;
    private String eventSynopsisDesc2;
    private String processDesc;
    private String organizer;
    private String hostUnit;
    private String committee;
    private String eventSynopsisBkImg;
    private String websiteId;
    private String websiteTitle;
    private String websiteLogo;
    private String websiteWeixinImg;

}
