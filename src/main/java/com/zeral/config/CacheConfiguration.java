package com.zeral.config;

import com.zeral.domain.Role;
import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.zeral.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(Role.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.User.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Role.class.getName() + ".menus", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Menu.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Menu.class.getName() + ".children", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Menu.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Department.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Department.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Units.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Supplier.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Process.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Purchase.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Equipment.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Material .class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Purchase.class.getName() + ".suppliers", jcacheConfiguration);
            cm.createCache(com.zeral.domain.TypeSpecification.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.TypeSpecification.class.getName() + ".purchase", jcacheConfiguration);
            cm.createCache(com.zeral.domain.ProjectType.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.ProductType.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.ProjectTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.ProjectTemplate.class.getName() + ".projectType", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Inventory.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Inventory.class.getName() + ".project", jcacheConfiguration);
            cm.createCache(com.zeral.domain.WorkPlan.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.WorkPlan.class.getName() + ".childrenWorkPlan", jcacheConfiguration);
            cm.createCache(com.zeral.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.Project.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(com.zeral.domain.ProjectPlan.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.LineBody.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.LineProcess.class.getName(), jcacheConfiguration);
            cm.createCache(com.zeral.domain.LineProcess.class.getName() + ".lineBody", jcacheConfiguration);
            cm.createCache(com.zeral.domain.LineProcess.class.getName() + ".process", jcacheConfiguration);
            cm.createCache(com.zeral.domain.File.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
