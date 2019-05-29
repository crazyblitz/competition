package com.ley.innovation.contest.business.service;

import com.ley.innovation.contest.business.page.EventPage;
import com.ley.innovation.contest.business.vo.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.EventDao;
import com.ley.innovation.contest.business.entity.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>EventService<br>
 */
@Service("eventService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class EventService extends BaseService<Event, String> {


    @Autowired(required = false)
    private EventDao dao;

    public EventDao getDao() {
        return dao;
    }


    /**
     * 获取赛事VO
     *
     * @see EventVO
     **/
    public List<EventVO> listEventVOs(EventPage page) {
        return dao.listEventVOs(page);
    }
}
