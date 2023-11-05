package br.com.learn.batch;

import br.com.learn.batch.domain.Autor;
import br.com.learn.batch.domain.Registro;
import br.com.learn.batch.repository.AutorRepository;
import br.com.learn.batch.repository.RegistroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.*;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import java.util.ArrayList;
import java.util.List;

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
class BatchApplicationTests {


				@Autowired
				private JobLauncherTestUtils jobLauncherTestUtils;

				@Autowired
				private JobRepositoryTestUtils jobRepositoryTestUtils;

				@Autowired
				private AutorRepository autorRepositoryTest;

				@Autowired
				private RegistroRepository registroRepositoryTest;

				@Autowired
    private ItemReader<Autor> itemReader;

				@Autowired
				private ItemProcessor<Autor, Registro> itemProcessor;

				@BeforeEach
				public void cleanUp(){
								jobRepositoryTestUtils.removeJobExecutions();
				}

				public StepExecution getStepExecution(){
								return MetaDataInstanceFactory.createStepExecution("primeiro-passo",254L);
				}
				@Test
				@DisplayName("O job deve executar sem erros")
				void verificaExecucaoNormalDoJob() throws Exception {
        JobExecution stepExecutionStatus = jobLauncherTestUtils.launchJob();
								assertThat(stepExecutionStatus.getStatus(),is(equalTo(BatchStatus.COMPLETED)));

				}

				@Test
				@DisplayName("Ao buscar todos os itens na base deve retornar 7 itens")
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

				@Test
				@DisplayName("Ao passar o os itens no processador deve retornar somente 5")
				void testaProcessor() throws Exception {
        //Give
								List<Autor> autorList = autorRepositoryTest.findAll();
								List<Registro> processorResulList = new ArrayList<>();

								for (Autor value : autorList) {
												//when
												Registro autor = itemProcessor.process(value);
												if (autor != null) {
																processorResulList.add(autor);
												}
								}

        //Then
						  assertThat(5, is(equalTo(processorResulList.size())));

				}

}
