package com.darfat.registats.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MatchLineupSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MatchLineupSearchRepositoryMockConfiguration {

    @MockBean
    private MatchLineupSearchRepository mockMatchLineupSearchRepository;

}
