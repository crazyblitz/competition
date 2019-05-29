package com.ley.innovation.contest.business.dao;

import com.ley.innovation.contest.business.page.EventPage;
import com.ley.innovation.contest.business.vo.EventVO;
import com.ley.springboot.base.dao.BaseDao;
import com.ley.innovation.contest.business.entity.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <b>功能：</b>EventDao<br>
 **/
@Mapper
public interface EventDao extends BaseDao<Event> {

    /**
     * list event vos
     **/
    List<EventVO> listEventVOs(EventPage eventPage);
}
