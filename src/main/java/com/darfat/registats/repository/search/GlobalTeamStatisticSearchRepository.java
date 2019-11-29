package com.darfat.registats.repository.search;
import com.darfat.registats.domain.GlobalTeamStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GlobalTeamStatistic} entity.
 */
public interface GlobalTeamStatisticSearchRepository extends ElasticsearchRepository<GlobalTeamStatistic, Long> {
}
