import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Tablero {
	public Jugador jugador1;
	public Jugador jugador2;
	public Jugador turnoJugador;
	private static Tablero miTablero = null;
	private Ficha[][] matrix=null;

	private static String filename = "PGuardada.txt";

	//Constructora
	private Tablero(){
		this.matrix = new Ficha[8][8];
	}

	//Otros metodos
	public static Tablero getTablero(){
		if (miTablero == null){
			miTablero = new Tablero();
		}
		return miTablero;
	}
	//Getter y setter
	public Ficha[][] getMatriz(){
		return this.matrix;
	}
	public void setMatriz(Ficha[][] pMatriz){
		this.matrix = pMatriz;
	}
	public Jugador getTurnoJugador(){
		return this.turnoJugador;
	}
	public void setTurnoJugador(Jugador pTurno){
		this.turnoJugador = pTurno;
	}
	public Jugador getJug1(){
		return this.jugador1;
	}
	public Jugador getJug2(){
		return this.jugador2;
	}


	public void jugarPartida() {
		// Inicia el juego si no se ha iniciado y 
		// ejecuta los movimientos de cada jugador, realiza los
		//correspondientes cambios y muestra actualizado el tablero

		Fichero fichero = Fichero.getFichero();
		String menu;
		menu = this.animacionInicial(); 

		//Da la opciÃ³n de continuar con la partida guardada en el fichero o iniciar una nueva partida
		if(menu.equals("2")) { 
			fichero.cargarPartida();
		} else if(menu.equals("1")) { //Nueva partida
			this.nuevaPartida();
		}else{
			System.out.println("Error de formato");
		}

		boolean fin = false;
		while (!fin) { //DETERMINAR CUANDO SE TERMINA LA PARTIDA
			this.print();

			System.out.println("Turno de: " + this.turnoJugador.getNombre());


			Ficha ficha = this.seleccionarPieza();

			if(ficha != null) {
				Posicion posicionDestino = this.seleccionarPosicionDestino();
				boolean esRey = false;
				if (this.matrix[posicionDestino.getFila()][posicionDestino.getColumna()] instanceof Rey){
					esRey = true;
				}
				boolean fichaMovida = ficha.realizarMovimiento(posicionDestino);
				//System.out.println(fichaMovida);
				if(fichaMovida) {

					if(esRey){
						System.out.println("Partida terminada. Ha ganado: " + this.turnoJugador.getNombre());
						fin = true;
					}else{
						this.cambiarJugador();
						fichero.guardarPartida();
					}

				}
			}
		}
	}

	private void nuevaPartida(){

		//Nombre de jugadores

		String j1= Teclado.leerString("Nombre jugador 1: ");
		String j2= Teclado.leerString("Nombre jugador 2: ");
		//this.jugador1 = new Jugador(Color.WHITE, j1); //Jugador1 = Blancas
		//this.jugador2 = new Jugador(Color.BLACK, j2); //Jugador2 = Negras
		//Quien empieza aleatorio
		System.out.println();
		int x = (int)(Math.random()*100);
		if (x%2==0){
			System.out.println("Empieza "+j1);
			this.jugador1 = new Jugador(Color.WHITE, j1); //Jugador1 = Blancas
			this.jugador2 = new Jugador(Color.BLACK, j2); //Jugador2 = Negras
			this.turnoJugador = this.jugador1;
		}
		else{
			System.out.println("Empieza "+j2);
			this.jugador2 = new Jugador(Color.WHITE, j2); //Jugador2 = Blancas
			this.jugador1 = new Jugador(Color.BLACK, j1); //Jugador1 = Negras
			this.turnoJugador = this.jugador2;
		}
		System.out.println("");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.inicializarPiezas();
	}

	private String animacionInicial() {

		String menu="Error";


		// ####Ascii art###
		System.out.println(" ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄       ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄ \n▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░▌       ▐░▌\n▐░█▀▀▀▀▀▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀      ▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌\n▐░▌          ▐░▌       ▐░▌▐░▌          ▐░▌          ▐░▌               ▐░▌       ▐░▌▐░▌       ▐░▌\n▐░▌          ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄      ▐░█▄▄▄▄▄▄▄█░▌▐░▌   ▄   ▐░▌\n▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌\n▐░▌          ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀█░▌ ▀▀▀▀▀▀▀▀▀█░▌     ▐░█▀▀▀▀█░█▀▀ ▐░▌ ▐░▌░▌ ▐░▌\n▐░▌          ▐░▌       ▐░▌▐░▌                    ▐░▌          ▐░▌     ▐░▌     ▐░▌  ▐░▌▐░▌ ▐░▌▐░▌\n▐░█▄▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄█░▌ ▄▄▄▄▄▄▄▄▄█░▌     ▐░▌      ▐░▌ ▐░▌░▌   ▐░▐░▌\n▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌       ▐░▌▐░░▌     ▐░░▌\n ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀       ▀         ▀  ▀▀       ▀▀ \n                                                                                                ");
		//System.out.println("------------------------------------------------------------------------------------------------");
		//###Animacion###

		try {
			TimeUnit.SECONDS.sleep(2);

			System.out.print("Made "); TimeUnit.MILLISECONDS.sleep(200);
			System.out.print("by:");		TimeUnit.MILLISECONDS.sleep(1000);
			System.out.println();
			System.out.print("I");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("b");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("i");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print(" ");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("I");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("r");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("s");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("t");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("o");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("r");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("z");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.println();
			System.out.print("A");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("n");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("d");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("r");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("e");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print(" ");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("G");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("r");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("c");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("i");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.println();
			System.out.print("D");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("v");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("i");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("d");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print(" ");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("R");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("e");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("v");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("i");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("l");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("l");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("a");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("s");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.println();
			System.out.print("O");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("w");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("e");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("n");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print(" ");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("T");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("r");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("i");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("g");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("u");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("e");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("r");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("o");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.print("s");		TimeUnit.MILLISECONDS.sleep(130);
			System.out.println();

			System.out.println("================================================================================================");

			System.out.println("\033[31m[1]NUEVA PARTIDA  \033[0m");//Rojo
			System.out.println("\033[31m[2]CARGAR PARTIDA  \033[0m");//No se ve en eclipse, pero en consola si
			//Menu AMPLIABLE

			menu=Teclado.leerString("=>");

			System.out.println("================================================================================================");


		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("***Error en la animación inicial, probablemente la función timeout...***");
		}

		return menu;


	}

	private Ficha seleccionarPieza() {
		Ficha ficha = null;
		try {
			String piezaOrigen = Teclado.leerString("Seleccionar pieza a mover (Columna y numero de fila, ej.: A2): ");
			char letra = piezaOrigen.charAt(0);
			int fila = Integer.valueOf("" + piezaOrigen.charAt(1)) -1;
			int columna = this.letraToNum(letra);

			ficha = this.matrix[fila][columna];

			if (ficha.getJugador() != this.turnoJugador) {
				ficha = null;
			}
		}
		catch (Exception e){
			System.out.println("Pieza escogida erronea.");
			ficha = null;
		}

		return ficha;		
	}

	private Posicion seleccionarPosicionDestino() {
		Posicion posicion = null;
		try{
			String piezaOrigen = Teclado.leerString("Seleccionar posicion de destino (Columna y numero de fila, ej.: A3): ");
			char letra = piezaOrigen.charAt(0);
			int fila = Integer.valueOf("" + piezaOrigen.charAt(1)) -1;
			int columna = this.letraToNum(letra);
			posicion = new Posicion(fila, columna);
		}
		catch (Exception e){
			System.out.println("Posicion de destino erronea.");
			posicion = new Posicion(0, 10); //Erronea
		}
		return posicion;		
	}

	private void inicializarPiezas() {

		//Inicializar las piezas segun los jugadores establecidos

		Jugador jb = this.turnoJugador;
		Jugador jn;
		if (jb == this.jugador1){
			jn = this.jugador2;
		}else{
			jn = this.jugador1;
		}

		this.matrix[0][0] = new Torre(jb, new Posicion(0, 0));
		this.matrix[0][1] = new Caballo(jb, new Posicion(0, 1));
		this.matrix[0][2] = new Alfil(jb, new Posicion(0, 2));
		this.matrix[0][3] = new Reina(jb, new Posicion(0, 3));
		this.matrix[0][4] = new Rey(jb, new Posicion(0, 4));
		this.matrix[0][5] = new Alfil(jb, new Posicion(0, 5));
		this.matrix[0][6] = new Caballo(jb, new Posicion(0, 6));
		this.matrix[0][7] = new Torre(jb, new Posicion(0, 7));

		this.matrix[1][0] = new Peon(jb, new Posicion(1, 0));
		this.matrix[1][1] = new Peon(jb, new Posicion(1, 1));
		this.matrix[1][2] = new Peon(jb, new Posicion(1, 2));
		this.matrix[1][3] = new Peon(jb, new Posicion(1, 3));
		this.matrix[1][4] = new Peon(jb, new Posicion(1, 4));
		this.matrix[1][5] = new Peon(jb, new Posicion(1, 5));
		this.matrix[1][6] = new Peon(jb, new Posicion(1, 6));
		this.matrix[1][7] = new Peon(jb, new Posicion(1, 7));

		this.matrix[6][0] = new Peon(jn, new Posicion(6, 0));
		this.matrix[6][1] = new Peon(jn, new Posicion(6, 1));
		this.matrix[6][2] = new Peon(jn, new Posicion(6, 2));
		this.matrix[6][3] = new Peon(jn, new Posicion(6, 3));
		this.matrix[6][4] = new Peon(jn, new Posicion(6, 4));
		this.matrix[6][5] = new Peon(jn, new Posicion(6, 5));
		this.matrix[6][6] = new Peon(jn, new Posicion(6, 6));
		this.matrix[6][7] = new Peon(jn, new Posicion(6, 7));

		this.matrix[7][0] = new Torre(jn, new Posicion(7, 0));
		this.matrix[7][1] = new Caballo(jn, new Posicion(7, 1));
		this.matrix[7][2] = new Alfil(jn, new Posicion(7, 2));
		this.matrix[7][3] = new Reina(jn, new Posicion(7, 3));
		this.matrix[7][4] = new Rey(jn, new Posicion(7, 4));
		this.matrix[7][5] = new Alfil(jn, new Posicion(7, 5));
		this.matrix[7][6] = new Caballo(jn, new Posicion(7, 6));
		this.matrix[7][7] = new Torre(jn, new Posicion(7, 7));
	}

	private int letraToNum(char pLetra) {
		//Pasa la letra (columna) a numero de columna para la matriz

		int num = 0;
		switch (pLetra) {
		case 'A':
			num = 0;
			break;
		case 'B':
			num = 1;
			break;
		case 'C':
			num = 2;
			break;
		case 'D':
			num = 3;
			break;
		case 'E':
			num = 4;
			break;
		case 'F':
			num = 5;
			break;
		case 'G':
			num = 6;
			break;
		case 'H':
			num = 7;
			break;
		case 'a':
			num = 0;
			break;
		case 'b':
			num = 1;
			break;
		case 'c':
			num = 2;
			break;
		case 'd':
			num = 3;
			break;
		case 'e':
			num = 4;
			break;
		case 'f':
			num = 5;
			break;
		case 'g':
			num = 6;
			break;
		case 'h':
			num = 7;
			break;
		default:
			System.out.println("Error de formato");
			break;
		}

		return num;
	}

	private void cambiarJugador() {
		//Cambio de turno
		if (this.turnoJugador == this.jugador1) {
			this.turnoJugador = this.jugador2;
		}else {
			this.turnoJugador = this.jugador1;
		}
	}

	private void print(){

		System.out.println();
		System.out.println();
		String linea = "	------------------------------------------------------";
		String letras = "	   A      B      C     D      E      F     G      H  ";
		System.out.println(letras);

		for (int x=0; x < matrix.length; x++) {
			if (x != 0){
				System.out.println();
			}
			System.out.println(linea);
			System.out.print("    " + (x+1) + "	|  ");
			for (int y=0; y < matrix[x].length; y++) {

				if (matrix[x][y] == null){

					String barra = " ";
					switch (y){
					case 0: 
						barra = "    |  "; break;
					case 1: 
						barra = "   |  "; break;
					case 2: 
						barra = "    |  "; break;
					case 3: 
						barra = "   |  "; break;
					case 4: 
						barra = "    |  "; break;
					case 5: 
						barra = "    |  "; break;
					case 6: 
						barra = "   |  "; break;
					case 7: 
						barra = "    |  "; break;
					}
					System.out.print(barra);

				} else{
					System.out.print(matrix[x][y].getIcono() + "  |  ");
				}
			}
		}
		System.out.println();
		System.out.println(linea);


	}

}
