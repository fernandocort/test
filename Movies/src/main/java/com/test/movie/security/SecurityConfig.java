package com.test.movie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.filter.GenericFilterBean;

import lombok.AccessLevel;
import lombok.Getter;


/**
 * 
 * @author Fernando
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   
   
   @Getter(value = AccessLevel.PRIVATE)
   @Autowired
   private AuthenticationApiFilter authenticationApiFilter;

//   @Override
//   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//      auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin").password(passwordEncoder().encode("12345")).roles("USER").and()
//            .passwordEncoder(passwordEncoder()).withUser("Fernando").password(passwordEncoder().encode("12345")).roles("ADMIN")
//            ;
//   }
  

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

//   @Bean
//   @Override
//   protected AuthenticationManager authenticationManager() throws Exception {
//      return super.authenticationManager();
//   }

   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/login").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilterAfter(this.authenticationApiFilter, UsernamePasswordAuthenticationFilter.class);
   }
   
   @Bean
   public FilterRegistrationBean<AuthenticationApiFilter> authenticationApiFilterBean() {
      final FilterRegistrationBean<AuthenticationApiFilter> filterRegBean = new FilterRegistrationBean<>();
      filterRegBean.setFilter(this.getAuthenticationApiFilter());
      filterRegBean.addUrlPatterns("/movies/*");
      filterRegBean.setName("AuthenticationApiFilter");
      return filterRegBean;
   }



}
