package br.com.learn.batch.job;

import br.com.learn.batch.domain.Autor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ItemProcessListener;


@Log4j2
public class MeProcessorListener implements ItemProcessListener<Autor,Autor> {

				@Override
				public void afterProcess(@NonNull Autor item, Autor result){
          log.info("result {}", result);
				}

}
