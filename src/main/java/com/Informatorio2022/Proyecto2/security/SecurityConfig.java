package com.Informatorio2022.Proyecto2.security;
import com.Informatorio2022.Proyecto2.filter.ConfigAutorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@CrossOrigin(origins = {"http://localhost:3000"})
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/auth/login", "/auth/register", "/auth/refresh", "/auth/accessdenied", "/auth/logout", "/auth/logoutsuccess").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/{id}", "/users").hasAnyRole("OWNER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/users/{id}", "/users/role/{id}").hasAnyRole("OWNER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyRole("OWNER");
        http.authorizeRequests().anyRequest().permitAll();
        http.logout()
                .logoutUrl("/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutSuccessUrl("/auth/logoutsuccess").permitAll();
        http.exceptionHandling().accessDeniedPage("/auth/accessdenied");
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.addFilterBefore(new ConfigAutorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
