package com.example.demo_security_in_action;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class Config {
    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService = new InMemoryUserDetailsManager();

        /*
        여기까지만 작업하면 엔드포인트에 접근이 안된다.
        1. 사용자가 없고
        2. PasswordEncoder가 없기 때문
        */

        // 그래서 아래에서 하나 이상의 사용자를 추가한다.
        var user = User.withUsername("yyj") // 이름
                .password("1234") // 암호
                .authorities("read") // 권한
                .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }
}
