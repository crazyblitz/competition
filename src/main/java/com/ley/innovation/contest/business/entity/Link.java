package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <b>功能：</b>LinkEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link extends BaseEntity {

    private String linkId;
    private String linkName;
    private String linkPhone;
    private String linkTelphone;
    private String linkEmail;
    private String linkAddress;
    private String linkPostalCode;
    private String linkFax;

}
