package com.darfat.registats.repository.search;
import com.darfat.registats.domain.CompetitionStatisticItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionStatisticItem} entity.
 */
public interface CompetitionStatisticItemSearchRepository extends ElasticsearchRepository<CompetitionStatisticItem, Long> {
}
