package com.zhaoshy.firstgeneration.service.impl;

import com.zhaoshy.firstgeneration.dao.UserDao;
import com.zhaoshy.firstgeneration.entity.User;
import com.zhaoshy.firstgeneration.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/****************************************
 * @author : zhaoshy
 * @description : 用户登陆, 注册, 认证, 授权等用户相关操作
 * @create_time : 2019/12/8 20:18
 ****************************************
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("请检查用户名是否输入正确");
        }
        return user;
    }
}
