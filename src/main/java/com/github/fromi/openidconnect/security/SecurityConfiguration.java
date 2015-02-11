package com.github.fromi.openidconnect.security;

import static org.springframework.http.HttpMethod.GET;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String ALLOWED_EMAIL = "choc.mah@gmail.com";
	
    private final String GOOGLE_LOGIN_URL = "/googlelogin";

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(GOOGLE_LOGIN_URL);
    }

    @Bean
    public OpenIDConnectAuthenticationFilter openIdConnectAuthenticationFilter() {
    	OpenIDConnectAuthenticationFilter oIDcaf = new OpenIDConnectAuthenticationFilter(GOOGLE_LOGIN_URL);
    	oIDcaf.setAuthenticationFailureHandler(authExceptionMapping());
    	oIDcaf.setAuthenticationSuccessHandler(authSuccessHandler());

        return oIDcaf;
    }
    
    @Bean
    public RequestMatcher googleLoginMatcher(){
    	return new AntPathRequestMatcher(GOOGLE_LOGIN_URL,HttpMethod.GET.toString());
    }
    
    @Bean
    public AuthenticationSuccessHandler authSuccessHandler(){
    	SimpleUrlAuthenticationSuccessHandler suash = new SimpleUrlAuthenticationSuccessHandler("/test");
    	return suash;
    }
    
    @Bean
    public ExceptionMappingAuthenticationFailureHandler authExceptionMapping() {
        final ExceptionMappingAuthenticationFailureHandler emafh = new ExceptionMappingAuthenticationFailureHandler();
        emafh.setDefaultFailureUrl("/login?error");

        return emafh;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.addFilterAfter(openIdConnectAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
  			.exceptionHandling().defaultAuthenticationEntryPointFor(authenticationEntryPoint(), googleLoginMatcher())
  			.and()
      			.authorizeRequests()
            .antMatchers(GET, "/").permitAll()
            .antMatchers(GET, "/test").authenticated()
            .and().formLogin()
            	.loginPage("/login")        
            	.defaultSuccessUrl("/test", true)   
            	.and()      
            .logout()
            	.logoutSuccessUrl("/")
            	.and()
            .csrf().disable();
    }
    
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(ALLOWED_EMAIL).password("test")
				.roles("USER");
	}
}
