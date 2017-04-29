
public class Alfil extends Ficha {

    public Alfil(Jugador pJugador, Posicion pPosicion) {
        super(pJugador, pPosicion);
    }
    public Alfil(){} //Comprobacion de movimient reina

    @Override
    public boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino) {
        boolean correcto = false;

        int pFila1 = posicionInicial.getFila();
        int pFila2 = posicionDestino.getFila();
        int pColumna1 = posicionInicial.getColumna();
        int pColumna2 = posicionDestino.getColumna();
        int columDist = pColumna2 - pColumna1;
        int filaDist = pFila2 - pFila1;

        if (Math.abs(columDist) == Math.abs(filaDist)) {
            if (this.comprobarIntermedio(pFila1, pColumna1, pFila2, pColumna2)
                    && this.posFinalCorrecta(pFila2, pColumna2))
                correcto = true;
        }
        return correcto;

    }


    public String getIcono() {
        String icono;
        if (this.getJugador().getColor() == Color.WHITE) {
            icono = "\u2657";
        } else {
            icono = "\u265D";
        }
        return icono;
    }

}
