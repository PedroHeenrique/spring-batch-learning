package br.com.learn.batch.release.job;

import br.com.learn.batch.release.domain.Autor;
import br.com.learn.batch.release.domain.Registro;
import br.com.learn.batch.release.repository.RegistroRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Configuration
public class Jobz {

				private final JobRepository jobRepository;

				private final PlatformTransactionManager transactionManager;
				private final RegistroRepository registroRepository;

				private final EntityManagerFactory entityManagerFactory;

				private final DataSource dataSource;

				private final KafkaTemplate<Integer,String> kafkaTemplate;

				@Autowired
				public Jobz(JobRepository jobRepository, PlatformTransactionManager transactionManager, RegistroRepository registroRepository, EntityManagerFactory entityManagerFactory, DataSource dataSource, KafkaTemplate<Integer, String> kafkaTemplate) {
								this.jobRepository = jobRepository;
								this.transactionManager = transactionManager;
								this.registroRepository = registroRepository;
								this.entityManagerFactory = entityManagerFactory;
								this.dataSource = dataSource;
								this.kafkaTemplate = kafkaTemplate;
				}


				@Bean(name = "iniciarOsTrabalhos")
				public Job iniciarOsTrabalhos(){
								return new JobBuilder("iniciar-trabalhos",jobRepository)
																.start(primeiroPasso())
																.build();
				}


				@Bean
				public Step primeiroPasso(){
								return new StepBuilder("primeiro-passo",jobRepository)
																.<Autor,Autor>chunk(10,transactionManager)
																.reader(itemReaderAutor())
																.listener(new MeListener())
  														.writer(itemWriter())
                .listener(new MeChunkListener())
																.build();
				}


				private ItemReader<Registro> itemReader(){
								HashMap<String, Sort.Direction>  ordered =  new HashMap<>();
								ordered.put("id",Sort.Direction.ASC);
							return new	RepositoryItemReaderBuilder<Registro>()
															.name("name-item-reader")
															.repository(registroRepository)
															.methodName("findByIdGreaterThan")
															.arguments(1)
															.sorts(ordered)
															.pageSize(50)
															.build();

				}


				@Bean
				public ItemReader<Autor> itemReaderAutor(){
								Map<String,Object> parameters = new HashMap<>();
								parameters.put("igual",31);
								return new JpaCursorItemReaderBuilder<Autor>()
																.name("busca-autor")
																.entityManagerFactory(entityManagerFactory)
																.queryString("SELECT  a FROM Autor a")
																.build();
				}


				@Bean
				public ItemWriter<String> itemWriterAutor(){
								return System.out::println;
//							 return new KafkaItemWriterBuilder<Integer,String>()
//															 .kafkaTemplate(kafkaTemplate)
//															 .itemKeyMapper(String::length)
//															 .build();

				}

				@Bean
				public ItemProcessor<Autor,String> itemProcessor(){
								return Autor::toString;
				}

				@Bean
				public ItemWriter<Autor> itemWriter(){

								ItemPreparedStatementSetter<Autor>  preparedStatementSetter = (item, ps) -> {
									  ps.setString(1,item.getAutor());

								};

								return new JdbcBatchItemWriterBuilder<Autor>()
															 .dataSource(dataSource)
																.itemPreparedStatementSetter(preparedStatementSetter)
															 .sql("INSERT INTO  RESULT_PROCESS (result) VALUES  (?)")
																.build();

				}
}
