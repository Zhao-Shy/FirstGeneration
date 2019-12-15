package com.zhaoshy.firstgeneration.config.security;

import com.zhaoshy.firstgeneration.entity.Authority;
import com.zhaoshy.firstgeneration.entity.Role;
import com.zhaoshy.firstgeneration.service.AuthorityService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/****************************************
 * @author : zhaoshy
 * @description : 当前请求URL的验权操作
 * @create_time : 2019/12/15 13:23
 ****************************************
 */

@Component
public class MyMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    private AuthorityService authorityService;

    //spring工具类, 用来匹配URL
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * @author : zhaoshy
     * @parameter : o
     * @return : java.util.Collection<org.springframework.security.access.ConfigAttribute>
     * @description : 根据请求URL判断用户当前角色是否匹配
     * @create_time : 2019/12/15
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求URL
        String url = ((FilterInvocation)o).getRequestUrl();
        if("/login".equals(url)) {
            return null;
        }
        List<Authority> authorities = authorityService.getAllAuth();
        for (Authority authority : authorities) {
            if (antPathMatcher.match(authority.getAuthUrl(), url) && authority.getRoles().size() > 0) {
                List<Role> roles = authority.getRoles();
                int size = roles.size();
                String[] authName = new String[size];
                for (int i = 0; i < size; i++) {
                    authName[i] = roles.get(i).getRoleName();
                }
                return SecurityConfig.createList(authName);
            }
        }
        //没有匹配到的都是登陆访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
