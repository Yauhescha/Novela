package com.novelasgame.novelas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.novelasgame.novelas.service.DataBase.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ApplicationContext context;


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setApplicationContext(context);
        web.expressionHandler(handler);
    }
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/games", "/games/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/login", "/registration").anonymous()
            .antMatchers("/h2-console/**","/images/**","/forPages/**","/js/**","/css/**","/").permitAll()
            .anyRequest().authenticated().and()
            .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/index?error=true").and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            .deleteCookies("JSESSIONID");
        http.headers().frameOptions().sameOrigin();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
