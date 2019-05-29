package com.ley.innovation.contest.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.AdminDao;
import com.ley.innovation.contest.business.entity.Admin;
import lombok.extern.slf4j.Slf4j;


/**
 * <br>
 * <b>功能：</b>AdminService<br>
 */
@Service("adminService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class AdminService extends BaseService<Admin, String> {


    @Autowired(required = false)
    private AdminDao dao;

    public AdminDao getDao() {
        return dao;
    }

}
