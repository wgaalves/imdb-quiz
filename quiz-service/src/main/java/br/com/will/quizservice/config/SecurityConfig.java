package br.com.will.quizservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(
            authorize -> {
              try {
                authorize
                    .antMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();
              } catch (Exception e) {
                e.printStackTrace();
              }
            })
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
   }
}