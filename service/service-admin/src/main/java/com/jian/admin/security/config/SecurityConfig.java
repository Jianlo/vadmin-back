package com.jian.admin.security.config;


import com.jian.admin.security.UserDetailServiceImpl;
import com.jian.admin.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    //指定放行的接口
    private static final String[] URLS_PERMIT = {
            "/admin/user/register",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/api/**"
    };


    //配置密码加密方式
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //注册jwt过滤器
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                //配置登录方式（使用SpringSecurity默认的form表单登录，接口地址为 /login)
                .formLogin()

                //配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URLS_PERMIT).permitAll()
                .anyRequest().authenticated()

                //配置自定义过滤器
                .and()
                .addFilter(jwtAuthenticationFilter());


    }



    //配置我们自定义的UserDetailService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }
}
