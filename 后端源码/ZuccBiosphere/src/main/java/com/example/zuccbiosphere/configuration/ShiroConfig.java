package com.example.zuccbiosphere.configuration;

import com.example.zuccbiosphere.filter.AnyRolesAuthorizationFilter;
import com.example.zuccbiosphere.filter.JwtAuthFilter;
import com.example.zuccbiosphere.service.UserService;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;


/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager, UserService userService) throws Exception{
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter)shiroFilter(securityManager, userService).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ASYNC);

        return filterRegistration;
    }

    @Bean
    public Authenticator authenticator(UserService userService) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean("dbRealm")
    public Realm dbShiroRealm(UserService userService) {
        DbShiroRealm myShiroRealm = new DbShiroRealm(userService);
        return myShiroRealm;
    }

    @Bean("jwtRealm")
    public Realm jwtShiroRealm(UserService userService) {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm(userService);
        return myShiroRealm;
    }

    /**
     * 设置过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserService userService) {
    	ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter(userService));
        filterMap.put("anyRole", createRolesFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        return factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //swagger2免拦截
        chainDefinition.addPathDefinition("/swagger-ui.html**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");

        //接口拦截
        //任务相关
        chainDefinition.addPathDefinition("/api/task/addUserScore", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/task/getTodayTask", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/task/finishTask", "noSessionCreation,anon");

        //用户相关
        chainDefinition.addPathDefinition("/api/user/getUserData", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/user/addUserFoot", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/user/addUserCollect", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/user/getUserCollectOrFoot", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/user/deleteCollect", "noSessionCreation,authcToken");

        //卡片相关
        chainDefinition.addPathDefinition("/api/card/uploadCard", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/card/checkCard", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/card/addCard", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/card/deleteCard", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/card/getCardStatus", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/card/getCards", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/card/cardsPopular", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/card/addCardsChat", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/api/card/addCardsScore", "noSessionCreation,authcToken");

        //排行榜相关
        chainDefinition.addPathDefinition("/api/rank/getUserRank", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/rank/getCardRank", "noSessionCreation,anon");

        //token
        chainDefinition.addPathDefinition("/api/login/getUserInfo", "noSessionCreation,anon");

        //测试
        chainDefinition.addPathDefinition("/api/test/hello", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/test/upload", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken");
        return chainDefinition;
    }

    protected JwtAuthFilter createAuthFilter(UserService userService){
        return new JwtAuthFilter(userService);
    }

    protected AnyRolesAuthorizationFilter createRolesFilter(){
        return new AnyRolesAuthorizationFilter();
    }

}
