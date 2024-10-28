package br.com.ucsal.corrida.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Corrida {
  private final int qtdVeiculos;
  private final int distanciaMaxima;
  private final int velocidadeAvancoMs;
  private final List<Carro> veiculos;
  private final ScheduledExecutorService scheduler;

  public Corrida(int qtdVeiculos, int distanciaMaxima, int velocidadeAvancoMs) {
    this.qtdVeiculos = qtdVeiculos;
    this.distanciaMaxima = distanciaMaxima;
    this.velocidadeAvancoMs = velocidadeAvancoMs;
    this.veiculos = new ArrayList<>();
    this.scheduler = Executors.newScheduledThreadPool(1);
  }

  private void instanciaVeiculos() {
    for (int i = 0; i < qtdVeiculos; i++) {
      Carro carro = new Carro(i + 1);
      veiculos.add(carro);
    }
  }

  private void renderizarPista() {
    System.out.print("\033[H\033[2J");
    System.out.flush();

    System.out.println("-----------------------------------------------------");

    for (Carro carro : veiculos) {
      int posicao = carro.getDistanciaPercorrida();
      StringBuilder linha = new StringBuilder();
      linha.append(carro.getNumero()).append(" ");
      for (int i = 0; i < distanciaMaxima; i++) {
        if (i == posicao) {
          linha.append("#");
        } else {
          linha.append(" ");
        }
      }
      System.out.println(linha.toString());
    }

    System.out.println("-----------------------------------------------------");
  }

  private void ordenarPorPosicao() {
    this.veiculos.sort(Comparator.comparingInt(Carro::getDistanciaPercorrida).reversed());
  }

  private boolean checarEmpate() {
    return this.veiculos.stream().allMatch(v -> v.getDistanciaPercorrida() == this.veiculos.getFirst().getDistanciaPercorrida());
  }

  private void finalizarCorrida() {
    System.out.println("Corrida finalizada!");
    scheduler.shutdown();
  }

  private void verificarVencedor() {
    this.ordenarPorPosicao();
    if (this.checarEmpate()) {
      System.out.println("Empate!");
      this.finalizarCorrida();
    } else {
      System.out.println("Vencedor: " + this.veiculos.getFirst().getNumero());
      this.finalizarCorrida();
    }
  }

  public void iniciar() {
    this.instanciaVeiculos();
    this.renderizarPista();
    Runnable task = () -> {
      this.veiculos.forEach(Carro::acelerar);
      this.renderizarPista();
      if (this.veiculos.stream().anyMatch(v -> v.getDistanciaPercorrida() >= this.distanciaMaxima)) {
        this.verificarVencedor();
      }
    };
    scheduler.scheduleAtFixedRate(task, this.velocidadeAvancoMs, this.velocidadeAvancoMs, TimeUnit.MILLISECONDS);
  }
}
