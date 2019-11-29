package com.darfat.registats.repository.search;
import com.darfat.registats.domain.Match;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Match} entity.
 */
public interface MatchSearchRepository extends ElasticsearchRepository<Match, Long> {
}
