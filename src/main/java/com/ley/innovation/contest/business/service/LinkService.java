package com.ley.innovation.contest.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.LinkDao;
import com.ley.innovation.contest.business.entity.Link;
import lombok.extern.slf4j.Slf4j;


/**
 * <br>
 * <b>功能：</b>LinkService<br>
 */
@Service("linkService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class LinkService extends BaseService<Link, String> {


    @Autowired(required = false)
    private LinkDao dao;

    public LinkDao getDao() {
        return dao;
    }

}
