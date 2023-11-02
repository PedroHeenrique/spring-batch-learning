package br.com.learn.batch.release;

import br.com.learn.batch.release.domain.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.*;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@SpringBatchTest
@SpringJUnitConfig
@ActiveProfiles(value = "test")
@TestExecutionListeners({
								DependencyInjectionTestExecutionListener.class,
								StepScopeTestExecutionListener.class
})
class ReleaseApplicationTests {


				@Autowired
				private JobLauncherTestUtils jobLauncherTestUtils;

				@Autowired
				private JobRepositoryTestUtils jobRepositoryTestUtils;

				@Autowired
    private ItemReader<Autor> itemReader;

				public StepExecution getStepExecution(){
								return MetaDataInstanceFactory.createStepExecution("primeiro-passo",254L);
				}
				@Test
				void verificaExecucaoNormalDoJob() {
        JobExecution stepExecutionStatus = jobLauncherTestUtils.launchStep("primeiro-passo");
								assertThat(stepExecutionStatus.getStatus(),is(equalTo(BatchStatus.COMPLETED)));
				}

				@Test
				void quantidadeItemLidos() throws Exception {
						int qtdItem =		StepScopeTestUtils.doInStepScope(getStepExecution(),() ->{
												int count = 0;
												while(itemReader.read() !=null){
																count++;
												}
												return count;
								});

								assertThat(qtdItem,is(equalTo(7)));
				}

}
