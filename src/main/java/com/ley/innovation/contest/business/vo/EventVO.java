package com.ley.innovation.contest.business.vo;

import com.ley.innovation.contest.business.entity.Event;
import com.ley.innovation.contest.business.entity.User;
import lombok.Data;

import java.util.List;

/**
 * event vo
 *
 * @author ley
 **/
@Data
public class EventVO extends Event {

    private String carouselPath;

    private String eventContent;

    private List<User> users;

}
