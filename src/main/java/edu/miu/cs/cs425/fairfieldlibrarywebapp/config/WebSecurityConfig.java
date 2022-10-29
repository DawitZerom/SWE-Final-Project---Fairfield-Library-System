package edu.miu.cs.cs425.fairfieldlibrarywebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl.LibraryUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(LibraryUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // .csrf().disable()
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/static/**", "/images/**", "/css/**").permitAll()
                .antMatchers("/", "/library", "/fairfieldlibrary", "/library/public/**").permitAll()
                .antMatchers("/library/secured/service/**", "/library/secured/home/**")
                .hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")
                .antMatchers("/library/secured/book/new", "/library/secured/book/update")
                .hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/library/secured/book/list", "/library/secured/book/search")
                .hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")
                .antMatchers("/library/secured/checkout/**", "/library/secured/checkin/**",
                        "/library/secured/member/**", "/library/secured/membertype/**", "/library/secured/book/**")
                .hasAnyRole("ADMIN", "LIBRARIAN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/library/public/login")
                .defaultSuccessUrl("/library/secured/home")
                .failureUrl("/library/public/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/library/public/logout"))
                .logoutSuccessUrl("/library/public/login?logout")
                .permitAll()
                .and()
                .exceptionHandling();
    }

}
