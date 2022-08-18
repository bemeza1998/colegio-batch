package ec.edu.espe.colegiobatch.process;

import ec.edu.espe.colegiobatch.model.Estudiante;
import ec.edu.espe.colegiobatch.utils.FileUtils;
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

public class InsercionEstudianteTask implements Tasklet, StepExecutionListener {

  private List<Estudiante> estudiantes;
  private RestTemplate restTemplate = new RestTemplate();
  private String ESTUDIANTE_URL = "http://localhost:8080/estudiante";

  @Override
  public void beforeStep(StepExecution stepExecution) {
    estudiantes = FileUtils.leerArchivo("c:/volumes/estudiantes.txt");
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    ResponseEntity<Estudiante[]> response =
        restTemplate.postForEntity(ESTUDIANTE_URL, estudiantes, Estudiante[].class);
    Estudiante[] objectArray = response.getBody();
    estudiantes = Arrays.asList(objectArray);
    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return ExitStatus.COMPLETED;
  }
}
