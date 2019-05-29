package com.ley.innovation.contest.business.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大赛流程简介网站配置
 **/
@Data
public class WebsiteProcessBO {

    @ApiModelProperty("网站赛事流程Id")
    private String websiteProcessId;

    @ApiModelProperty("赛事流程描述")
    private String processDesc;
}
