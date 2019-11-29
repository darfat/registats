package com.darfat.registats.repository.search;
import com.darfat.registats.domain.PlayerHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlayerHistory} entity.
 */
public interface PlayerHistorySearchRepository extends ElasticsearchRepository<PlayerHistory, Long> {
}
