package com.darfat.registats.repository.search;
import com.darfat.registats.domain.GlobalPlayerStatistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GlobalPlayerStatistic} entity.
 */
public interface GlobalPlayerStatisticSearchRepository extends ElasticsearchRepository<GlobalPlayerStatistic, Long> {
}
