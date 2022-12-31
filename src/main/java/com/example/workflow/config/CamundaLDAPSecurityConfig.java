package com.example.workflow.config;

import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class CamundaLDAPSecurityConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "true", prefix = "ldap.auth")
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
    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "true", prefix = "ldap.auth")
    public ProcessEngineAuthenticationFilter getProcessEngineAuthenticationFilter()
    {
        return new ProcessEngineAuthenticationFilter();
    }

    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "true", prefix = "ldap.auth")
    public AdministratorAuthorizationPlugin administratorAuthorizationPlugin() {
        AdministratorAuthorizationPlugin plugin = new AdministratorAuthorizationPlugin();
        /* ldap.camunda.admin.group.name Property must exist in LDAP as this is created
         in Camunda database while bootstrapping, All users from this group will be Admin by default */
        plugin.setAdministratorGroupName(env.getProperty("ldap.camunda.webapps.admin.group.name"));
        plugin.setAdministratorUserName(env.getProperty("ldap.camunda.admin.user")); // In case you want to make single user as Admin
        return plugin;
    }

    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "true", prefix = "ldap.auth")
    public LdapIdentityProviderPlugin ldapIdentityProviderPlugin() {
        LdapIdentityProviderPlugin plugin = new LdapIdentityProviderPlugin();
        plugin.setServerUrl(env.getProperty("ldap.server.uri"));
        plugin.setAcceptUntrustedCertificates(Boolean.parseBoolean(env.getProperty("ldap.acceptUntrustedCertificates")));
        plugin.setManagerDn(env.getProperty("ldap.manager.dn"));
        plugin.setManagerPassword(env.getProperty("ldap.manager.password"));
        plugin.setBaseDn(env.getProperty("ldap.search.base.dn"));
        plugin.setUserDnPattern("");
        plugin.setUserSearchBase(env.getProperty("user.search.base"));
        plugin.setUserSearchFilter(env.getProperty("ldap.user.search.filter"));
        plugin.setUserIdAttribute(env.getProperty("ldap.user.id.attribute"));
        plugin.setUserFirstnameAttribute(env.getProperty("ldap.user.firstName.Attribute"));
        plugin.setUserLastnameAttribute(env.getProperty("ldap.user.lastName.Attribute"));
        plugin.setUserEmailAttribute(env.getProperty("ldap.user.email.Attribute"));
        plugin.setUserPasswordAttribute(env.getProperty("ldap.user.password.Attribute"));
        plugin.setGroupSearchBase(env.getProperty("ldap.group.search.base"));
        plugin.setGroupSearchFilter(env.getProperty("ldap.group.search.filter"));
        plugin.setGroupIdAttribute("cn");
        plugin.setGroupNameAttribute("cn");
        plugin.setGroupTypeAttribute("");
        plugin.setGroupMemberAttribute(env.getProperty("ldap.group.search.groupMember.attribute"));
        plugin.setUsePosixGroups(Boolean.parseBoolean(env.getProperty("ldap.usePosixGroups")));
        plugin.setSortControlSupported(Boolean.parseBoolean(env.getProperty("ldap.sortControlSupported")));
        plugin.setAuthorizationCheckEnabled(Boolean.parseBoolean(env.getProperty("ldap.authorizationCheckEnabled")));
        return plugin;
    }
}