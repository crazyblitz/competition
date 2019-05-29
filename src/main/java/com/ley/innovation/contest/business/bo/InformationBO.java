package com.ley.innovation.contest.business.bo;

import com.ley.innovation.contest.business.entity.Information;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ley
 **/
@Data
public class InformationBO extends Information {

    @ApiModelProperty("信息内容")
    private String infoContent;

    @ApiModelProperty("信息发布内容毫秒")
    private String infoReleaseTimeLong;
}
