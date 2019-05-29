package com.ley.innovation.contest.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.WebsiteDao;
import com.ley.innovation.contest.business.entity.Website;
import lombok.extern.slf4j.Slf4j;


/**
 * <br>
 * <b>功能：</b>WebsiteService<br>
 */
@Service("websiteService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class WebsiteService extends BaseService<Website, String> {


    @Autowired(required = false)
    private WebsiteDao dao;

    public WebsiteDao getDao() {
        return dao;
    }

}
