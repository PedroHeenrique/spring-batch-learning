package br.com.learn.batch.release;

import br.com.learn.batch.release.repository.ResultProcessRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@SpringBatchTest
@SpringJUnitConfig
@ActiveProfiles(value = "test")
class ReleaseApplicationTests {

				@Autowired
				private JobLauncherTestUtils jobLauncherTestUtils;

				@Autowired
				private JobRepositoryTestUtils jobRepositoryTestUtils;

				@Autowired
				private ResultProcessRepository resultProcessRepository;

				@Test
				void contextLoads() {

				}

}
