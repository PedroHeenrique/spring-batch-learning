package br.com.learn.batch.job;

import br.com.learn.batch.domain.Autor;
import br.com.learn.batch.domain.Registro;

import br.com.learn.batch.repository.AutorRepository;
import br.com.learn.batch.repository.RegistroRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

@Log4j2
@Configuration
public class Jobz {

				private final JobRepository jobRepository;

				private final PlatformTransactionManager transactionManager;
				private final AutorRepository autorRepository;

				private final RegistroRepository registroRepository;


				@Autowired
				public Jobz(JobRepository jobRepository, PlatformTransactionManager transactionManager, AutorRepository autorRepository, RegistroRepository registroRepository) {
								this.jobRepository = jobRepository;
								this.transactionManager = transactionManager;
								this.autorRepository = autorRepository;
								this.registroRepository = registroRepository;

				}


				@Bean
				public Job job(){
								return new JobBuilder("iniciar-trabalhos",jobRepository)
																.start(primeiroPasso())
																.build();
				}


				@Bean
				public Step primeiroPasso(){
								return new StepBuilder("primeiro-passo",jobRepository)
																.<Autor, Registro>chunk(10,transactionManager)
																.reader(itemReaderAutor())
																.listener(new MeReaderListener())
																.processor(itemProcessor())
																.listener(new MeProcessorListener())
  														.writer(itemWriter())
                .listener(new MeChunkListener())
																.faultTolerant()
																.skip(RuntimeException.class)
																.skipLimit(100)
																.allowStartIfComplete(true)
																.build();
				}


				@Bean
				public ItemReader<Autor> itemReaderAutor(){
								HashMap<String, Sort.Direction>  ordered =  new HashMap<>();
								ordered.put("id",Sort.Direction.ASC);
								return new RepositoryItemReaderBuilder<Autor>()
																.name("buscar-todos")
																.repository(autorRepository)
																.methodName("findAll")
																.sorts(ordered)
																.pageSize(10)
																.build();

				}

				@Bean
				public ItemWriter<Registro>itemWriter(){
       return new RepositoryItemWriterBuilder<Registro>()
								       .repository(registroRepository)
								       .methodName("save")
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
				public ItemProcessor<Autor,Registro> itemProcessor(){
								return (autor -> autor.getTitle().startsWith("OC") ?
																Registro.builder()
																.autor(autor)
																.name(autor.getAutor())
																.build() : null);
				}

}
