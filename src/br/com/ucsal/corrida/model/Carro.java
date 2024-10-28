package br.com.ucsal.corrida.model;

import java.util.Random;

public class Carro {
  private final int numero;
  private int velocidade;
  private int distanciaPercorrida;

  public Carro(int numero) {
    this.numero = numero;
    this.velocidade = 0;
    this.distanciaPercorrida = 0;
  }

  public void acelerar() {
    Random random = new Random();
    this.velocidade = random.nextInt(3) + 1;
    this.distanciaPercorrida += velocidade;
  }

  public int getNumero() {
    return this.numero;
  }

  public int getDistanciaPercorrida() {
    return this.distanciaPercorrida;
  }

  public void setDistanciaPercorrida(int distancia) {
    this.distanciaPercorrida = distancia;
  }
}
