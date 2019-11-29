package com.darfat.registats.repository.search;
import com.darfat.registats.domain.MatchStatisticItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchStatisticItem} entity.
 */
public interface MatchStatisticItemSearchRepository extends ElasticsearchRepository<MatchStatisticItem, Long> {
}
