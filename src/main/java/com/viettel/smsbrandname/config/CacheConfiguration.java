package com.viettel.smsbrandname.config;

import java.time.Duration;

import com.viettel.smsbrandname.domain.Orders;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.viettel.smsbrandname.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.viettel.smsbrandname.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.viettel.smsbrandname.domain.User.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.Authority.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.User.class.getName() + ".authorities");
            createCache(cm, com.viettel.smsbrandname.domain.Mt.class.getName());
            createCache(cm, Orders.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.CpGroup.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.Telco.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.TransLog.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.ProvinceBccs.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.CpGroupSub.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.CpAlias.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.AdminMsisdn.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.AdCategory.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.AdPackage.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.AdJob.class.getName());
            createCache(cm, com.viettel.smsbrandname.domain.AdRegFinal.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
