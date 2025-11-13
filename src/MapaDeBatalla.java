import java.util.Scanner;
import java.util.Random;

public class MapaDeBatalla {
  public static void game(int[][] mapa, GamePiece jugador, GamePiece enemigo, Scanner usrInput) {
    int usrChoice;
    boolean partida = true;
    System.out.println("Estado inicial del mapa");
    imprimirMapa(mapa);
    while (partida) {
      System.out.println("Escribe la acción que quieres hacer:");
      System.out.println("1: Moverse");
      System.out.println("2: Disparar");
      usrChoice = usrInput.nextInt();
      switch (usrChoice) {
        case 1:
          moverJugador(mapa, jugador, usrInput);
          break;
        case 2:
          break;

      }
      imprimirMapa(mapa);
    }
    usrInput.close();
  }

  public static void setPosicion(int[][] mapa, int newPos, int oldPos) {
    int swap;
    swap = mapa[oldPos / mapa.length][oldPos % mapa.length];
    mapa[oldPos / mapa.length][oldPos % mapa.length] = 0;
    mapa[(oldPos + newPos) / mapa.length][(oldPos + newPos) % mapa.length] = swap;
  }

  public static void moverJugador(int[][] mapa, GamePiece jugador, Scanner usrInput) {
    // Chuleta W = -N ; S = +N ; A = -1 ; D = +1 ; N = 0
    String usrChoice;
    System.out.println("\nEscribe que quieres hacer");
    System.out.println("W: moverse hacia arriba");
    System.out.println("A: moverse hacia la izquierda");
    System.out.println("S: moverse hacia abajo");
    System.out.println("D: moverse hacia la derecha");
    System.out.println("N: no moverse");
    usrInput.nextLine();
    usrChoice = usrInput.nextLine();
    switch (usrChoice) {
      case "W":
        setPosicion(mapa, -(mapa.length), jugador.pos);
        jugador.pos = jugador.pos - mapa.length;
        break;
      case "S":
        setPosicion(mapa, mapa.length, jugador.pos);
        jugador.pos = jugador.pos + mapa.length;
        break;
      case "A":
        setPosicion(mapa, -1, jugador.pos);
        jugador.pos = jugador.pos - 1;
        break;
      case "D":
        setPosicion(mapa, 1, jugador.pos);
        jugador.pos = jugador.pos + 1;
    }
  }

  public static void imprimirMapa(int[][] mapa) {
    for (int i = 0; i < mapa.length; i++) {
      for (int j = 0; j < mapa.length; j++) {
        if (mapa[i][j] == 0) {
          System.out.print(" . ");
        } else if (mapa[i][j] == 1) {
          System.out.print(" J ");
        } else if (mapa[i][j] == 2) {
          System.out.print(" E ");
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    Random rand = new Random();
    Scanner usrInput = new Scanner(System.in);
    GamePiece jugador = new GamePiece();
    GamePiece enemigo = new GamePiece();
    int[][] mapa;
    int usrChoice, randInt;
    boolean isDiff = false;
    System.out.println("Escribe el tamaño del tablero para la partida:");
    usrChoice = usrInput.nextInt();
    mapa = new int[usrChoice][usrChoice];
    randInt = rand.nextInt((usrChoice * usrChoice));
    jugador.pos = randInt;
    mapa[jugador.pos / usrChoice][jugador.pos % usrChoice] = 1;
    while (!isDiff) {
      randInt = rand.nextInt((usrChoice * usrChoice));
      if (randInt != jugador.pos) {
        isDiff = true;
      }
    }
    enemigo.pos = randInt;
    mapa[enemigo.pos / usrChoice][enemigo.pos % usrChoice] = 2;
    game(mapa, jugador, enemigo, usrInput);
    usrInput.close();
  }
}
