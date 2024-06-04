package cn.iyque.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IYqueConfig iYqueConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启httpbasic认证
//        http.httpBasic()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                // 所有请求都需要登录认证才能访问
//                .authenticated();
        http
                .authorizeRequests()
                .antMatchers("/iycallback/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .disable(); // 禁用 X-Frame-Options 响应头

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(iYqueConfig.getUserName()) // 默认用户名
                .password("{noop}"+iYqueConfig.getPwd()) // 默认密码，{noop} 表示不加密
                .roles("USER"); // 用户角色
    }
}
