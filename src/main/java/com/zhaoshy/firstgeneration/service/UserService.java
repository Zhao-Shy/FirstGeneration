package com.zhaoshy.firstgeneration.service;

import com.zhaoshy.firstgeneration.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/****************************************
 * @author : zhaoshy
 * @description : 用户相关
 * @create_time : 2019/12/15 13:30
 ****************************************
 */

public interface UserService extends UserDetailsService {

    int addUser(User user);
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
