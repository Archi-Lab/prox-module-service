package io.archilab.prox.moduleservice.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  public static final String MODULES_PATTERN = "/modules/**";
  public static final String STUDY_COURSES_PATTERN = "/studyCourses/**";
  public static final String PROFILE_PATTERN = "/profile/**";

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        this.keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, SecurityConfig.MODULES_PATTERN)
        .permitAll()
        .antMatchers(HttpMethod.HEAD, SecurityConfig.MODULES_PATTERN)
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, SecurityConfig.MODULES_PATTERN)
        .permitAll()
        .antMatchers(SecurityConfig.MODULES_PATTERN)
        .denyAll()
        .antMatchers(HttpMethod.GET, SecurityConfig.STUDY_COURSES_PATTERN)
        .permitAll()
        .antMatchers(HttpMethod.HEAD, SecurityConfig.STUDY_COURSES_PATTERN)
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, SecurityConfig.STUDY_COURSES_PATTERN)
        .permitAll()
        .antMatchers(SecurityConfig.STUDY_COURSES_PATTERN)
        .denyAll()
        .antMatchers(SecurityConfig.PROFILE_PATTERN)
        .permitAll()
        .anyRequest()
        .denyAll();
  }
}
