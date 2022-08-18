package ec.edu.espe.colegiobatch.process;

import ec.edu.espe.colegiobatch.model.Estudiante;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AsignacionEstudianteParaleloTask implements Tasklet, StepExecutionListener {
  private RestTemplate restTemplate = new RestTemplate();
  private String ESTUDIANTE_URL = "http://localhost:8080/estudiante";

  @Override
  public void beforeStep(StepExecution stepExecution) {}

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    for (int i = 1; i <= 10; i++) {
      List<Estudiante> estudiantesNivel = new ArrayList<>();
      ResponseEntity<Estudiante[]> response =
          restTemplate.getForEntity(ESTUDIANTE_URL + "/" + i, Estudiante[].class);
      Estudiante[] objectArray = response.getBody();
      estudiantesNivel = Arrays.asList(objectArray);

      for (Estudiante estudiante : estudiantesNivel) {
        restTemplate.put(
            ESTUDIANTE_URL + "/" + estudiante.getCedula() + "/" + estudiante.getNivel(),
            estudiante);
      }
    }

    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return ExitStatus.COMPLETED;
  }
}
