package com.ley.innovation.contest.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <b>功能：</b>AdminEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseEntity {

    private String adminId;

    private String adminName;

    private String adminPassword;

    @JsonIgnore
    private String encryptKey;


    private Integer isAdminSuper;

    @JsonIgnore
    private String plainPassword;

}
