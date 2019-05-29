package com.ley.innovation.contest.business.service;

import com.ley.innovation.contest.business.bo.UserBO;
import com.ley.innovation.contest.business.dao.EventDao;
import com.ley.innovation.contest.business.page.UserPage;
import com.ley.innovation.contest.utils.BeanMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ley.springboot.base.service.BaseService;
import com.ley.innovation.contest.business.dao.UserDao;
import com.ley.innovation.contest.business.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * <br>
 * <b>功能：</b>UserService<br>
 */
@Service("userService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
@Slf4j
public class UserService extends BaseService<User, String> implements InitializingBean {


    @Autowired(required = false)
    private UserDao dao;

    @Autowired(required = false)
    private EventDao eventDao;

    private static List<UserBO> userBOS;

    @Autowired
    private BeanMapper beanMapper;

    public UserDao getDao() {
        return dao;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        UserPage page = new UserPage();
        page.setPage(1);
        page.setPageSize(Integer.MAX_VALUE);
        List<User> users = dao.queryByList(page);
        userBOS = beanMapper.mapList(users, UserBO.class);
        int size = users.size();
        for (int i = 0; i < size; i++) {
            userBOS.get(i).setEventName(eventDao.selectByPrimaryKey(users.get(i).getEventId()).getEventTitle());
        }
    }

    public static List<UserBO> getUserBOS() {
        return userBOS;
    }
}
