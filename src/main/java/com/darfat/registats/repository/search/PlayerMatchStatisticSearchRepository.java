package com.darfat.registats.repository.search;
import com.darfat.registats.domain.PlayerMatchStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlayerMatchStatistic} entity.
 */
public interface PlayerMatchStatisticSearchRepository extends ElasticsearchRepository<PlayerMatchStatistic, Long> {
}
