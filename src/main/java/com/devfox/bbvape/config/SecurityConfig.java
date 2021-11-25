package com.devfox.bbvape.config;

import com.devfox.bbvape.config.handler.UserDeniedHandler;
import com.devfox.bbvape.service.VapeUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final VapeUserDetailsService vapeUserDetailsService;
    private final UserDeniedHandler userDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(vapeUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());

        web
                .expressionHandler(expressionHandler)
                .ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                )
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .formLogin(login ->
                        login
                                .loginPage("/signin")
                                .loginProcessingUrl("/signinprocess")
                                .permitAll()
                                .defaultSuccessUrl("/home", true)
                                .failureUrl("/signin?success=false")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/signout")
                                .logoutSuccessUrl("/home"))
                .exceptionHandling()
                .accessDeniedHandler(userDeniedHandler)
                .and()
                .rememberMe()
                .key("remember-me-key")
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me-cookie")
                .userDetailsService(vapeUserDetailsService)
                .tokenValiditySeconds(60 * 60 * 24)
                .and()
                .authorizeRequests()
                .antMatchers("/signin").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/home").permitAll()

                .antMatchers("/liquid").permitAll()
                .antMatchers("/device").permitAll()
                .antMatchers("/etc").permitAll()
                .antMatchers("/thumbnail/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/liquid/product").hasRole("USER")
                .antMatchers("/device/product").hasRole("USER")
                .antMatchers("/etc/product").hasRole("USER")

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/*").permitAll()
        ;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_REGISTER");
        return roleHierarchy;
    }

    @Bean
    public AccessDecisionVoter<?> roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

}
