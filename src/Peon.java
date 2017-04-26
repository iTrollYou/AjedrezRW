
public class Peon extends Ficha {

	public Peon(Jugador pJugador, Posicion pPosicion){
		super(pJugador, pPosicion);
	}

	public boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino) {

		Ficha[][] matrix = Tablero.getTablero().getMatriz();
		
		int pFila1 = posicionInicial.getFila();
		int pFila2 = posicionDestino.getFila();
		int pColumna1 = posicionInicial.getColumna();
		int pColumna2 = posicionDestino.getColumna();

		
		boolean correcto = false;
		
		int offsetX = pColumna2 - pColumna1;
		int offsetY = pFila2 - pFila1;
		int absOffsetX = Math.abs(pColumna2 - pColumna1);
		int absOffsetY = Math.abs(pFila2 - pFila1);

		
		if(pFila1 != pFila2 || pColumna1 != pColumna2) {
			
			switch (Tablero.getTablero().getTurnoJugador().getColor()) {
			case WHITE:
				if (offsetY > 0){
					if(offsetX == 0 && ((offsetY == 1)||(offsetY == 2 && pFila1 == 1))) {
						if(matrix[pFila2][pColumna2] == null) {
							correcto = true;
						}						
					} else {
						if(absOffsetX == offsetY && absOffsetX == 1) {
							if(matrix[pFila2][pColumna2] != null && matrix[pFila2][pColumna2].getJugador() != Tablero.getTablero().getTurnoJugador()) {
								correcto = true;
							}
						}
					}
				}
				break;
			case BLACK:
				if (offsetY < 0){
					if(offsetX == 0 && ((absOffsetY == 1)||(absOffsetY == 2 && pFila1 == 6))) {
						if(matrix[pFila2][pColumna2] == null) {
							correcto = true;
						}	
					} else {
						if(absOffsetX == absOffsetY && absOffsetX == 1) {
							if(matrix[pFila2][pColumna2] != null && matrix[pFila2][pColumna2].getJugador() != Tablero.getTablero().getTurnoJugador()) {
								correcto = true;
							}
						}
					}
				}
				break;
			default:
				break;
			}


		}
		
		return correcto;
	}

	public String getIcono(){
		String icono;
		if (this.getJugador().getColor() == Color.WHITE){
			icono = "\u2659";
		} else{
			icono = "\u265F";
		}
		return icono;
	}
}

