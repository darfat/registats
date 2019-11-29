package com.darfat.registats.repository.search;
import com.darfat.registats.domain.PlayerHistoryStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlayerHistoryStatistic} entity.
 */
public interface PlayerHistoryStatisticSearchRepository extends ElasticsearchRepository<PlayerHistoryStatistic, Long> {
}
