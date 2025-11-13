import java.util.Scanner;
import java.util.Random;

public class MapaDeBatalla {
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
    int[][] mapa;
    int usrChoice, randInt, buffer;
    boolean isDiff = false;
    System.out.println("Escribe el tamaño del tablero para la partida:");
    usrChoice = usrInput.nextInt();
    mapa = new int[usrChoice][usrChoice];
    randInt = rand.nextInt((usrChoice * usrChoice));
    buffer = randInt;
    mapa[buffer / usrChoice][buffer % usrChoice] = 1;
    while (!isDiff) {
      randInt = rand.nextInt((usrChoice * usrChoice));
      if (randInt != buffer) {
        isDiff = true;
      }
    }
    buffer = randInt;
    mapa[buffer / usrChoice][buffer % usrChoice] = 2;
    imprimirMapa(mapa);
    usrInput.close();
  }
}
