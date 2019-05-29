package com.ley.innovation.contest.business.service;

import com.ley.innovation.contest.business.page.CarouselPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.CarouselDao;
import com.ley.innovation.contest.business.entity.Carousel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>CarouselService<br>
 */
@Service("carouselService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class CarouselService extends BaseService<Carousel, String> {


    @Autowired(required = false)
    private CarouselDao dao;

    public CarouselDao getDao() {
        return dao;
    }


    /**
     * get carouse by event id
     **/
    public Carousel getCarouselByEventId(String eventId) {
        CarouselPage page = new CarouselPage();
        page.setEventId(eventId);
        List<Carousel> carouselList = dao.queryByPage(page);
        if (CollectionUtils.isEmpty(carouselList)) {
            return null;
        } else {
            return carouselList.get(0);
        }
    }

}
