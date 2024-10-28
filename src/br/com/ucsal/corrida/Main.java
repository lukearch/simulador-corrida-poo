package br.com.ucsal.corrida;

import br.com.ucsal.corrida.model.Corrida;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    Corrida corrida = new Corrida(2, 50, 1000);
    corrida.iniciar();
  }
}
