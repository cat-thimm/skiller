package com.anderscore.skiller.security;


import com.anderscore.skiller.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(GET, "/api/v1/coworker/**").hasAuthority("ROLE_MANAGER");  //FA1, FA2, FA6
        http.authorizeRequests().antMatchers(GET, "/api/v1/project/**").hasAuthority("ROLE_MANAGER");   //FA5
        http.authorizeRequests().antMatchers(GET, "/api/v1/skill/**").hasAuthority("ROLE_MANAGER");   //FA3


        http.authorizeRequests().antMatchers(POST, "/api/v1/project/**").hasAuthority("ROLE_MANAGER");   //FA4
        http.authorizeRequests().antMatchers(POST, "/api/v1/coworker").hasAuthority("ROLE_MANAGER"); //FA 8
        http.authorizeRequests().antMatchers(POST, "/api/v1/coworker/**").hasAuthority("ROLE_COWORKER"); // FA7
        http.authorizeRequests().antMatchers(POST, "/api/v1/skill").hasAuthority("ROLE_MANAGER");;  //FA 8

        http.authorizeRequests().antMatchers(PUT, "/api/v1/project/**").hasAuthority("ROLE_MANAGER"); //FA4
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
