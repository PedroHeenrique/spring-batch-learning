package br.com.learn.batch.release;


import br.com.learn.batch.release.job.Jobz;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class ControllerRequest {

				private final Jobz job;

				private final JobLauncher launcher;

				public ControllerRequest(Jobz job, JobLauncher launcher) {
								this.job = job;
								this.launcher = launcher;
				}

				@GetMapping("/jobExecute")
				public  void  executarJod() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
								log.info("Requisitando execucao");
								JobParameters parameters = new JobParameters();
								launcher.run(job.iniciarOsTrabalhos(), parameters);
				}
}
