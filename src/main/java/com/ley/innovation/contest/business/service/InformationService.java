package com.ley.innovation.contest.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.InformationDao;
import com.ley.innovation.contest.business.entity.Information;
import lombok.extern.slf4j.Slf4j;


/**
 * <br>
 * <b>功能：</b>InformationService<br>
 */
@Service("informationService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class InformationService extends BaseService<Information, String> {


    @Autowired(required = false)
    private InformationDao dao;

    public InformationDao getDao() {
        return dao;
    }

}
