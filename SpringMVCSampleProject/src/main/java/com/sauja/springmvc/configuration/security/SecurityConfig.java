package com.sauja.springmvc.configuration.security;

import com.sauja.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.sauja.springmvc.*")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

            //auth.inMemoryAuthentication().withUser("123").password("123").roles("USER");


            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(usersQuery)
                    .authoritiesByUsernameQuery(rolesQuery);

	}

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
       //web.debug(true);
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                //.antMatchers("/securedResource/**").access("hasRole('USER')")
                //.antMatchers("/securedResource/**").authenticated()
                //Database should have roles prefixed with ROLE_
                .antMatchers("/securedResource/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")

                .and()
                //form input name in custom form
                .formLogin().loginPage("/customLogin").usernameParameter("username").passwordParameter("password")
        .and().logout().permitAll()

        ; //default spring provided form

        http.exceptionHandling().accessDeniedPage("/403");

    }
}