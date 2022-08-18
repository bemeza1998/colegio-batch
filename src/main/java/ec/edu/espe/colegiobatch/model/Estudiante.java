package ec.edu.espe.colegiobatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante {
  private String cedula;

  private String apellidos;

  private String nombres;

  private Integer nivel;
}
