package com.darfat.registats.repository.search;
import com.darfat.registats.domain.CompetitionTeamStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionTeamStatistic} entity.
 */
public interface CompetitionTeamStatisticSearchRepository extends ElasticsearchRepository<CompetitionTeamStatistic, Long> {
}
