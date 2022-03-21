package com.management.project.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	final DataSource dataSource;

	final BCryptPasswordEncoder bCryptPasswordEncoder;
	private CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();

	public SecurityConfiguration(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.dataSource = dataSource;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.usersByUsernameQuery("select username, password, enabled from user_accounts where username = ?")
			.authoritiesByUsernameQuery("select username, role, enabled from user_accounts where username = ?")
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/projects/new").hasRole("ADMIN")
			.antMatchers("/projects/update").hasRole("ADMIN")
			.antMatchers("/projects/save").hasRole("ADMIN")
			.antMatchers("/projects/delete").hasRole("ADMIN")
			.antMatchers("/employees/new").hasRole("ADMIN")
			.antMatchers("/employees/update").hasRole("ADMIN")
			.antMatchers("/employees/save").hasRole("ADMIN")
			.antMatchers("/employees/delete").hasRole("ADMIN")
			.antMatchers( "/register" ,  "/register/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
			.and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				.and().headers().cacheControl()
				.and().xssProtection().xssProtectionEnabled(true)
				.and().frameOptions().sameOrigin()
				.addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","default-src 'self'"))
				.addHeaderWriter(new StaticHeadersWriter("X-WebKit-CSP","default-src 'self'"))
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).sessionFixation().migrateSession()
				.and().csrf().csrfTokenRepository(cookieCsrfTokenRepository);;
	}
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/webjars/**", "/js/**","/error/**"
				, "/css/**","/fonts/**","/libs/**","/img/**", "/h2-console/**");
	}
}
