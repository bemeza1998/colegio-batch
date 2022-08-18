package ec.edu.espe.colegiobatch.process;

import ec.edu.espe.colegiobatch.model.Estudiante;
import ec.edu.espe.colegiobatch.utils.FileUtils;
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

public class GenerarListaTask implements Tasklet, StepExecutionListener {
  private RestTemplate restTemplate = new RestTemplate();
  private String PARALELO_URL = "http://localhost:8080/paralelo";

  @Override
  public void beforeStep(StepExecution stepExecution) {}

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    String[] letras = {"A", "B", "C"};
    for (int i = 1; i <= 10; i++) {
      for (int j = 0; j < letras.length; j++) {
        List<Estudiante> estudiantesNivel = new ArrayList<>();
        ResponseEntity<Estudiante[]> response =
            restTemplate.getForEntity(PARALELO_URL + "/" + i + "/" + letras[j], Estudiante[].class);
        Estudiante[] objectArray = response.getBody();
        estudiantesNivel = Arrays.asList(objectArray);
        String ruta = "c:/volumes/nivel_" + i + "_Paralelo_" + letras[j];
        FileUtils.crearArchivo(ruta);
        FileUtils.aniadirArchivo(ruta, estudiantesNivel);
      }
    }

    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return ExitStatus.COMPLETED;
  }
}
