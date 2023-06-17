package com.example.demo_security_in_action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class Config {
    private final CustomAuthenticationProvider authProvider;

    public Config(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults()); // 여기를 주석처리하면 기본 인증을 사용하지 않기 때문에 403 에러 발생
        http.authorizeHttpRequests(auth -> {
            auth.anyRequest().authenticated(); // 모든 요청에 대하여 인증 필요
        });

        return http.build();
    }

    // 커스텀 프로바이더 등록
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        var userDetailsService = new InMemoryUserDetailsManager();
//
//        /*
//        여기까지만 작업하면 엔드포인트에 접근이 안된다.
//        1. 사용자가 없고
//        2. PasswordEncoder가 없기 때문
//        */
//
//        // 그래서 아래에서 하나 이상의 사용자를 추가한다.
//        var user = User.withUsername("yyj") // 이름
//                .password("1234") // 암호
//                .authorities("read") // 권한
//                .build();
//
//        userDetailsService.createUser(user);
//
//        return userDetailsService;
//    }
//
//    // PasswordEncoder도 추가!
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}
