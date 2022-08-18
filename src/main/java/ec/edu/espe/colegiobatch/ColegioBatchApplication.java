package ec.edu.espe.colegiobatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class ColegioBatchApplication {

  @Autowired JobLauncher jobLauncher;

  @Autowired
  @Qualifier("crearListasEstudiantesJob")
  Job job1;

  public static void main(String[] args) {
    SpringApplication.run(ColegioBatchApplication.class, args);
  }

  @Scheduled(fixedDelay = 100000, initialDelay = 3000)
  public void perform() throws Exception {
    log.info("------------------------ INICIALIZACION DEL JOB -----------------------");
    JobParameters params =
        new JobParametersBuilder()
            .addString("crearListasEstudiantesJob", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
    jobLauncher.run(job1, params);
  }
}
