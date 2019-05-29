package com.ley.innovation.contest.business.entity;

import com.ley.springboot.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>功能：</b>ProcessEntity<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Process extends BaseEntity {

    private String processId;
    private String processTime;
    private Integer processEnable;
    private Integer processOrder;
    private Integer processStage;
    private String processDesc;
    private String processTitle;

}
