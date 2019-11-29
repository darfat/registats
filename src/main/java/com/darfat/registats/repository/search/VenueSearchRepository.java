package com.darfat.registats.repository.search;
import com.darfat.registats.domain.Venue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Venue} entity.
 */
public interface VenueSearchRepository extends ElasticsearchRepository<Venue, Long> {
}
