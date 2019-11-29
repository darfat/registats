package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchStatistic} entity.
 */
public interface MatchStatisticSearchRepository extends ElasticsearchRepository<MatchStatistic, Long> {
}
