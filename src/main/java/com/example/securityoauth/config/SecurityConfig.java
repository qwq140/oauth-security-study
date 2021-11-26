package com.example.securityoauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/","/js/**","/css/**","/img/**")
                .permitAll()
                .antMatchers("/google/**","/v1/user/**", "/auth/**").permitAll()
                .antMatchers("/test").authenticated()
                .antMatchers("/v1/admin/**").access("hasRole('RORE_ADMIN')")
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/v1/user/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/test", true); //정상적으로 요청 완료
        http
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true);
    }
}
