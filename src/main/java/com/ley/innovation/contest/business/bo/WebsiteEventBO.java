package com.ley.innovation.contest.business.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大赛简介网站配置
 *
 * @author ley
 **/
@Data
public class WebsiteEventBO {

    @ApiModelProperty("大赛背景简介")
    private String eventSynopsisDesc1;

    @ApiModelProperty("大赛概况简介")
    private String eventSynopsisDesc2;

    @ApiModelProperty("大赛简介配置Id")
    private String websiteEventId;
}
