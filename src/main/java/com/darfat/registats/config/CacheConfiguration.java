package com.darfat.registats.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

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
            createCache(cm, com.darfat.registats.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.darfat.registats.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.darfat.registats.domain.User.class.getName());
            createCache(cm, com.darfat.registats.domain.Authority.class.getName());
            createCache(cm, com.darfat.registats.domain.User.class.getName() + ".authorities");
            createCache(cm, com.darfat.registats.domain.MatchCommentary.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchLineup.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchLineup.class.getName() + ".statistics");
            createCache(cm, com.darfat.registats.domain.PlayerMatchStatistic.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchStatistic.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchHomeInfo.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchHomeInfo.class.getName() + ".lineups");
            createCache(cm, com.darfat.registats.domain.MatchHomeInfo.class.getName() + ".statistics");
            createCache(cm, com.darfat.registats.domain.MatchAwayInfo.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchAwayInfo.class.getName() + ".lineups");
            createCache(cm, com.darfat.registats.domain.MatchAwayInfo.class.getName() + ".statistics");
            createCache(cm, com.darfat.registats.domain.Match.class.getName());
            createCache(cm, com.darfat.registats.domain.Match.class.getName() + ".commentaries");
            createCache(cm, com.darfat.registats.domain.Venue.class.getName());
            createCache(cm, com.darfat.registats.domain.CompetitionName.class.getName());
            createCache(cm, com.darfat.registats.domain.CompetitionName.class.getName() + ".competitions");
            createCache(cm, com.darfat.registats.domain.Competition.class.getName());
            createCache(cm, com.darfat.registats.domain.Competition.class.getName() + ".teams");
            createCache(cm, com.darfat.registats.domain.Competition.class.getName() + ".standings");
            createCache(cm, com.darfat.registats.domain.Competition.class.getName() + ".playerStatistics");
            createCache(cm, com.darfat.registats.domain.Competition.class.getName() + ".teamStatistics");
            createCache(cm, com.darfat.registats.domain.Competition.class.getName() + ".matches");
            createCache(cm, com.darfat.registats.domain.CompetitionTeam.class.getName());
            createCache(cm, com.darfat.registats.domain.CompetitionStanding.class.getName());
            createCache(cm, com.darfat.registats.domain.CompetitionPlayerStatistic.class.getName());
            createCache(cm, com.darfat.registats.domain.CompetitionTeamStatistic.class.getName());
            createCache(cm, com.darfat.registats.domain.TeamRegisteredPlayer.class.getName());
            createCache(cm, com.darfat.registats.domain.Team.class.getName());
            createCache(cm, com.darfat.registats.domain.Team.class.getName() + ".members");
            createCache(cm, com.darfat.registats.domain.TeamMember.class.getName());
            createCache(cm, com.darfat.registats.domain.Player.class.getName());
            createCache(cm, com.darfat.registats.domain.Player.class.getName() + ".histories");
            createCache(cm, com.darfat.registats.domain.PlayerHistory.class.getName());
            createCache(cm, com.darfat.registats.domain.PlayerHistory.class.getName() + ".statistics");
            createCache(cm, com.darfat.registats.domain.PlayerHistoryStatistic.class.getName());
            createCache(cm, com.darfat.registats.domain.Position.class.getName());
            createCache(cm, com.darfat.registats.domain.PlayerStatisticItem.class.getName());
            createCache(cm, com.darfat.registats.domain.MatchStatisticItem.class.getName());
            createCache(cm, com.darfat.registats.domain.CompetitionStatisticItem.class.getName());
            createCache(cm, com.darfat.registats.domain.GlobalPlayerStatistic.class.getName());
            createCache(cm, com.darfat.registats.domain.GlobalTeamStatistic.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
