package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <b>功能：</b>UserEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    private String userId;
    private String userTelphone;
    private Integer userAge;
    private String userEmail;
    private String userName;
    private String eventId;

}
