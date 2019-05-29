package com.ley.innovation.contest.business.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网站承办单位网站配置
 *
 * @author ley
 **/
@Data
public class WebsiteCommitteeBO {

    @ApiModelProperty("网站承办方配置Id")
    private String websiteCommitteeId;

    @ApiModelProperty("承办单位")
    private String organizer;

    @ApiModelProperty("主办单位")
    private String hostUnit;

    @ApiModelProperty("居委会联系方式")
    private String committee;

    @ApiModelProperty("网站承办方配置背景图")
    private String websiteBkImg2;
}
