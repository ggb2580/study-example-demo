package com.hrbu.springsecuritydemo.config;

import com.hrbu.springsecuritydemo.handler.CustomFailureHandler;
import com.hrbu.springsecuritydemo.handler.CustomSuccessHandler;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Say my name
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomFailureHandler customFailureHandler;
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.authorizeHttpRequests(auth->
                auth.requestMatchers("/api/user/**").authenticated()
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers("/api/index/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAnyRole("USER")
                        .anyRequest().authenticated())
                // 3. 登录成功/失败处理
                .formLogin(form -> form
                        //拦截器
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler)
                )
               .csrf(csrf -> csrf.disable())

        //退出登录
                .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
        ).build();
    }

    /*
    * 密码加密
    * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
