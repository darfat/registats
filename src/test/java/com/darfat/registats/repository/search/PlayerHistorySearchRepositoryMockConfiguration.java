package com.darfat.registats.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PlayerHistorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PlayerHistorySearchRepositoryMockConfiguration {

    @MockBean
    private PlayerHistorySearchRepository mockPlayerHistorySearchRepository;

}
