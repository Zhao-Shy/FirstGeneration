package com.zhaoshy.firstgeneration.controller;

import com.zhaoshy.firstgeneration.entity.User;
import com.zhaoshy.firstgeneration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/****************************************
 * @author : zhaoshy
 * @description : 处理用户相关的请求
 * @create_time : 2019/12/8 20:22
 ****************************************
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserDetailsService {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody User user) {
        int i = userService.addUser(user);
        if (i > 0) {
            return "success";
        }else {
            return "failed";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
