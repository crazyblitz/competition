package com.ley.competition.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Student extends Person {

    private String idCard;

    private String major;
}
