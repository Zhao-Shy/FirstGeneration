package com.zhaoshy.firstgeneration.utils;

import com.zhaoshy.firstgeneration.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/****************************************
 * @author : zhaoshy
 * @description : 用户相关工具
 * @create_time : 2019/12/15 15:37
 ****************************************
 */

public class UserUtils {
    /**
     * @author : zhaoshy
     * @parameter : []
     * @return : com.zhaoshy.firstgeneration.entity.User
     * @description : 从Security中获取当前用户
     * @create_time : 2019/12/15
     */
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
