package ec.edu.espe.colegiobatch.config;

import ec.edu.espe.colegiobatch.process.AsignacionEstudianteParaleloTask;
import ec.edu.espe.colegiobatch.process.GenerarListaTask;
import ec.edu.espe.colegiobatch.process.InsercionEstudianteTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class JobConfig {

  @Autowired private JobBuilderFactory jobs;

  @Autowired private StepBuilderFactory steps;

  @Bean
  protected Step insercionEstudianteTask() {
    return steps.get("readAndInsertTask").tasklet(new InsercionEstudianteTask()).build();
  }

  @Bean
  protected Step asignacionEstudianteParaleloTask() {
    return steps.get("processDataTask").tasklet(new AsignacionEstudianteParaleloTask()).build();
  }

  @Bean
  protected Step generarListaTask() {
    return steps.get("processDataTask").tasklet(new GenerarListaTask()).build();
  }

  @Bean
  public Job crearListasEstudiantesJob() {
    return jobs.get("crearListasEstudiantesJob")
        .start(insercionEstudianteTask())
        .next(asignacionEstudianteParaleloTask())
        .next(generarListaTask())
        .build();
  }
}
