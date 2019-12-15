package com.zhaoshy.firstgeneration.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaoshy.firstgeneration.service.impl.UserServiceImpl;
import com.zhaoshy.firstgeneration.utils.UserUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/****************************************
 * @author : zhaoshy
 * @description : spring security相关配置
 * @create_time : 2019/12/14 10:58
 ****************************************
 */

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserServiceImpl userServiceImpl;

    @Resource
    MyMetadataSource myMetadataSource;

    @Resource
    MyDecisionManager myDecisionManager;

    @Resource
    MyDeniedHandler myDeniedHandler;

    /**
     * @author : zhaoshy
     * @parameter : [auth]
     * @return : void
     * @description : 生成身份验证管理器
     * @create_time : 2019/12/15
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl);
    }

    /**
     * @author : zhaoshy
     * @parameter : [web]
     * @return : void
     * @description : 忽略无需权限访问资源的URL
     * @create_time : 2019/12/15
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }

    /**
     * @author : zhaoshy
     * @parameter : [http]
     * @return : void
     * @description : 权限验证综合管理器
     * @create_time : 2019/12/15
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(myMetadataSource);
                        object.setAccessDecisionManager(myDecisionManager);
                        return object;
                    }
                }).and().formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                .permitAll()
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
                            , AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        StringBuilder stringBuffer = new StringBuilder();
                        stringBuffer.append("{\"status\":\"error\",\"msg\":\"");
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                            stringBuffer.append("用户名或密码错了, 愣球, 好好想想吧!");
                        }else if (e instanceof DisabledException) {
                            stringBuffer.append("你TM对你爹网站干啥了? 还有B脸跟着登陆呢? GNMLGB");
                        }else {
                            stringBuffer.append("别你吗尝试登陆了, 去充钱啊, 钱到位了, 啥都到位了");
                        }
                        stringBuffer.append("\"}");
                        out.write(stringBuffer.toString());
                        out.flush();
                        out.close();
                    }
                }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
                    , Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.setCharacterEncoding("UTF-8");
                PrintWriter out = httpServletResponse.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                String s = "{\"status\":\"success\",\"msg\":" + objectMapper.writeValueAsString(UserUtils.getCurrentUser())
                        + " 大爷愣着干嘛, 快往里走啊, 咱这新推出了一个功能, 只需998, 贵族身份领回家呀 }";
                out.write(s.toString());
                out.flush();
                out.close();
            }
        }).and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(myDeniedHandler);
    }
}
