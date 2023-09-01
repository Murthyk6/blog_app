package com.murthy.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class BlogSecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManagers(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager
                .setUsersByUsernameQuery("SELECT email, password, active FROM user WHERE email = ?");
        jdbcUserDetailsManager
                .setAuthoritiesByUsernameQuery("SELECT email, role FROM user WHERE email = ?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/comment/edit","/comment/delete").hasAnyRole("AUTHOR","ADMIN")
                        .requestMatchers("/comment/save").permitAll()
                        .requestMatchers("/profile").authenticated()
                        .requestMatchers("/blog/createPost").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form ->
                        form
                                .loginPage("/userLogin")
                                .loginProcessingUrl("/validateUser")
                                .defaultSuccessUrl("/feed", true)
                                .permitAll()
                )
                .logout(logout->
                        logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}
