package br.com.learn.batch.release.job;

import br.com.learn.batch.release.domain.Autor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ItemReadListener;


@Log4j2
public class MeListener implements ItemReadListener<Autor> {

				@Override
				public void beforeRead() {

				}

				@Override
				public void afterRead(Autor item){
								log.info(item + " lido");

				}

				public void onReadError(Exception ex){
								log.error(ex.getMessage());
				}


}
