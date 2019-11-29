package com.darfat.registats.repository.search;
import com.darfat.registats.domain.CompetitionPlayerStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionPlayerStatistic} entity.
 */
public interface CompetitionPlayerStatisticSearchRepository extends ElasticsearchRepository<CompetitionPlayerStatistic, Long> {
}
