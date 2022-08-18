package ec.edu.espe.colegiobatch.utils;

import ec.edu.espe.colegiobatch.model.Estudiante;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

  public static void crearArchivo(String ruta) {
    FileWriter flwriter = null;
    try {
      flwriter = new FileWriter(ruta);
      BufferedWriter bfwriter = new BufferedWriter(flwriter);
      bfwriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (flwriter != null) {
        try {
          flwriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static ArrayList<Estudiante> leerArchivo(String ruta) {
    File file = new File(ruta);
    ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
    Scanner scanner;
    try {
      scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String linea = scanner.nextLine();
        Scanner delimitar = new Scanner(linea);
        delimitar.useDelimiter("\\s*,\\s*");
        Estudiante estudiante =
            Estudiante.builder()
                .cedula(delimitar.next())
                .apellidos(delimitar.next())
                .nombres(delimitar.next())
                .nivel(delimitar.nextInt())
                .build();
        listaEstudiantes.add(estudiante);
        delimitar.close();
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return listaEstudiantes;
  }

  public static void aniadirArchivo(String ruta, List<Estudiante> lista) {
    FileWriter flwriter = null;
    try {
      flwriter = new FileWriter(ruta, true);
      BufferedWriter bfwriter = new BufferedWriter(flwriter);
      for (Estudiante c : lista) {
        bfwriter.write(
            c.getCedula()
                + ","
                + c.getApellidos()
                + ","
                + c.getNombres()
                + ","
                + c.getNivel()
                + "\n");
      }
      bfwriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (flwriter != null) {
        try {
          flwriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
