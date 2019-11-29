package com.darfat.registats.repository.search;
import com.darfat.registats.domain.CompetitionStanding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionStanding} entity.
 */
public interface CompetitionStandingSearchRepository extends ElasticsearchRepository<CompetitionStanding, Long> {
}
