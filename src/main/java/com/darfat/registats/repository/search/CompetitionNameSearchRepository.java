package com.darfat.registats.repository.search;
import com.darfat.registats.domain.CompetitionName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionName} entity.
 */
public interface CompetitionNameSearchRepository extends ElasticsearchRepository<CompetitionName, Long> {
}
