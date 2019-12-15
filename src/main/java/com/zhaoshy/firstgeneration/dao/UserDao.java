package com.zhaoshy.firstgeneration.dao;

import com.zhaoshy.firstgeneration.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;

/****************************************
 * @author : zhaoshy
 * @description : UserDao
 * @create_time : 2019/12/8 20:07
 ****************************************
 */

@Mapper
public interface UserDao {
    int addUser(User user);
    User loadUserByUsername(String s);
}
