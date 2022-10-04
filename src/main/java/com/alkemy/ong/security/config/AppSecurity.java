package com.alkemy.ong.security.config;

import com.alkemy.ong.util.Constants;
import com.alkemy.ong.security.filter.JwtRequestFilter;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import static com.alkemy.ong.util.Constants.ALL_ROLES;
import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.Endpoints.USER_UPDATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import org.springframework.http.HttpMethod;


@Configuration
@EnableWebSecurity
public class AppSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userDetailsCustomService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/*").permitAll()
                .antMatchers(HttpMethod.GET, "/**").hasAnyAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PATCH, USER_UPDATE).hasAnyAuthority(ALL_ROLES)
                .antMatchers(HttpMethod.PUT, "/news/{id}").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/news/{id}").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/organization/public").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/organization/public").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/activities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/organization/public/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore((Filter) jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsCustomService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}