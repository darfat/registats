package com.darfat.registats.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GlobalTeamStatisticSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GlobalTeamStatisticSearchRepositoryMockConfiguration {

    @MockBean
    private GlobalTeamStatisticSearchRepository mockGlobalTeamStatisticSearchRepository;

}
