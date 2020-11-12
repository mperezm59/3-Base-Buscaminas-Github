import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int [][] tablero;
	private int puntuacion;
	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//Inicializamos una nueva partida
		inicializarPartida();
		depurarTablero();
	}
	
	
	/**Método para generar un nuevo tablero de partida:
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){

		// rellenamos el tablero con 0 al inicar la partida
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
					tablero[i][j] = 0;
			}
		}
		
		// ponemos la puntuacion a 0 al emprezar
		puntuacion = 0;

		//TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
		
		// nos creamos 2 aleatorios que guardaremos en 2 int;
		Random ri = new Random();
		int alei, alej;
		Random rj = new Random();
		
		for(int i = 0;i < MINAS_INICIALES;i++){
			// creamos 2 aleatorios.
			alei = ri.nextInt(LADO_TABLERO);
			alej = rj.nextInt(LADO_TABLERO);
			// si en la posicion del tablero sacada anteriormente con los aleatorios
			// no hay minas colocamos una mina, y si la ahi volvemos a generar los aleatorios.
			if(tablero[alei][alej] != MINA){
				tablero[alei][alej] = MINA;
			}else{
				i--;
			}
		}
		
		
		//Al final del m�todo hay que guardar el n�mero de minas para las casillas que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
				}
			}
		}
	}
	
	/**Cálculo de las minas adjuntas: 
	 * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		// nos declaramos un contador.
		int cont = 0;
		// nos declaraos una variables para ver donde empieza y acaba nuestro tablero (vertical, horizontal).
		int iIni, iFin = i+1, jIni, jFin = j+1;

		// nos recorremos los alrededores de nuestra celda.
		for(iIni = i-1;iIni <= iFin;iIni++){
			for(jIni = j-1;jIni <= jFin;jIni++){

				// comprobamos que la celda a comprobar esta dentro del tablero.
				if(iIni >= 0 && iIni <= LADO_TABLERO-1){
					if(jIni >= 0 && jIni <= LADO_TABLERO-1){
						// comprobamos si la celda de los alrededores de uestra celda es una mina
						if(tablero[iIni][jIni] == MINA){
							// si o es aumentamos el contador y asi sabremos cuantas minas ahi al lado de nuestra celda.
							cont++;
						}
					}
				}

			}
		}

		return cont;
	}
	
	/**
	 * Método que nos permite abrir una casilla, retorna verdadero y suma 1 punto si no hay mina.
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		boolean abierta = false;

		if(tablero[i][j] != MINA){
			abierta = true;
			puntuacion++;
		}

		return abierta;
	}
	
	
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		boolean fin = false;

		if(puntuacion == ((LADO_TABLERO * LADO_TABLERO) - MINAS_INICIALES) -1){
			fin = true;
		}
		return fin;
	}
	
	
	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: "+puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}
	
}
