/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author PC
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
               // antMatchers("/faces/roles/**").access("hasRole('ROLE_ADMIN')").
                //antMatchers("/faces/home/**").access("hasRole('ROLE_ADMIN')").
                antMatchers("/faces/home/**").permitAll().
                antMatchers("/faces/roles/**").permitAll().
                antMatchers("/prueba/**").anonymous().
                and().formLogin(). //login configuration
                loginPage("/faces/login.xhtml").
                failureUrl("/faces/login.xhtml?error=true").
                loginProcessingUrl("/appLogin").
                usernameParameter("app_username").
                passwordParameter("app_password").
                defaultSuccessUrl("/secure/student").
                and().logout(). //logout configuration
                logoutUrl("/appLogout").
                
                logoutSuccessUrl("/faces/login.xhtml")
                .and()
                .exceptionHandling().accessDeniedPage("/faces/access.xhtml")
                ;
        http.csrf().disable();

    }
    //estp debe cambiar

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("dmorales").password("putito").roles("ADMIN");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/sass/**", "/WEB-INF/**");
    }
}
