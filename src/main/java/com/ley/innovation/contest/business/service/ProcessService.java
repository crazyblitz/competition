package com.ley.innovation.contest.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.ProcessDao;
import com.ley.innovation.contest.business.entity.Process;
import lombok.extern.slf4j.Slf4j;



/**
 * <br>
 * <b>功能：</b>ProcessService<br>
 */
@Service("processService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class ProcessService extends BaseService<Process, String> {


    @Autowired(required = false)
    private ProcessDao dao;


    public ProcessDao getDao() {
        return dao;
    }

}
