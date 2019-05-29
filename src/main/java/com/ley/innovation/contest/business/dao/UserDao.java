package com.ley.innovation.contest.business.dao;

import com.ley.springboot.base.dao.BaseDao;
import com.ley.innovation.contest.business.entity.User;
import org.apache.ibatis.annotations.Mapper;
/**
 * <b>功能：</b>UserDao<br>
 **/
@Mapper
public interface UserDao extends BaseDao<User> {

}
