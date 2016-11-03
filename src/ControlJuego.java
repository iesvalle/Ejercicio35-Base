import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {

	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no
	 *        son minas guardan en el entero cuántas minas hay alrededor de la
	 *        celda
	 */
	public void inicializarPartida() {
		// Borro del tablero la información que pudiera haber anteriormente
		// (los pongo todos a cero):
		// Me creo LADO_TABLERO*LADO_TABLERO números en un array list, uno para
		// cada una de las posiciones del tablero:

		ArrayList<Integer> posicionesTablero = new ArrayList<>();
		ArrayList<Integer> posicionesMinas = new ArrayList<>();

		int contador = 0;
		// creo el tablero con todas las casillas a 0
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tablero[i][j] = 0;
				posicionesTablero.add((contador));// las posiciones del tablero
				contador++;

			}
		}

		// for (int i = 0; i < posicionesTablero.size(); i++) {
		// System.out.println(posicionesTablero.get(i));
		// }
		//
		// Saco 20 posiciones sin repetir del array y les coloco una mina en el
		// tablero:
		// puesto que el array esta relleno con 100 posiciones saco 20
		// aletaorias

		do {

			// obtengo una posicion del array
			int indiceMina = calcularAleatorio(posicionesTablero.size());
			// obtengo el valor de la posicion
			int posicionMinaObtenida = posicionesTablero.get(indiceMina);
			// elimino el valor obtenido para que no se repita
			posicionesTablero.remove(indiceMina);
			// a�ado al array la posicion de las minas
			posicionesMinas.add(posicionMinaObtenida);

		} while (posicionesMinas.size() != 20);

		// lo ordeno para pintarlo de manera m�s facil
		Collections.sort(posicionesMinas);
		// pinto las minas en las posiciones correspondientes
		for (int i = 0; i < posicionesMinas.size(); i++) {
			int columna, fila;
			int posicionMina = posicionesMinas.get(i);// guardamos el valor
														// aleatorio
			// lo primero que haremos sera sacar la posicion de la fila
			// el 00...09 se corresponde con la comlumna 0, y asi
			// sucesimavemente

			fila = posicionMina / 10; // divido entre 10 para coger la fila
			columna = posicionMina % 10;// cogo el resto para la columna

			tablero[fila][columna] = -1;

		}

		// Calculo para todas las posiciones que no tienen minas, cuántas minas
		// hay alrededor.

		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				if (tablero[i][j] != MINA) {

					tablero[i][j] = calculoMinasAdjuntas(i, j);

				}
			}
		}

		// Pongo la puntuación a cero:

		puntuacion = 0;

	}

	// calcular aleatoiro
	public int calcularAleatorio(int rango) {
		Random rd = new Random();
		return rd.nextInt(rango);
	}

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos
	 * que tener en cuenta que no nos salimos nunca del tablero. Por lo tanto,
	 * como mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como mucho
	 * la i y la j valdrán como poco 0.
	 * 
	 * @param i:
	 *            posición verticalmente de la casilla a rellenar
	 * @param j:
	 *            posición horizontalmente de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 */
	private int calculoMinasAdjuntas(int fila, int columna) {
		int minasAlrededor = 0;

		// recorremos las filas y columnas que hay alrededor

		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna + 1; j++) {

				if (i >= 0 && j >= 0 && i < 10 && j < 10) {
					if (tablero[i][j] == MINA) {
						minasAlrededor++;
					}
				}

			}
		}

		return minasAlrededor;
	}

	/**
	 * Método que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado
	 *      por el GestorJuego. Por lo tanto siempre sumaremos puntos
	 * @param i:
	 *            posición verticalmente de la casilla a abrir
	 * @param j:
	 *            posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		boolean abrirCasilla;

		if (tablero[i][j] == MINA) {
			abrirCasilla = false;
		} else {
			abrirCasilla = true;
		}

		return abrirCasilla;
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto
	 * todas las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		boolean finJuego;
		//comprobamos cuando la puntuacion es 80, ya que es el total que podemos sacar
		int puntuacionMaxima=LADO_TABLERO*LADO_TABLERO-MINAS_INICIALES; 

		//si la puntuacion del jugador es la maxima hemos acabado la partida
		if (puntuacionMaxima==this.puntuacion) {
			finJuego=true;
		}else{
			finJuego=false;
		}
		return finJuego;
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se
	 * utiliza para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: " + puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una
	 * celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace
	 *      falta calcularlo, símplemente consultarlo
	 * @param i
	 *            : posición vertical de la celda.
	 * @param j
	 *            : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la
	 *         celda
	 */
	 public int getMinasAlrededor(int i, int j) {
		 return tablero[i][j];
	 }
	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	 public int getPuntuacion() {
		 return puntuacion;
	 }
	
}
