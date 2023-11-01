package br.com.learn.batch.release.job;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Log4j2
public class MeChunkListener implements ChunkListener {

				@Override
				public void beforeChunk(ChunkContext contex){
								log.info("beforeChunk- "+contex.getStepContext().getStepExecution().getCommitCount());
				}

				@Override
				public void afterChunk(ChunkContext context){
								log.info("afterChunk- " + context.getStepContext().getStepExecution().getCommitCount());
				}

				@Override
				public void afterChunkError(ChunkContext context){
								log.info("afterChunkError- "+context.toString());
				}
}
