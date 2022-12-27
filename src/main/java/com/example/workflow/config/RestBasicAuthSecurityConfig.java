package com.example.workflow.config;

import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * This is for Basic Authentication for REST calls
 */
@Configuration
public class RestBasicAuthSecurityConfig
{

    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "false", prefix = "ldap.auth")
    public FilterRegistrationBean<ProcessEngineAuthenticationFilter> processEngineAuthenticationFilter()
    {
        FilterRegistrationBean<ProcessEngineAuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setName("camunda-auth");
        registration.setFilter(this.getProcessEngineAuthenticationFilter());
        registration
                .addInitParameter("authentication-provider", HttpBasicAuthenticationProvider.class.getName());
        registration.addUrlPatterns("/engine-rest/*");
        return registration;
    }

    /*
     * ProcessEngineAuthenticationFilter will authenticate REST Calls against DB Users Basic Authentication
     * based on property : ldap.auth.enabled = false
     * */
    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "false", prefix = "ldap.auth")
    public ProcessEngineAuthenticationFilter getProcessEngineAuthenticationFilter()
    {
        return new ProcessEngineAuthenticationFilter();
    }
}