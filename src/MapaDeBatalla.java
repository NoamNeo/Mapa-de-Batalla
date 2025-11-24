import java.util.Scanner;
import java.util.Random;

public class MapaDeBatalla {
  public static boolean checkSquare(int[][] mapa, GamePiece piece, int targetPos) {
    /*
     * Siguiendo con la misma lógica para mover algo a través de nuestro array (ve
     * el comentario de la función moverJugador antes que esta) a la hora de mover
     * algo nos interesa saber cuando la posición es menor que 0 o mayor o igual que
     * el cuadrado de nuestro orden en esta situación tenemos que también checkear
     * si es igual porque el valor n*2 está fuera de nuestro rango.
     *
     * A la hora de checkear el moviemiento horizontal emplearemos el módulo para
     * acotar nuestra posición para poder comprobar que si restamos (nos movemos a
     * la izuqierda) es menor que 0 y si sumamos que no sea mayor que n-1.
     *
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * Vamos a tomar como ejemplo la línea 3 (6 7 8). Haciendo el módulo por n(3 en
     * este caso) nos daría lo siguiente:
     *
     * 0 1 2 <-- (6%3)(7%3)(8%3) respectivamente.
     *
     * Ahora podemos comprobar si a la hora de sumar o restar 1 el resultado es
     * menor que 0 (fuera de rango por la izquierda) o mayor que n-1 (fuera de rango
     * por la derecha)
     */
    boolean valid = true;
    // Primero hacemos un check de si la posición que tenemos está en una fila con
    // módulo 0 o n-1 (en los extremos izquierdos y derechos) para evitar que a la
    // hora de mover verticalmente no nos lo evite.
    // Luego hacemos un check de si nos queremos mover horizontalmente (si targetPos
    // == 1 o -1), ya que la siguiente cadena de ifs es la que revisa los
    // moviemientos verticales y no queremos que esta compruebe nada en esos casos.
    if ((piece.pos % mapa.length == 0 || piece.pos % mapa.length == mapa.length - 1)
        && (targetPos == -1 || targetPos == 1)) {
      if (piece.pos % mapa.length + targetPos < 0) {
        valid = false;
      } else if (piece.pos % mapa.length + targetPos == mapa.length) {
        valid = false;
      }
    } else if (piece.pos + targetPos < 0) {
      valid = false;
    } else if (piece.pos + targetPos >= mapa.length * mapa.length) {
      valid = false;
    }
    if (!valid) {
      System.out.println("\nDirección no válida, prueba de nuevo\n");
    }
    return valid;
  }

  public static void game(int[][] mapa, GamePiece jugador, GamePiece enemigo, Scanner usrInput) {
    String usrChoice;
    boolean partida = true;
    System.out.println("Estado inicial del mapa");
    imprimirMapa(mapa);
    while (partida) {
      System.out.println("Escribe la acción que quieres hacer:");
      System.out.println("1: Moverse");
      System.out.println("2: Disparar");
      usrChoice = usrInput.nextLine().trim();
      switch (usrChoice) {
        case "1":
          moverJugador(mapa, jugador, usrInput);
          break;
        case "2":
          break;

      }
      imprimirMapa(mapa);
    }
    usrInput.close();
  }

  public static void setPosicion(int[][] mapa, int newPos, int oldPos) {
    /*
     * Con el sistema que tenemos montado para movernos a través de nuestro array
     * bidimensional tenemos que convertir los valores a sus respectivas
     * coordenadas.
     * Por ejemplo:
     *
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * Nosotros podemos saber que 6 = [0][2] pero tenemos que encontrar la manera de
     */
    int swap;
    swap = mapa[oldPos / mapa.length][oldPos % mapa.length];
    mapa[oldPos / mapa.length][oldPos % mapa.length] = 0;
    mapa[(oldPos + newPos) / mapa.length][(oldPos + newPos) % mapa.length] = swap;
  }

  public static void moverJugador(int[][] mapa, GamePiece jugador, Scanner usrInput) {
    /*
     * El funcionamiento de esta función es el siguiente:
     * Le pedidmos al usuario un input de tipo string W , A , S , D
     * Cada uno de estos valores le asignamos un valor de movimiento, dependiendo de
     * la dirección
     * La logica es la siguiente: Teniendo un array bidimensional de tamaño n (como
     * ejemplo usaremos 3)
     * Tendrá la siguiente forma según los índices de posición:
     *
     * 0 2 3
     * 4 5 6
     * 7 8 9
     *
     * Por lo tanto si estamos en la posición 5 (el centro del array) para movernos
     * una posición hacia abajo tenemos que sumar 3 (5 + 3 = 8).
     * Esta suma no es arbitraria ya que cualquier movimiento vertical en una matriz
     * cuadrada es igual a su orden de manera positiva o negativa.
     * Puesto de otra manera:
     * El array foo[1][3] tendría una fila con 3 elementos en esa fila.
     *
     * 0 1 2 <-- Fila 1
     *
     * Si añadimos una fila a foo, para que tenga la forma foo[2][3] estaremos
     * añadiendo 3 elementos.
     *
     * 0 1 2
     * 3 4 5 <-- Fila 2
     *
     * Por lo tanto podemos interpretar el movimiento de una fila a otra como un
     * +-n
     * donde n es el orden de nuestro array.
     *
     * A la hora de hacer un cambio horizontal se trata de un cambio de 1 índice,
     * por lo tanto tendrá la forma +-1 siempre. (Con esta implementación moverse a
     * la derecha desde la posición 6 en nuestro array de ejemplo provoca que se
     * mueva a la posición 7. Solucionamos esto en la función checkSquare)
     *
     * El problema de convertir un número como 5 en [1][1] como en el caso de
     * nuestro ejemplo lo solucionamos con la función setPosicion
     *
     * Por lo tanto tenemos la siguiente tabla de valores de moviemiento:
     * Para n = longitud de nuestra tabla.
     * W = + n
     * S = - n
     * A = - 1
     * D = + 1
     */
    String usrChoice;
    boolean validSquare = true;
    int valorMoviemiento = 0;
    while (validSquare) {
      System.out.println("\nEscribe que quieres hacer");
      System.out.println("W: moverse hacia arriba");
      System.out.println("A: moverse hacia la izquierda");
      System.out.println("S: moverse hacia abajo");
      System.out.println("D: moverse hacia la derecha");
      System.out.println("N: no moverse");
      usrChoice = usrInput.nextLine().trim().toUpperCase();
      switch (usrChoice) {
        case "W":
          valorMoviemiento = -(mapa.length);
          break;
        case "S":
          valorMoviemiento = mapa.length;
          break;
        case "A":
          valorMoviemiento = -1;
          break;
        case "D":
          valorMoviemiento = 1;
          break;
      }
      validSquare = checkSquare(mapa, jugador, valorMoviemiento);
      if (validSquare) {
        setPosicion(mapa, valorMoviemiento, jugador.pos);
        jugador.pos = jugador.pos + valorMoviemiento;
      }
      validSquare = !validSquare;
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
