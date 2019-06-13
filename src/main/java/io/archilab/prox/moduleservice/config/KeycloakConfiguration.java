package io.archilab.prox.moduleservice.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@Configuration
@EnableWebSecurity
class KeycloakConfiguration extends KeycloakWebSecurityConfigurerAdapter {

  /**
   * Registers the KeycloakAuthenticationProvider with the authentication manager.
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        this.keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }


  /**
   * Defines the session authentication strategy.
   */
  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    // return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    return new NullAuthenticatedSessionStrategy();
  }

  @Bean
  public KeycloakConfigResolver KeycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http
        // .cors()

        // .and()
        // TODO vlt. in Zukunft csrf protection aktiveren, dann müsste im Client ein solches Token
        // immer mitgeschickt werden
        .csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // STATELESS
        .sessionAuthenticationStrategy(this.sessionAuthenticationStrategy()).and()
        .authorizeRequests().antMatchers(HttpMethod.GET, "/modules*").permitAll()
        .antMatchers(HttpMethod.GET, "/modules/*").permitAll()
        .antMatchers(HttpMethod.GET, "/modules/**").permitAll().antMatchers("/modules*").denyAll()
        .antMatchers("/modules/*").denyAll().antMatchers("/modules/**").denyAll()
        .antMatchers(HttpMethod.GET, "/studyCourses*").permitAll()
        .antMatchers(HttpMethod.GET, "/studyCourses/*").permitAll()
        .antMatchers(HttpMethod.GET, "/studyCourses/**").permitAll().antMatchers("/studyCourses*")
        .denyAll().antMatchers("/studyCourses/*").denyAll().antMatchers("/studyCourses/**")
        .denyAll().antMatchers("/").permitAll().antMatchers("/profile*").permitAll()
        .antMatchers("/profile/*").permitAll().antMatchers("/profile/**").permitAll()

        .anyRequest().denyAll();
    // .anyRequest().permitAll();

  }


  @Bean
  public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
      KeycloakAuthenticationProcessingFilter filter) {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    registrationBean.setEnabled(false);
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
      KeycloakPreAuthActionsFilter filter) {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    registrationBean.setEnabled(false);
    return registrationBean;
  }

}
