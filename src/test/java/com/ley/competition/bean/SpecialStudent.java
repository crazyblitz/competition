package com.ley.competition.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SpecialStudent extends Student {

    private String sex;

}
