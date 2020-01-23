package io.archilab.prox.moduleservice.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        this.keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Override
  @ConditionalOnMissingBean(HttpSessionManager.class)
  protected HttpSessionManager httpSessionManager() {
    return new HttpSessionManager();
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Bean
  public KeycloakConfigResolver KeycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
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

  // @Bean
  // public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
  // KeycloakAuthenticationProcessingFilter filter) {
  // FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
  // registrationBean.setEnabled(false);
  // return registrationBean;
  // }
  //
  // @Bean
  // public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
  // KeycloakPreAuthActionsFilter filter) {
  // FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
  // registrationBean.setEnabled(false);
  // return registrationBean;
  // }

}
